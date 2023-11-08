package kaleb.entities;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import kaleb.main.Game;
import kaleb.world.Camera;

public class Locale extends Entity{

	public Pokemon pokemon;
	public String type;
	
	public Locale(int x, int y, int width, int height, BufferedImage sprite, String type) {
		super(x, y, width, height, sprite);
		// TODO Auto-generated constructor stub
		this.type = type;
		this.width = 13;
		this.height = 13;
	}

	public Pokemon getPokemon() {
		return pokemon;
	}

	public void setPokemon(Pokemon pokemon) {
		this.pokemon = pokemon;
	}
	public void renderStr(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(3));
		if(this.type.equalsIgnoreCase("null")) {
	        Color c = new Color(0, 0, 0, 128);
	        g.setColor(c);
		}else if(this.type.equalsIgnoreCase("Normal")){			
			String codigoHexadecimal = "636348";
	        int vermelho = Integer.parseInt(codigoHexadecimal.substring(0, 2), 16);
	        int verde = Integer.parseInt(codigoHexadecimal.substring(2, 4), 16);
	        int azul = Integer.parseInt(codigoHexadecimal.substring(4, 6), 16);
	        Color c = new Color(vermelho, verde, azul, 128);
	        g.setColor(c);
	        g2d.drawImage(Game.pokedex.allTypesSprites[1],(int) x*Game.SCALE - Camera.x, (int) y*Game.SCALE - Camera.y,null);
			
		}else if(this.type.equalsIgnoreCase("Fighting")){
			String codigoHexadecimal = "3b0000";
	        int vermelho = Integer.parseInt(codigoHexadecimal.substring(0, 2), 16);
	        int verde = Integer.parseInt(codigoHexadecimal.substring(2, 4), 16);
	        int azul = Integer.parseInt(codigoHexadecimal.substring(4, 6), 16);
	        Color c = new Color(vermelho, verde, azul, 128);
	        g.setColor(c);
	        g2d.drawImage(Game.pokedex.allTypesSprites[2],(int) x*Game.SCALE - Camera.x, (int) y*Game.SCALE - Camera.y,null);
			
		}else if(this.type.equalsIgnoreCase("Flying")){
			String codigoHexadecimal = "83718a";
	        int vermelho = Integer.parseInt(codigoHexadecimal.substring(0, 2), 16);
	        int verde = Integer.parseInt(codigoHexadecimal.substring(2, 4), 16);
	        int azul = Integer.parseInt(codigoHexadecimal.substring(4, 6), 16);
	        Color c = new Color(vermelho, verde, azul, 128);
	        g.setColor(c);
	        g2d.drawImage(Game.pokedex.allTypesSprites[3],(int) x*Game.SCALE - Camera.x, (int) y*Game.SCALE - Camera.y,null);
			
		}else if(this.type.equalsIgnoreCase("Poison")){
			String codigoHexadecimal = "5d2573";
	        int vermelho = Integer.parseInt(codigoHexadecimal.substring(0, 2), 16);
	        int verde = Integer.parseInt(codigoHexadecimal.substring(2, 4), 16);
	        int azul = Integer.parseInt(codigoHexadecimal.substring(4, 6), 16);
	        Color c = new Color(vermelho, verde, azul, 128);
	        g.setColor(c);
	        g2d.drawImage(Game.pokedex.allTypesSprites[4],(int) x*Game.SCALE - Camera.x, (int) y*Game.SCALE - Camera.y,null);
			
		}else if(this.type.equalsIgnoreCase("Ground")){
			String codigoHexadecimal = "d9a066";
	        int vermelho = Integer.parseInt(codigoHexadecimal.substring(0, 2), 16);
	        int verde = Integer.parseInt(codigoHexadecimal.substring(2, 4), 16);
	        int azul = Integer.parseInt(codigoHexadecimal.substring(4, 6), 16);
	        Color c = new Color(vermelho, verde, azul, 128);
	        g.setColor(c);
	        g2d.drawImage(Game.pokedex.allTypesSprites[5],(int) x*Game.SCALE - Camera.x, (int) y*Game.SCALE - Camera.y,null);
			
		}else if(this.type.equalsIgnoreCase("Rock")){
			String codigoHexadecimal = "8f563b";
	        int vermelho = Integer.parseInt(codigoHexadecimal.substring(0, 2), 16);
	        int verde = Integer.parseInt(codigoHexadecimal.substring(2, 4), 16);
	        int azul = Integer.parseInt(codigoHexadecimal.substring(4, 6), 16);
	        Color c = new Color(vermelho, verde, azul, 128);
	        g.setColor(c);
	        g2d.drawImage(Game.pokedex.allTypesSprites[6],(int) x*Game.SCALE - Camera.x, (int) y*Game.SCALE - Camera.y,null);
			
		}else if(this.type.equalsIgnoreCase("Bug")){
			String codigoHexadecimal = "a6a232";
	        int vermelho = Integer.parseInt(codigoHexadecimal.substring(0, 2), 16);
	        int verde = Integer.parseInt(codigoHexadecimal.substring(2, 4), 16);
	        int azul = Integer.parseInt(codigoHexadecimal.substring(4, 6), 16);
	        Color c = new Color(vermelho, verde, azul, 128);
	        g.setColor(c);
	        g2d.drawImage(Game.pokedex.allTypesSprites[7],(int) x*Game.SCALE - Camera.x, (int) y*Game.SCALE - Camera.y,null);
			
		}else if(this.type.equalsIgnoreCase("Ghost")){
			String codigoHexadecimal = "694f73";
	        int vermelho = Integer.parseInt(codigoHexadecimal.substring(0, 2), 16);
	        int verde = Integer.parseInt(codigoHexadecimal.substring(2, 4), 16);
	        int azul = Integer.parseInt(codigoHexadecimal.substring(4, 6), 16);
	        Color c = new Color(vermelho, verde, azul, 128);
	        g.setColor(c);
	        g2d.drawImage(Game.pokedex.allTypesSprites[8],(int) x*Game.SCALE - Camera.x, (int) y*Game.SCALE - Camera.y,null);
			
		}else if(this.type.equalsIgnoreCase("Steel")){
			String codigoHexadecimal = "9badb7";
	        int vermelho = Integer.parseInt(codigoHexadecimal.substring(0, 2), 16);
	        int verde = Integer.parseInt(codigoHexadecimal.substring(2, 4), 16);
	        int azul = Integer.parseInt(codigoHexadecimal.substring(4, 6), 16);
	        Color c = new Color(vermelho, verde, azul, 128);
	        g.setColor(c);
	        g2d.drawImage(Game.pokedex.allTypesSprites[9],(int) x*Game.SCALE - Camera.x, (int) y*Game.SCALE - Camera.y,null);
			
		}else if(this.type.equalsIgnoreCase("Fire")){
			String codigoHexadecimal = "df7126";
	        int vermelho = Integer.parseInt(codigoHexadecimal.substring(0, 2), 16);
	        int verde = Integer.parseInt(codigoHexadecimal.substring(2, 4), 16);
	        int azul = Integer.parseInt(codigoHexadecimal.substring(4, 6), 16);
	        Color c = new Color(vermelho, verde, azul);
	        g.setColor(c);
	        g2d.drawImage(Game.pokedex.allTypesSprites[10],(int) x*Game.SCALE - Camera.x, (int) y*Game.SCALE - Camera.y,null);
			
		}else if(this.type.equalsIgnoreCase("Water")){
			String codigoHexadecimal = "639bff";
	        int vermelho = Integer.parseInt(codigoHexadecimal.substring(0, 2), 16);
	        int verde = Integer.parseInt(codigoHexadecimal.substring(2, 4), 16);
	        int azul = Integer.parseInt(codigoHexadecimal.substring(4, 6), 16);
	        Color c = new Color(vermelho, verde, azul, 128);
	        g.setColor(c);
	        g2d.drawImage(Game.pokedex.allTypesSprites[11],(int) x*Game.SCALE - Camera.x, (int) y*Game.SCALE - Camera.y,null);
			
		}else if(this.type.equalsIgnoreCase("Grass")){
			String codigoHexadecimal = "6abe30";
	        int vermelho = Integer.parseInt(codigoHexadecimal.substring(0, 2), 16);
	        int verde = Integer.parseInt(codigoHexadecimal.substring(2, 4), 16);
	        int azul = Integer.parseInt(codigoHexadecimal.substring(4, 6), 16);
	        Color c = new Color(vermelho, verde, azul, 128);
	        g.setColor(c);
	        g2d.drawImage(Game.pokedex.allTypesSprites[12],(int) x*Game.SCALE - Camera.x, (int) y*Game.SCALE - Camera.y,null);
			
		}else if(this.type.equalsIgnoreCase("Electric")){
			String codigoHexadecimal = "fbf236";
	        int vermelho = Integer.parseInt(codigoHexadecimal.substring(0, 2), 16);
	        int verde = Integer.parseInt(codigoHexadecimal.substring(2, 4), 16);
	        int azul = Integer.parseInt(codigoHexadecimal.substring(4, 6), 16);
	        Color c = new Color(vermelho, verde, azul, 128);
	        g.setColor(c);
	        g2d.drawImage(Game.pokedex.allTypesSprites[13],(int) x*Game.SCALE - Camera.x, (int) y*Game.SCALE - Camera.y,null);
			
		}else if(this.type.equalsIgnoreCase("Psychic")){
			String codigoHexadecimal = "d95763";
	        int vermelho = Integer.parseInt(codigoHexadecimal.substring(0, 2), 16);
	        int verde = Integer.parseInt(codigoHexadecimal.substring(2, 4), 16);
	        int azul = Integer.parseInt(codigoHexadecimal.substring(4, 6), 16);
	        Color c = new Color(vermelho, verde, azul, 128);
	        g.setColor(c);
	        g2d.drawImage(Game.pokedex.allTypesSprites[14],(int) x*Game.SCALE - Camera.x, (int) y*Game.SCALE - Camera.y,null);
			
		}else if(this.type.equalsIgnoreCase("Ice")){
			String codigoHexadecimal = "b6cdfc";
	        int vermelho = Integer.parseInt(codigoHexadecimal.substring(0, 2), 16);
	        int verde = Integer.parseInt(codigoHexadecimal.substring(2, 4), 16);
	        int azul = Integer.parseInt(codigoHexadecimal.substring(4, 6), 16);
	        Color c = new Color(vermelho, verde, azul, 128);
	        g.setColor(c);
	        g2d.drawImage(Game.pokedex.allTypesSprites[15],(int) x*Game.SCALE - Camera.x, (int) y*Game.SCALE - Camera.y,null);
			
		}else if(this.type.equalsIgnoreCase("Dragon")){
			String codigoHexadecimal = "24265c";
	        int vermelho = Integer.parseInt(codigoHexadecimal.substring(0, 2), 16);
	        int verde = Integer.parseInt(codigoHexadecimal.substring(2, 4), 16);
	        int azul = Integer.parseInt(codigoHexadecimal.substring(4, 6), 16);
	        Color c = new Color(vermelho, verde, azul, 128);
	        g.setColor(c);
	        g2d.drawImage(Game.pokedex.allTypesSprites[16],(int) x*Game.SCALE - Camera.x, (int) y*Game.SCALE - Camera.y,null);
			
		}else if(this.type.equalsIgnoreCase("Dark")){
			String codigoHexadecimal = "453521";
	        int vermelho = Integer.parseInt(codigoHexadecimal.substring(0, 2), 16);
	        int verde = Integer.parseInt(codigoHexadecimal.substring(2, 4), 16);
	        int azul = Integer.parseInt(codigoHexadecimal.substring(4, 6), 16);
	        Color c = new Color(vermelho, verde, azul, 128);
	        g.setColor(c);
	        g2d.drawImage(Game.pokedex.allTypesSprites[17],(int) x*Game.SCALE - Camera.x, (int) y*Game.SCALE - Camera.y,null);
			
		}else if(this.type.equalsIgnoreCase("Fairy")){
			String codigoHexadecimal = "d77bba";
	        int vermelho = Integer.parseInt(codigoHexadecimal.substring(0, 2), 16);
	        int verde = Integer.parseInt(codigoHexadecimal.substring(2, 4), 16);
	        int azul = Integer.parseInt(codigoHexadecimal.substring(4, 6), 16);
	        Color c = new Color(vermelho, verde, azul, 128);
	        g.setColor(c);
	        g2d.drawImage(Game.pokedex.allTypesSprites[18],(int) x*Game.SCALE - Camera.x, (int) y*Game.SCALE - Camera.y,null);
			
		}
		
		g2d.drawRect((int) x*Game.SCALE - Camera.x, (int) y*Game.SCALE - Camera.y, width * Game.SCALE, height*Game.SCALE);
		
	}
	
	

}
