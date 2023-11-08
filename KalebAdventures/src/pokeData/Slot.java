package pokeData;

import kaleb.entities.Pokemon;

public class Slot {
	public int nSlot;
	public String slotStatus;//FREE - INMAP - DEFEATED
	public boolean isSelected;
	public Pokemon pokemon;
	
	public Slot(int nSlot, Pokemon pokemon) {
		this.nSlot = nSlot;
		this.slotStatus = "free";
		this.pokemon = pokemon;
	}

	public int getnSlot() {
		return nSlot;
	}

	public void setnSlot(int nSlot) {
		this.nSlot = nSlot;
	}

	public String getSlotStatus() {
		return slotStatus;
	}

	public void setSlotStatus(String slotStatus) {
		this.slotStatus = slotStatus;
	}

	public Pokemon getPokemon() {
		return pokemon;
	}

	public void setPokemon(Pokemon pokemon) {
		this.pokemon = pokemon;
	}

	
	
	
}
