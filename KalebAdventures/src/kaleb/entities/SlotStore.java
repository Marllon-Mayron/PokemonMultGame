package kaleb.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import kaleb.itens.Item;
import kaleb.main.Game;

public class SlotStore extends Entity{
	
	public BufferedImage sprite;
	public BufferedImage background;
	public boolean selected;
	public Item item;
	public SlotStore(int x, int y, int width, int height, BufferedImage sprite, Item item) {
		super(x, y, width, height, sprite);
		// TODO Auto-generated constructor stub
		this.width = width;
		this.height = height; 
		
	}
	public void tick() {
		if(Game.gameState == 2 && Game.ui.pcView == 1) {
			if(Entity.isColidding2(Game.player, this)) {
				selected = true;
			}else {
				selected = false;
			}
		}
		
	}
	
	
	public void renderStr(Graphics g) {
		if(Game.gameState == 2 && Game.ui.pcView == 1) {
			if(selected) {
				g.setColor(Color.green);
			}else {
				g.setColor(Color.black);
			}
			g.drawRect((int)this.getX(), (int)this.getY(), width, height);
			if(item != null) {
				g.drawImage(item.sprite, (int)this.getX() - 4, (int)this.getY() - 4, null);	
				g.drawString(item.name, (int)this.getX(), (int)this.getY() - 2);
			}
			
			
			
		}
	}
}
