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
    public String clienteName ="";
    public String[] allNames = new String[2];

    public Conection(String ip) {
        try {
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
        // Aqui pode ser deixado vazio, pois as operações estão sendo feitas em threads separadas.
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
                if(cliente.idJogador == 0) {
                	cliente.idJogador = Integer.parseInt(linha);
                }
                if (linha.equalsIgnoreCase("nomeJogador")) {
                	for(int i = 0; i < 2; i++) {
                		cliente.allNames[i] = entrada.readLine();
                		System.out.println("adicionado "+cliente.allNames[i]);
                	}
                }else  if (linha.equalsIgnoreCase("começar")) {
                	
                    Game.gameState = "tutorial";
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
                //System.out.println("Enviei"+cliente.info);
                cliente.info = "";
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("IOException: " + e);
        }
    }
}
