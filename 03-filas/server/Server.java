package server;
import java.util.concurrent.TimeUnit;
import java.io.IOException;
import java.util.Random;
import utils.Message;
import com.rabbitmq.client.*;
import java.util.ArrayList;//vetor

public class Server {
    //filas
    private final static String LOW_PRIOR_QUEUE = "filinha";
    private final static String HIGH_PRIOR_QUEUE = "filhona";

    //recursos
    private int recursos_totais;
    private int recursos_usados = 0;

    //variaveis
    private boolean espera = false;
    ArrayList<Integer> vetor_tasks = new ArrayList<>();

    public static int get_Recursos_totais(Server instance) {
        return instance.recursos_totais;
    }

    public static int get_Recursos_usados(Server instance) {
        return instance.recursos_usados;
    }

    public static void set_Recursos_usados(Server instance, int recursos,  int codOp) {
        if(codOp==0){//recursos sendo usados
            instance.recursos_usados = instance.recursos_usados + recursos;
        }else{//recursos sendo liberados
            instance.recursos_usados = instance.recursos_usados - recursos;
        }
    }

    public static boolean get_Espera(Server instance) {
        return instance.espera;
    }

    public static void set_Espera(Server instance, int codOp) {
        if(codOp==0){//tem recursos disponivel, continua
            instance.espera = false;
        }else{//não tem recursos disponivel, loop
            instance.espera = true;
        }
    }

    public static void main(String[] argv) throws Exception {
        
        /*
         * Objeto server
         * - RecursosEmUso //falta retirar o recurso em uso
         * - RecursosTotal (100 ou 200) // feito
         * - Tarefas (vetor de tarefas com seu tempo de finalização)
         * -- loop q verifica se tempo atual é maior q o tempo de
         *    finalizacao e libera recursos e retira do vetor se for (feito sem a parte do vetor)
         * 
         * Tenta pegar msg de alta prioridade, se n tiver pega baixa
         * Pega mensagem (atribui um id pra ela (incrimental)), chama .execute
         * execute da erro se não couber, entao tenta dnv ate conseguir
         * - quando pega msg, chama o logger
         * 
         * Objeto logger
         * vetor de tarefas (que seu id é o index no vetor)
         * Metodos:
         * - adicionar tarefa (que pega o tempo atual e ja usa como recebimento)
         * - começar tarefa (adiciona o tempo atual como tempo de inicio)
         * 
         * Exemplo de item do vetor de tarefas:
         * {Mensagem, tempoRecebimento, tempoInicio}
         * 
         * No fim do programa, criar arquivo com os logs
         */

        // Criando uma instância do server
        Server server = new Server();

        //definindo sua capacidade
        Random rand = new Random();
        int codRec = rand.nextInt(2);
        if(rand.nextInt(2) == 0){//escolhe a capacidade
            server.recursos_totais = 200;
        }else{
            server.recursos_totais = 100;
        }
        
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel1 = connection.createChannel();
        Channel channel2 = connection.createChannel();

        channel1.queueDeclare(LOW_PRIOR_QUEUE, false, false, false, null);
        channel2.queueDeclare(HIGH_PRIOR_QUEUE, false, false, false, null);
        System.out.println(" Capacidade do server = " + get_Recursos_totais(server) + ". To exit press CTRL+C");

        //alta prioridade
        Consumer consumer2 = new DefaultConsumer(channel2) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                    byte[] body)
                    throws IOException {
                try{
                    Message message = Message.fromBytes(body);
                    do{
                        if(server.get_Recursos_totais(server) - ( server.get_Recursos_usados(server) + message.getResource() ) >0){
                            server.set_Recursos_usados(server, message.getResource(), 0);
                            System.out.println(" high priority: (" + message + "). Using: " + message.getResource() + " resources");
                            server.set_Espera(server, 0);
                        }
                        else{
                            try {
                                TimeUnit.SECONDS.sleep(1);//dorme por 1 segundo, esperando liberar recurso
                                server.set_Espera(server, 1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }while(server.get_Espera(server));
                    
                    
                }catch(IOException | ClassNotFoundException e){
                }
            }
        };
        channel2.basicConsume(HIGH_PRIOR_QUEUE, true, consumer2);
        //aqui que tira do vetor

        //baixa prioridade
        Consumer consumer1 = new DefaultConsumer(channel1) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                    byte[] body)
                    throws IOException {
                try{
                    Message message = Message.fromBytes(body) ;
                    System.out.println(" low priority: (" + message + "). Using: " + message.getResource() + " resources");
                }catch(IOException | ClassNotFoundException e){

                }
            }
        };
        channel1.basicConsume(LOW_PRIOR_QUEUE, true, consumer1);
        
    }
}
