package gui;

//MODIFIKACIJA

public class Baterija {
	
	private int energija;
	private int maxKap;

	public Baterija(int max) {
		energija = max;
		maxKap = max;
	}
	
	public void dodajEnergiju(int energ) {
		energija += energ;
		if (energija >= maxKap) energija = maxKap;
		else if (energija < 0) energija = 0;
	}
	
	public void isprazniBateriju() {
		energija = 0;
	}
	
	public boolean puna() {
		return energija == maxKap;
	}
	
	public int dohvMaxKapacitet() {
		return maxKap;
	}
	
	public int dohvEnergiju() {
		return energija;
	}

}
