package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import kaleb.main.Game;

public class Conection extends Thread {
	public String info = "";
	public int idJogador = 0;
	public String clienteName = "";
	public String[] allNames = new String[10];
	public int[] score = new int[10];
	public boolean inGame = false;
	public int skipFase = 0;
	public Game game;
	public Conection(String ip, Game game) {
		this.game = game;
		try {
			System.out.println(ip);
			Socket conexao = new Socket(ip, 8080);
			this.clienteName = Game.multiConf.name;
			Thread tReceber = new ConectionReceber(conexao, this);
			tReceber.start();

			Thread tEnviar = new ConectionEnviar(conexao, this);
			tEnviar.start();
		} catch (IOException e) {
			System.out.println("IOException: " + e);
		}
	}

	private Socket conexao;

	public Conection(Socket s) {
		conexao = s;
	}

	public void run() {
		// Aqui pode ser deixado vazio, pois as operações estão sendo feitas em threads
		// separadas.
	}
}

class ConectionReceber extends Thread {
	private Socket conexao;
	public Conection cliente;

	public ConectionReceber(Socket s, Conection cliente) {
		conexao = s;
		this.cliente = cliente;
	}

	public void run() {
		try {
			BufferedReader entrada = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
			String linha;
			while (true) {
				linha = entrada.readLine();
				if (!(linha == "")) {
					if (cliente.inGame == false) {
						if (cliente.idJogador == 0) {
							cliente.idJogador = Integer.parseInt(linha);
						}
						if (linha.equalsIgnoreCase("RetorneNome")) {
							cliente.allNames[0] = cliente.clienteName;
							if ((cliente.allNames[1] == "" || cliente.allNames[1] == null)) {
								do {
									linha = entrada.readLine();
								} while (linha.equalsIgnoreCase("") || linha.equalsIgnoreCase(cliente.allNames[0]));
								cliente.allNames[1] = linha;
								System.out.println(cliente.allNames[0] + cliente.allNames[1]);

							} else {
								linha = "";
								continue;
							}
							cliente.info = "startGame";

						} else if (linha.equalsIgnoreCase("começar")) {
							cliente.game.gameState = "tutorial";
							cliente.inGame = true;
						}
					}
					if (linha.equalsIgnoreCase("sendScore")) {
						//ENVIAR O SCORE ATUAL NO JOGADOR
						cliente.info = "" + cliente.score[0];

					}else if (linha.equalsIgnoreCase("getScore")) {
						//PEGAR O SCORE DO JOGADOR
						do {
							linha = entrada.readLine();
						} while (linha.equalsIgnoreCase("") || linha.equalsIgnoreCase("score") || linha.equalsIgnoreCase("updateScore"));						
						cliente.score[1] = Integer.parseInt(linha);
						System.out.println("mandando " + cliente.score[1]);
					}else if (linha.equalsIgnoreCase("skip")) {
						//BTN DE SKIP
						cliente.skipFase++;
						if(cliente.skipFase == 2) {
							//QUANDO RECONHECER A QUANTIDADE NECESSARIA DE SKIP
							cliente.game.configs.userPerformance();
							cliente.game.player.nextGameLevel();
							cliente.game.gameState = "catch";
							cliente.game.ui.pcView = "pc";
							cliente.game.ui.skipPress = false;
							cliente.skipFase = 0;
						}
					}else if (linha.equalsIgnoreCase("skipBack")) {
						//QUANDO CANCELAREM O SKIP
						cliente.skipFase--;
					}

				}

			}

		} catch (IOException e) {
			System.out.println("IOException: " + e);
		}
	}
}

class ConectionEnviar extends Thread {
	private Socket conexao;
	public Conection cliente;

	public ConectionEnviar(Socket s, Conection cliente) {
		conexao = s;
		this.cliente = cliente;
	}

	public void run() {
		try {
			PrintStream saida = new PrintStream(conexao.getOutputStream());
			saida.println(cliente.clienteName);
			while (true) {
				Thread.sleep(1000);

				saida.println(cliente.info);
				if (!(cliente.info == "")) {
					// System.out.println("Mandei"+cliente.info);
				}

				cliente.info = "";
			}
		} catch (IOException | InterruptedException e) {
			System.out.println("IOException: " + e);
		}
	}
}
