package gui;

//MODIFIKACIJA

import java.awt.Color;

public abstract class Proizvodjac extends Parcela implements Runnable {

	private static final long serialVersionUID = 1L;
	protected long osnVreme, ukupVreme;
	protected Baterija baterija;
	protected Thread nit;
	//private boolean radi;

	public Proizvodjac(char ozn, Color col, long vreme, Baterija bat) {
		super(ozn, col);
		osnVreme = vreme;
		baterija = bat;
		ukupVreme = osnVreme + (long)(Math.random() * 301);
		nit = new Thread(this);
		nit.start();
	}
	
	protected abstract int proizvJedinice();
	
	protected abstract boolean uspesnaProizv();

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
				int jed = proizvJedinice();
				if (uspesnaProizv()) {
					baterija.dodajEnergiju(jed);
					this.setForeground(Color.red);
					this.revalidate();
					Thread.sleep(300);
					if (getParent() != null)
						((Plac)getParent()).azurirajLabele();
					this.setForeground(Color.white);
					this.revalidate();
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
