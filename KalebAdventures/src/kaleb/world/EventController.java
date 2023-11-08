package kaleb.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import kaleb.entities.Npc;
import kaleb.entities.Pokemon;
import kaleb.main.Game;

public class EventController {
	public int x = 0;
	public int y = 0;
	public int current = 0;
	public int qnt;
	double spdMedia = 0;
	public String[] pokeList = null;
	public OwnerController ownerController;
	public EventController() {
		ownerController = new OwnerController();
	}

	public void pokemonWithOwner() {
		List<PokeGenerator> generateTempList = new ArrayList<PokeGenerator>();
		List<Pokemon> pokemonTempList = new ArrayList<Pokemon>();
		
		//ESCOLHER UMA DAS ROTAS PARA APARECER:
		for (int i = 0; i < Game.generateList.size(); i++) {
			if (Game.generateList.get(i).myLvl == Game.currentLvl) {
				generateTempList.add(Game.generateList.get(i));
			}
		}
		int n = Game.random.nextInt(generateTempList.size());
		int difficulty = 0;
		//=====================================================
		
		int typeNum = generateTempList.get(n).numType;
		if(typeNum == 1) {
			pokeList = ownerController.treinersPokemon[0][Game.random.nextInt(ownerController.num[typeNum][difficulty])].split(",");
		}else if(typeNum == 7) {
			pokeList = ownerController.treinersBugPokemon[0][Game.random.nextInt(ownerController.num[typeNum][difficulty])].split(",");
			
		}else if(typeNum == 6) {
			pokeList = ownerController.treinersRockPokemon[0][Game.random.nextInt(ownerController.num[typeNum][difficulty])].split(",");
			
		}
		qnt = pokeList.length - 1;
		
		for(int j = 1; j < pokeList.length; j++) {
			
			int x = (int) generateTempList.get(n).x;
			int y = (int) generateTempList.get(n).y;
			Pokemon pokemon = new Pokemon(x, y, 2, 2, null, true,Integer.parseInt(pokeList[j]), generateTempList.get(n).lvlMax, true);
			pokemon.withOwner = true;
			pokemon.updateLife();
			pokemon.routes = generateTempList.get(n).routes.split(",");
			pokemonTempList.add(pokemon);
		}
		
		for(int i = 0; i < pokemonTempList.size(); i++) {
			spdMedia = spdMedia + pokemonTempList.get(i).spdInMap;
		}
		
		Timer timer = new Timer();
		
		
		
		TimerTask tarefa = new TimerTask() {
			private int contador = 0;

			@Override
			public void run() {
				
				if (contador == qnt) {
					Npc npc = new Npc(x, y, 2, 2, null, Integer.parseInt(pokeList[0]), pokemonTempList);
					npc.spd = spdMedia/pokemonTempList.size();
					Game.entities.add(npc);
					timer.cancel();
					
				} else {
					
					pokemonTempList.get(current).spdInMap = spdMedia/pokemonTempList.size();
					Game.entities.add(pokemonTempList.get(current));
					x = (int) pokemonTempList.get(current).x;
					y = (int) pokemonTempList.get(current).y;
					contador++;
					current++;
					
				}
			}
		};

		timer.schedule(tarefa, 0, 800);
	}
}
