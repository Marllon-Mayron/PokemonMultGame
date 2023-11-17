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
    public static int totalJogadores = 0;
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
                        totalJogadores++;

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

    public void run() {
		try {

			BufferedReader entrada = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
			PrintStream saida = new PrintStream(conexao.getOutputStream());

			meuNome = entrada.readLine();
			
			if (meuNome == null) {return;}
			clientes.add(saida);
			
			String linha = entrada.readLine();
			
			while (linha != null && !(linha.trim().equals(""))) {
				sendToAll(saida, " disse: ", linha);
				linha = entrada.readLine();
			}

			sendToAll(saida, " saiu ", "do chat!");
			clientes.remove(saida);
			conexao.close();
		}
		catch (IOException e) {
			System.out.println("IOException: " + e);
		}
	}
	public void sendToAll(PrintStream saida, String acao, String linha) throws IOException {
		Enumeration e = clientes.elements();
		while (e.hasMoreElements()) {
			PrintStream chat = (PrintStream) e.nextElement();
			if (chat != saida) {
				chat.println(meuNome + acao + linha);
			}
		}
	}
	public void sendAll(PrintStream saida, String acao, String linha) throws IOException {
		Enumeration e = clientes.elements();
		while (e.hasMoreElements()) {
			PrintStream chat = (PrintStream) e.nextElement();
			chat.println(meuNome + acao + linha);
			
		}
	}
}