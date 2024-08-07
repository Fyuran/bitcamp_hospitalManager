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
	private final static String notFoundErrorMsg = toColor("Emergenza non trovata", Colors.RED);
	private final static String emptyListErrorMsg = toColor("Lista emergenza vuota", Colors.RED);

	private final static String modifiedMsg = toColor("Emergenza modificata", Colors.GREEN);
	private final static String removedMsg = toColor("Emergenza rimossa", Colors.GREEN);

	// private final static String[] titles = { "Descrizione", "Staff",
	// "Ambulatorio" }; // used for inputs

	private static String toColor(String text, Colors color) {
		return Colors.toColor(text, color);
	}

	private getInput input = new getInput();
	private EmergencyManager manager;
	private AmbulatoryManager ambulatoryManager;

	public EmergencyMenu(EmergencyManager manager) {
		this.manager = manager;
		this.ambulatoryManager = new AmbulatoryManager();
		MenuUI menu = new MenuUI("Gestione Emergenze");

		menu.addCmd("Registra Emergenza", () -> addEmergencyMenu());
		menu.addCmd("Modifica Emergenza", () -> editEmergencyMenu());
		menu.addCmd("Elimina Emergenza", () -> deleteEmergencyMenu());
		menu.addCmd("Visualizza Emergenze", () -> viewEmergenciesMenu());
		menu.showCmds();
	}

	private void addEmergencyMenu() {
		List<StaffMember> staff = manager.filterByTimeSlot(LocalDateTime.now());
		if (staff.size() > 0) {
			manager.add(createEmergencyObject());
		} else {
			System.out.println(toColor("Nessun operatore trovato", Colors.YELLOW));
		}
	}

	private void editEmergencyMenu() {
		viewEmergenciesMenu();
		if (manager.getAll().size() > 0) {
			editEmergencyObject();
		}
	}

	private void deleteEmergencyMenu() {
		viewEmergenciesMenu();
		if (manager.getAll().size() > 0) {
			deleteEmergencyObject();
		}
	}

	private void viewEmergenciesMenu() {
		if (manager.getAll().size() > 0) {
			for (Emergency em : manager.getAll()) {
				System.out.println(em);
			}
		}
		else {
			System.out.println(toColor("Lista vuota", Colors.RED));
		}
	}

	private Emergency createEmergencyObject() {
		String description = obtainEmergencyDescription();
		List<StaffMember> staffMember = obtainEmergencyStaff();

		if (staffMember == null) {
			return null;
		}

		Ambulatory ambulatory = obtainAmbulatory();

		if (ambulatory != null) {
			return new Emergency(description, staffMember, ambulatory);
		}
		return null;
	}

	private void editEmergencyObject() {
		while (true) {
			int index = input.askInt(toColor("Seleziona l'emergenza che vuoi modificare, premi 0 per uscire: ", Colors.PURPLE));

			if (index > 0 && index <= manager.getAll().size()) {
				Emergency emergency = createEmergencyObject();
				if (manager.update(index - 1, emergency)) {
					System.out.println(modifiedMsg);
				}
			} else if (index == 0) {
				break;
			}

			else {
				System.out.println(notFoundErrorMsg);
			}
		}
	}

	private void deleteEmergencyObject() {
		while (true) {
			int index = input
					.askInt(toColor("Seleziona l'emergenza che vuoi eliminare, premi 0 per uscire: ", Colors.PURPLE));

			if (index > 0 && index <= manager.getAll().size()) {
				if (manager.remove(index - 1)) {
					System.out.println(removedMsg);					
				}				
			} 
			else if (index == 0) {
				break;
			}

			else {
				System.out.println(notFoundErrorMsg);
			}
		}
	}

	private String obtainEmergencyDescription() {
		return input.askLine(toColor("Inserisci descrizione emergenza", Colors.PURPLE));
	}

	private List<StaffMember> obtainEmergencyStaff() {
		LocalDateTime now = LocalDateTime.now();
		List<StaffMember> turnStaff = manager.filterByTimeSlot(now);
		List<StaffMember> assignedStaff = new ArrayList<StaffMember>();

		if (turnStaff != null) {
			printStaff(turnStaff);

			while (true) {
				int index = input.askInt(toColor("Seleziona lo staff, premi zero per terminare l'inserimento", Colors.PURPLE));
				if (index > 0 && index <= turnStaff.size()) {
					assignedStaff.add(turnStaff.get(index - 1));
				} else if (index == 0 && assignedStaff.size() > 0) {
					break;
				} else if (index == 0 && assignedStaff.size() == 0) {
					System.out.println(toColor("Devi inserire almeno un membro", Colors.RED));
				} else {
					System.out.println(toColor("Input fuori range", Colors.YELLOW));
				}
			}
		} 
		else {
			System.out.println(toColor("Nessun operatore trovato", Colors.YELLOW));
		}
		return assignedStaff;
	}

	private Ambulatory obtainAmbulatory() {
		List<Ambulatory> ambulatories = ambulatoryManager.viewAmbulatory();
		if (ambulatories.size() > 0) {
			printAmbulatory(ambulatories);

			while (true) {
				int index = input.askInt(toColor("Seleziona l'ambulatorio", Colors.PURPLE));
				if (index > 0 && index <= ambulatories.size()) {
					System.out.println((toColor("Hai selezionato " + ambulatories.get(index - 1), Colors.GREEN)));
					return ambulatories.get(index - 1);
				}

				else {
					System.out.println(toColor("Input non compreso nel range", Colors.RED));
				}
			}
		}
		return null;
	}

	private void printStaff(List<StaffMember> turnStaff) {
		for (StaffMember staffMember : turnStaff) {
			System.out.println(staffMember);
		}
	}

	private void printAmbulatory(List<Ambulatory> ambulatories) {
		int index = 1;
		for (Ambulatory ambulatory : ambulatories) {
			System.out.println(String.valueOf(index) + ": " + ambulatory);
			index++;
		}
	}

}
