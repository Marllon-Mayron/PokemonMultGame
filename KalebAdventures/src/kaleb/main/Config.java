package kaleb.main;

import java.text.DecimalFormat;

public class Config {
	public int decimalPreference = 1;
	public DecimalFormat format = new DecimalFormat("#." + "0".repeat(decimalPreference));
	
	public boolean activeHud = true;
	public boolean activeDesc;
	public boolean activeRange;
	public boolean debugMode = false;
	public boolean showCorrectRoutes = debugMode;
	//QUANTOS SEGUNDOS NA LOJA/PC
	public double state2Time = 1000;
	
	public void userPerformance() {
		int media = 0;
		int num = 0;
		for(int i = 0; i < Game.slotList.size(); i++) {
			if(Game.slotList.get(i).pokemon != null) {
				media = media+Game.slotList.get(i).pokemon.lvl;
				num++;
			}
			
		}
		String performance = null;
		int defeatNum = Game.lvlConfig.get(Game.currentLvl).myVictorys;
		int totalWave = Game.lvlConfig.get(Game.currentLvl).nGen * Game.lvlConfig.get(Game.currentLvl).pokeGenNum[Game.currentLvl];
		
		if(defeatNum >= (double)(totalWave * 80)/ 100) {
			performance = "Excelente!";
		}else if(defeatNum >= (double)(totalWave * 60)/ 100 && defeatNum < (double)(totalWave * 80)/ 100) {
			performance = "Boa!";
		}else if(defeatNum >= (double)(totalWave * 40)/ 100 && defeatNum < (double)(totalWave * 60)/ 100) {
			performance = "Mediana!";
		}else if(defeatNum >= (double)(totalWave * 20)/ 100 && defeatNum < (double)(totalWave * 40)/ 100) {
			performance = "Ruim!";
		}else{
			performance = "Horrivel!";
		}
		System.out.println(defeatNum+"-"+totalWave);
		System.out.println("Seu desempenho foi "+performance+"\nSua media de nivel foi de "+media/num);
		
	}
	
	public String quebrarLinhas(String texto, int limiteCaracteres) {
		String[] palavras = texto.split(" ");
        StringBuilder resultado = new StringBuilder();
        int caracteresNaLinha = 0;

        for (String palavra : palavras) {
            if (caracteresNaLinha + palavra.length() + 1 <= limiteCaracteres) {
                if (caracteresNaLinha > 0) {
                    resultado.append(" ");
                    caracteresNaLinha++;
                }
                resultado.append(palavra);
                caracteresNaLinha += palavra.length();
            } else {
                resultado.append("\n").append(palavra);
                caracteresNaLinha = palavra.length();
            }
        }

        return resultado.toString();
    }
}
