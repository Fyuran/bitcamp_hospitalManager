package prontoSoccorsoMenu;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.List;
import java.util.ArrayList;
import consoleMenuUI.Colors;
import consoleMenuUI.MenuUI;
import prontoSoccorsoManagers.EmergencyManager;
import scannerChecks.getInput;
import prontoSoccorso.StaffMember;
import prontoSoccorsoManagers.AmbulatoryManager;
import prontoSoccorsoManagers.StaffManager;
import prontoSoccorsoManagers.TurnManager;
import prontoSoccorso.Ambulatory;
import prontoSoccorso.Emergency;

public class EmergencyMenu {
	private final static String notFoundErrorMsg = toColor("Personale non trovato", Colors.RED);
	private final static String emptyListErrorMsg = toColor("Lista personale vuota", Colors.RED);

	private final static String modifiedMsg = toColor("Personale modificato", Colors.GREEN);
	private final static String removedMsg = toColor("Personale rimosso", Colors.GREEN);

	private final static String[] titles = { "Descrizione", "Staff", "Ambulatorio" }; // used for inputs

	private static String toColor(String text, Colors color) {
		return Colors.toColor(text, color);
	}

	private getInput input = new getInput();
	private EmergencyManager manager;
	private AmbulatoryManager ambulatoryManager;

	public EmergencyMenu(EmergencyManager manager) {
		this.manager = manager;
		this.ambulatoryManager = new AmbulatoryManager();
		MenuUI menu = new MenuUI("Gestione Personale");

		menu.addCmd("Registra Emergenza", () -> addEmergencyMenu());
		menu.showCmds();
	}
		

	private void addEmergencyMenu() {
		System.out.println(manager.filterByTimeSlot(LocalDateTime.now()));
		String[] params = new String[titles.length];

		/*
		 * for (int i = 0; i < titles.length; i++) { params[i] =
		 * input.askLine(toColor("Inserire " + titles[i], Colors.PURPLE)); }
		 */

		createEmergencyObject();
	}

	private Emergency createEmergencyObject() {
		String description = obtainEmergencyDescription();
		List<StaffMember> staffMember = obtainEmergencyStaff();
		
		if(staffMember != null) {
			return null;
		}
		Ambulatory ambulatory = obtainAmbulatory();
		
		if(ambulatory!=null) {
			return new Emergency(description, staffMember, ambulatory);
		}
		
		return null;
	}

	private String obtainEmergencyDescription() {
		return input.askLine("Inserisci descrizione emergenza");
	}

	private List<StaffMember> obtainEmergencyStaff() {
		List<StaffMember> staff = new ArrayList<StaffMember>();

		LocalDateTime now = LocalDateTime.now();
		List<StaffMember> turnStaff = manager.filterByTimeSlot(now);

		if (turnStaff != null) {
			printStaff(turnStaff);

			while (true) {
				int index = input.askInt("Seleziona lo staff, inserisci un numero negativo per terminare l'inserimento");
				if (index >= 0 && index < turnStaff.size()) {
					staff.add(turnStaff.get(index));
				} else if (index < 0 && turnStaff.size() > 0) {
					break;
				} else if (index < 0 && turnStaff.size() == 0) {
					System.out.println("Devi inserire almeno un membro");
				}

				else {
					System.out.println("Input fuori range");
				}
			}
		}
		else {
			System.out.println("Nessun operatore trovato");
		}
		return staff;
	}

	private Ambulatory obtainAmbulatory() {
		List<Ambulatory> ambulatories = ambulatoryManager.viewAmbulatory();
		if (ambulatories.size() > 0) {
			printAmbulatory(ambulatories);

			while (true) {
				int index = input.askInt("Seleziona l'ambulatorio");
				if (index >= 0 && index < ambulatories.size()) {
					return ambulatories.get(index);
				}

				else {
					System.out.println("Input non compreso nel range");
				}
			}
		}
		return null;
	}

	private void printStaff(List<StaffMember> turnStaff) {
		if (turnStaff != null) {
			for (StaffMember staffMember : turnStaff) {
				System.out.println(staffMember);
			}
		}
	}

	private void printAmbulatory(List<Ambulatory> ambulatories) {
		for (Ambulatory ambulatory : ambulatories) {
			System.out.println(ambulatory);
		}
	}

}
