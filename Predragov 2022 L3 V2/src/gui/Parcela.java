package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Parcela extends Label {

	private static final long serialVersionUID = 1L;
	protected char oznaka;
	protected Color bojaPoz;
	//protected Plac plac;

	public Parcela(char ozn, Color col) {
		oznaka = ozn;
		bojaPoz = col;
		this.setAlignment(Label.CENTER);
		this.setFont(new Font(Font.SERIF, Font.BOLD, 14));
		this.setForeground(Color.white);
		this.setBackground(bojaPoz);
		this.setText(Character.toString(oznaka));
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Plac plac = (Plac)getParent();
				plac.izaberiParcelu(Parcela.this);
			}
		});
		this.revalidate();
	}
	
	public void promeniBojuPozadine(Color col) {
		bojaPoz = col;
		this.setBackground(bojaPoz);
		this.revalidate();
	}
	
	/*
	void postaviRoditelja(Plac p) {
		plac = p;
	}
	*/

}
