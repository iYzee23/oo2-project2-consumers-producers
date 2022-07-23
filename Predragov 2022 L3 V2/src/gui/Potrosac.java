package gui;

//MODIFIKACIJA

import java.awt.Color;

public abstract class Potrosac extends Parcela implements Runnable {

	private static final long serialVersionUID = 1L;
	protected long osnVreme, ukupVreme;
	protected Baterija baterija;
	protected Thread nit;
	//private boolean radi;

	public Potrosac(char ozn, Color col, long vreme, Baterija bat) {
		super(ozn, col);
		osnVreme = vreme;
		baterija = bat;
		ukupVreme = osnVreme + (long)(Math.random() * 301);
		nit = new Thread(this);
		nit.start();
	}
	
	protected abstract int potrosJedinice();
	
	protected abstract boolean uspesnaPotros();

	@Override
	public void run() {
		try {
			while (!nit.isInterrupted()) {
				/*
				synchronized (this) {
					while (!radi) wait();
				}
				*/
				Thread.sleep(ukupVreme);
				int jed = potrosJedinice();
				if (uspesnaPotros()) {
					baterija.dodajEnergiju(-jed);
					this.setForeground(Color.magenta);
					this.revalidate();
					Thread.sleep(300);
					if (getParent() != null) ((Plac)getParent()).azurirajLabele();
					this.setForeground(Color.white);
					this.revalidate();
					if (baterija.dohvEnergiju() == 0)
						((EnergetskiSistem)(getParent().getParent())).obavesti();
				}
			}
		} catch (InterruptedException e) {
			//bez efekta
		}
		synchronized (this) {
			nit = null;
			notifyAll();
		}
	}
	
	/*
	protected synchronized void kreni() {
		radi = true;
		notifyAll();
	}
	
	protected synchronized void zaustavi() {
		radi = false;
		notifyAll();
	}
	*/

	protected synchronized void finish() {
		if (nit != null) nit.interrupt();
		while (nit != null) {
			try {
				wait();
			} catch (InterruptedException e) {
				//bez efekta
			}
		}
	}
	
	public void prekini() {
		if (nit != null) nit.interrupt();
	}
	
}
