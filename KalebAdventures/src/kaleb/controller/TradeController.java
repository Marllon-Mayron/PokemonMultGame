package kaleb.controller;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import kaleb.entities.Pokemon;
import kaleb.entities.SlotTrade;
import kaleb.main.Game;

public class TradeController {
	public List<Pokemon> sameOfferList = new ArrayList<Pokemon>();
	Pokemon requested, offered;
	public Pokemon chosen;
	public boolean offertOn = true;
	
	public List<SlotTrade> slotList = new ArrayList<SlotTrade>();
	public TradeController() {
		createNewSlotList();
	}
	public void createNewSlotList() {
		for(int i = 0; i < 6; i++) { 
			SlotTrade st = new SlotTrade((Game.WIDTH * 14 / 100) * Game.SCALE + (Game.WIDTH * ((i) * 10) / 100) * Game.SCALE+ (i + 1) * (Game.WIDTH * 2 / 100) * Game.SCALE , ((Game.HEIGHT * 81 / 100) * Game.SCALE) + 2,(Game.WIDTH * 10 / 100) * Game.SCALE, 70, null);
			slotList.add(st);
		}
	}
	//CRIAR A TRADE DE NPC
	public void populateNpcTrade() {		
		Game.ui.pkmOffer.clear();
		int tradeOption = 0;
		//0 = pokemon aleatorio, 1 - pokemon da minha lista, 2 - pokemons que ja passaram no mapa.
		if(Game.random.nextInt(100)+1 < 101) {
			tradeOption = 1;
		}
		//LOOP PARA TRADE NÃO ENVOLVER LENDARIOS/MITICOS, E ENCONTRAR TROCAS JUSTAS
		while(true) {
			
			while(true) {
				offered = new Pokemon(0, 0, 5, 5, null, false, Game.random.nextInt(Game.pokedex.maxId-1)+1, 5, false);
				if(!(offered.isLegendary || offered.isMythical)) {
					break;
				}
			}
			while(true) {
				if(tradeOption == 1) {
					int n = Game.random.nextInt(Game.pokeList.size());
					Pokemon p = Game.pokeList.get(n);
					requested = new Pokemon(0, 0, 5, 5, null, false, p.id , 5, false);
					if(!(requested.isLegendary || requested.isMythical)) {
						break;
					}
				}else{
					requested = new Pokemon(0, 0, 5, 5, null, false, Game.random.nextInt(Game.pokedex.maxId-1)+1, 5, false);
					if(!(requested.isLegendary || requested.isMythical)) {
						break;
					}
				}					
			}
			if(offered.totalStates >= requested.totalStates - 30 && offered.totalStates <= requested.totalStates + 30) {
				break;
			}
		}
		Game.tradeController.offertOn = true;
		Game.ui.pkmOffer.add(offered);
		Game.ui.pkmOffer.add(requested);
	}
	public void populateSameOffer(){
		slotList.clear();
		createNewSlotList();
		sameOfferList.clear();
		for(int i = 0; i < Game.pokeList.size(); i++) {
			if(Game.pokeList.get(i).id == requested.id) {
				for(int i2 = 0; i2 < slotList.size(); i2++) {
					if(slotList.get(i2).pokemon == null) {
						slotList.get(i2).pokemon = Game.pokeList.get(i);
						break;
					}
				}
				sameOfferList.add(Game.pokeList.get(i));
			}
		}
	}
	//METODO CHAMADO QUANDO APERTAR O BTN TROCA
	public void tradeAccept(Pokemon pkm) {
		for(int i = 0; i < Game.pokeList.size(); i++) {
			if(pkm.equals(Game.pokeList.get(i))) {
				for(int j = 0; j < 6; j++) {
					if(Game.slotList.get(j).pokemon.equals(pkm)) {
						offered.lvl = pkm.lvl; 
						offered.evs = pkm.evs;
						offered.currentHp = offered.maxHp;
						
						offered.statsCalculator();
						offered.updateLife();
						offered.updateXp();
						
						Game.pokeList.set(i, offered);
						for(int k = 0; k < Game.slotPcList.size(); k++) {
							if(Game.slotPcList.get(k).pokemon.equals(pkm)) {
								Game.slotPcList.get(k).pokemon = offered;							
								break;
							}
						}
						Game.slotList.get(j).pokemon = offered;
						break;
					}
				}
				break;
				
			}
		}
		offertOn = false;
		sameOfferList.clear();
		for(int i = 0; i < slotList.size(); i++) {
			slotList.get(i).pokemon = null;
		}
	}
	public void render(Graphics g) {
		if(sameOfferList.size() > 0 && Game.gameState.equalsIgnoreCase("user_view") && Game.ui.pcView.equalsIgnoreCase("trade")) {
			for(int i = 0; i < 6; i++) {
				if(sameOfferList.size() > i) {
					for(int j = 0; j < Game.pokeList.size(); j++) {
						if(Game.pokeList.get(j).equals(sameOfferList.get(i))) {
							g.setColor(Color.red);
							g.drawString((j+1)+"",(Game.WIDTH * 14 / 100) * Game.SCALE + (Game.WIDTH * ((i) * 10) / 100) * Game.SCALE+ (i + 1) * (Game.WIDTH * 2 / 100) * Game.SCALE , ((Game.HEIGHT * 81 / 100) * Game.SCALE) + 2);
							break;
						}				
					}
				}
				
			}
		}
	}
}
