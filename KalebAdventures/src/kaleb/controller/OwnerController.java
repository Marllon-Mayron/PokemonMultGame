package kaleb.controller;

public class OwnerController {

	public int num[][] = new int[18][3];
	public String[][] treinersPokemon;
	public String[][] treinersBugPokemon;
	public String[][] treinersRockPokemon;
	
	public OwnerController() {
		populateAllTreiners();
	}


	private void populateAllTreiners() {
		num[1][0] = 6;
		num[1][1] = 3;
		num[1][2] = 0;
		treinersPokemon = new String[3][num[1][0]];
		treinersPokemon[0][0] = "4,16";
		treinersPokemon[0][1] = "4,19";
		treinersPokemon[0][2] = "4,21";
		treinersPokemon[0][3] = "4,25";
		treinersPokemon[0][4] = "4,29";
		treinersPokemon[0][5] = "4,32";
		/**
		treinersPokemon[1][0] = "4,19,19";
		treinersPokemon[1][1] = "4,19,21";
		treinersPokemon[1][2] = "4,19,16";
		**/
		//============================================
		num[6][0] = 3;
		num[6][1] = 3;
		num[6][2] = 0;
		//treinersRockPokemon = new String[3][num[6][0]];
		//treinersRockPokemon[0][0] = "2,27";
		//treinersRockPokemon[0][1] = "2,50";
		//treinersRockPokemon[0][2] = "2,74";
		/**
		treinersRockPokemon[1][0] = "2,27,74";
		treinersRockPokemon[1][1] = "2,74,74";
		treinersRockPokemon[1][2] = "2,50,27";
		**/
		//============================================
		num[7][0] = 4;
		num[7][1] = 8;
		num[7][2] = 0;
		treinersBugPokemon = new String[3][num[7][0]];
		treinersBugPokemon[0][0] = "1,10";
		treinersBugPokemon[0][1] = "1,13";
		treinersBugPokemon[0][2] = "1,11";
		treinersBugPokemon[0][3] = "1,14";
		/**
		treinersBugPokemon[1][0] = "1,10,13";
		treinersBugPokemon[1][1] = "1,10,11";
		treinersBugPokemon[1][2] = "1,13,14";
		treinersBugPokemon[1][3] = "1,11,14";
		treinersBugPokemon[1][4] = "1,10,10";
		treinersBugPokemon[1][5] = "1,13,13";
		treinersBugPokemon[1][6] = "1,11,11";
		treinersBugPokemon[1][7] = "1,14,14";
		**/
		//============================================
		
	}
	
}
