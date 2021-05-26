package modele;

import java.util.ArrayList;

// Classe parent de tous les vehicules

public abstract class Vehicule {
	
	private String Type;
	private String Marque;
	private String Modele;
	private String Color;
	private Boolean vendue;
	public String url_image;

// Liste des vehicules depuis cet attribut statique
	
private static ArrayList<Vehicule> liste_vehicules = new ArrayList<Vehicule>();
	
	protected Vehicule(String type, String marque, String modele, String color) {
		this.setType(type);
		this.setMarque(marque);
		this.setModele(modele);
		this.setColor(color);
		this.setVendue(false);
		this.add_vehicules(this);
		// this.url_image = url;
	}

	public String getMarque() {
		return Marque;
	}

	public void setMarque(String marque) {
		Marque = marque;
	}

	public String getModele() {
		return Modele;
	}

	public void setModele(String modele) {
		Modele = modele;
	}

	public String getColor() {
		return Color;
	}

	public void setColor(String color) {
		Color = color;
	}

	public Boolean getVendue() {
		return vendue;
	}

	public void setVendue(Boolean vendue) {
		this.vendue = vendue;
	}
		
	public String toString() {
		// return "{ Type = " + getType() + ", Marque = " + getMarque() + ", Modele = " + getModele() + ", Couleur = " + getColor() + ", En vente = " + getVendue() + " }" ;
		return "{  " + getType() + ", " + getMarque() + ", " + getModele() + ", " + getColor() + " }" ;
	}

	// public abstract void Ajouter_Element(Vehicule v);
	
	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public static ArrayList<Vehicule> get_vehicules() {
		ArrayList<Vehicule> liste = new ArrayList<Vehicule>();
		
		for(int i=0; i<liste_vehicules.size(); i++) {
			if( liste_vehicules.get(i).getVendue() == false ) {
				liste.add(liste_vehicules.get(i));
			}	
		}
		
		return liste;
	}

	public static ArrayList<Vehicule> get_all_vehicules() {
		return liste_vehicules;
	}
	
	public void add_vehicules(Vehicule element) {	
		liste_vehicules.add((Vehicule)element);
	}

	public static void show_listes() {
		System.out.println(get_vehicules());
	}
	
	
	// methode pour obtenir des vehicules de meme couleur
	public static ArrayList<Vehicule> get_vehicules_same_couleur(String color) {
		
		ArrayList<Vehicule> liste = new ArrayList<Vehicule>();
		
		for(int i=0; i<get_vehicules().size(); i++) {
			if( get_vehicules().get(i).getColor() == color ) {
				liste.add(get_vehicules().get(i));
			}	
		}
//		System.out.println(liste);
		return liste;
	}
	// methode pour obtenir des vehicules de meme marque et meme modele
	
	public static ArrayList<Vehicule> get_vehicules_same_marque_modele(String marque, String modele) {
	
		ArrayList<Vehicule> liste = new ArrayList<Vehicule>();
		
		for(int j=0; j<get_vehicules().size(); j++) {
			if( get_vehicules().get(j).getMarque() == marque && get_vehicules().get(j).getModele() == modele) {
//				System.out.println(" Disponible");
				liste.add(get_vehicules().get(j));
			}
		}
		return liste;
	}
	// methode pour vendre un vehicule donnée

	public static void vendre_vehicule(Vehicule vehicule) {	
		if( get_vehicules().contains(vehicule)) {
			vehicule.setVendue(false);
			// get_vehicules().remove(vehicule);
		}	
	}
}
