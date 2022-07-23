package gui;

//MODIFIKACIJA

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EnergetskiSistem extends Frame {

	private static final long serialVersionUID = 1L;
	private Baterija baterija;
	private Plac plac;
	private Button dodaj, velicina;
	private TextField v, k;
	private Checkbox proizv, potros;
	private Choice moguce;
	private Label bat;
	private long time = -1;
	private int proizvCounter;
	private Panel sever;
	
	private class Dijalog extends Dialog {

		private static final long serialVersionUID = 1L;
		private long time;
		
		public Dijalog(long t) {
			super(EnergetskiSistem.this);
			time = t;
			setTitle("Restart");
			setResizable(false);
			
			addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent we) {
					dispose();
					if (plac != null) plac.prekini();
					EnergetskiSistem.this.dispose();
				}
			});
			
			popuniDijProzor();
			pack();
			setLocation(
					EnergetskiSistem.this.getX() + EnergetskiSistem.this.getWidth() / 2 - getWidth() / 2,
					EnergetskiSistem.this.getY() + EnergetskiSistem.this.getHeight() / 2 - getHeight() / 2
			);
			setVisible(true);
		}

		private void popuniDijProzor() {
			setLayout(new GridLayout(3, 1, 5, 5));
			add(new Label("Vreme: " + time / 1000));
			add(new Label("Restart?"));
			Button dugm = new Button("OK");
			add(dugm);
			
			dugm.addActionListener(ae -> {
				dispose();
				if (plac != null) plac.prekini();
				EnergetskiSistem.this.dispose();
				new EnergetskiSistem(100);
			});
		}
		
	}

	public EnergetskiSistem(int kap) {
		setBounds(700, 300, 700, 500);
		setTitle("Energetski sistem");
		setResizable(false);
		
		//plac = new Plac(v, k);
		baterija = new Baterija(kap);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				if (plac != null) plac.prekini();
				dispose();
			}
		});
		
		popuniProzor();
		//pack();
		setVisible(true);
	}

	private void popuniProzor() {
		sever = new Panel();
		CheckboxGroup grp = new CheckboxGroup();
		proizv = new Checkbox("Proizvodjac", false, grp);
		potros = new Checkbox("Potrosac", false, grp);
		moguce = new Choice();
		dodaj = new Button("Dodaj");
		bat = new Label("Baterija: 0/0");
		bat.setAlignment(Label.CENTER);
		v = new TextField(5);
		k = new TextField(5);
		velicina = new Button("Zapocni");
		sever.add(proizv);
		sever.add(potros);
		sever.add(moguce);
		sever.add(dodaj);
		sever.add(bat);
		sever.add(v);
		sever.add(k);
		sever.add(velicina);
		add(sever, BorderLayout.NORTH);
		//add(plac, BorderLayout.CENTER);
		
		velicina.addActionListener(ae -> {
			if (v != null && !v.getText().equals("") && k != null && !k.getText().equals("")) {
				if (plac != null) plac.prekini();
				plac = new Plac(Integer.parseInt(v.getText()), Integer.parseInt(k.getText()));
				removeAll();
				add(sever, BorderLayout.NORTH);
				add(plac, BorderLayout.CENTER);
				revalidate();
			}
		});
		
		proizv.addItemListener(ie -> {
			moguce.removeAll();
			moguce.add("Termoelektrana");
			moguce.add("Hidroelektrana");
			moguce.revalidate();
		});
		
		potros.addItemListener(ie -> {
			moguce.removeAll();
			moguce.add("Fabrika");
			moguce.add("Mlin");
			moguce.revalidate();
		});
		
		dodaj.addActionListener(ae -> {
			if (plac != null && moguce.getSelectedItem() != null) {
				if (moguce.getSelectedItem().equals("Hidroelektrana")) {
					Hidroelektrana h = new Hidroelektrana(baterija);
					plac.dodaj(h);
					promeniProizvodjace(1);
				}
				else if (moguce.getSelectedItem().equals("Termoelektrana")) { 
					Termoelektrana t = new Termoelektrana(baterija);
					plac.dodaj(t);
					promeniProizvodjace(1);
				}
				else if (moguce.getSelectedItem().equals("Fabrika")) { 
					Fabrika f = new Fabrika(baterija);
					plac.dodaj(f);
				}
				else { 
					Mlin m = new Mlin(baterija);
					plac.dodaj(m);
				}
				if (time == -1) time = System.currentTimeMillis();
				plac.revalidate();
			}
		});
	}
	
	public void promeniProizvodjace(int br) {
		proizvCounter += br;
	}
	
	public void obavesti() {
		if (proizvCounter > 0) {
			long newTime = System.currentTimeMillis() - time;
			plac.prekini();
			new Dijalog(newTime);
		}
	}
	
	public void azurirajLabele() {
		bat.setText("Baterija: " + baterija.dohvEnergiju() + "/" + baterija.dohvMaxKapacitet());
	}
	
	public static void main(String[] args) {
		new EnergetskiSistem(100);
	}

}
