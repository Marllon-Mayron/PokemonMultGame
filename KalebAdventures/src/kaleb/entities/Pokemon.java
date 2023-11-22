package kaleb.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import kaleb.graphics.Spritesheet;
import kaleb.main.Game;
import kaleb.world.Camera;


public class Pokemon extends Entity {

	public int id;
	public String nm;
	public String myTypes[];
	public int[] myTypesNum = new int[2];
	private String ivsClassification;
	private int[] ivs = new int[6];
	public String[] ivsDesc = new String[6];
	public int[] evs = new int[6];
	public String[] stats = new String[6];

	//STATES BASE==============================
	public double spd;
	public double def;
	public double spDef;
	public double atk;
	public double spAtk;
	//=========================================
	//VELOCIDADE DO POKEMON NO MAPA
	public double spdInMap;
	//SOMA DE TODOS OS STATES, USADO PARA BALANCEAR NO TRADE
	public double totalStates;
	public double cooldown;
	public double cooldownFrame;
	public String status;
	
	//SISTEMA DE EVOLUCAO========================
	public int minLevelToEvolue;
	public int minHappinessToEvolue;
	public boolean evolueHappness;
	public boolean evoluelearnMove;
	public String moveToEvolue;
	public String itemToEvolue;
	public String triggerEvolution;
	//==========================================
	public String nature;
	public int natureId;

	public int lvl;
	public String userMessage = "";
	
	public boolean lowLife;
	//SABER SE O POKEMON ACABOU DE SER CAPTURADO
	public boolean catchNow;
	//SABER RARIDADE DO POKEMON
	public boolean isLegendary;
	public boolean isMythical;
	//SABER SE OS POKEMONS SÃO SELVAGEM
	public boolean isWild;
	//POKEMON QUE NÃO É CAPTURAVEL
	//POKEMON ZANGADOS
	public boolean isAngry;
	//POKEMON BOSS
	public boolean isBoss;
	//POKEMON DE TREINADOR;
	public boolean withOwner;
	//POKEMON SHINY
	public boolean isShiny;
	//SABER SE MEU POKEMON ESTÁ NO MAPA
	public boolean inMap;
	//SABER SE O POKEMON ESTÁ EM COOLDOWN
	public boolean isAttakingCooldown;
	//SABER SE MEU POKEMON ESTÁ NO LOCAL QUE FOI COLOCADO
	public boolean inInitialPosition = true;
	//INDO ATACAR
	public boolean goingTo;
	//CHEGOU NO LUGAR DE ATACAR
	public boolean arrived;
	//SELVAGEM SEM ALVO
	public boolean wildWithTarget;
	//SELVAGEM VOLTANDO
	public boolean wildBacking;
	//SABER SE MEU POKEMON ESTÁ BUFFADO DOS LOCAIS DE BUFF
	public boolean buffed;
	//ALVO QUE O POKEMON ESTÁ ATACANDO
	public Pokemon target;
	//SABER SE POKEMON ESTÁ SEGURANDO ITEM DE BATALHA
	public boolean isHoldding;
	public int holddingItem;
	public int timeHoldding;
	public int holdFrame;
	public int holdSec;
	//LISTA DE POKEMONS QUE ESTÃO ME ATACANDO
	public List<Pokemon> attakers;
	
	public double initialY;
	public double initialX;
	public double predictY;
	public double predictX;
	
	public int stepRoute;
	public String routes[];;

	// CALCULO DE BARRA DE VIDA===============================//
	public double maxHp;
	public double currentHp;
	public double porcentagemLife;//
	public double maxBarWidth; //
	public double barWidth;//
	// =======================================================//
	public double nextLevel;
	public double maxXp;
	public double currentXp;
	public double leftoverXp;
	public double porcentageXp;//
	public double maxBarWidthXp; //
	public double barWidthXp;//
	// =======================================================//
	public double xpDefault;
	public String dir = "DOWN";

	//IMAGENS
	private int frames = 0, maxFrames = 8, index = 0, maxIndex = 3;
	private BufferedImage[] right;
	private BufferedImage[] left;
	private BufferedImage[] up;
	private BufferedImage[] down;
	private BufferedImage holdIcon;
	public  Spritesheet spritesheet;
		
	public Pokemon(int x, int y, int width, int height, BufferedImage sprite, boolean isWild,int  id, int lvl, boolean indomitable) {
		super(x, y, width, height, sprite);
		this.inMap = true;
		this.x = x;
		this.y = y;
		this.width = 10;
		this.height = 10;
		this.rangeWidth = 50;
		this.rangeHeight = 50;
		this.isWild = isWild;
		this.isBoss = indomitable;
		this.isAngry = indomitable;
		
		this.id = id;
		
		if(Game.pokedex.getInfo(id, "legendary").equalsIgnoreCase("true")) {
			this.isLegendary = true;
		}else if(Game.pokedex.getInfo(id, "mythical").equalsIgnoreCase("true")) {
			this.isMythical = true;
		}
		 
		this.lvl = lvl;
		defineEvolutionMode();
		this.nm = Game.pokedex.getInfo(id, "name");
		this.natureId = Game.random.nextInt(20)+1;
		this.xpDefault = 3;
		
		
		attakers = new ArrayList<Pokemon>();
		if(isWild) {
			
			initialX = x;
			initialY = y;
			predictX = x;
			predictY = y;
			
			if(Game.random.nextInt(100) + 1 <= 15) {
				this.isAngry = true;
			}
			this.cooldown = 4;
			
		}else if(withOwner){
			this.isAngry = true;
			this.cooldown = 2.5;
		}else if(!isWild){
			this.cooldown = 1;
			
		}
		stats = Game.pokedex.getInfo(id, "baseStats").split(",");

		
		ivsGenerator();
		statsCalculator();
		
		this.currentHp = maxHp;
		
		natureCalculator();
		updateLife();
		updateXp();
		checkType();
		
		
			
		loadSprite();
		spritesheet = new Spritesheet("/PokemonSprites/battle-item/holdIcon.png") ;
		holdIcon = spritesheet.getSprite(0 , 0, 12, 16);
		
		
	}
	
	//NATIVO=============================================
	public void ivsGenerator() {
		int total = 0;
		for(int i = 0; i < 6; i++) {
			ivs[i] = Game.random.nextInt(32);
			
			if(ivs[i] == 0) {
				ivsDesc[i] = "No Good";
			}else if(ivs[i] >= 1 && ivs[i] <= 10) {
				ivsDesc[i] = "Decent";
			}else if(ivs[i] >= 11 && ivs[i] <= 20) {
				ivsDesc[i] = "Pretty Good";
			}else if(ivs[i] >= 21 && ivs[i] <= 30) {
				ivsDesc[i] = "Very Good";
			}else {
				ivsDesc[i] = "Best";
			}
			total = total + ivs[i];			
		}
		
		if(total <= 90) {
			ivsClassification = "OK stats!";
		}else if(total > 90 && total <= 120) {
			ivsClassification = "Good stats!";
		}else if(total > 120 && total <= 150) {
			ivsClassification = "Great stats!";
		}else if(total > 150 && total <= 186){
			ivsClassification="Amazing stats!";	
		}
	
		
	}
	public void statsCalculator() {
		int nvlHold = 2;
		int HoldItemAtk = 0;
		int HoldItemDef = 0;
		int HoldItemSpDef = 0;
		int HoldItemSpAtk = 0;
		
		if(holddingItem != 0) {
			int duration = Integer.parseInt(Game.itemController.getBattleItemInfo(holddingItem-1, "duration"));
			
			if(holddingItem == 1) {
				HoldItemAtk = nvlHold;
				timeHoldding = duration;
			}else if(holddingItem == 2) {
				HoldItemDef = nvlHold;
				timeHoldding = duration;
			}else if(holddingItem == 3) {
				HoldItemSpDef = nvlHold;
				timeHoldding = duration;
			}else if(holddingItem == 4) {
				HoldItemSpAtk = nvlHold;
				timeHoldding = duration;
			}
			
			if(!isHoldding) {
				isHoldding = true;
				//timeItem();
			}
		}
		//FORMULA: (vBase x 2 + 5) / 100 RETORNA QUANTO UPA POR NIVEL SE FOR HP, SUBSTITUE O 5 POR 110.
		this.maxHp = ((( (Double.parseDouble(stats[0])*2 + 110)+ ivs[0] + evs[0]/4) / 100) * (this.lvl));		
		this.atk = ((( (Double.parseDouble(stats[1])*2 + 5)+ ivs[1] + evs[1]/4) / 100) * (this.lvl+HoldItemAtk));
		this.def = ((( (Double.parseDouble(stats[2])*2 + 5)+ ivs[2] + evs[2]/4) / 100) * (this.lvl+HoldItemDef));
		this.spAtk = ((( (Double.parseDouble(stats[3])*2 + 5)+ ivs[3] + evs[3]/4) / 100) * (this.lvl+HoldItemSpAtk));
		this.spDef = ((( (Double.parseDouble(stats[4])*2 + 5)+ ivs[4] + evs[4]/4) / 100) * (this.lvl+HoldItemSpDef));
		this.spd = ((( (Double.parseDouble(stats[5])*2 + 5)+ ivs[5] + evs[5]/4) / 100) * (this.lvl));
		this.spdInMap = (Double.parseDouble(stats[5]) /120);
		this.maxXp = 10 * lvl;
		
		if(this.isShiny) {
			this.maxHp = this.maxHp + this.maxHp*5/100;
			this.atk = this.atk + this.atk*5/100;
			this.def = this.def + this.def*5/100;
			this.spAtk = this.spAtk + this.spAtk*5/100;
			this.spDef = this.spDef + this.spDef*5/100;
			this.spd = this.spd + this.spd*5/100;
			this.spdInMap = this.spdInMap + this.spdInMap*2/100;
		}
		double baseHp, baseAtk, baseDef, baseSpd, baseSpAtk, baseSpDef;
		baseHp = Double.parseDouble(stats[0]);
		baseAtk = Double.parseDouble(stats[1]);
		baseDef = Double.parseDouble(stats[2]);
		baseSpAtk = Double.parseDouble(stats[3]);
		baseSpDef = Double.parseDouble(stats[4]);
		baseSpd = Double.parseDouble(stats[5]);
		this.totalStates = baseHp + baseAtk + baseDef + baseSpAtk + baseSpDef + baseSpd;
	}
	public void timeItem(){
		holdFrame++;
		if(holdFrame == 60) {
			holdSec++;
			if(holdSec == timeHoldding) {
				holddingItem = 0;
				holdFrame = 0;
				holdSec = 0;
				isHoldding = false;
				statsCalculator();
				if(inMap && buffed) {
					aplicateBuffLocale(true);
				}
			}
			holdFrame = 0;
		}
		
	}
	public void updateLife() {
		this.porcentagemLife = currentHp * 100 / maxHp; //
		int barSize = 0;
		barSize = 20;
		if(isBoss && !withOwner) {
			barSize = 30;
		}
		this.maxBarWidth = barSize;
		this.barWidth = porcentagemLife / 100 * maxBarWidth;//
	}
	public void updateXp() {
		
		this.porcentageXp = currentXp * 100 / maxXp; //
		this.maxBarWidthXp = 20;//
		this.barWidthXp = porcentageXp / 100 * maxBarWidthXp;//
		
		
	}
	public double xpGainCalculator(int lvl) { 		
	    int lvlDifference = this.lvl - lvl;
	    double xpBase = this.lvl * this.xpDefault;
	    double xpBasePercent = xpBase*10/100;
	    double xpLvlDifficulty = xpBasePercent*(lvlDifference/2);
	    double xpFinal = ((xpBase + xpBasePercent) + xpLvlDifficulty * 20 / 100);	
	    if(isBoss) {
	    	if(withOwner) {
	    		xpFinal = xpFinal + xpFinal*30/100;
	    		xpFinal = xpFinal + (xpFinal * 10) /100;
	    	}else {
	    		xpFinal = xpFinal + xpFinal*30/100;
	    	}
	    	
		}
	    return xpFinal;
	}
	public void checkType() {
		String type[] = Game.pokedex.getInfo(this.id, "type").split("\"");
		
		int nType = 0;
		
		if(type.length > 3) {
			nType = 2;	
		}else {
			nType = 1;	
		}
		
		myTypes = new String[nType];
		
		if(nType > 1) {
			myTypes[0] = type[1]; 
			myTypes[1] = type[3];
		}else {
			myTypes[0] = type[1]; 
			 
		}
		
		for(int i = 0; i < nType; i++) {
			for(int i2 = 0; i2 < 18; i2++) {
				if(myTypes[i].equalsIgnoreCase(Game.pokedex.allTypes[i2])) {
					myTypesNum[i] = i2;
				}
			}
		}
		
	}
	public void natureCalculator() {
		if(natureId == 1) {
			nature = "Lonely";
			this.atk = atk + (atk * 10)/100;
			this.def = def - (def * 10)/100;
		}else if(natureId == 2) {
			nature = "Brave";
			this.atk = atk + (atk * 10)/100;
			this.spd = spd - (spd * 10)/100;
		}else if(natureId == 3) {
			nature = "Adamant";
			this.atk = atk + (atk * 10)/100;
			this.spAtk = spAtk - (spAtk * 10)/100;
		}else if(natureId == 4) {
			nature = "Naughty";
			this.atk = atk + (atk * 10)/100;
			this.spDef = spDef - (spDef * 10)/100;
		}else if(natureId == 5) {
			nature = "Bold";
			this.def = def + (def * 10)/100;
			this.atk = atk - (atk * 10)/100;
		}else if(natureId == 6) {
			nature = "Relaxed";
			this.def = def + (def * 10)/100;
			this.spd = spd - (spd * 10)/100;
		}else if(natureId == 7) {
			nature = "Impish";
			this.def = def + (def * 10)/100;
			this.spAtk = spAtk - (spAtk * 10)/100;
		}else if(natureId == 8) {
			nature = "Lax";
			this.def = def + (def * 10)/100;
			this.spDef = spDef - (spDef * 10)/100;
		}else if(natureId == 9) {
			nature = "Timid";
			this.spd = spd + (spd * 10)/100;
			this.atk = atk - (atk * 10)/100;
		}else if(natureId == 10) {
			nature = "Hasty";
			this.spd = spd + (spd * 10)/100;
			this.def = def - (def * 10)/100;
		}else if(natureId == 11) {
			nature = "Jolly";
			this.spd = spd + (spd * 10)/100;
			this.spAtk = spAtk - (spAtk * 10)/100;
		}else if(natureId == 12) {
			nature = "Naive";
			this.spd = spd + (spd * 10)/100;
			this.spDef = spDef - (spDef * 10)/100;
		}else if(natureId == 13) {
			nature = "Modest";
			this.spAtk = spAtk + (spAtk * 10)/100;
			this.atk = atk - (atk * 10)/100;
		}else if(natureId == 14) {
			nature = "Mild";
			this.spAtk = spAtk + (spAtk * 10)/100;
			this.def = def - (def * 10)/100;
		}else if(natureId == 15) {
			nature = "Quiet";
			this.spAtk = spAtk + (spAtk * 10)/100;
			this.spd = spd - (spd * 10)/100;
		}else if(natureId == 16) {
			nature = "Rash";
			this.spAtk = spAtk + (spAtk * 10)/100;
			this.spDef = spDef - (spDef * 10)/100;
		}else if(natureId == 17) {
			nature = "Calm";
			this.spDef = spDef + (spDef * 10)/100;
			this.atk = atk - (atk * 10)/100;
		}else if(natureId == 18) {
			nature = "Gentle";
			this.spDef = spDef + (spDef * 10)/100;
			this.def = def - (def * 10)/100;
		}else if(natureId == 19) {
			nature = "Sassy";
			this.spDef = spDef + (spDef * 10)/100;
			this.spd = spd - (spd * 10)/100;
		}else if(natureId == 20) {
			nature = "Careful";
			this.spDef = spDef + (spDef * 10)/100;
			this.spAtk = spAtk - (spAtk * 10)/100;
		}
	}
	private void nextLevel() {
		lvl++;
		leftoverXp = currentXp - maxXp;
		currentXp = leftoverXp;
		if(triggerEvolution.equalsIgnoreCase("level-up") && !evolueHappness) {
			if(this.lvl >= minLevelToEvolue) {
				evolue();
			}
		}
		
		statsCalculator();
		natureCalculator();
		currentHp = currentHp + (( (Double.parseDouble(stats[0])*2 + 110)+ ivs[0] + evs[0]/4) / 100);
		updateLife();
		updateXp();
	}
	private void defineEvolutionMode() {
		this.triggerEvolution = Game.pokedex.getInfo(this.id, "trigger");
		if(triggerEvolution.equalsIgnoreCase("level-up")) {
			
			String minLvl = Game.pokedex.getInfo(this.id, "minLevel");
			String minHappy = Game.pokedex.getInfo(this.id, "minHappness");
			moveToEvolue = Game.pokedex.getInfo(this.id, "known_move");
			
			if(!minLvl.equalsIgnoreCase("null")) {
				this.minLevelToEvolue = Integer.parseInt(minLvl);
				
			}else if(!minHappy.equalsIgnoreCase("null")){		
				this.evolueHappness = true;
				this.minHappinessToEvolue = Integer.parseInt(minHappy);
				
			}else if(!moveToEvolue.equalsIgnoreCase("null")) {
				this.evoluelearnMove = true;
				
			}
					
		}else if(triggerEvolution.equalsIgnoreCase("use-item")) {
			if(this.id == 133) {
				itemToEvolue = Game.pokedex.getInfo(this.id, "stone");
			}else {
				itemToEvolue = Game.pokedex.getInfo(this.id, "item_name");
			}
			
		}
	}
	public void evolue() {
		String[] allMembers = Game.pokedex.getInfo(id, "members").split(",");
		int myOrderNum = 0;
		int idEvolue = 0;
		if(this.id != 133) {
			for(int i = 0; i < allMembers.length; i++) {
				if(this.nm.equalsIgnoreCase(allMembers[i])) {
					myOrderNum = i;
					break;
				}
			}
			for(int j = 0; j < Game.pokedex.maxId-1; j++) {
				if(allMembers[myOrderNum+1].equalsIgnoreCase(Game.pokedex.getInfo(j, "name"))) {
					idEvolue = j;
					break;
				}
			}
		}else {
			if(Player.evolueItemList.get(Game.ui.nColumn).name.equalsIgnoreCase("water-stone")) {
				idEvolue = 134;
			}else if(Player.evolueItemList.get(Game.ui.nColumn).name.equalsIgnoreCase("thunder-stone")) {
				idEvolue = 135;
			}else if(Player.evolueItemList.get(Game.ui.nColumn).name.equalsIgnoreCase("fire-stone")) {
				idEvolue = 136;
			}
		}
		
		
		this.id = idEvolue;
		this.nm = Game.pokedex.getInfo(this.id, "name");
		this.triggerEvolution = Game.pokedex.getInfo(this.id, "trigger");
		if(triggerEvolution.equalsIgnoreCase("level-up")) {
			this.minLevelToEvolue = Integer.parseInt(Game.pokedex.getInfo(this.id, "minLevel"));			
		}
		String happy = Game.pokedex.getInfo(this.id, "min_happiness");
		if(!happy.equalsIgnoreCase("")) {
			this.minHappinessToEvolue = Integer.parseInt(happy);
		}
		this.evolueHappness = false;
		this.itemToEvolue = Game.pokedex.getInfo(this.id, "item_name");
		this.triggerEvolution = Game.pokedex.getInfo(this.id, "trigger");
		stats = Game.pokedex.getInfo(id, "baseStats").split(",");
		loadSprite();
		updateLife();
		updateXp();
	}
	//OCASIONAIS=========================================
	public void aplicateBuffLocale(boolean buff){
		if(buff) {
			this.atk = this.atk + this.atk *10/100;
			this.def = this.def + this.def*10/100;
			this.spAtk = this.spAtk + this.spAtk*10/100;
			this.spDef = this.spDef + this.spDef*10/100;
			buffed = true;
		}else {
			statsCalculator();
			buffed = false;
		}
		
	}
	public void domesticPokemon() {
		this.cooldown = 1;
		
		this.catchNow = true;
		this.isWild = false;
		this.inMap = false;
		Game.pokeList.add(this);
	}
	public void tick() {	
		if(isHoldding && Game.gameState.equalsIgnoreCase("catch")) {
			timeItem();
		}
		cooldownTick();
		if(!isWild) {
			//System.out.println(nm+isWild+cooldown);
		}
		
		//QUANDO ESSE POKEMON PODE SER CAPTURAVEL
		if(this.currentHp <= this.maxHp*25/100) {
			lowLife = true;
		}else {
			if(!isShiny) {
				lowLife = false;	
			}		
		}
		if(inMap) {
			//CARREGAR ANIMAÇÕES
			framesAnimations();
			
			//===========================================================
			//IR ATACAR 
			goingTarget();
			//VOLTAR DO ATAQUE
			
			if(goingTo == false) {
				
				if(isWild) {	
					
					if(!wildWithTarget) {
						//CONTROLAR ROTA
						
					}else {
						if(wildBacking) {
							backToRoute();
						}
					}
				}else {
					goingLocale();	
				}
			}
			if(isWild) {
				
				routeController();
				//DESTRUIR OBJ CASO SAI DO MAPA
				checkExit();
				//MOVIMENTAÇÃO DOS SELVAGEM
				wildWalk();
				
				checkColision(isWild);
				
			}else {
				if(barWidthXp >= maxBarWidthXp) {
					nextLevel();
				}
				//CHECAR COLISÃO
				checkColision(isWild);
			}
		}
	}
	//SISTEMA DE ATAQUE==================================
	private void cooldownTick() {
		if(isAttakingCooldown) {
			cooldownFrame++;
			if(cooldownFrame >= cooldown*60) {
				isAttakingCooldown = false;
				cooldownFrame = 0;
			}
		}
	}
	private void checkColision(boolean myIsWild) {
		for(int i = 0; i < Game.entities.size(); i++) {				
			if(Game.entities.get(i) instanceof Pokemon){
				Pokemon e = (Pokemon) Game.entities.get(i);
				if(e == this) {
					continue;
				}
				if(Entity.isColidding(this, e)) {
					if((!myIsWild && arrived && e.isWild) || (myIsWild && arrived && !e.isWild && isAngry && !e.goingTo)) {
						
						atackPokemon(e, e.isWild);
					}
				}
				if(e.isWild != myIsWild && Entity.isColiddingAtk(this, e)) {
					//CHECAR SE PODE ATACAR
					if((!myIsWild && this.isAttakingCooldown == false && inInitialPosition) || (myIsWild && this.isAttakingCooldown == false && isAngry)) {
						wildWithTarget = true;
						populateAttakersList(e);
						target = e;	
					}
					if(e.catchNow && !myIsWild) {
						Game.lvlConfig.get(Game.currentLvl).totalDefeat++;
						Game.lvlConfig.get(Game.currentLvl).myVictorys++;
						DestroySelf(e);
					}
				}
			}				
		}
	}
	private void populateAttakersList(Pokemon e) {
		if(e.attakers.size() == 0) {
			e.attakers.add(this);
		}
		
		goingTo = true;	
		inInitialPosition = false;
		
		boolean here = false;
		//LISTAR POKEMONS QUE ESTÃO ATACANDO, PARA QUANDO O ALVO MORRER, AVISAR A TODOS QUE ELE MORREU
		for(int i2 = 0; i2 < e.attakers.size(); i2++){
			if(e.attakers.get(i2) == this) {
				here = true;
				break;
			}
		}
		if(!here) {
			e.attakers.add(this);
		}
		
	}
	private void atackPokemon(Pokemon e, boolean targetIsWild) {
		if(targetIsWild) {
			//CALCULO DE DANO===============================
			
			goingTo = false;
			double pDamage = ((((this.lvl*2)/5)* this.atk) / e.def) + 0.3;
			if(pDamage <= 0.3) {
				pDamage = 0.3;
			}
			//==============================================
			if(e.currentHp - pDamage <= 0) {
				for(int i2 = 0; i2 < e.attakers.size(); i2++){	
					e.attakers.get(i2).target = null;									
				}
				defeatEnemy(e);
				if(!e.withOwner) {
					Game.lvlConfig.get(Game.currentLvl).totalDefeat++;
					Game.lvlConfig.get(Game.currentLvl).myVictorys++;
					DestroySelf(e);
				}else {
					defeat(e);
				}
				
				
			}else {
				e.currentHp -= pDamage ;
				e.updateLife();
			}	
			
			
		}else {
			if(isWild) {
				wildBacking = true;
			}
			goingTo = false;
			double pDamage = ((((this.lvl*2)/5)* this.atk) / e.def) + 0.3;
			if(pDamage <= 0.3) {
				pDamage = 0.3;
			}
			if(e.currentHp - pDamage <= 0) {
				for(int i2 = 0; i2 < e.attakers.size(); i2++){	
					e.attakers.get(i2).target = null;									
				}
				for(int i = 0; i < 6; i++) {
					if(Game.slotList.get(i).pokemon != null){
						if(Game.slotList.get(i).pokemon == e) {
							Game.slotList.get(i).slotStatus = "defeat";
							break;
						}
					}
				}
				for(int i = 0; i < Game.entities.size(); i++) {				
					if(Game.entities.get(i) instanceof Locale ){
						Locale l = (Locale) Game.entities.get(i);
						if(l.pokemon == e) {
							l.pokemon = null;
						}
					}
				}
				defeat(e);
			}else {
				e.currentHp -= pDamage ;
				e.updateLife();
			}
			//==============================================
		}
		arrived = false;								
		isAttakingCooldown = true;
	}
	public void defeatEnemy(Pokemon defeated) {
		if(defeated.withOwner) {
			Game.player.money += 10;
			Game.player.score += 10;
		}else if(defeated.isBoss) {
			Game.player.money += 6;
			Game.player.score += 6;
		}else {
			Game.player.money += 3;
			Game.player.score += 3;
		}
		if(Game.gameMode.equalsIgnoreCase("multi")) {
			Game.cliente.info = "updateScore";
			
			Game.cliente.score[0] = Game.player.score;
		}
		
		
		
		
		for(int i = 0; i < defeated.attakers.size(); i++) {	 
			defeated.attakers.get(i).currentXp+=defeated.xpGainCalculator(defeated.attakers.get(i).lvl) / defeated.attakers.size();; 			
			defeated.attakers.get(i).updateXp();
			String[] evs = new String[6];
			evs = Game.pokedex.getInfo(defeated.id, "evs").split(",");
			for(int i2 = 0; i2 < 6; i2++) {
				defeated.attakers.get(i).evs[i2] = defeated.attakers.get(i).evs[i2] + Integer.parseInt(evs[i2]);
			}
			if(defeated.attakers.get(i).barWidthXp >= defeated.attakers.get(i).maxBarWidthXp) {
				defeated.attakers.get(i).nextLevel();	
			}
		}	
	}
	//MOVIMENTAÇÃO=======================================
	public void resetExit() {
		this.frames = 0;
		this.index = 0;
		this.dir = "DOWN";
	}
	private void wildWalk() {
		if (dir.equalsIgnoreCase("UP")) {
			if(!wildWithTarget) {
				setY(getY() - spdInMap);
				initialY = getY();
			}
			
		} else if (dir.equalsIgnoreCase("DOWN")) {
			if(!wildWithTarget) {
				setY(getY() + spdInMap);
				initialY = getY();
			}
		} else if (dir.equalsIgnoreCase("LEFT")) {
			if(!wildWithTarget) {
				setX(getX() - spdInMap);
				initialX = getX();
			}
		} else if (dir.equalsIgnoreCase("RIGHT")) {
			if(!wildWithTarget) {
				setX(getX() + spdInMap);
				initialX = getX();
			}
		} else {

		}
	}
	private void checkExit() {
		if (getX() <= 0 || getX() > Game.lvlConfig.get(0).mapSize[Game.currentLvl][0] || y < 0 || y > Game.lvlConfig.get(0).mapSize[Game.currentLvl][1]) {
			if(!withOwner) {
				Game.lvlConfig.get(Game.currentLvl).totalDefeat++;
			}
			
			this.DestroySelf(this);	
			for(int i = 0; i < attakers.size(); i++) { 
				attakers.get(i).target = null;
			}
		}	
	}
	private void backToRoute() {
		if(((int)this.x == (int)predictX ) && ((int)this.y == (int)predictY)) {
			inInitialPosition = true;
			wildBacking = false;
			wildWithTarget = false;	
			
		}
		if(this.x < predictX) {
			this.x++;
		}else if(this.x > predictX){
			this.x--;
		}
		
		if(this.y < predictY) {
			this.y++;
		}else if(this.y > predictY){
			this.y--;
		}
		
	}
	private void goingLocale() {
		if(this.x == initialX && this.y == initialY) {
			inInitialPosition = true;
		}
		
		if(this.x < initialX) {
			this.x++;
		}else if(this.x > initialX){
			this.x--;
		}
		
		if(this.y < initialY) {
			this.y++;
		}else if(this.y > initialY){
			this.y--;
		}
	}
	private void goingTarget() {
		if(target != null) {
			
			if(this.x - target.x > this.y - target.y) {
				if(this.y > target.y) {
					dir = "UP";
				}else {
					dir = "DOWN";
				}
			}else {
				if(this.x > target.x) {
					dir = "LEFT";
				}else {
					dir = "RIGHT";
				}
			}
			if(goingTo) {
				if(this.calculateDistance((int)x, (int)y, (int)target.x, (int)target.y) > 1) {
					arrived = true;
				}
				
				if(this.x < target.x) {
					this.x+=2;
				}else if(this.x > target.x){
					this.x-=2;
				}
				
				if(this.y < target.y) {
					this.y+=2;
				}else if(this.y > target.y){
					this.y-=2;
				}	
			};
		}else {
			goingTo = false;
			dir = "DOWN";
		}
	}
	private void routeController() {
		String dirRoute[] = routes[stepRoute].split("-");
		if (dirRoute[0].equalsIgnoreCase("L")) {
			dir = "LEFT";
			predictX = predictX - spdInMap;
			if (getX() <= Integer.parseInt(dirRoute[1])) {
				stepRoute++;
				
			}
		} else if (dirRoute[0].equalsIgnoreCase("R")) {
			dir = "RIGHT";
			predictX = predictX + spdInMap;
			if (getX() >= Integer.parseInt(dirRoute[1])) {
				stepRoute++;
			}
		} else if (dirRoute[0].equalsIgnoreCase("U")) {
			dir = "UP";
			predictY = predictY - spdInMap;
			if (y <= Integer.parseInt(dirRoute[1])) {
				stepRoute++;
			}
		} else if (dirRoute[0].equalsIgnoreCase("D")) {
			dir = "DOWN";
			predictY = predictY + spdInMap;
			if (y >= Integer.parseInt(dirRoute[1])) {
				stepRoute++;
			}
		}
		 
	}
	//GRAFICOS===========================================
	public void loadSprite() {
		
		//ORGANIZAR NOME DO SPRITE
		String tempUrl ="";
		if(id < 10) {
			tempUrl = "00";
		}else if (id >= 10 && id < 100) {
			tempUrl = "0";
		}
		//SHINY CONFIG	
		String shinyUrl = "";
		if(isShiny) {
			shinyUrl = "s";
		}
		spritesheet = new Spritesheet("/PokemonSprites/InMaps/"+tempUrl+this.id+shinyUrl+".png");
		right = new BufferedImage[4];
		left = new BufferedImage[4];
		up = new BufferedImage[4];
		down = new BufferedImage[4];
		
		for (int i = 0; i < 4; i++) {
			up[i] = spritesheet.getSprite(0 + (i * 64), 192, 64, 64);
			down[i] = spritesheet.getSprite(0 + (i * 64),0, 64, 64);
			right[i] = spritesheet.getSprite(0 + (i * 64), 128, 64, 64);
			left[i] = spritesheet.getSprite(0 + (i * 64), 64, 64, 64);	
		}
	}
	private void framesAnimations() {
		frames++;
		if (frames == maxFrames) {
			frames = 0;
			index++;
			if (index > maxIndex) {
				index = 0;
			}
		}
	}
	public void render(Graphics g) {
		if((isWild && Game.configs.showCorrectRoutes)) {
			g.setColor(Color.red);
			g.drawRect((int)predictX * 3 - 12 - Camera.x,(int) predictY * 3 - Camera.y,20, 20);
		}
		
		if(inMap) {
			// BARRA DE VIDA
			Color darkRed = new Color(180, 0, 0);
			Color darkGreen = new Color(0, 180, 0);
			Color darkBlue = new Color(0, 0, 180);
			
			Color lifeColor = null;
			Color lifeColorLight = null;
			
			
			if(!lowLife) {
				lifeColor = darkGreen;
				lifeColorLight = Color.GREEN;
			}else {
				lifeColor = darkRed;
				lifeColorLight = Color.RED;
			}
			if(isBoss || withOwner) {
				lifeColor = darkBlue;
				lifeColorLight = Color.BLUE;
			}
			int xTemp = 0;
			int yTemp = 0;
			if(isWild) {
				xTemp = (int)this.getX();
				yTemp = (int)this.getY();
			}else {
				xTemp = (int)initialX;
				yTemp = (int)initialY;
			}
			g.setColor(Color.BLACK);
			g.fillRect(xTemp*Game.SCALE - (int)(maxBarWidth/2)*Game.SCALE -Camera.x, yTemp*Game.SCALE - 9*Game.SCALE -Camera.y, (int) maxBarWidth*3, 3*Game.SCALE);
			g.setColor(lifeColorLight);
			g.fillRect(xTemp*Game.SCALE - (int)(maxBarWidth/2)*Game.SCALE -Camera.x, yTemp*Game.SCALE - 9 *Game.SCALE -Camera.y, (int) barWidth*3, 2*Game.SCALE);
			g.setColor(lifeColor);
			g.fillRect(xTemp*Game.SCALE - (int)(maxBarWidth/2)*Game.SCALE -Camera.x, yTemp*Game.SCALE - 8 *Game.SCALE -Camera.y, (int) barWidth*3, 2*Game.SCALE);		
			//HOLD ITEM
			if(isHoldding) {
				g.drawImage(holdIcon,(xTemp - 5 )*Game.SCALE - (int)(maxBarWidth/2)*Game.SCALE -Camera.x, (int)((yTemp + 0.6) *Game.SCALE) - 9*Game.SCALE -Camera.y , null);
			}
			// BARRA DE XP
			if(!isWild) {
				g.setColor(Color.BLACK);
				g.fillRect(xTemp*Game.SCALE - (int)(maxBarWidthXp/2)*Game.SCALE -Camera.x, (int) yTemp*Game.SCALE - 5*Game.SCALE -Camera.y, (int) maxBarWidthXp*3, 2*Game.SCALE);
				g.setColor(Color.blue);
				g.fillRect(xTemp*Game.SCALE - (int)(maxBarWidthXp/2)*Game.SCALE -Camera.x, (int) yTemp*Game.SCALE - 5*Game.SCALE -Camera.y, (int) barWidthXp*3, 1*Game.SCALE);
				g.setColor(darkBlue);
				g.fillRect(xTemp*Game.SCALE - (int)(maxBarWidthXp/2)*Game.SCALE -Camera.x, (int) yTemp*Game.SCALE - 4*Game.SCALE -Camera.y, (int) barWidthXp*3, 1*Game.SCALE);		
					
			}
			
			
			
			if(Game.configs.activeDesc == true) {
				g.setColor(Color.gray);
				g.fillRect((int) getX() * Game.SCALE - 60 -Camera.x, ((int) y * Game.SCALE) - 10*12 -Camera.y, 120, 94);				
				g.setColor(Color.black);
				g.drawRect((int) getX() * Game.SCALE - 62 -Camera.x, ((int) y * Game.SCALE) - (int)(10*12.2) -Camera.y, 120, 94);							
				g.drawString(ivsClassification, (int) getX() * Game.SCALE - 60 -Camera.x, ((int) y * Game.SCALE) - 10*11 -Camera.y);
				g.drawString(nm+" nvl: "+ lvl, (int) getX() * Game.SCALE - 60 -Camera.x, ((int) y * Game.SCALE) - 10*10 -Camera.y);
				g.drawString("Nature: " + nature, (int) getX() * Game.SCALE - 60 -Camera.x, ((int) y * Game.SCALE) - 10*9 -Camera.y);		
				g.drawString("Hp: " + Game.configs.format.format(currentHp)+" / "+Game.configs.format.format(maxHp) +"--"+ivsDesc[0], (int) getX() * Game.SCALE - 60 -Camera.x, ((int) y * Game.SCALE) - 10*8 -Camera.y);		
				g.drawString("Atk: " + Game.configs.format.format(atk)+"--"+ivsDesc[1], (int) getX() * Game.SCALE - 60 -Camera.x, ((int) y * Game.SCALE) - 10*7 -Camera.y);	
				g.drawString("Def: " + Game.configs.format.format(def)+"--"+ivsDesc[2], (int) getX() * Game.SCALE - 60 -Camera.x, ((int) y * Game.SCALE) - 10*6 -Camera.y);
				g.drawString("SpAtk: " + Game.configs.format.format(spAtk)+"--"+ivsDesc[3], (int) getX() * Game.SCALE - 60 -Camera.x, ((int) y * Game.SCALE) - 10*5 -Camera.y);
				g.drawString("SpDef: " + Game.configs.format.format(spDef)+"--"+ivsDesc[4], (int) getX() * Game.SCALE - 60 -Camera.x, ((int) y * Game.SCALE) - 10*4 -Camera.y);
				g.drawString("Spd: " + Game.configs.format.format(spdInMap)+" / "+Game.configs.format.format(spd)+"--"+ivsDesc[5], (int) getX() * Game.SCALE - 60 -Camera.x, ((int) y * Game.SCALE) - 10*3 -Camera.y);
		        // Formate o número com o número de casas decimais desejadas
			}
		}
		
	}
	public void renderStr(Graphics g) {
		if(inMap) {
			if(Game.configs.activeRange == true) {
				g.setColor(Color.yellow);
				g.fillRect(((int)this.getX()*Game.SCALE) - (this.getRangeWidth() *Game.SCALE) / 2 -Camera.x,(int)this.getY()*Game.SCALE - (this.getRangeHeight()*Game.SCALE) / 2 + (this.getHeight()*Game.SCALE)/2 -Camera.y, this.getRangeWidth()*Game.SCALE, this.getRangeHeight()*Game.SCALE);
				g.setColor(Color.red);
				g.fillRect(((int)this.getX()*Game.SCALE) - (this.getWidth()*Game.SCALE) / 2 -Camera.x,(int)this.getY()*Game.SCALE -Camera.y, this.getWidth()*Game.SCALE , this.getHeight()*Game.SCALE);
				
			}
			
			g.drawString(userMessage, (int) getX() * Game.SCALE - 15 -Camera.x, ((int) y * Game.SCALE) - 10*3 -Camera.y);
	        	
			if(isShiny) {
				g.setColor(Color.YELLOW);
			}else {
				g.setColor(Color.red);
			}
			if (dir.equalsIgnoreCase("UP")) {
				g.drawImage(up[index], (int)getX()*Game.SCALE -32 -Camera.x, (int)y*Game.SCALE -32 -Camera.y, null);
			} else if (dir.equalsIgnoreCase("DOWN")) {
				g.drawImage(down[index], (int)getX()*Game.SCALE -32 -Camera.x, (int)y*Game.SCALE -32 -Camera.y, null);;
			} else if (dir.equalsIgnoreCase("LEFT")) {
				g.drawImage(left[index], (int)getX()*Game.SCALE -32 -Camera.x, (int)y*Game.SCALE -32 -Camera.y, null);
			} else if (dir.equalsIgnoreCase("RIGHT")) {
				g.drawImage(right[index], (int)getX()*Game.SCALE -32 -Camera.x, (int)y*Game.SCALE -32 -Camera.y, null);
			}
			
			
		}
		
		
	}

}
