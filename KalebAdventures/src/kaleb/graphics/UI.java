package kaleb.graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import kaleb.entities.Player;
import kaleb.entities.Pokemon;
import kaleb.itens.Item;
import kaleb.main.Game;
import kaleb.world.Camera;

public class UI {

	public int startSelect = 0;
	public Spritesheet sprite;
	public BufferedImage ballIcon;
	public BufferedImage pOak;
	public BufferedImage marill;
	public int pcPage = 1;
	public BufferedImage leftArrow, rightArrow, dice, btn;
	public BufferedImage[] starters = new BufferedImage[3];
	public Pokemon pokemonPcDetails;
	
	public List<Pokemon> pkmOffer = new ArrayList<Pokemon>();
	public Item itemDetails;
	public String pcView = "pc";
	public int nDice = 2;
	public int nColumn;
	public int nRow;
	private BufferedImage[] down;
	public Spritesheet spritesheet;
	private int frames = 0, maxFrames = 8, index = 0, maxIndex = 3;

	public UI() {
		sprite = new Spritesheet("/PokemonSprites/ballIcon.png");
		ballIcon = sprite.getSprite(0, 0, 64, 64);
		sprite = new Spritesheet("/PokemonSprites/pokeball/1.png");
		sprite = new Spritesheet("/introOak.png");
		pOak = sprite.getSprite(0, 0, 110, 172);
		sprite = new Spritesheet("/introMarill.png");
		marill = sprite.getSprite(0, 0, 80, 76);

		sprite = new Spritesheet("/huds.png");
		leftArrow = sprite.getSprite(0, 0, 64, 48);
		rightArrow = sprite.getSprite(64, 0, 64, 48);
		btn = sprite.getSprite(0, 48, 80, 48);
		sprite = new Spritesheet("/dice.png");
		dice = sprite.getSprite(0, 0, 38, 38);
		starters[0] = Game.pokedex.getSprite(1, "front", false);
		starters[1] = Game.pokedex.getSprite(4, "front", false);
		starters[2] = Game.pokedex.getSprite(7, "front", false);

		down = new BufferedImage[4];

	}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(3));
		Font fonte = new Font("Arial", Font.BOLD, 12); // Fonte Arial, negrito, tamanho 24
		g.setFont(fonte);
		g.setColor(Color.gray);

		tutorialRender(g);
		if(!pcView.equalsIgnoreCase("trade")) {
			if (Game.configs.activeHud == true) {
				for (int i = 0; i < 6; i++) {
					if (Game.slotList.get(i).getSlotStatus().equalsIgnoreCase("free")) {
						g.setColor(Color.gray);
					} else if (Game.slotList.get(i).getSlotStatus().equalsIgnoreCase("inMap")) {
						g.setColor(Color.blue);
					} else if (Game.slotList.get(i).getSlotStatus().equalsIgnoreCase("defeat")){
						g.setColor(Color.red);
					}

					g.fillRect((Game.WIDTH * 14 / 100) * Game.SCALE + (Game.WIDTH * ((i) * 10) / 100) * Game.SCALE+ (i + 1) * (Game.WIDTH * 2 / 100) * Game.SCALE,((Game.HEIGHT * 81 / 100) * Game.SCALE) + 2, (Game.WIDTH * 10 / 100) * Game.SCALE, 70);
					g.setColor(Color.black);

					if (Game.slotList.get(i).isSelected) {
						g.setColor(Color.green);
					}
					g.drawRect((Game.WIDTH * 14 / 100) * Game.SCALE + (Game.WIDTH * ((i) * 10) / 100) * Game.SCALE+ (i + 1) * (Game.WIDTH * 2 / 100) * Game.SCALE,((Game.HEIGHT * 81 / 100) * Game.SCALE) + 2, (Game.WIDTH * 10 / 100) * Game.SCALE, 70);
					g.drawImage(ballIcon,(Game.WIDTH * 14 / 100) * Game.SCALE + 4 + (Game.WIDTH * ((i) * 10) / 100) * Game.SCALE+ (i + 1) * (Game.WIDTH * 2 / 100) * Game.SCALE,((Game.HEIGHT * 82 / 100) * Game.SCALE), null);

					if (Game.slotList.get(i).getPokemon() != null) {
						g.setColor(Color.BLACK);
						g.drawString(Game.pokedex.getInfo((Game.slotList.get(i).getPokemon().id), "name"),(Game.WIDTH * 14 / 100) * Game.SCALE + (Game.WIDTH * ((i) * 10) / 100) * Game.SCALE+ (i + 1) * (Game.WIDTH * 2 / 100) * Game.SCALE,((Game.HEIGHT * 99 / 100) * Game.SCALE) + 2);
						g.drawImage(null,(Game.WIDTH * 14 / 100) * Game.SCALE + (Game.WIDTH * ((i) * 10) / 100) * Game.SCALE+ (i + 1) * (Game.WIDTH * 2 / 100) * Game.SCALE,((Game.HEIGHT * 99 / 100) * Game.SCALE) + 2, null);

						g.drawString("Lvl: " + Game.slotList.get(i).getPokemon().lvl,(Game.WIDTH * 14 / 100) * Game.SCALE + (Game.WIDTH * ((i) * 10) / 100) * Game.SCALE+ (i + 1) * (Game.WIDTH * 2 / 100) * Game.SCALE,((Game.HEIGHT * 80 / 100) * Game.SCALE) + 2);
						g.drawImage(Game.pokedex.getSprite(Game.slotList.get(i).getPokemon().id, "Front",Game.slotList.get(i).getPokemon().isShiny),(Game.WIDTH * 14 / 100) * Game.SCALE - 10 + (Game.WIDTH * ((i) * 10) / 100) * Game.SCALE+ (i + 1) * (Game.WIDTH * 2 / 100) * Game.SCALE,((Game.HEIGHT * 81 / 100) * Game.SCALE) - 8, null);
						g.drawImage(Game.pokedex.allTypesSprites[Game.slotList.get(i).getPokemon().myTypesNum[0]],(Game.WIDTH * 20 / 100) * Game.SCALE - 10 + (Game.WIDTH * ((i) * 10) / 100) * Game.SCALE+ (i + 1) * (Game.WIDTH * 2 / 100) * Game.SCALE,((Game.HEIGHT * 79 / 100) * Game.SCALE) - 10, null);
						g.drawImage(Game.pokedex.allTypesSprites[Game.slotList.get(i).getPokemon().myTypesNum[1]],(Game.WIDTH * 23 / 100) * Game.SCALE - 10 + (Game.WIDTH * ((i) * 10) / 100) * Game.SCALE+ (i + 1) * (Game.WIDTH * 2 / 100) * Game.SCALE,((Game.HEIGHT * 79 / 100) * Game.SCALE) - 10, null);

					}

				}
				
				g.setColor(Color.gray);
				g.fillRect((Game.WIDTH * 87 / 100) * Game.SCALE, (Game.HEIGHT * 83 / 100) * Game.SCALE,(Game.WIDTH * 10 / 100) * Game.SCALE, (Game.WIDTH * 8 / 100) * Game.SCALE);
				g.fillRect((int) ((Game.WIDTH * 0.8 / 100) * Game.SCALE), (Game.HEIGHT * 2 / 100) * Game.SCALE,(Game.WIDTH * 10 / 100) * Game.SCALE,(int) (Game.WIDTH * 5.5 / 100) * Game.SCALE);
				g.fillRect((int) (Game.WIDTH * 2 / 100) * Game.SCALE, (int) (Game.HEIGHT * 85 / 100) * Game.SCALE, 86, 30);

				g.setColor(Color.BLACK);
				g.drawString("SCORE: " + Game.player.score, (int) (Game.WIDTH * 1 / 100) * Game.SCALE,(int) (Game.HEIGHT * 7 / 100) * Game.SCALE);
				g.drawString("FPS: " + Game.FPS, (int) (Game.WIDTH * 1 / 100) * Game.SCALE,(int) (Game.HEIGHT * 9.5 / 100) * Game.SCALE);
				g.drawString("MONEY: " + Game.player.money, (int) (Game.WIDTH * 1 / 100) * Game.SCALE,(int) (Game.HEIGHT * 4.5 / 100) * Game.SCALE);
				
				
			}
			drawItemSlot(g);
		}
		
		if (Game.gameState.equalsIgnoreCase("user_view")) {
			g.setColor(Color.red);
			//g.fillRect(Game.WIDTH * 25 / 100 * Game.SCALE, Game.HEIGHT * 2 / 100 * Game.SCALE,Game.WIDTH * 10 / 100 * Game.SCALE, Game.HEIGHT * 8 / 100 * Game.SCALE);
			//g.fillRect(Game.WIDTH * 45 / 100 * Game.SCALE, Game.HEIGHT * 2 / 100 * Game.SCALE,Game.WIDTH * 10 / 100 * Game.SCALE, Game.HEIGHT * 8 / 100 * Game.SCALE);
			//g.fillRect(Game.WIDTH * 65 / 100 * Game.SCALE, Game.HEIGHT * 2 / 100 * Game.SCALE,Game.WIDTH * 10 / 100 * Game.SCALE, Game.HEIGHT * 8 / 100 * Game.SCALE);
			
			g.drawImage(btn, (int)(Game.WIDTH * 24.5 / 100 * Game.SCALE), Game.HEIGHT * 1 / 100 * Game.SCALE ,null);
			g.drawImage(btn, (int)(Game.WIDTH * 44.5 / 100 * Game.SCALE), Game.HEIGHT * 1 / 100 * Game.SCALE,null);
			g.drawImage(btn, (int)(Game.WIDTH * 64.5 / 100 * Game.SCALE), Game.HEIGHT * 1 / 100 * Game.SCALE,null);
			
			g.setColor(Color.black);
			g.drawString("PC", (int)(Game.WIDTH * 29 / 100 * Game.SCALE), Game.HEIGHT * 7 / 100 * Game.SCALE);
			g.drawString("LOJA",(int)( Game.WIDTH * 48 / 100 * Game.SCALE), Game.HEIGHT * 7 / 100 * Game.SCALE);
			g.drawString("TRADE",(int)( Game.WIDTH * 68 / 100 * Game.SCALE), Game.HEIGHT * 7 / 100 * Game.SCALE);
			
			
			if(pcView.equalsIgnoreCase("pc")) {
				g.setColor(Color.gray);
				g.fillRect(Game.WIDTH * 2 / 100 * Game.SCALE, Game.HEIGHT * 20 / 100 * Game.SCALE, (int) (Game.WIDTH * 71 / 100 * Game.SCALE), Game.HEIGHT * 54 / 100 * Game.SCALE);
				g.fillRect(Game.WIDTH * 75 / 100 * Game.SCALE, Game.HEIGHT * 20 / 100 * Game.SCALE,(int) (Game.WIDTH * 22.5 / 100 * Game.SCALE), Game.HEIGHT * 54 / 100 * Game.SCALE);
				g.drawImage(leftArrow, Game.WIDTH * 2 / 100 * Game.SCALE, Game.HEIGHT * 9 / 100 * Game.SCALE, null);
				g.drawImage(rightArrow, Game.WIDTH * 64 / 100 * Game.SCALE, Game.HEIGHT * 9 / 100 * Game.SCALE, null);				
			
				if (this.pokemonPcDetails != null) {
					drawSpriteDetails(g);
				}
			}else if(pcView.equalsIgnoreCase("store")){
				g.setColor(Color.gray);
				g.fillRect(Game.WIDTH * 25 / 100 * Game.SCALE, Game.HEIGHT * 20 / 100 * Game.SCALE, (int) (Game.WIDTH * 50 / 100 * Game.SCALE), Game.HEIGHT * 54 / 100 * Game.SCALE);
				
				g.fillRect(Game.WIDTH * 77 / 100 * Game.SCALE, Game.HEIGHT * 20 / 100 * Game.SCALE, (int) (Game.WIDTH * 21 / 100 * Game.SCALE), Game.HEIGHT * 46 / 100 * Game.SCALE);
				g.fillRect((int)(Game.WIDTH * 2.5 / 100 * Game.SCALE), Game.HEIGHT * 20 / 100 * Game.SCALE, (int) (Game.WIDTH * 21 / 100 * Game.SCALE), Game.HEIGHT * 46 / 100 * Game.SCALE);
				
				g.drawImage(dice,(int)(Game.WIDTH * 49.5 / 100 * Game.SCALE) - 15, Game.HEIGHT * 63 / 100 * Game.SCALE, null);				
				g.setColor(Color.black);
				g.drawString("ITENS A VENDA:", Game.WIDTH * 44 / 100 * Game.SCALE, Game.HEIGHT * 24 / 100 * Game.SCALE);
				g.drawString(nDice+"x",(int) (Game.WIDTH * 49.4 / 100 * Game.SCALE), Game.HEIGHT * 73 / 100 * Game.SCALE);
				drawItensDetails(g);
				
				
				
				
			}else if(pcView.equalsIgnoreCase("trade")){
				g.setColor(Color.gray);
				
					
				g.fillRect((int)(Game.WIDTH * 3.3 / 100 * Game.SCALE), Game.HEIGHT * 20 / 100 * Game.SCALE, (int) (Game.WIDTH * 28 / 100 * Game.SCALE), Game.HEIGHT * 54 / 100 * Game.SCALE);
				g.fillRect((int)(Game.WIDTH * 36.3 / 100 * Game.SCALE), Game.HEIGHT * 20 / 100 * Game.SCALE, (int) (Game.WIDTH * 28 / 100 * Game.SCALE), Game.HEIGHT * 54 / 100 * Game.SCALE);
				g.fillRect((int)(Game.WIDTH * 69.3 / 100 * Game.SCALE), Game.HEIGHT * 20 / 100 * Game.SCALE, (int) (Game.WIDTH * 28 / 100 * Game.SCALE), Game.HEIGHT * 54 / 100 * Game.SCALE);
				
				g.setColor(Color.black);
				g.drawString("TRADE COM NPC", (int)(Game.WIDTH * 11 / 100 * Game.SCALE), Game.HEIGHT * 26 / 100 * Game.SCALE);
				
				if(Game.tradeController.offertOn) {
					g.drawRect((int)(Game.WIDTH * 4.3 / 100 * Game.SCALE), Game.HEIGHT * 40 / 100 * Game.SCALE, (Game.WIDTH * 10 / 100) * Game.SCALE, 70);
					g.drawRect((int)(Game.WIDTH * 20 / 100 * Game.SCALE), Game.HEIGHT * 40 / 100 * Game.SCALE, (Game.WIDTH * 10 / 100) * Game.SCALE, 70);
					g.drawRect((int)(Game.WIDTH * 9.5 / 100 * Game.SCALE), Game.HEIGHT * 62 / 100 * Game.SCALE, (Game.WIDTH * 15 / 100) * Game.SCALE, 30);
					g.drawString("TRADE", (int)(Game.WIDTH * 14.5 / 100 * Game.SCALE), Game.HEIGHT * 66 / 100 * Game.SCALE);
					
					if(pkmOffer.get(0).nm != null) {
						g.drawString(pkmOffer.get(0).nm, (int)(Game.WIDTH * 20 / 100 * Game.SCALE), Game.HEIGHT * 38 / 100 * Game.SCALE);
						g.drawImage(Game.pokedex.getSprite(pkmOffer.get(0).id, "front", pkmOffer.get(0).isShiny),(int)(Game.WIDTH * 18.5 / 100 * Game.SCALE), Game.HEIGHT * 38 / 100 * Game.SCALE,null);
						
					}if(pkmOffer.get(1).nm != null) {
						g.drawString(pkmOffer.get(1).nm, (int)(Game.WIDTH * 4.3 / 100 * Game.SCALE), Game.HEIGHT * 38 / 100 * Game.SCALE);
						g.drawImage(Game.pokedex.getSprite(pkmOffer.get(1).id, "front", pkmOffer.get(1).isShiny),(int)(Game.WIDTH * 2.8 / 100 * Game.SCALE), Game.HEIGHT * 38 / 100 * Game.SCALE,null);
						
					}
				}else {
					g.drawString("SEM OFERTAS", (int)(Game.WIDTH * 12 / 100 * Game.SCALE), Game.HEIGHT * 66 / 100 * Game.SCALE);
					
				}
				
				g.drawRect((int)(Game.WIDTH * 37.3 / 100 * Game.SCALE), Game.HEIGHT * 40 / 100 * Game.SCALE, (Game.WIDTH * 10 / 100) * Game.SCALE, 70);
				g.drawRect((int)(Game.WIDTH * 53 / 100 * Game.SCALE), Game.HEIGHT * 40 / 100 * Game.SCALE, (Game.WIDTH * 10 / 100) * Game.SCALE, 70);
				g.drawRect((int)(Game.WIDTH * 42.5 / 100 * Game.SCALE), Game.HEIGHT * 62 / 100 * Game.SCALE, (Game.WIDTH * 15 / 100) * Game.SCALE, 30);
				
				g.drawRect((int)(Game.WIDTH * 70.3 / 100 * Game.SCALE), Game.HEIGHT * 40 / 100 * Game.SCALE, (Game.WIDTH * 10 / 100) * Game.SCALE, 70);
				g.drawRect((int)(Game.WIDTH * 86 / 100 * Game.SCALE), Game.HEIGHT * 40 / 100 * Game.SCALE, (Game.WIDTH * 10 / 100) * Game.SCALE, 70);
				g.drawRect((int)(Game.WIDTH * 75.5 / 100 * Game.SCALE), Game.HEIGHT * 62 / 100 * Game.SCALE, (Game.WIDTH * 15 / 100) * Game.SCALE, 30);
				
			}
			

		}
		
		
		
		g.drawString("WAVE: " + (Game.lvlConfig.get(Game.currentLvl).nGen* Game.lvlConfig.get(Game.currentLvl).pokeGenNum[Game.currentLvl] - Game.lvlConfig.get(Game.currentLvl).totalDefeat) + " / "+ Game.lvlConfig.get(Game.currentLvl).nGen* Game.lvlConfig.get(Game.currentLvl).pokeGenNum[Game.currentLvl],(int) (Game.WIDTH * 2.5 / 100) * Game.SCALE, (int) (Game.HEIGHT * 89 / 100) * Game.SCALE);

	}

	private void drawItemSlot(Graphics g) {
		
		g.setColor(Color.RED);
		g.drawRect((int)(Game.WIDTH * 84.8 / 100) * Game.SCALE,(int) (Game.HEIGHT * 86.5 / 100) * Game.SCALE, 10, 20);
		g.drawRect((int)(Game.WIDTH * 97.5 / 100) * Game.SCALE,(int) (Game.HEIGHT * 86.5 / 100) * Game.SCALE, 10, 20);
		g.drawRect((int)(Game.WIDTH * 90.5 / 100) * Game.SCALE,(int) (Game.HEIGHT * 79.6 / 100) * Game.SCALE, 20, 10);
		g.drawRect((int)(Game.WIDTH * 90.5 / 100) * Game.SCALE,(int) (Game.HEIGHT * 96 / 100) * Game.SCALE, 20, 10);
		g.setColor(Color.BLACK);
		g.drawRect((Game.WIDTH * 87 / 100) * Game.SCALE, (Game.HEIGHT * 83 / 100) * Game.SCALE,(Game.WIDTH * 10 / 100) * Game.SCALE, (Game.WIDTH * 8 / 100) * Game.SCALE);
		g.drawRect((int) (Game.WIDTH * 2 / 100) * Game.SCALE, (int) (Game.HEIGHT * 85 / 100) * Game.SCALE, 86, 30);

		
		if(nRow == 0) {
			if(Player.pokeballItemList.size() > 0) {
				g.drawImage(Player.pokeballItemList.get(nColumn).sprite,(int) (Game.WIDTH * 87.3 / 100) * Game.SCALE,(int) (Game.HEIGHT * 82.5 / 100) * Game.SCALE, null);
				g.drawString(Player.pokeballItemList.get(nColumn).current + "x", (int) (Game.WIDTH * 91 / 100) * Game.SCALE,(Game.HEIGHT * 85 / 100) * Game.SCALE);
				
			}else {
				g.drawImage(Game.itemController.nullPokeball,(int) (Game.WIDTH * 87.3 / 100) * Game.SCALE,(int) (Game.HEIGHT * 82.5 / 100) * Game.SCALE, null);
				g.drawString("0x", (int) (Game.WIDTH * 88 / 100) * Game.SCALE,(Game.HEIGHT * 85 / 100) * Game.SCALE);
				
			}
		}else if(nRow == 1) {
			if(Player.medicineItemList.size() > 0) {
				g.drawImage(Player.medicineItemList.get(nColumn).sprite,(int) (Game.WIDTH * 87.3 / 100) * Game.SCALE, (int) (Game.HEIGHT * 82.5 / 100) * Game.SCALE, null);
				g.drawString(Player.medicineItemList.get(nColumn).current + "x", (int) (Game.WIDTH * 91 / 100) * Game.SCALE,(Game.HEIGHT * 85 / 100) * Game.SCALE);
				
			}else {
				g.drawImage(Game.itemController.nullMedicine,(int) (Game.WIDTH * 87.3 / 100) * Game.SCALE, (Game.HEIGHT * 83 / 100) * Game.SCALE, null);
				g.drawString("0x", (int) (Game.WIDTH * 88 / 100) * Game.SCALE,(Game.HEIGHT * 85 / 100) * Game.SCALE);
				
			}
		}else if(nRow == 2) {
			if(Player.battleItemList.size() > 0) {
				g.drawImage(Player.battleItemList.get(nColumn).sprite,(int) (Game.WIDTH * 87.3 / 100) * Game.SCALE, (int) (Game.HEIGHT * 82.5 / 100) * Game.SCALE, null);
				g.drawString(Player.battleItemList.get(nColumn).current + "x", (int) (Game.WIDTH * 91 / 100) * Game.SCALE,(Game.HEIGHT * 85 / 100) * Game.SCALE);
				
			}else {
				g.drawImage(Game.itemController.nullbattleItem,(int) (Game.WIDTH * 87.3 / 100) * Game.SCALE, (Game.HEIGHT * 83 / 100) * Game.SCALE, null);
				g.drawString("0x", (int) (Game.WIDTH * 88 / 100) * Game.SCALE,(Game.HEIGHT * 85 / 100) * Game.SCALE);
				
			}
		}else if(nRow == 3) {
			if(Player.evolueItemList.size() > 0) {
				g.drawImage(Player.evolueItemList.get(nColumn).sprite,(int) (Game.WIDTH * 87.3 / 100) * Game.SCALE, (int) (Game.HEIGHT * 82.5 / 100) * Game.SCALE, null);
				g.drawString(Player.evolueItemList.get(nColumn).current + "x", (int) (Game.WIDTH * 91 / 100) * Game.SCALE,(Game.HEIGHT * 85 / 100) * Game.SCALE);
				
			}else {
				g.drawImage(Game.itemController.nullEvolueItem,(int) (Game.WIDTH * 87.3 / 100) * Game.SCALE, (Game.HEIGHT * 83 / 100) * Game.SCALE, null);
				g.drawString("0x", (int) (Game.WIDTH * 88 / 100) * Game.SCALE,(Game.HEIGHT * 85 / 100) * Game.SCALE);
				
			}
		}
	}

	private void tutorialRender(Graphics g){
		if (Game.tutorialSteps == 0) {
			g.fillRect((Game.WIDTH * Game.SCALE) / 2 - (Game.WIDTH * 50 / 100 * Game.SCALE) / 2,(Game.HEIGHT * Game.SCALE) / 2 - (Game.HEIGHT * 50 / 100 * Game.SCALE) / 2,(Game.WIDTH * 50 / 100 * Game.SCALE), (Game.HEIGHT * 50 / 100 * Game.SCALE));
			g.setColor(Color.black);

			g.drawString("Seja bem-vindo ao mundo pokemon!", Game.WIDTH * 36 / 100 * Game.SCALE,Game.HEIGHT * 32 / 100 * Game.SCALE);
			g.drawString("Vou mostrar como as coisas funcionam, escolha seu inicial.",(int)( Game.WIDTH * 26.5 / 100 * Game.SCALE),Game.HEIGHT * 37 / 100 * Game.SCALE);
			g.drawString("Selecione um, e aperte SPACE", Game.WIDTH * 38 / 100 * Game.SCALE,Game.HEIGHT * 68 / 100 * Game.SCALE);

			g.drawRect((Game.WIDTH * Game.SCALE) / 2 - (Game.WIDTH * 50 / 100 * Game.SCALE) / 2,(Game.HEIGHT * Game.SCALE) / 2 - (Game.HEIGHT * 50 / 100 * Game.SCALE) / 2,(Game.WIDTH * 50 / 100 * Game.SCALE), (Game.HEIGHT * 50 / 100 * Game.SCALE));
			g.drawImage(pOak, Game.WIDTH * 65 / 100 * Game.SCALE, (Game.HEIGHT * 42 / 100 * Game.SCALE), null);
			g.drawImage(marill, Game.WIDTH * 20 / 100 * Game.SCALE, Game.HEIGHT * 60 / 100 * Game.SCALE, null);
			for (int i = 0; i < 3; i++) {
				if (Game.player.x > Game.WIDTH * 35 / 100 + 80 / 3 * i
						&& Game.player.x < Game.WIDTH * 35 / 100 + 80 / 3 * i + 20) {
					g.setColor(Color.green);
					if (i == 0) {
						startSelect = 1;
					} else if (i == 1) {
						startSelect = 4;
					} else {
						startSelect = 7;
					}

				} else {
					g.setColor(Color.black);
				}
				g.drawRect(Game.WIDTH * 35 / 100 * Game.SCALE + 80 * i, Game.HEIGHT * 45 / 100 * Game.SCALE, 60, 60);
				g.drawImage(starters[i], Game.WIDTH * 35 / 100 * Game.SCALE + 80 * i - 16,Game.HEIGHT * 45 / 100 * Game.SCALE - 20, null);
			}

		}
		if (Game.tutorialSteps == 1) {
			g.fillRect((Game.WIDTH * Game.SCALE) / 2 - (Game.WIDTH * 50 / 100 * Game.SCALE) / 2,(Game.HEIGHT * Game.SCALE) / 2 - (Game.HEIGHT * 50 / 100 * Game.SCALE) / 2,(Game.WIDTH * 50 / 100 * Game.SCALE), (Game.HEIGHT * 50 / 100 * Game.SCALE));
			g.setColor(Color.black);
			g.drawRect((Game.WIDTH * Game.SCALE) / 2 - (Game.WIDTH * 50 / 100 * Game.SCALE) / 2,(Game.HEIGHT * Game.SCALE) / 2 - (Game.HEIGHT * 50 / 100 * Game.SCALE) / 2,	(Game.WIDTH * 50 / 100 * Game.SCALE), (Game.HEIGHT * 50 / 100 * Game.SCALE));
			g.drawImage(pOak, Game.WIDTH * 65 / 100 * Game.SCALE, (Game.HEIGHT * 42 / 100 * Game.SCALE), null);

			g.drawString("Para jogar, arraste seu pokemon para esses slots:", Game.WIDTH * 30 / 100 * Game.SCALE,Game.HEIGHT * 30 / 100 * Game.SCALE);
			g.drawRect(Game.WIDTH * 48 / 100 * Game.SCALE, Game.HEIGHT * 35 / 100 * Game.SCALE, 40, 40);
			g.drawImage(Game.pokedex.getSprite(startSelect, "BACK", false), Game.WIDTH * 43 / 100 * Game.SCALE - 16,Game.HEIGHT * 38 / 100 * Game.SCALE - 20, null);
			g.drawString("Existem slots para tipos especificos", Game.WIDTH * 35 / 100 * Game.SCALE,Game.HEIGHT * 53 / 100 * Game.SCALE);
			g.drawString("O tipo ganha buff de 10%, mas não pode outros tipos", Game.WIDTH * 29 / 100 * Game.SCALE,Game.HEIGHT * 57 / 100 * Game.SCALE);
			for (int i = 0; i < 7; i++) {
				g.drawImage(Game.pokedex.allTypesSprites[i + 9], Game.WIDTH * 42 / 100 * Game.SCALE + i * 18,Game.HEIGHT * 60 / 100 * Game.SCALE, null);

			}
			g.drawString("Aperte SPACE", Game.WIDTH * 45 / 100 * Game.SCALE, Game.HEIGHT * 70 / 100 * Game.SCALE);

		}
	}

	public void drawItensDetails(Graphics g) {
		if (this.itemDetails != null) {
			g.setColor(Color.red);
			g.fillRect((int)(Game.WIDTH * 82.5 / 100 * Game.SCALE), Game.HEIGHT * 59 / 100 * Game.SCALE, 70, 20);
			
			g.setColor(Color.black);
			g.drawString(itemDetails.name,(int)(Game.WIDTH * 83.5 / 100 * Game.SCALE), Game.HEIGHT * 24 / 100 * Game.SCALE);
			g.drawRect((int)(Game.WIDTH * 83.5 / 100 * Game.SCALE), Game.HEIGHT * 26 / 100 * Game.SCALE,(Game.WIDTH*8/100)*Game.SCALE, (Game.WIDTH*8/100)*Game.SCALE);
			g.drawImage(itemDetails.sprite,(Game.WIDTH * 83 / 100 * Game.SCALE), Game.HEIGHT * 25 / 100 * Game.SCALE, null);
			
			g.drawRect((int)(Game.WIDTH * 79.5 / 100 * Game.SCALE),(int)(Game.HEIGHT * 39.5 / 100 * Game.SCALE),(Game.WIDTH*16/100)*Game.SCALE, (Game.WIDTH*12/100)*Game.SCALE);
			g.drawString("MONEY: "+itemDetails.value, (int)(Game.WIDTH * 80.4 / 100 * Game.SCALE), Game.HEIGHT * 43 / 100 * Game.SCALE);
			g.drawString("DESC:", (int)(Game.WIDTH * 85.4 / 100 * Game.SCALE), Game.HEIGHT * 45 / 100 * Game.SCALE);
			
			String brokenText = Game.configs.quebrarLinhas(itemDetails.desc, 17);
	        String[] lines = brokenText.split("\n");

	        //QUEBRA DE LINHAS PARA O DRAWSTRING
	        int y = Game.HEIGHT * 48 / 100 * Game.SCALE;
	        for (String line : lines) {
	            g.drawString(line, (int)(Game.WIDTH * 80.4 / 100 * Game.SCALE), y);
	            y += g.getFontMetrics().getHeight();
	        }
			
			//btn comprar
			g.drawRect((int)(Game.WIDTH * 82.5 / 100 * Game.SCALE), Game.HEIGHT * 59 / 100 * Game.SCALE, 70, 20);
			g.drawString("COMPRAR", (int)(Game.WIDTH * 83.4 / 100 * Game.SCALE), Game.HEIGHT * 62 / 100 * Game.SCALE);
		}
	}
	public void drawSpriteDetails(Graphics g) {
		g.setColor(Color.black);
		g.drawRect(Game.WIDTH * 76 / 100 * Game.SCALE, Game.HEIGHT * 22 / 100 * Game.SCALE, 80, 20);
		g.drawRect(Game.WIDTH * 88 / 100 * Game.SCALE, Game.HEIGHT * 22 / 100 * Game.SCALE, 60, 60);
		g.drawString("" + pokemonPcDetails.nm, Game.WIDTH * 77 / 100 * Game.SCALE, Game.HEIGHT * 25 / 100 * Game.SCALE);
		g.drawString("Lvl: " + pokemonPcDetails.lvl, Game.WIDTH * 76 / 100 * Game.SCALE,Game.HEIGHT * 30 / 100 * Game.SCALE);
		g.drawString("Nature: " + pokemonPcDetails.nature, Game.WIDTH * 76 / 100 * Game.SCALE,Game.HEIGHT * 34 / 100 * Game.SCALE);

		g.drawImage(Game.pokedex.allTypesSprites[pokemonPcDetails.myTypesNum[0]], Game.WIDTH * 82 / 100 * Game.SCALE,Game.HEIGHT * 28 / 100 * Game.SCALE, null);
		g.drawImage(Game.pokedex.allTypesSprites[pokemonPcDetails.myTypesNum[1]],(int) (Game.WIDTH * 84.5 / 100 * Game.SCALE), Game.HEIGHT * 28 / 100 * Game.SCALE, null);
		g.drawString("STATES", Game.WIDTH * 83 / 100 * Game.SCALE, (int) (Game.HEIGHT * 38.4 / 100 * Game.SCALE));
		g.drawRect(Game.WIDTH * 76 / 100 * Game.SCALE, Game.HEIGHT * 39 / 100 * Game.SCALE, 150,Game.HEIGHT * 20 / 100 * Game.SCALE);

		g.drawString("Hp: " + Game.configs.format.format(pokemonPcDetails.currentHp) + " / "+ Game.configs.format.format(pokemonPcDetails.maxHp) + " " + pokemonPcDetails.ivsDesc[0],Game.WIDTH * 77 / 100 * Game.SCALE, Game.HEIGHT * 43 / 100 * Game.SCALE);
		g.drawString("Atk: " + Game.configs.format.format(pokemonPcDetails.atk) + " " + pokemonPcDetails.ivsDesc[1],Game.WIDTH * 77 / 100 * Game.SCALE, Game.HEIGHT * 46 / 100 * Game.SCALE);
		g.drawString("Def: " + Game.configs.format.format(pokemonPcDetails.def) + " " + pokemonPcDetails.ivsDesc[2],Game.WIDTH * 77 / 100 * Game.SCALE, Game.HEIGHT * 49 / 100 * Game.SCALE);
		g.drawString("SpAtk: " + Game.configs.format.format(pokemonPcDetails.spAtk) + " " + pokemonPcDetails.ivsDesc[3],Game.WIDTH * 77 / 100 * Game.SCALE, Game.HEIGHT * 52 / 100 * Game.SCALE);
		g.drawString("SpDef: " + Game.configs.format.format(pokemonPcDetails.spDef) + " " + pokemonPcDetails.ivsDesc[4],Game.WIDTH * 77 / 100 * Game.SCALE, Game.HEIGHT * 55 / 100 * Game.SCALE);
		g.drawString("Spd: " + Game.configs.format.format(pokemonPcDetails.spdInMap) + " / "+ Game.configs.format.format(pokemonPcDetails.spd) + " " + pokemonPcDetails.ivsDesc[5],	Game.WIDTH * 77 / 100 * Game.SCALE, Game.HEIGHT * 58 / 100 * Game.SCALE);

		g.drawString("HABILIDADES", Game.WIDTH * 76 / 100 * Game.SCALE, (int) (Game.HEIGHT * 64 / 100 * Game.SCALE));

		String tempUrl = "";
		if (pokemonPcDetails.id < 10) {
			tempUrl = "00";
		} else if (pokemonPcDetails.id >= 10 && pokemonPcDetails.id < 100) {
			tempUrl = "0";
		}
		String shinyUrl = "";
		if (pokemonPcDetails.isShiny) {
			shinyUrl = "s";
		}
		spritesheet = new Spritesheet("/PokemonSprites/InMaps/" + tempUrl + pokemonPcDetails.id + shinyUrl + ".png");

		frames++;
		if (frames == maxFrames) {
			frames = 0;
			index++;
			if (index > maxIndex) {
				index = 0;
			}
		}
		for (int i = 0; i < 4; i++) {
			down[i] = spritesheet.getSprite(0 + (i * 64), 0, 64, 64);
		}
		g.drawImage(down[index], Game.WIDTH * 88 / 100 * Game.SCALE, Game.HEIGHT * 20 / 100 * Game.SCALE, null);

		// DELETAR POKEMON
		g.setColor(Color.red);
		g.drawString("DELETE", Game.WIDTH * 91 / 100 * Game.SCALE, (int) (Game.HEIGHT * 64 / 100 * Game.SCALE));
		g.fillRect(Game.WIDTH * 91 / 100 * Game.SCALE, Game.HEIGHT * 65 / 100 * Game.SCALE, 40, 36);
		g.setColor(Color.black);
		g.drawRect(Game.WIDTH * 91 / 100 * Game.SCALE, Game.HEIGHT * 65 / 100 * Game.SCALE, 40, 36);
		
	}
	 
}
