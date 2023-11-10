package kaleb.itens;

import java.awt.image.BufferedImage;

import kaleb.main.Game;

public class BattleItem extends Item{
	public int current;
	public int duration;
	public BattleItem(int idItem, String type, String name, int value, BufferedImage sprite,  int current ) {
		super(idItem, type, name, value, sprite);
		// TODO Auto-generated constructor stub
		this.name = Game.itemController.getBattleItemInfo(idItem,"name");
		this.sprite = Game.itemController.battleItemList[idItem];
		this.duration = Integer.parseInt(Game.itemController.getBattleItemInfo(idItem,"duration"));
		this.current = current;
	}

}
