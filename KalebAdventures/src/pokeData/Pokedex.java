package pokeData;

import java.awt.image.BufferedImage;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import kaleb.graphics.Spritesheet;

public class Pokedex {

	public int maxId = 151 + 1;
	private int npcNum = 3;
	private BufferedImage[] allSprite = new BufferedImage[maxId];
	private BufferedImage[] allSpriteBack = new BufferedImage[maxId];
	private BufferedImage[] allSpriteShiny = new BufferedImage[maxId];
	private BufferedImage[] allSpriteShinyBack = new BufferedImage[maxId];
	public Spritesheet spritesheet;
	public String[] allTypes = new String[19];
	public BufferedImage[] allTypesSprites = new BufferedImage[19];
	Long id;
	String name;
	JSONArray type;
	JSONObject base;
	JSONObject evs;
	Long catchRate;
	JSONArray pokedex;
	JSONObject evolucaoJson ;
	public Pokedex() {
		populateDex();
		populateTypesTable();
		
		// ORGANIZAR NOME DO SPRITE
		for (int i = 1; i < maxId; i++) {

			spritesheet = new Spritesheet("/PokemonSprites/im/" + i + ".png");
			allSprite[i] = spritesheet.getSprite(0, 0, 96, 96);

			spritesheet = new Spritesheet("/PokemonSprites/back/" + i + ".png");
			allSpriteBack[i] = spritesheet.getSprite(0, 0, 96, 96);

			spritesheet = new Spritesheet("/PokemonSprites/im/" + i + "s.png");
			allSpriteShiny[i] = spritesheet.getSprite(0, 0, 96, 96);

			spritesheet = new Spritesheet("/PokemonSprites/back/" + i + "s.png");
			allSpriteShinyBack[i] = spritesheet.getSprite(0, 0, 96, 96);

		}
	}
	public void populateDex() {
		String arquivoJson = "/pokemon_data.json";
		JSONParser parser = new JSONParser();
		try (InputStream inputStream = getClass().getResourceAsStream(arquivoJson); InputStreamReader reader = new InputStreamReader(inputStream)) {
			// Parse o arquivo JSON
			pokedex = (JSONArray) parser.parse(reader);

			// Itera sobre os Pokémon na Pokédex
			for (Object obj : pokedex) {
				JSONObject pokemon = (JSONObject) obj;

				id = (Long) pokemon.get("id");
				name = (String) pokemon.get("name");
				type = (JSONArray) pokemon.get("type");
				base = (JSONObject) pokemon.get("base");
				evs = (JSONObject) pokemon.get("ev");
				catchRate = (Long) pokemon.get("rate");
				evolucaoJson = (JSONObject) pokemon.get("Evolucao");

			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}
	
	public void populateTypesTable() {
		allTypes[0] = "";
		allTypes[1] = "Normal";
		allTypes[2] = "Fighting";
		allTypes[3] = "Flying";
		allTypes[4] = "Poison";
		allTypes[5] = "Ground";
		allTypes[6] = "Rock";
		allTypes[7] = "Bug";
		allTypes[8] = "Ghost";
		allTypes[9] = "Steel";
		allTypes[10] = "Fire";
		allTypes[11] = "Water";
		allTypes[12] = "Grass";
		allTypes[13] = "Electric";
		allTypes[14] = "Psychic";
		allTypes[15] = "Ice";
		allTypes[16] = "Dragon";
		allTypes[17] = "Dark";
		allTypes[18] = "Fairy";
		
		spritesheet = new Spritesheet("/TYPES1.png");
		for(int i = 0; i < 19; i++) {			
			allTypesSprites[i] = spritesheet.getSprite(i*19, 0, 19, 18);
		}
	}
	public String getInfo(int idPokemon, String info) {
	    String returnInfo = "";

	    // Itera sobre os Pokémon na Pokédex
	    for (Object obj : pokedex) {
	        JSONObject pokemon = (JSONObject) obj;

	        id = (Long) pokemon.get("id");

	        if (id == maxId) {
	            break;
	        }

	        if (id != idPokemon) {
	            continue;
	        } else {
	            name = (String) pokemon.get("name");
	            type = (JSONArray) pokemon.get("type");
	            base = (JSONObject) pokemon.get("base");
	            evs = (JSONObject) pokemon.get("ev");
	            catchRate = (Long) pokemon.get("rate");
	            evolucaoJson =  (JSONObject) pokemon.get("Evolucao");
	            // Verifique se há informações de evolução disponíveis
	            

	            switch (info) {
	                case "name":
	                    returnInfo = name;
	                    break;
	                case "type":
	                    returnInfo = "" + type;
	                    break;
	                case "baseStats":
	                    returnInfo = base.get("hp") + "," + base.get("attack") + "," + base.get("defense") + ","
	                            + base.get("special-attack") + "," + base.get("special-defense") + "," + base.get("speed");
	                    break;
	                case "evs":
	                    returnInfo = evs.get("hp") + "," + evs.get("attack") + "," + evs.get("defense") + ","
	                            + evs.get("special-attack") + "," + evs.get("special-defense") + "," + evs.get("speed");
	                    break;
	                case "catchRate":
	                    returnInfo = "" + catchRate;
	                    break;
	                case "minLevel":
	                    returnInfo = "" + evolucaoJson.get("min_level");
	                    break;
	                case "minHappness":
	                    returnInfo = "" + evolucaoJson.get("min_happiness");
	                    break;
	                case "trigger":
	                    returnInfo = "" + evolucaoJson.get("trigger_name");
	                    break;
	                case "known_move":
	                    returnInfo = "" + evolucaoJson.get("known_move");
	                    break; 
	                case "members":
	                    returnInfo = "" + evolucaoJson.get("all_family_members");
	                    break;
	                case "item_name":
	                    returnInfo = "" + evolucaoJson.get("item_name");
	                    break;
	            }
	        }
	    }

	    return returnInfo;
	}

	public BufferedImage getSprite(int id, String position, boolean isShiny) {
		if (position.equalsIgnoreCase("Front")) {
			if (isShiny) {
				return allSpriteShiny[id];
			} else {
				return allSprite[id];
			}
		} else if (position.equalsIgnoreCase("Back")) {
			if (isShiny) {
				return allSpriteShinyBack[id];
			} else {
				return allSpriteBack[id];
			}
		}
		return allSprite[id];
	}
	
	

}
