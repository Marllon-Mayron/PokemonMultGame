package kaleb.controller;

import java.util.ArrayList;
import java.util.List;

import kaleb.entities.SlotStore;
import kaleb.itens.Item;
import kaleb.main.Game;

public class StoreController {
	public List<SlotStore> slotStoreList;
	
	public StoreController() {
		slotStoreList = new ArrayList<SlotStore>();
		 
	}
	
	public void generateItem() {
		for(int i = 0; i < slotStoreList.size(); i++) {
			slotStoreList.get(i).item = null;
		}
		for(int i = 0; i < slotStoreList.size(); i++) {
			Item item = null;
			int maxPokeball = Game.random.nextInt(Game.itemController.pokeballList.length);
			int maxMedicine = Game.random.nextInt(Game.itemController.medicineList.length);
			int maxBattleItem = Game.random.nextInt(Game.itemController.battleItemList.length);
			int maxEvolueItem = Game.random.nextInt(Game.itemController.evolueItemList.length);
			
			int n =  Game.random.nextInt(4);
			if(n == 0) {
				item = new Item(maxPokeball, "pokeball", null, 0, null);
			}else if(n == 1){
				item = new Item(maxMedicine, "medicine", null, 0, null);
			}else if(n == 2){
				item = new Item(maxMedicine, "battleItem", null, 0, null);
			}else if(n == 3){
				item = new Item(maxEvolueItem, "evolueItem", null, 0, null);
			}
			
			
			slotStoreList.get(i).item = item ;
		}
	}

}
