package itens;

import java.awt.image.BufferedImage;

import kaleb.main.Game;

public class Pokeball extends Item{

	public double catchRate;
	public int current;
	
	public Pokeball(int idItem,String type, String name, int value, BufferedImage sprite, int current ) {
		super(idItem, name, type, value, sprite);
		// TODO Auto-generated constructor stub
		this.name = Game.itemController.getPokeballInfo(idItem,"name");
		catchRate = Integer.parseInt(Game.itemController.getPokeballInfo(idItem,"catchRate"));
		this.sprite = Game.itemController.pokeballList[idItem];
		this.current = current;
	}
	

}
