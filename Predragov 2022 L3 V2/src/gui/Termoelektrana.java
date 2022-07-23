package gui;

//MODIFIKACIJA

import java.awt.Color;

public class Termoelektrana extends Proizvodjac {

	private static final long serialVersionUID = 1L;
	private int brTravnatih;

	public Termoelektrana(Baterija bat) {
		super('T', Color.orange, 2500, bat);
	}
	
	public void postaviBrojTravnatih(int br) {
		brTravnatih = br;
	}

	@Override
	protected int proizvJedinice() {
		return brTravnatih;
	}

	@Override
	protected boolean uspesnaProizv() {
		return brTravnatih >= 1;
	}

}
