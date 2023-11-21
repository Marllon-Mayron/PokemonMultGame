package kaleb.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import kaleb.itens.BattleItem;
import kaleb.itens.EvolutionItem;
import kaleb.itens.Medicine;
import kaleb.itens.Pokeball;
import kaleb.main.Game;

public class Player extends Entity {
	public int money = 50;
	public int score = 0;
	public int totalCaptured;
	public int currentPokeball;
	public int listSize = 0;
	public String spriteDesc = "";
	public boolean draged;
	public Pokemon pokemonDragged;
	public static List<Pokeball> pokeballItemList = new ArrayList<Pokeball>();
	public static List<EvolutionItem> berryItemList = new ArrayList<EvolutionItem>();
	public static List<Medicine> medicineItemList = new ArrayList<Medicine>();
	public static List<BattleItem> battleItemList = new ArrayList<BattleItem>();
	public static List<EvolutionItem> evolueItemList = new ArrayList<EvolutionItem>();
	
	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		this.speed = 0.5;
		this.currentPokeball = 0;
		this.width = 10;
		this.height = 10;
	}

	public void tick() {
		//PARA SABER QUANTOS ITENS TEM NA LINHA
		if(Game.ui.nRow == 0) {
			listSize = pokeballItemList.size();
		}if(Game.ui.nRow == 1) {
			listSize = medicineItemList.size();
		}if(Game.ui.nRow == 2) {
			listSize = battleItemList.size();
		}if(Game.ui.nRow == 3) {
			listSize = evolueItemList.size();
		}
		//=====================================
		if (Game.waveList.size() == 0 && Game.generateList.size() == 0 && Game.gameState.equalsIgnoreCase("catch")) {
			Game.gameState = "user_view";
			Game.ui.pcView = "pc";
			Game.ui.nDice = 2;
			Game.storeController.generateItem();
			Game.tradeController.populateNpcTrade();
			//RETIRAR POKEMON DO MAPA
			removePokemons();
			//REMOVER TODOS OS LOCALES DO MAPA 
			removeLocales();
			//DEVOLVER 10% DA VIDA DOS POKEMONS DERROTADOS
			restorePokemons();
		}
		
	}
	public void nextGameLevel() {
		if(Game.currentLvl+1 !=  Game.lvlConfig.get(0).total) {
			Game.currentLvl++;
			
			
			//crirar o mapa
			Game.lvlConfig.get(0).generateLvl();
			//criar os locais 
			Game.lvlConfig.get(0).generateLocale();
			
			int prob = Game.random.nextInt(100)+1;
			int sec = Game.random.nextInt(10)+1;
			if(prob < 101) {
				if(Game.currentLvl > 0 && Game.currentLvl < 3) {
					Timer timer = new Timer();
					
					TimerTask tarefa = new TimerTask() {
						private int contador = 0;

						@Override
						public void run() {
							if (contador == 2) {
								Game.eventController.pokemonWithOwner();
								timer.cancel();
							} else {
								contador++;
							}
						}
					};

					timer.schedule(tarefa, 0, sec*1000);
				}
			}
			
		}
		
	}
	public void removeLocales() {
		
		for (int i = 0; i < Game.entities.size(); i++) {
			if (Game.entities.get(i) instanceof Locale) {
				Game.entities.remove(Game.entities.get(i));
				i--;
			}
		}
	}
	public void removePokemons() {
		for (int i = 0; i < Game.entities.size(); i++) {
			if (Game.entities.get(i) instanceof Locale) {
				Locale l = (Locale) Game.entities.get(i);
				if (l.getPokemon() != null) {
					for (int i2 = 0; i2 < Game.entities.size(); i2++) {
						if (Game.entities.get(i2) instanceof Pokemon) {
							if (Game.entities.get(i2) == l.getPokemon()) {
								Pokemon p = (Pokemon) Game.entities.get(i2);
								for (int i3 = 0; i3 < Game.slotList.size(); i3++) {
									if (Game.slotList.get(i3).getPokemon() == l.getPokemon()) {
										Game.slotList.get(i3).setSlotStatus("free");
										p.inMap = false;
										p.resetExit();
										l.setPokemon(null);
										Game.entities.remove(p);
									}
								}

							}

						}
					}

				}
			}
		}
	}
	private void restorePokemons() {
		for(int i = 0; i < Game.pokeList.size(); i++) {
			if(Game.pokeList.get(i).currentHp < (Game.pokeList.get(i).maxHp * 25) / 100) {
				Game.pokeList.get(i).currentHp = (Game.pokeList.get(i).maxHp * 25) / 100;
				Game.pokeList.get(i).updateLife();
			}
		}
		//TIRAR OS POKEMONS DO STATUS DE DERROTADO
		for(int i = 0; i < Game.slotList.size(); i++) {
			Game.slotList.get(i).slotStatus = "free";
		}
	}
	
	public void render(Graphics g) {
		
		if (spriteDesc.equalsIgnoreCase("pokemon")) {
			g.drawImage(this.sprite, (int) this.getX() * Game.SCALE - 48, (int) this.y * Game.SCALE - 48, null);
		} else if (spriteDesc.equalsIgnoreCase("pokeball")) {
			g.drawImage(this.sprite, (int) this.getX() * Game.SCALE - 26, (int) this.y * Game.SCALE - 26, null);
		}else if (spriteDesc.equalsIgnoreCase("medicine")) {
			g.drawImage(this.sprite, (int) this.getX() * Game.SCALE - 30, (int) this.y * Game.SCALE - 30, null);
		}else if (spriteDesc.equalsIgnoreCase("battleItem")) {
			g.drawImage(this.sprite, (int) this.getX() * Game.SCALE - 26, (int) this.y * Game.SCALE - 30, null);
		}else if (spriteDesc.equalsIgnoreCase("evolueItem")) {
			g.drawImage(this.sprite, (int) this.getX() * Game.SCALE - 26, (int) this.y * Game.SCALE - 30, null);
		}

		g.setColor(Color.white);
		g.drawRect((int) x * Game.SCALE , (int) y * Game.SCALE , width, height);
		
		g.setColor(Color.red);
		g.drawRect((int)this.x *3 ,(int)this.y *3,this.getWidth(),this.getHeight());
		
		
	}

	public void renderStr(Graphics g) {

	}

}
