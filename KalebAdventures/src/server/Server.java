package server;


import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kaleb.main.Game;

/**
 *
 * @author wolmir
 */
public class Server extends Thread {

    private static Vector clientes;
    private Socket conexao;
    private String meuNome;
    public static int totalJogadores = 0;
    public static String[] allNames = new String[2];
    public static int[] score = new int[2];
    public boolean startedGame;
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
			String linha;
			clientes.add(saida);
			sendToMe(saida,""+totalJogadores);
				do {
					linha = entrada.readLine();
				}while(linha.equalsIgnoreCase(""));
				System.out.println("Servidor reconheceu o "+linha);
				meuNome = linha;
			
			while(true) {
				linha = entrada.readLine();
				if(startedGame == false) {
					if(totalJogadores == 2) {
						if(linha.equalsIgnoreCase("play")) {
							//PREENCHER LISTA DE JOGADORES DOS CLIENTES
							Enumeration e = clientes.elements();
							int n = 0;
							while (e.hasMoreElements()) {
								System.out.println("foi");
								allNames[n] = meuNome;
								n++;
							}
							sendAll("nomeJogador");
							for(int i = 0; i < 2; i++) {
								sendAll(allNames[i]);
							}
							//COMEÇAR O JOGO
							System.out.println("JOGO COMEÇOU");
							sendAll("começar");	
							startedGame = true;
						}
					}
				}
				//System.out.println("SERVIDOR RECEBEU: "+ linha);
				if(linha.equalsIgnoreCase("")) {
					continue;
				}else {
					if(linha.equalsIgnoreCase("scoreUpdate")) {
						score[Integer.parseInt(entrada.readLine())] = Integer.parseInt(entrada.readLine());
					}
				}
				
			}
			
			
		}
		catch (IOException e) {
			System.out.println("IOException: " + e);
		}
	}
	public void sendToAll(PrintStream saida, String linha) throws IOException {
		Enumeration e = clientes.elements();
		while (e.hasMoreElements()) {
			PrintStream chat = (PrintStream) e.nextElement();
			if (chat != saida) {
				chat.println(linha);
			}
		}
	}
	public void sendToMe(PrintStream saida, String linha) throws IOException {
		Enumeration e = clientes.elements();
		while (e.hasMoreElements()) {
			PrintStream chat = (PrintStream) e.nextElement();
			if (chat == saida) {
				chat.println(linha);
			}
		}
	}
	public void sendAll(String linha) throws IOException {
		Enumeration e = clientes.elements();
		while (e.hasMoreElements()) {
			PrintStream chat = (PrintStream) e.nextElement();
			chat.println(linha);
			
		}
	}
}