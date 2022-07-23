package gui;

//MODIFIKACIJA

import java.awt.Color;

public class Hidroelektrana extends Proizvodjac {

	private static final long serialVersionUID = 1L;
	private int brVodenih;

	public Hidroelektrana(Baterija bat) {
		super('H', Color.blue, 1500, bat);
	}
	
	public void postaviBrojVodenih(int br) {
		brVodenih = br;
	}

	@Override
	protected int proizvJedinice() {
		return brVodenih;
	}

	@Override
	protected boolean uspesnaProizv() {
		return brVodenih >= 1;
	}

}
