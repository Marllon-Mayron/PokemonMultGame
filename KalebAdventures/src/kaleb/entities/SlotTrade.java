package kaleb.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import itens.Item;
import kaleb.main.Game;

public class SlotTrade extends Entity{
	
	public BufferedImage sprite;
	public BufferedImage background;
	public boolean selected;
	public Pokemon pokemon;
	public int numberInList;
	public SlotTrade(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		// TODO Auto-generated constructor stub
		this.width = width;
		this.height = height; 
		
	}
	public void tick() {
		
		
	}
	
	
	public void renderStr(Graphics g) {
		if(Game.gameState == 2 && Game.ui.pcView == 2) {
			if(selected) {
				g.setColor(Color.green);
			}else {
				g.setColor(Color.gray);
			}
			if(pokemon != null) {

				g.fillRect((int)this.getX(), (int)this.getY(), width, height);
				g.drawImage(Game.pokedex.getSprite(pokemon.id, "front", pokemon.isShiny), (int)this.getX() - 12, (int)this.getY() - 16, null);	
			}
			
			
			
		}
	}
}
