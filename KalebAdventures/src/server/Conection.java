package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import kaleb.main.Game;

public class Conection extends Thread{
	
	public Conection(String ip) {
		try {
			Socket conexao = new Socket(ip, 8080);  
			
			Thread t = new Conection(conexao);
			t.start();
		}
		catch (IOException e) {
			System.out.println("IOException: " + e);
		}
	}
	
	private Socket conexao;
	public Conection(Socket s) {
		conexao = s;
	}
	
	public void run() {
		try {
			BufferedReader entrada = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
			System.out.println("CLIENTE ESPERANDO RESPOSTA");
			if(entrada.readLine().equalsIgnoreCase("começar")){
				Game.gameState = "tutorial";
			}
		}
		catch (IOException e) {
			System.out.println("IOException: " + e);
		}
	}
}
