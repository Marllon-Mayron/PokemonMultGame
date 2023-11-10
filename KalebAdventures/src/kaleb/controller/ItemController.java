package kaleb.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import kaleb.entities.Player;
import kaleb.graphics.Spritesheet;
import kaleb.itens.BattleItem;
import kaleb.itens.EvolutionItem;
import kaleb.itens.Item;
import kaleb.itens.Medicine;
import kaleb.itens.Pokeball;

public class ItemController {
	
	public Spritesheet spritesheet;
	
	//pokeballs===================================================================
	JSONArray pokeballs;
	long id;
	String name;
    long catchRate;
    long value;
    String desc;
    private int pokeballNum = 4;
	public BufferedImage[] pokeballList = new BufferedImage[pokeballNum];
	public BufferedImage nullPokeball;
	//=============================================================================
	//Medicine===================================================================
	JSONArray medicines;
	long medicineId;
	String medicineName;
	long medicineCure;
	long medicineValue;
	String medicineDesc;
	private int medicineNum = 3;
    public BufferedImage[] medicineList = new BufferedImage[medicineNum];
	public BufferedImage nullMedicine;
	//BattleItem===================================================================
	JSONArray battleItens;
	long battleItemId;
	String battleItemName;
	long battleItemDuration;
	long battleItemValue;
	String battleItemDesc;
	private int battleItemNum = 4;
	public BufferedImage[] battleItemList = new BufferedImage[battleItemNum];
	public BufferedImage nullbattleItem;
	//EvolueItem==================================================================
	JSONArray evolueItens;
	long evolueItemId;
	String evolueItemName;
	long evolueItemValue;
	String evolueItemDesc;
	private int evolueItemNum = 10;
	public BufferedImage[] evolueItemList = new BufferedImage[evolueItemNum];
	public BufferedImage nullEvolueItem;
	public ItemController() {
		createItem();
		populateSprites();
	}
	public void typifyItem(Item item) {
		boolean add = false;
		if(item.type == "pokeball") {
			Pokeball ball;
			
			ball = new Pokeball(item.idItem, null, null, 0, null,1);
			
			for(int i = 0; i < Player.pokeballItemList.size() ;i++) {
				if(ball.name == Player.pokeballItemList.get(i).name) {
					Player.pokeballItemList.get(i).current++;
					add = true;
				}
			}
			if(!add) {
				Player.pokeballItemList.add(ball);
			}			
		}else if(item.type == "medicine") {
			Medicine medicine;
			
			medicine = new Medicine(item.idItem, null, null, 0, null,1);
			
			for(int i = 0; i < Player.medicineItemList.size() ;i++) {
				if(medicine.name == Player.medicineItemList.get(i).name) {
					Player.medicineItemList.get(i).current++;
					add = true;
				}
			}
			if(!add) {
				Player.medicineItemList.add(medicine);
			}	
		}else if(item.type == "battleItem") {
			BattleItem battleItem;
			
			battleItem = new BattleItem(item.idItem, null, null, 0, null,1);
			
			for(int i = 0; i < Player.battleItemList.size() ;i++) {
				if(battleItem.name == Player.battleItemList.get(i).name) {
					Player.battleItemList.get(i).current++;
					add = true;
				}
			}
			if(!add) {
				Player.battleItemList.add(battleItem);
			}	
		}else if(item.type == "evolueItem") {
			EvolutionItem evolueItem;
			
			evolueItem = new EvolutionItem(item.idItem, null, null, 0, null,1);
			
			for(int i = 0; i < Player.evolueItemList.size() ;i++) {
				if(evolueItem.name == Player.evolueItemList.get(i).name) {
					Player.evolueItemList.get(i).current++;
					add = true;
				}
			}
			if(!add) {
				Player.evolueItemList.add(evolueItem);
			}	
		}
	}
	public void createItem() {		
	    String arquivoJson = "/json/pokeball.json";
		JSONParser parser = new JSONParser();
		try (InputStream inputStream = getClass().getResourceAsStream(arquivoJson); InputStreamReader reader = new InputStreamReader(inputStream)) {
			pokeballs = (JSONArray) parser.parse(reader);
			for (Object obj : pokeballs) {
				JSONObject jsonPokeball = (JSONObject) obj;

				id = (Long) jsonPokeball.get("id");
				name = (String) jsonPokeball.get("name");
				catchRate = (Long) jsonPokeball.get("catch_rate");
				value = (Long) jsonPokeball.get("value");
				desc = (String) jsonPokeball.get("desc");
				
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		arquivoJson = "/json/medicine.json";
		
		try (InputStream inputStream = getClass().getResourceAsStream(arquivoJson); InputStreamReader reader = new InputStreamReader(inputStream)) {
			medicines = (JSONArray) parser.parse(reader);
			for (Object obj : medicines) {
				JSONObject jsonMedicine = (JSONObject) obj;

				medicineId = (Long) jsonMedicine.get("id");
				medicineName = (String) jsonMedicine.get("name");
				medicineCure = (Long) jsonMedicine.get("cure");
				medicineValue = (Long) jsonMedicine.get("value");
				medicineDesc = (String) jsonMedicine.get("desc");
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		
		arquivoJson = "/json/battleItem.json";
		try (InputStream inputStream = getClass().getResourceAsStream(arquivoJson); InputStreamReader reader = new InputStreamReader(inputStream)) {
			battleItens = (JSONArray) parser.parse(reader);
			for (Object obj : battleItens) {
				JSONObject jsonBattleItem = (JSONObject) obj;

				battleItemId = (Long) jsonBattleItem.get("id");
				battleItemName = (String) jsonBattleItem.get("name");
				battleItemDuration = (Long) jsonBattleItem.get("duration");
				battleItemValue = (Long) jsonBattleItem.get("value");
				battleItemDesc = (String) jsonBattleItem.get("desc");
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		
		arquivoJson = "/json/EvolueItens.json";
		try (InputStream inputStream = getClass().getResourceAsStream(arquivoJson); InputStreamReader reader = new InputStreamReader(inputStream)) {
			evolueItens = (JSONArray) parser.parse(reader);
			for (Object obj : evolueItens) {
				JSONObject jsonEvolueItem = (JSONObject) obj;

				evolueItemId = (Long) jsonEvolueItem.get("id");
				evolueItemName = (String) jsonEvolueItem.get("name");
				evolueItemValue = (Long) jsonEvolueItem.get("value");
				evolueItemDesc = (String) jsonEvolueItem.get("desc");
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}
	public String getPokeballInfo(int idPokeball, String info) {
		String returnInfo = "";
		for (Object obj : pokeballs) {
			JSONObject pokeball = (JSONObject) obj;
			
			id = (Long) pokeball.get("id");
			
			
			
			if (id != idPokeball+1) {
				continue;
			}else {
				name = (String) pokeball.get("name");
				catchRate = (Long) pokeball.get("catch_rate");
				value = (Long) pokeball.get("value");
				desc = (String) pokeball.get("desc");
				switch (info) {
				case "id":
					returnInfo = ""+id;
					break;
				case "name":
					returnInfo = name;
					break;
				case "catchRate":
					returnInfo = ""+catchRate;
					break;
				case "value":
					returnInfo = ""+value;
					break;
				case "desc":
					returnInfo = ""+desc;
					break;
				}
			}
		}
		return returnInfo;
		
	}
	public String getMedicineInfo(int idMedicine, String info) {
		String returnInfo = "";
		for (Object obj : medicines) {
			JSONObject medicine = (JSONObject) obj;
			
			medicineId = (Long) medicine.get("id");
			
			
			
			if (medicineId != idMedicine+1) {
				continue;
			}else {
				medicineName = (String) medicine.get("name");
				medicineCure = (Long) medicine.get("cure");
				medicineValue = (Long) medicine.get("value");
				medicineDesc = (String) medicine.get("desc");
				switch (info) {
				case "id":
					returnInfo = ""+medicineId;
					break;
				case "name":
					returnInfo = medicineName;
					break;
				case "cure":
					returnInfo = ""+medicineCure;
					break;
				case "value":
					returnInfo = ""+medicineValue;
					break;
				case "desc":
					returnInfo = ""+medicineDesc + " " + medicineCure;
					break;
			}
			}
			
			
		}
		return returnInfo;
		
	}
	public String getBattleItemInfo(int idBattleItem, String info) {
		String returnInfo = "";
		for (Object obj : battleItens) {
			JSONObject bItem = (JSONObject) obj;
			
			battleItemId = (Long) bItem.get("id");
			
			
			
			if (battleItemId != idBattleItem+1) {
				continue;
			}else {
				battleItemName = (String) bItem.get("name");
				battleItemDuration = (Long) bItem.get("duration");
				battleItemValue = (Long) bItem.get("value");
				battleItemDesc = (String) bItem.get("desc");
				switch (info) {
				case "id":
					returnInfo = ""+battleItemId;
					break;
				case "name":
					returnInfo = battleItemName;
					break;
				case "duration":
					returnInfo = ""+battleItemDuration;
					break;
				case "value":
					returnInfo = ""+battleItemValue;
					break;
				case "desc":
					returnInfo = ""+battleItemDesc + battleItemDuration + " segundos.";
					break;
			}
			}
			
			
		}
		return returnInfo;
		
	}
	public String getEvolutionItemInfo(int idEvolueItem, String info) {
		String returnInfo = "";
		for (Object obj : evolueItens) {
			JSONObject bItem = (JSONObject) obj;
			
			evolueItemId = (Long) bItem.get("id");
			
			
			
			if (evolueItemId != idEvolueItem+1) {
				continue;
			}else {
				evolueItemName = (String) bItem.get("name");
				evolueItemValue = (Long) bItem.get("value");
				evolueItemDesc = (String) bItem.get("desc");
				switch (info) {
				case "id":
					returnInfo = ""+evolueItemId;
					break;
				case "name":
					returnInfo = evolueItemName;
					break;
				case "value":
					returnInfo = ""+evolueItemValue;
					break;
				case "desc":
					returnInfo = ""+evolueItemDesc;
					break;
				}
			}
			
			
		}
		return returnInfo;
		
	}
	public void populateSprites() {
		spritesheet = new Spritesheet("/PokemonSprites/pokeball/0.png");
		nullPokeball = spritesheet.getSprite(0, 0, 64, 64);
		for (int i = 0; i < pokeballNum; i++) {
			spritesheet = new Spritesheet("/PokemonSprites/pokeball/"+(i+1)+".png");
			pokeballList[i] = spritesheet.getSprite(0, 0, 64, 64);
		}
		
		spritesheet = new Spritesheet("/PokemonSprites/medicine/0.png");
		nullMedicine = spritesheet.getSprite(0, 0, 64, 64);
		for (int i = 0; i < medicineNum; i++) {
			spritesheet = new Spritesheet("/PokemonSprites/medicine/"+(i+1)+".png");
			medicineList[i] = spritesheet.getSprite(0, 0, 64, 64);
		}
		
		spritesheet = new Spritesheet("/PokemonSprites/battle-item/0.png");
		nullbattleItem = spritesheet.getSprite(0, 0, 64, 64);
		for (int i = 0; i < battleItemNum; i++) {
			spritesheet = new Spritesheet("/PokemonSprites/battle-item/"+(i+1)+".png");
			battleItemList[i] = spritesheet.getSprite(0, 0, 64, 64);
		}
		spritesheet = new Spritesheet("/PokemonSprites/evo-item/0.png");
		nullEvolueItem = spritesheet.getSprite(0, 0, 64, 64);
		for (int i = 0; i < evolueItemNum; i++) {
			spritesheet = new Spritesheet("/PokemonSprites/evo-item/"+(i+1)+".png");
			evolueItemList[i] = spritesheet.getSprite(0, 0, 64, 64);
		}
	}
	
	
}
