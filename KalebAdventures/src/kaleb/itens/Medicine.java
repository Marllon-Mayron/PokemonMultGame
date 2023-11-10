package kaleb.itens;

import java.awt.image.BufferedImage;

import kaleb.main.Game;

public class Medicine extends Item{
	public int cure;
	public int current;
	
	public Medicine(int idItem, String type, String name, int value, BufferedImage sprite,  int current ) {
		super(idItem, type, name, value, sprite);
		// TODO Auto-generated constructor stub
		this.name = Game.itemController.getMedicineInfo(idItem,"name");
		this.cure = Integer.parseInt(Game.itemController.getMedicineInfo(idItem,"cure"));
		this.sprite = Game.itemController.medicineList[idItem];
		this.current = current;
	}

}
