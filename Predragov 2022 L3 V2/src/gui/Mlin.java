package gui;

//MODIFIKACIJA

import java.awt.Color;

public class Mlin extends Potrosac {

	private static final long serialVersionUID = 1L;
	private int brHidro;

	public Mlin(Baterija bat) {
		super('M', Color.lightGray, 1000, bat);
	}
	
	public void postaviBrojHidro(int br) {
		brHidro = br;
	}

	@Override
	protected int potrosJedinice() {
		return brHidro;
	}

	@Override
	protected boolean uspesnaPotros() {
		return brHidro >= 1;
	}

}
