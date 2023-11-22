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

    private static Vector<PrintStream> clientes;
    private Socket conexao;
    private String meuNome;
    public String msgLog;
    public static int totalJogadores = 0, qnt;
    public boolean startedGame;
    public boolean sendTotal = false;
    public boolean debug = true;
    public Server(Socket s) {
        conexao = s;
    }

    // Método para iniciar o servidor
    public static void startServer() {
        clientes = new Vector<PrintStream>();

        try {
            // Criando um socket que fica escutando a porta 8080.
            ServerSocket serverSocket = new ServerSocket(8080);

            // Usando ExecutorService para executar o servidor em uma thread separada
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(() -> {
                // Loop principal.
                while (true) {
                    //System.out.print("Esperando alguem se conectar...");
                    try {
                        Socket conexao = serverSocket.accept();
                        //System.out.println("Jogador entrou na sala!");
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
			
			meuNome = linha;
						msgLog = meuNome+" entrou no game!";
			debugServer();
			
			while(true) {
				linha = entrada.readLine();
				if(startedGame == false) {
					if(totalJogadores >= 2) {
						if(linha.equalsIgnoreCase("start")) {
							sendAll("preparationStart");
						}
						if(linha.equalsIgnoreCase("playPreparation")) {
							//ENVIAR QUANTIDADE EXATA DE JOGADORES NA PARTIDA
							if(sendTotal == false) {
								sendToMe(saida,"totalJogadores");
								sendToMe(saida,""+totalJogadores);
								
								sendToMe(saida,"RetorneNome");
								sendToMe(saida, meuNome);
								sendTotal = true;
							}
							//===============================================//
							
							
							
						}else if(linha.equalsIgnoreCase("startGame")) {
							//COMEÇAR O JOGO
							msgLog = meuNome+" começou a jogar!";
							debugServer();
							
							sendToAll(saida,"RetorneNome");
						    sendToAll(saida, meuNome);

						}else if(linha.equalsIgnoreCase("startGame2")) {
							
							 sendToAll(saida,"RetorneNome");
							 sendToAll(saida, meuNome);
							 sendAll("começar");
						}
						
					}
				}
				if(linha.equalsIgnoreCase("updateScore")) {
					//=========================================
					
					sendToMe(saida,"sendScore");
					do {
						msgLog = "Esperando score...";
						debugServer();
						
						linha = entrada.readLine();
					}while(linha.equalsIgnoreCase(""));
					String score = linha;
					
					//=========================================
					
					sendToMe(saida,"sendNome");
					do {
						msgLog = "Esperando nome...";
						debugServer();
						
						linha = entrada.readLine();
					}while(linha.equalsIgnoreCase(""));
					String nome = linha;
					
					//=========================================
					
					sendToAll(saida, "getScore");
					sendToAll(saida,score);
					sendToAll(saida,nome);
					
				}
				if(linha.equalsIgnoreCase("skipPreparation")) {
					sendToAll(saida,"skip");
					sendToMe(saida,"skip");
				}else if(linha.equalsIgnoreCase("skipBack")) {
					sendToAll(saida,"skipBack");
					sendToMe(saida,"skipBack");
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
	public void debugServer() {
		if(debug) {
			System.out.println("Servidor: "+msgLog);
		}
		
	}
}