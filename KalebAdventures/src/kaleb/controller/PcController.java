package kaleb.controller;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import kaleb.entities.Entity;
import kaleb.entities.SlotPc;
import kaleb.entities.SlotStore;
import kaleb.main.Game;

public class PcController extends Entity{
	public PcController(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		// TODO Auto-generated constructor stub
		creatPc();
		createStore();
		
		
	}
	public void creatPc() {
		for(int i = 0; i < 3; i++) {
			for(int i2 = 0; i2 < 6; i2++) {
				SlotPc sp = new SlotPc((Game.WIDTH*6/100 * Game.SCALE )/2 + i2*(Game.WIDTH*12/100)*Game.SCALE,(Game.HEIGHT*44/100 * Game.SCALE)/2 + i * (Game.HEIGHT*18/100)*Game.SCALE, (Game.WIDTH*10/100)*Game.SCALE,70, null, null);				
				Game.slotPcList.add(sp);
				
			}

		}
	}
	public void createStore() {
		for(int i = 0; i < 4; i++) {
			for(int i2 = 0; i2 < 2; i2++) {
				SlotStore ss = new SlotStore((int)(Game.WIDTH*28.5/100 * Game.SCALE ) + i*(Game.WIDTH*12/100)*Game.SCALE, (Game.HEIGHT*59/100 * Game.SCALE)/2 + i2 * (Game.HEIGHT*20/100)*Game.SCALE, (Game.WIDTH*8/100)*Game.SCALE, (Game.WIDTH*8/100)*Game.SCALE, null, null);
				Game.storeController.slotStoreList.add(ss);
			}
		}
	}
}
