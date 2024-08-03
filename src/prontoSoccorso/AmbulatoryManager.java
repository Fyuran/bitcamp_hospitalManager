package prontoSoccorso;

import java.util.ArrayList;


public class AmbulatoryManager {

	int currentId;
	private ArrayList<Ambulatory> ambulatories;

	// costruttore
	public AmbulatoryManager() {
		this.currentId = 0;
		this.ambulatories = new ArrayList<Ambulatory>();
		addAmbulatory("Ortopedia");
		addAmbulatory("Cardiologia");
		addAmbulatory("Neonatologia");
		addAmbulatory("Geriatria");
		addAmbulatory("Fisioterapia");
	}

	public void addAmbulatory(String name) {
		if (ambulatories.size() >= 7) {
			System.out.println("Non puoi inserire altri ambulatori");
			return;
		}
		for (Ambulatory ambulatory : ambulatories) {
			if(ambulatory.getName().equalsIgnoreCase(name)) {
				System.out.println("L'ambulatorio " + name + " è già presente");
			}
		}
		currentId++;
		Ambulatory ambulatory = new Ambulatory(currentId, name, true);
		ambulatories.add(ambulatory);
		System.out.println("Ambulatorio " + name + " aggiunto!");
	}

	public ArrayList<Ambulatory> viewAmbulatory() {
		ArrayList<Ambulatory> list = new ArrayList<>();
		for (Ambulatory ambulatory : ambulatories) {
			if(ambulatory.isAvailable()) {
				list.add(ambulatory);
			}
		}
		return list;

	}
	
	public void removeAmbulatory(int index) {
		try {
			ambulatories.remove(index);
		} catch (IndexOutOfBoundsException e) {
			System.out.println("L'indice " + index + " non è valido");
		}
	}
	
}
