package gui;

//MODIFIKACIJA

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Panel;

public class Plac extends Panel {

	private static final long serialVersionUID = 1L;
	private Parcela tr;
	private Parcela[][] parcele;
	private int v, k;
	
	public Plac(int vv, int kk) {
		v = vv;
		k = kk;
		parcele = new Parcela[v][k];
		this.setLayout(new GridLayout(v, k, 5, 5));
		for (int i = 0; i < v; i++) {
			for (int j = 0; j < k; j++) {
				if (Math.random() >= 0.3) parcele[i][j] = new Travnata();
				else parcele[i][j] = new Vodena();
				//parcele[i][j].postaviRoditelja(this);
				parcele[i][j].revalidate();
				//add(parcele[i][j]);
			}
		}
		iscrtajPlac();
	}
	
	private void iscrtajPlac() {
		this.removeAll();
		for (int i = 0; i < v; i++)
			for (int j = 0; j < k; j++)
				add(parcele[i][j]);
		this.revalidate();
	}

	public void izaberiParcelu(Parcela p) {
		if (tr != null) {
			tr.setFont(new Font(Font.SERIF, Font.BOLD, 14));
			tr.revalidate();
		}
		tr = p;
		tr.setFont(new Font(Font.SERIF, Font.BOLD, 20));
		tr.revalidate();
	}
	
	public synchronized void dodaj(Parcela p) {
		if (tr == null) {
			if (p != null) {
				if (p instanceof Proizvodjac) {
					((EnergetskiSistem)getParent()).promeniProizvodjace(-1);
					((Proizvodjac)p).prekini();
				}
				else if (p instanceof Potrosac) ((Potrosac)p).prekini();
			}
			return;
		}
		spolj: for (int i = 0; i < v; i++) {
			for (int j = 0; j < k; j++) {
				if (parcele[i][j] == tr) {
					if (parcele[i][j] instanceof Proizvodjac) {
						((EnergetskiSistem)getParent()).promeniProizvodjace(-1);
						((Proizvodjac)parcele[i][j]).prekini();
					}
					else if (parcele[i][j] instanceof Potrosac)
						((Potrosac)parcele[i][j]).prekini();
					parcele[i][j] = p;
					parcele[i][j].revalidate();
					break spolj;
					//if (!(p instanceof Hidroelektrana)) return;
					//Hidroelektrana h = (Hidroelektrana)p;
					//h.postaviBrojVodenih(azurirajVodene(i, j));
				}
			}
		}
		azurirajSve();
		iscrtajPlac();
		tr = null;
	}
	
	public void azurirajLabele() {
		((EnergetskiSistem)getParent()).azurirajLabele();
	}

	private void azurirajSve() {
		for (int i = 0; i < v; i++) {
			for (int j = 0; j < k; j++) {
				if (parcele[i][j] instanceof Hidroelektrana) {
					Hidroelektrana h = (Hidroelektrana)parcele[i][j];
					h.postaviBrojVodenih(azurirajPovrsine(i, j, '~'));
				}
				else if (parcele[i][j] instanceof Termoelektrana) {
					Termoelektrana h = (Termoelektrana)parcele[i][j];
					h.postaviBrojTravnatih(azurirajPovrsine(i, j, '"'));
				}
				else if (parcele[i][j] instanceof Fabrika) {
					Fabrika f = (Fabrika)parcele[i][j];
					f.postaviBrojTermo(azurirajPovrsine(i, j, 'T'));
				}
				else if (parcele[i][j] instanceof Mlin) {
					Mlin m = (Mlin)parcele[i][j];
					m.postaviBrojHidro(azurirajPovrsine(i, j, 'H'));
				}
			}
		}	
	}

	private int azurirajPovrsine(int i, int j, char ozn) {
		int counter = 0;
		if (i != 0) {
			if (j != 0 && parcele[i-1][j-1].oznaka == ozn) counter++;
			if (parcele[i-1][j].oznaka == ozn) counter++;
			if (j != k-1 && parcele[i-1][j+1].oznaka == ozn) counter++;
		}
		if (j != 0 && parcele[i][j-1].oznaka == ozn) counter++;
		if (i != v-1) {
			if (j != 0 && parcele[i+1][j-1].oznaka == ozn) counter++;
			if (parcele[i+1][j].oznaka == ozn) counter++;
			if (j != k-1 && parcele[i+1][j+1].oznaka == ozn) counter++;
		}
		if (j != k-1 && parcele[i][j+1].oznaka == ozn) counter++;
		return counter;
	}
	
	public void prekini() {
		for (int i = 0; i < v; i++) {
			for (int j = 0; j < k; j++) {
				if (parcele[i][j] != null) {
					if (parcele[i][j] instanceof Proizvodjac) {
						Proizvodjac p = (Proizvodjac)parcele[i][j];
						p.prekini();
					}
					else if (parcele[i][j] instanceof Potrosac) {
						Potrosac p = (Potrosac)parcele[i][j];
						p.prekini();
					}
				}
			}
		}
	}

}