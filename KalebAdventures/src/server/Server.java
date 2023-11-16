package server;


import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author wolmir
 */
public class Server extends Thread {

    private static Vector clientes;
    private Socket conexao;
    private String meuNome;

    public Server(Socket s) {
        conexao = s;
    }

    // Método para iniciar o servidor
    public static void startServer() {
        clientes = new Vector();

        try {
            // Criando um socket que fica escutando a porta 8080.
            ServerSocket serverSocket = new ServerSocket(8080);

            // Usando ExecutorService para executar o servidor em uma thread separada
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(() -> {
                // Loop principal.
                while (true) {
                    System.out.print("Esperando alguem se conectar...");
                    try {
                        Socket conexao = serverSocket.accept();
                        System.out.println("Jogador entrou na sala!");

                        // Cria uma nova thread para tratar essa conexão
                        Thread t = new Server(conexao);
                        t.start();
                    } catch (IOException e) {
                        System.out.println("IOException: " + e);
                    }
                }
            });

            // Continue com outras tarefas na classe principal se necessário

        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
    }

    // Execução da thread
    public void run() {
        try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            PrintStream saida = new PrintStream(conexao.getOutputStream());

            meuNome = entrada.readLine();

            conexao.close();
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
    }
}