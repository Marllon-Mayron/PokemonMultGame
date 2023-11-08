package kaleb.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

import kaleb.graphics.Spritesheet;
import kaleb.main.Game;
import kaleb.world.Camera;


public class Npc extends Entity{
	public int idOwner = 1;
	public int stepRoute;
	public String routes[];;
	public String dir = "DOWN";
	private int frames = 0, maxFrames = 8, index = 0, maxIndex = 3;
	private BufferedImage[] right;
	private BufferedImage[] left;
	private BufferedImage[] up;
	private BufferedImage[] down;
	public  Spritesheet spritesheet;
	public List<Pokemon> pokemon;
	public double spd;
	public Npc(int x, int y, int width, int height, BufferedImage sprite, int idOwner, List<Pokemon> pokemonTempList) {
		super(x, y, width, height, sprite);
		// TODO Auto-generated constructor stub
		this.idOwner = idOwner;
		this.pokemon = pokemonTempList;
		right = new BufferedImage[4];
		left = new BufferedImage[4];
		up = new BufferedImage[4];
		down = new BufferedImage[4];
		spritesheet = new Spritesheet("/PokemonSprites/Npc/" +"NPC_"+ this.idOwner + ".png");
		this.routes = pokemonTempList.get(0).routes;
		for (int i = 0; i < 4; i++) {
			up[i] = spritesheet.getSprite(0 + (i * 64), 192, 64, 64);
			down[i] = spritesheet.getSprite(0 + (i * 64),0, 64, 64);
			right[i] = spritesheet.getSprite(0 + (i * 64), 128, 64, 64);
			left[i] = spritesheet.getSprite(0 + (i * 64), 64, 64, 64);	
		}
		
	}
	public void tick() {
		if (getX() <= 1 || getX() > Game.lvlConfig.get(0).mapSize[Game.currentLvl][0] -1 || y < 1 || y > Game.lvlConfig.get(0).mapSize[Game.currentLvl][1]-1) {
			Game.lvlConfig.get(Game.currentLvl).totalDefeat++;
			this.DestroySelf(this);	
		}	
		routeController();
		frames++;
		if (frames == maxFrames) {
			frames = 0;
			index++;
			if (index > maxIndex) {
				index = 0;
			}
		}
	}
	private void routeController() {
		String dirRoute[] = routes[stepRoute].split("-");
		if (dirRoute[0].equalsIgnoreCase("L")) {
			dir = "LEFT";
			setX(getX() - spd);
			if (getX() <= Integer.parseInt(dirRoute[1])) {
				stepRoute++;
				
			}
		} else if (dirRoute[0].equalsIgnoreCase("R")) {
			dir = "RIGHT";
			setX(getX() + spd);
			if (getX() >= Integer.parseInt(dirRoute[1])) {
				stepRoute++;
			}
		} else if (dirRoute[0].equalsIgnoreCase("U")) {
			dir = "UP";
			setY(getY() - spd);
			if (y <= Integer.parseInt(dirRoute[1])) {
				stepRoute++;
			}
		} else if (dirRoute[0].equalsIgnoreCase("D")) {
			dir = "DOWN";
			setY(getY() + spd);
			if (y >= Integer.parseInt(dirRoute[1])) {
				stepRoute++;
			}
		}
	}
	public void render(Graphics g) {
		if (dir.equalsIgnoreCase("UP")) {
			g.drawImage(up[index], (int)getX()*Game.SCALE -32 -Camera.x, (int)y*Game.SCALE -32 -Camera.y, null);
		} else if (dir.equalsIgnoreCase("DOWN")) {
			g.drawImage(down[index], (int)getX()*Game.SCALE -32 -Camera.x, (int)y*Game.SCALE -32 -Camera.y, null);
		} else if (dir.equalsIgnoreCase("LEFT")) {
			g.drawImage(left[index], (int)getX()*Game.SCALE -32 -Camera.x, (int)y*Game.SCALE -32 -Camera.y, null);
		} else if (dir.equalsIgnoreCase("RIGHT")) {
			g.drawImage(right[index], (int)getX()*Game.SCALE -32 -Camera.x, (int)y*Game.SCALE -32 -Camera.y, null);
		} 
	}

}
