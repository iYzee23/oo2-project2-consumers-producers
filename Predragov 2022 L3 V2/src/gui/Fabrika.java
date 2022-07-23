package gui;

//MODIFIKACIJA

import java.awt.Color;

public class Fabrika extends Potrosac {

	private static final long serialVersionUID = 1L;
	private int brTermo;

	public Fabrika(Baterija bat) {
		super('F', Color.pink, 500, bat);
	}
	
	public void postaviBrojTermo(int br) {
		brTermo = br;
	}

	@Override
	protected int potrosJedinice() {
		return brTermo;
	}

	@Override
	protected boolean uspesnaPotros() {
		return brTermo >= 1;
	}

}
