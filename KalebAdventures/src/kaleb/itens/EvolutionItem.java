package kaleb.itens;

import java.awt.image.BufferedImage;

import kaleb.main.Game;

public class EvolutionItem extends Item{
	public int current;
	public EvolutionItem(int idItem, String type, String name, int value, BufferedImage sprite, int current) {
		super(idItem, type, name, value, sprite);
		this.name = Game.itemController.getEvolutionItemInfo(idItem,"name");
		this.sprite = Game.itemController.evolueItemList[idItem];
		this.current = current;
	}

}
