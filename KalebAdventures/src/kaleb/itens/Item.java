package kaleb.itens;

import java.awt.image.BufferedImage;

import kaleb.main.Game;

public class Item {
	
	public int idItem;
	public String name;
	public int value;
	public BufferedImage sprite;
	public String type;
	public String desc;
	
	public Item(int idItem, String type ,String name, int value, BufferedImage sprite) {
		super();
		
		this.idItem = idItem;
		this.type = type;
		
		if(this.type == "pokeball") {
			this.sprite = Game.itemController.pokeballList[this.idItem];
			this.name = Game.itemController.getPokeballInfo(this.idItem, "name");
			this.value = Integer.parseInt(Game.itemController.getPokeballInfo(this.idItem, "value"));
			this.desc = Game.itemController.getPokeballInfo(this.idItem, "desc");
		}else if(this.type == "medicine") {
			this.sprite = Game.itemController.medicineList[this.idItem];
			this.name = Game.itemController.getMedicineInfo(this.idItem, "name");
			this.value = Integer.parseInt(Game.itemController.getMedicineInfo(this.idItem, "value"));
			this.desc = Game.itemController.getMedicineInfo(this.idItem, "desc");
		}else if(this.type == "battleItem") {
			this.sprite = Game.itemController.battleItemList[this.idItem];
			this.name = Game.itemController.getBattleItemInfo(this.idItem, "name");
			this.value = Integer.parseInt(Game.itemController.getBattleItemInfo(this.idItem, "value"));
			this.desc = Game.itemController.getBattleItemInfo(this.idItem, "desc");
		}else if(this.type == "evolueItem") {
			this.sprite = Game.itemController.evolueItemList[this.idItem];
			this.name = Game.itemController.getEvolutionItemInfo(this.idItem, "name");
			this.value = Integer.parseInt(Game.itemController.getEvolutionItemInfo(this.idItem, "value"));
			this.desc = Game.itemController.getEvolutionItemInfo(this.idItem, "desc");
		}
	}
	
	
	
	
}
