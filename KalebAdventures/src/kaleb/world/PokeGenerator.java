package kaleb.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import kaleb.entities.Entity;
import kaleb.entities.Pokemon;
import kaleb.main.Game;

public class PokeGenerator extends Entity{

	public int secondForStart = 180;
	public boolean start = false;
	public double cooldown;
	public int maxNum;
	public int currentNum = 0;
	public boolean loading;
	public int time;
	public int lvlMin;
	public int lvlMax;
	public int myLvl;
	public String routes;
	public int numType;
	int nRouteList;
	public PokeGenerator(int x, int y, int width, int height, BufferedImage sprite, int lvl, double cooldown, int maxNum, String routes,int nRouteList,int numType) {
		super(x, y, width, height, sprite);
		// TODO Auto-generated constructor stub
		this.cooldown = cooldown;
		this.maxNum = maxNum;
		this.lvlMin = Game.lvlConfig.get(lvl).minLvl;
		this.lvlMax = Game.lvlConfig.get(lvl).maxLvl;
		this.myLvl = lvl;
		this.routes = routes;
		this.nRouteList = nRouteList;
		this.numType = numType;
	}

	public void tick() {
		if(start) {
			//TEMPO ENTRE CADA NOVA GERAÇÃO
			
			time++;
			if(currentNum < maxNum) {
				if(time == 60*cooldown ) {
					boolean bossNow = false;
					int nvlPkm = 0;
					if(currentNum == maxNum-1) {
						bossNow = true;
						nvlPkm = Game.lvlConfig.get(Game.currentLvl).maxLvl;
					}else {
						nvlPkm = this.lvlMin + Game.random.nextInt(((Game.lvlConfig.get(Game.currentLvl).maxLvl) - (Game.lvlConfig.get(Game.currentLvl).minLvl))+1);
					}
					
					
					Pokemon pokemon = new Pokemon((int)getX(), (int)y , 2, 2, null, true,whatGenerate(bossNow, nRouteList),nvlPkm, bossNow);
					pokemon.routes = routes.split(",");
					Game.entities.add(pokemon);	
					Game.waveList.add(pokemon);
					time = 0;
					currentNum++;
				}
			}else{
				Game.generateList.remove(this);
				Game.entities.remove(this);				
			}
			
		}else {
			//ESPERANDO PARA COMEÇAR A GERAR 
			if(time < secondForStart) {
				time++;
			}else{
				time = 0;
				start = true;
			}
		}
	}
	public int whatGenerate(boolean boss, int nRouteList) {
		String encounters[] = null;
		if(boss) {
			encounters = Game.lvlConfig.get(Game.currentLvl).routePokemonBossList[Game.currentLvl][nRouteList].split(",");
		}else {
			encounters = Game.lvlConfig.get(Game.currentLvl).routePokemonList[Game.currentLvl][nRouteList].split(",");	
		}
				
		
		int num = Game.random.nextInt(200)+1;
		int maxEnc = encounters.length;
		int total = 0;
		for(int i = 0; i < maxEnc; i++) {
			String pokemons[] = encounters[i].split("-");
			int id = Integer.parseInt(pokemons[0]);
			int prob = Integer.parseInt(pokemons[1]);
			if(num > total  && num <= total + prob) {
				return id;
			}else{
				total = total + prob;
			}
		}
		return 0;
		
		
		
		
		
		
		
	}
	
	public void render(Graphics g) {
		g.setColor(Color.blue);
		//g.fillRect((int)getX(), (int)y, width, height);
		
	}

}
