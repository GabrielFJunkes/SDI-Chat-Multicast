package server;

public class Server {
    public static void main(String[] argv) throws Exception {
        /*
         * Objeto server
         * - RecursosEmUso
         * - RecursosTotal (100 ou 200)
         * - Tarefas (vetor de tarefas com seu tempo de finalização)
         * -- loop q verifica se tempo atual é maior q o tempo de
         *    finalizacao e libera recursos e retira do vetor se for
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
    }
}