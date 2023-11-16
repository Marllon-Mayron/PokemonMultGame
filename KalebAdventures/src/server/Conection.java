package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Conection extends Thread{
	public Conection() {
		try {
			Socket conexao = new Socket("26.105.29.162", 8080);           
		}
		catch (IOException e) {
			System.out.println("IOException: " + e);
		}
	}
}
