package kaleb.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import kaleb.main.Game;

public class SlotPc extends Entity{
	
	public BufferedImage sprite;
	public BufferedImage background;
	public boolean slotPrincipal;
	public boolean selected;
	public Pokemon pokemon;
	public String num = "";
	public SlotPc(int x, int y, int width, int height, BufferedImage sprite, Pokemon pokemon) {
		super(x, y, width, height, sprite);
		// TODO Auto-generated constructor stub
		this.width =(Game.WIDTH*10/100)*Game.SCALE;
		this.height = 70;
		this.background = Game.ui.ballIcon;      
	}
	public void tick() {
		if(Game.gameState.equalsIgnoreCase("user_view") && Game.ui.pcView.equalsIgnoreCase("pc")) {
			for(int i = 0; i < Game.slotList.size(); i++) {
				if(Game.slotList.get(i).pokemon != null && Game.slotList.get(i).pokemon.equals(pokemon)) {
					slotPrincipal = true;
				}
				
			}
			if(Entity.isColidding2(Game.player, this)) {
				selected = true;
			}else {
				selected = false;
			}
		}
		
	}
	
	
	public void renderStr(Graphics g) {
		if(Game.gameState.equalsIgnoreCase("user_view") && Game.ui.pcView.equalsIgnoreCase("pc")) {
			if(slotPrincipal) {
				g.setColor(Color.darkGray);
				g.fillRect((int)this.x, (int)this.y,(Game.WIDTH*10/100)*Game.SCALE, 70);				
			}
			for(int i = 0; i < Game.entities.size(); i++) {
				if(pokemon == null) {
					continue;
				}
				Entity e = Game.entities.get(i);
				if(e instanceof Player && slotPrincipal == false) {
					if(Entity.isColidding2(e, this)) {
						if(Game.player.draged) {
							Game.player.pokemonDragged = pokemon;
							Game.player.spriteDesc = "pokemon";
							Game.player.sprite = Game.pokedex.getSprite(pokemon.id, "front", pokemon.isShiny);							
						}
						
					}
				}
			}
			
			if(selected) {
				g.setColor(Color.green);
			}else {
				g.setColor(Color.black);
			}
			g.drawRect((int)this.getX(), (int)this.getY(), width, height);
			if(this.pokemon != null) {
				for(int i = 0; i < Game.pokeList.size(); i++) {
					if(Game.pokeList.get(i).equals(this.pokemon)) {
						num = "" + (i+1);
						break;
					}				
				}
			}
			
			g.drawString(num,(int)this.getX(), (int)this.getY());
			g.drawImage(background, (int)this.getX() + 5, (int)this.getY() + 4, null);
			if(pokemon != null  ) {
				g.drawImage(Game.pokedex.getSprite(pokemon.id,"front", pokemon.isShiny), (int)this.getX() - 8, (int)this.getY() - 8, null);	
			}
			
		}
	}
}
