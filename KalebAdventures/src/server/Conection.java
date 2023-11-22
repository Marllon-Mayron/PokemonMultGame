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
	public String[] allNames ;
	public int[] score;
	public boolean inGame = false;
	public int skipFase = 0;
	public int totalJogadores, cn = 0;
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
						if (linha.equalsIgnoreCase("totalJogadores")) {
							do {
								linha = entrada.readLine();
							} while (linha.equalsIgnoreCase(""));
							
							cliente.totalJogadores = Integer.parseInt(linha);
							cliente.allNames = new String[cliente.totalJogadores];
							cliente.score = new int[cliente.totalJogadores];
							
						}
						if (linha.equalsIgnoreCase("preparationStart")) {
							cliente.info = "playPreparation";
						}
						if (linha.equalsIgnoreCase("RetorneNome")) {
							do {
								linha = entrada.readLine();
							} while (linha.equalsIgnoreCase(""));
							cliente.allNames[cliente.cn] = linha;
							System.out.println("nm"+cliente.cn+" ADICIONADO: "+linha);
							cliente.cn++;
							cliente.info = "startGame";
							
							if(cliente.idJogador == 1) {
								cliente.info = "startGame2";
							}

						} else if (linha.equalsIgnoreCase("começar")) {
							cliente.game.gameState = "tutorial";
							cliente.inGame = true;
						}
					}
					if (linha.equalsIgnoreCase("sendScore")) {
						//ENVIAR O SCORE ATUAL NO JOGADOR
						cliente.info = "" + cliente.score[0];

					}else if (linha.equalsIgnoreCase("sendNome")) {
						//ENVIAR O SCORE ATUAL NO JOGADOR
						cliente.info = cliente.allNames[0];

					}else if (linha.equalsIgnoreCase("getScore")) {
						//PEGAR O SCORE DO JOGADOR
						do {
							linha = entrada.readLine();
						} while (linha.equalsIgnoreCase("") || linha.equalsIgnoreCase("score") || linha.equalsIgnoreCase("updateScore"));						
						
						int score = Integer.parseInt(linha);
						
						do {
							linha = entrada.readLine();
						} while (linha.equalsIgnoreCase("") || linha.equalsIgnoreCase("score") || linha.equalsIgnoreCase("updateScore"));						
						
						for(int i = 0; i < cliente.totalJogadores; i++) {
							if(cliente.allNames[i].equalsIgnoreCase(linha)) {
								cliente.score[i] = score;
							}
						}
					
					}else if (linha.equalsIgnoreCase("skip")) {
						//BTN DE SKIP
						cliente.skipFase++;
						if(cliente.skipFase == cliente.totalJogadores) {
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
				if(cliente.info != "") {
					saida.println(cliente.info);
					// System.out.println("Mandei"+cliente.info);
					cliente.info = "";
				}

			}
		} catch (IOException | InterruptedException e) {
			System.out.println("IOException: " + e);
		}
	}
}
