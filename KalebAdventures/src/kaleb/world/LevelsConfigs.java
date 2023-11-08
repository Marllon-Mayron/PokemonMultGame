package kaleb.world;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import kaleb.entities.Locale;
import kaleb.graphics.Spritesheet;
import kaleb.main.Game;

public class LevelsConfigs {
	public int total = 7;
	public int nLvl;
	public int nGen;
	public  double cooldown;
	public int maxLvl;
	public int minLvl;
	public int shinyChance;
	public int totalDefeat = 0;
	public int myVictorys = 0;
	
	public String[][] codeRoute = new String[total][8];
	public String[][] routePokemonList = new String[total][4];
	public String[][] routePokemonBossList = new String[total][4];
	public int[] pokeGenNum = new int[total];
	public int[][] mapSize = new int[total][2];
	public int[][] mapSizeOffset = new int[total][2];
	public BufferedImage[] mapSprite = new BufferedImage[total];
	public BufferedImage[] mapSprite2 = new BufferedImage[total];
	public Spritesheet mapSpritesheet;
	
	public String[] locales = new String[total];
	
	
	
	public LevelsConfigs(int nLvl, int nGen,double cooldown, int minLvl, int maxLvl) {
		super();
		this.nLvl = nLvl;
		this.nGen = nGen;
		this.cooldown = cooldown;
		this.maxLvl = maxLvl;
		this.minLvl = minLvl;

		//CARREGAR IMAGENS DOS MAPAS 
		mapSpritesheet = new Spritesheet("/Maps/lvl0.png");
		mapSprite[0] = mapSpritesheet.getSprite(0, 0, 720, 480);
		mapSpritesheet = new Spritesheet("/Maps/lvl0-2.png");
		mapSprite2[0] = mapSpritesheet.getSprite(0, 0, 720, 480);
		
		mapSpritesheet = new Spritesheet("/Maps/lvl1.png");
		mapSprite[1] = mapSpritesheet.getSprite(0, 0, 720, 480);
		mapSpritesheet = new Spritesheet("/Maps/lvl1-2.png");
		mapSprite2[1] = mapSpritesheet.getSprite(0, 0, 720, 480);
		
		mapSpritesheet = new Spritesheet("/Maps/lvl2.png");
		mapSprite[2] = mapSpritesheet.getSprite(0, 0, 720, 480);
		mapSpritesheet = new Spritesheet("/Maps/lvl2-2.png");
		mapSprite2[2] = mapSpritesheet.getSprite(0, 0, 720, 480);
		
		mapSpritesheet = new Spritesheet("/Maps/lvl3.png");
		mapSprite[3] = mapSpritesheet.getSprite(0, 0, 960, 480);
		mapSpritesheet = new Spritesheet("/Maps/lvl3-2.png");
		mapSprite2[3] = mapSpritesheet.getSprite(0, 0, 960, 480);
		
		mapSpritesheet = new Spritesheet("/Maps/lvl4.png");
		mapSprite[4] = mapSpritesheet.getSprite(0, 0, 720, 480);
		mapSpritesheet = new Spritesheet("/Maps/lvl4-2.png");
		mapSprite2[4] = mapSpritesheet.getSprite(0, 0, 720, 480);
		
		mapSpritesheet = new Spritesheet("/Maps/lvl5.png");
		mapSprite[5] = mapSpritesheet.getSprite(0, 0, 860, 480);
		mapSpritesheet = new Spritesheet("/Maps/lvl5-2.png");
		mapSprite2[5] = mapSpritesheet.getSprite(0, 0, 860, 480);
		
		mapSpritesheet = new Spritesheet("/Maps/lvl6.png");
		mapSprite[6] = mapSpritesheet.getSprite(0, 0, 960, 620);
		mapSpritesheet = new Spritesheet("/Maps/lvl6-2.png");
		mapSprite2[6] = mapSpritesheet.getSprite(0, 0, 960, 620);
		//======================================================
		
		//TAMANHO DO MAPA
		mapSize[0][0] = mapSprite[0].getWidth()/Game.SCALE;
		mapSize[0][1] = mapSprite[0].getHeight()/Game.SCALE;
		//ESPAÇO A MAIS PARA CAMERA
		mapSizeOffset[0][1] = 20;
		
		//LOCAIS ONDE MEU POKEMON PODE FICAR (N, X1-Y1, X2-Y2 ,TYPE1/TYPE2)
		locales[0] = "1,100-110,NULL";
		//ROTAS QUE OS POKEMONS SELVAGENS IRÃO FAZER (LEFT-RIGHT-DOWN-TOP)
		codeRoute[0][0] = "D-240";
		//ID DOS POKEMONS QUE APARECEM NA ROTA, E SUAS PROBABILIDADES
		routePokemonList[0][0] = "1-33,4-33,7-34";
		//O MESMO, SÓ QUE PARA OS BOSS
		routePokemonBossList[0][0] = "1-66,4-66,7-68";
		//QUANTIDADE DE GERADORES DE POKEMON NO LEVEL
		pokeGenNum[0] = 1;
		
		mapSize[1][0] = mapSprite[1].getWidth()/Game.SCALE;
		mapSize[1][1] = mapSprite[1].getHeight()/Game.SCALE;
		locales[1] = "4,27-113,68-86,125-49,198-87,NULL/BUG/NULL/NULL";
		codeRoute[1][0] = "U-68,L-54,D-131,L-0";
		routePokemonList[1][0] = "10-90,11-8,13-90,14-8,16-2,21-2";
		routePokemonBossList[1][0] = "12-100,15-100";
		pokeGenNum[1] = 1;
		
		mapSize[2][0] = mapSprite[2].getWidth()/Game.SCALE;
		mapSize[2][1] = mapSprite[2].getHeight()/Game.SCALE;
		mapSizeOffset[2][1] = 30;
		locales[2] = "4,75-102,111-110,145-102,111-65,NULL/NULL/NULL/ROCK";
		codeRoute[2][0] = "D-300";
		routePokemonList[2][0] = "41-100,74-50,50-28,27-10,104-2,35-10";
		routePokemonBossList[2][0] = "95-70,36-50,42-80";
		pokeGenNum[2] = 2;
		
		mapSize[3][0] = mapSprite[3].getWidth()/Game.SCALE;
		mapSize[3][1] = mapSprite[3].getHeight()/Game.SCALE;
		mapSizeOffset[3][0] = -30;
		locales[3] = "5,40-96,40-50,142-50,142-96,244-50,NULL/NULL/NULL/NULL/NULL";
		codeRoute[3][0] = "L-0";
		codeRoute[3][1] = "U-75,L-0";
		routePokemonList[3][0] = "11-28,14-28,16-12,21-12,29-16,32-16,43-8,46-24,48-20,56-12,19-22,25-2";
		routePokemonBossList[3][0] = "30-50,33-50,47-100";
		pokeGenNum[3] = 2;
		
		mapSize[4][0] = mapSprite[4].getWidth()/Game.SCALE;
		mapSize[4][1] = mapSprite[4].getHeight()/Game.SCALE;
		mapSizeOffset[4][1] = 30;
		locales[4] = "5,64-72,114-74,113-22,161-72,113-126,NULL/NULL/WATER/NULL/WATER";
		codeRoute[4][0] = "D-50,R-144,D-100,L-0";
		codeRoute[4][1] = "D-50,L-96,D-100,R-241";
		routePokemonList[4][0] = "60-16,72-50,90-30,116-2,118-6,119-6,120-30,129-60";
		routePokemonBossList[4][0] = "117-100,121-100";
		pokeGenNum[4] = 2;
		
		mapSize[5][0] = mapSprite[5].getWidth()/Game.SCALE;
		mapSize[5][1] = mapSprite[5].getHeight()/Game.SCALE;
		locales[5] = "9,58-54,58-92,214-54,214-92,137-73,116-28,116-116,156-28,156-116,NULL/NULL/NULL/NULL/GHOST/NULL/NULL/NULL/NULL";
		codeRoute[5][0] = "L-0";
		codeRoute[5][1] = "D-241";
		routePokemonList[5][0] = "23-50,92-50,44-18,52-18,33-20,30-20,42-18,63-12";
		routePokemonBossList[5][0] = "93-200";
		pokeGenNum[5] = 2;
		
		mapSize[6][0] = mapSprite[6].getWidth()/Game.SCALE;
		mapSize[6][1] = mapSprite[6].getHeight()/Game.SCALE;
		mapSizeOffset[6][1] = 30;
		mapSizeOffset[6][0] = 30;
		locales[6] = "9,18-70,78-70,133-70,133-116,173-116,78-163,230-163,259-24,286-24,GRASS/NULL/NULL/BUG/NULL/NULL/BUG/NULL/NULL";
		codeRoute[6][0] = "R-160,D-142,L-63,D-208";
		codeRoute[6][1] = "U-142,R-256,D-208";
		codeRoute[6][2] = "D-30,R-250,U-5,R-278,D-38,R-306,U-18,R-321";
		routePokemonList[6][0] = "69-30,114-30,123-10,127-10,17-20,22-20,15-20,14-20,12-20,11-20";
		routePokemonBossList[6][0] = "115-68,18-66,70-66";
		
		routePokemonList[6][1] = "50-40,74-40,51-30,104-30,36-30,75-20,95-10";
		routePokemonBossList[6][1] = "111-68,105-66,76-66";
		pokeGenNum[6] = 3;
	}
	public void generateLvl() {
		PokeGenerator pGen0 = new PokeGenerator(124, 70, 5, 5, null, 0,Game.lvlConfig.get(Game.currentLvl).cooldown, Game.lvlConfig.get(Game.currentLvl).nGen, codeRoute[0][0], 0, 0);				
		PokeGenerator pGen1 = new PokeGenerator(224, 110, 5, 5, null, 1,Game.lvlConfig.get(Game.currentLvl).cooldown, Game.lvlConfig.get(Game.currentLvl).nGen, codeRoute[1][0], 0, 7);		
		PokeGenerator pGen2 = new PokeGenerator(101, 25, 5, 5, null, 2,Game.lvlConfig.get(Game.currentLvl).cooldown, Game.lvlConfig.get(Game.currentLvl).nGen, codeRoute[2][0], 0, 6);
		PokeGenerator pGen2p= new PokeGenerator(134, 25, 5, 5, null, 2,Game.lvlConfig.get(Game.currentLvl).cooldown, Game.lvlConfig.get(Game.currentLvl).nGen, codeRoute[2][0], 0, 6);
		PokeGenerator pGen3= new PokeGenerator(310, 74, 5, 5, null, 3,Game.lvlConfig.get(Game.currentLvl).cooldown, Game.lvlConfig.get(Game.currentLvl).nGen, codeRoute[3][0], 0, 1);
		PokeGenerator pGen3p= new PokeGenerator(177, 155, 5, 5, null, 3,Game.lvlConfig.get(Game.currentLvl).cooldown, Game.lvlConfig.get(Game.currentLvl).nGen, codeRoute[3][1], 0, 0);
		PokeGenerator pGen4= new PokeGenerator(92, 1, 5, 5, null, 4,Game.lvlConfig.get(Game.currentLvl).cooldown, Game.lvlConfig.get(Game.currentLvl).nGen, codeRoute[4][0], 0, 0);
		PokeGenerator pGen4p= new PokeGenerator(148, 1, 5, 5, null, 4,Game.lvlConfig.get(Game.currentLvl).cooldown, Game.lvlConfig.get(Game.currentLvl).nGen, codeRoute[4][1], 0, 0);
		PokeGenerator pGen5= new PokeGenerator(280, 73, 5, 5, null, 5,Game.lvlConfig.get(Game.currentLvl).cooldown, Game.lvlConfig.get(Game.currentLvl).nGen, codeRoute[5][0], 0, 0);
		PokeGenerator pGen5p= new PokeGenerator(144, 1, 5, 5, null, 5,Game.lvlConfig.get(Game.currentLvl).cooldown, Game.lvlConfig.get(Game.currentLvl).nGen, codeRoute[5][1], 0, 0);
		PokeGenerator pGen6= new PokeGenerator(1, 90, 5, 5, null, 6,Game.lvlConfig.get(Game.currentLvl).cooldown, Game.lvlConfig.get(Game.currentLvl).nGen, codeRoute[6][0], 0, 0);
		PokeGenerator pGen6p= new PokeGenerator(64, 206, 5, 5, null, 6,Game.lvlConfig.get(Game.currentLvl).cooldown, Game.lvlConfig.get(Game.currentLvl).nGen, codeRoute[6][1], 0, 0);
		PokeGenerator pGen6p2= new PokeGenerator(218, 20, 5, 5, null, 6,Game.lvlConfig.get(Game.currentLvl).cooldown, Game.lvlConfig.get(Game.currentLvl).nGen, codeRoute[6][2], 1, 0);
		
		Game.generateList.add(pGen0);
		Game.generateList.add(pGen1);
		Game.generateList.add(pGen2);
		Game.generateList.add(pGen2p);
		Game.generateList.add(pGen3);
		Game.generateList.add(pGen3p);
		Game.generateList.add(pGen4);
		Game.generateList.add(pGen4p);
		Game.generateList.add(pGen5);
		Game.generateList.add(pGen5p);
		Game.generateList.add(pGen6);
		Game.generateList.add(pGen6p);
		Game.generateList.add(pGen6p2);
		for(int i = 0; i < Game.generateList.size(); i++) {
			PokeGenerator g = Game.generateList.get(i);
			if(Game.currentLvl == Game.generateList.get(i).myLvl) {				
				Game.entities.add(g);
			}else {
				i--;
				Game.generateList.remove(g);
			}
		}
	}
	public void generateLocale() {
		String[] localesPos = Game.lvlConfig.get(Game.currentLvl).locales[Game.currentLvl].split(",");
		int[] lX = new int[Integer.parseInt(localesPos[0])];
		int[] lY = new int[Integer.parseInt(localesPos[0])];
		
		String[] buffs = localesPos[localesPos.length-1].split("/");
		
		
		for(int i = 0; i < Integer.parseInt(localesPos[0]); i++) {	
			String pos[] = localesPos[i+1].split("-");
			
			lX[i] = Integer.parseInt(pos[0]);
			lY[i] = Integer.parseInt(pos[1]);
			Locale en = new Locale(lX[i], lY[i], 40/3, 40/3, null, buffs[i]);
			//Game.ltst = en;
			Game.entities.add(en);
		}		
	}
	public void render(Graphics g) {
		if(Game.gameState == 1) {
			g.drawImage(mapSprite[Game.currentLvl], 0 - Camera.x ,0 - Camera.y, null);
			
		}
		
		
		
		
		String[] localesPos = locales[Game.currentLvl].split(",");
		
		int[] lX = new int[Integer.parseInt(localesPos[0])];
		int[] lY = new int[Integer.parseInt(localesPos[0])];
		
		for(int i = 0; i < Integer.parseInt(localesPos[0]); i++) {	
			String pos[] = localesPos[i+1].split("-");
			lX[i] = Integer.parseInt(pos[0]);
			lY[i] = Integer.parseInt(pos[1]);
		}
			
		
	}
	public void render2(Graphics g) {
		if(Game.gameState == 1) {
			g.drawImage(mapSprite2[Game.currentLvl], 0 - Camera.x ,0 - Camera.y, null);
			
		}
	}
	
	
	
	
			
	
	
}
