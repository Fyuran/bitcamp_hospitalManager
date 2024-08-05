package prontoSoccorsoMenu;

import java.util.List;

import consoleMenuUI.Colors;
import consoleMenuUI.MenuUI;
import prontoSoccorso.MedCode;
import prontoSoccorso.Patient;
import prontoSoccorso.StaffMember;
import prontoSoccorsoManagers.CRUD;
import prontoSoccorsoManagers.PatientManager;
import prontoSoccorsoManagers.StaffManager;
import scannerChecks.getInput;

public class PatientMenu {
	private final static String notFoundErrorMsg = toColor("Paziente non trovato", Colors.RED);
	private final static String emptyListErrorMsg = toColor("Lista Pazienti vuota", Colors.RED);
	private final static String emptyStaffListErrorMsg = toColor("Lista personale vuota", Colors.RED);
	private final static String staffAlreadyAssignedErrorMsg = toColor("Personale già assegnato a questo paziente", Colors.RED);
	private final static String staffNotFoundErrorMsg = toColor("Personale non trovato", Colors.RED);
	
	private final static String modifiedMsg = toColor("Paziente modificato", Colors.GREEN);
	private final static String removedMsg = toColor("Paziente rimosso", Colors.GREEN);
	private final static String dismissedMsg = toColor("Paziente dimesso", Colors.GREEN);
	private final static String assignedMsg = toColor("Turno assegnato", Colors.GREEN);
	
	private final static String[] titles = { "Nome", "Cognome"}; //used for inputs
	
	private static String toColor(String text, Colors color) {
		return Colors.toColor(text, color);
	}
	
	private PatientManager manager;
	private StaffManager staffManager;
	private getInput input = new getInput();
	
	public PatientMenu(PatientManager manager, StaffManager staffManager) {
		this.manager = manager;
		this.staffManager = staffManager;
		
		MenuUI menu = new MenuUI("Gestione Pazienti");
		
		menu.addCmd("Registra paziente", () -> addPatientMenu());
		menu.addCmd("Dimetti/Cancella paziente", () -> {
			if(manager.isEmpty()) {
				System.out.println(emptyListErrorMsg);
				return;
			}
			MenuUI chooseMenu = new MenuUI("Opzioni");
			chooseMenu.addCmd("Dimetti", () -> deletePatientsMenu(true)); //true if we want to manage admittedPatients
			chooseMenu.addCmd("Cancella", () -> deletePatientsMenu(false));
			
			chooseMenu.showCmds();
		});
		menu.addCmd("Visualizza elenco pazienti", () -> {
			if(manager.isEmpty()) {
				System.out.println(emptyListErrorMsg);
				return;
			}
			MenuUI chooseMenu = new MenuUI("Opzioni");
			chooseMenu.addCmd("Amessi", () -> viewPatients(true)); //true if we want to manage admittedPatients
			chooseMenu.addCmd("Dimessi", () -> viewPatients(false));
			
			chooseMenu.showCmds();
		}); //admitted and dismissed patients choice is a submenu
		menu.addCmd("Modifica informazioni pazienti", () -> {
			if(manager.isEmpty()) {
				System.out.println(emptyListErrorMsg);
				return;
			}
			MenuUI chooseMenu = new MenuUI("Opzioni");
			chooseMenu.addCmd("Amessi", () -> editPatientsMenu(true)); //true if we want to manage admittedPatients
			chooseMenu.addCmd("Dimessi", () -> editPatientsMenu(false));
			
			chooseMenu.showCmds();
		});
		menu.addCmd("Assegna Personale a Paziente", () -> assignStaffMenu());
		
		menu.showCmds();
		
	}
	
	private void addPatientMenu() {
		String[] params = new String[titles.length];

		for (int i = 0; i < titles.length; i++) {
			params[i] = input.askLine(toColor("Inserire " + titles[i], Colors.PURPLE));
		}
		
		String[] codes = {"Bianco", "Verde", "Giallo", "Rosso"};
		System.out.println("Inserire codice: ");
		for (int i = 0; i < codes.length; i++) {
			System.out.println("\t" + (i+1) + ". " + codes[i]);
		}
		MedCode code = null;
		
		while(code == null) {
		String codeInput = input.askLine("");
		if(getInput.isNumber(codeInput))
			code = MedCode.toMedCode(Integer.parseInt(codeInput));
		else
			code = MedCode.toMedCode(codeInput);
		
			if(code == null) {
				System.out.println(toColor("Codice errato", Colors.RED));
			}
		}
		
		Patient patient = new Patient(params[0], params[1], code);
		manager.add(patient);
		System.out.println(toColor("Registrato paziente", Colors.GREEN));
	}
	
	private void assignStaffMenu() {
		if(manager.isEmpty()) {
			System.out.println(emptyListErrorMsg);
			return;
		}
		if(staffManager.isEmpty()) {
			System.out.println(emptyStaffListErrorMsg);
			return;
		}
		
		MenuUI dismissMenu = new MenuUI("Assegna personale a Paziente");
		dismissMenu.addCmd("Assegna per indice", ()->{
			if(manager.isEmpty()) {
				System.out.println(emptyListErrorMsg);
				return;
			}
			if(staffManager.isEmpty()) {
				System.out.println(emptyStaffListErrorMsg);
				return;
			}
			System.out.println(staffManager);
			
			StaffMember chosenStaff = null;
			while(chosenStaff == null) {
				int choice = input.askInt(toColor("Inserire l'indice del personale a cui assegnare il paziente", Colors.PURPLE)) - 1;
				chosenStaff = staffManager.get(choice);
				if(chosenStaff == null)
					System.out.println(staffNotFoundErrorMsg);
			}
			
			System.out.println(manager);
			Patient chosenPatient = null;
			while(chosenPatient == null) {
				int choice = input.askInt(toColor("Inserire l'indice del paziente", Colors.PURPLE)) - 1;
				chosenPatient = manager.get(choice);
				if(chosenPatient == null)
					System.out.println(notFoundErrorMsg);
			}
			
			if(!chosenPatient.getAssigned().contains(chosenStaff)) {
				manager.assignStaffToPatient(chosenStaff, chosenPatient);
				System.out.println(assignedMsg);				
			} else {
				System.out.println(staffAlreadyAssignedErrorMsg);
			}
			
			
		});
		dismissMenu.addCmd("Assegna per ID", ()->{
			
			if(manager.isEmpty()) {
				System.out.println(emptyListErrorMsg);
				return;
			}
			if(staffManager.isEmpty()) {
				System.out.println(emptyStaffListErrorMsg);
				return;
			}
			System.out.println(staffManager);
			
			StaffMember chosenStaff = null;
			while(chosenStaff == null) {
				String choice = input.askLine(toColor("Inserire l'ID del personale a cui assegnare il paziente", Colors.PURPLE));
				chosenStaff = staffManager.get(choice);
				if(chosenStaff == null)
					System.out.println(staffNotFoundErrorMsg);
			}
			
			System.out.println(manager);
			Patient chosenPatient = null;
			while(chosenPatient == null) {
				String choice = input.askLine(toColor("Inserire l'ID del personale a cui assegnare il paziente", Colors.PURPLE));
				chosenPatient = manager.get(choice);
				if(chosenPatient == null)
					System.out.println(notFoundErrorMsg);
			}
			
			if(!chosenPatient.getAssigned().contains(chosenStaff)) {
				manager.assignStaffToPatient(chosenStaff, chosenPatient);
				System.out.println(assignedMsg);				
			} else {
				System.out.println(staffAlreadyAssignedErrorMsg);
			}
			
		});
		dismissMenu.showCmds();
	}
	
	private void viewPatients(boolean isAdmitted) {
		if(manager.isEmpty()) {
			System.out.println(emptyListErrorMsg);
			return;
		}
		if(isAdmitted) {
			List<Patient> temp = manager.filter(p -> !p.isDismissed());
			if(temp.isEmpty()) {
				System.out.println(emptyListErrorMsg);
				return;
			}
			System.out.println("Lista Personale amesso:" + CRUD.listToString(temp, 1));
		}
		else {
			List<Patient> temp = manager.filter(p -> p.isDismissed());
			if(temp.isEmpty()) {
				System.out.println(emptyListErrorMsg);
				return;
			}
			System.out.println("Lista Personale dimesso:" + CRUD.listToString(temp, 1));
		}
	}
	
	private void deletePatientsMenu(boolean isAdmitted) {
	
		String comand = isAdmitted ? "Dimetti" : "Cancella";
		MenuUI dismissMenu = new MenuUI(comand + " Paziente");
		dismissMenu.addCmd(comand + " per indice", ()->{
			
			Patient patient = getElemByIndex(isAdmitted);
			if(manager.remove(patient))
				System.out.println(dismissedMsg + " " + patient.getName());
		});
		dismissMenu.addCmd(comand + " per ID", ()->{
			
			Patient patient = getElemByID(isAdmitted);
			if(manager.remove(patient))
				System.out.println(dismissedMsg + " " + patient.getName());
		});
		dismissMenu.showCmds();
	}
	
	private void editPatientsMenu(boolean isAdmitted) {
		if(manager.isEmpty()) {
			System.out.println(emptyListErrorMsg);
			return;
		}
		
		
		MenuUI editMenu = new MenuUI("Modifica Paziente");
		editMenu.addCmd("Modifica per indice", ()->{
			Patient patient = getElemByIndex(isAdmitted);
			if(patient == null) return;
			
			String[] params = new String[titles.length];
			for (int i = 0; i < titles.length; i++) {
				params[i] = input.askLine(toColor("Inserire " + titles[i], Colors.PURPLE));
			}
			MedCode code = getCode();
	
			patient.setName(params[0]);
			patient.setSurname(params[1]);
			patient.setCode(code);
	
			if(manager.update(patient))
				System.out.println(modifiedMsg + " " + patient.getName());
		});
		editMenu.addCmd("Modifica per ID", ()->{
			Patient patient = getElemByID(isAdmitted);
			if(patient == null) return;
			
			String[] params = new String[titles.length];
			for (int i = 0; i < titles.length; i++) {
				params[i] = input.askLine(toColor("Inserire " + titles[i], Colors.PURPLE));
			}
			
			MedCode code = getCode();

			patient.setName(params[0]);
			patient.setSurname(params[1]);
			patient.setCode(code);
	
			if(manager.update(patient))
				System.out.println(modifiedMsg + " " + patient.getName());
		});
		
		editMenu.showCmds();
	}


	public Patient getElemByIndex(boolean isAdmitted) {
		List<Patient> patients = getFilteredList(isAdmitted);
		if(patients.isEmpty()) {
			System.out.println(emptyListErrorMsg);
			return null;
		}
		
		viewPatients(isAdmitted);
		Patient chosen = null;
		int choice = input.askInt(toColor("Inserire l'indice", Colors.PURPLE)) - 1;
		if(choice >= 0 && choice < patients.size())
			chosen = patients.get(choice);
		
		if(chosen == null) {
			System.out.println(notFoundErrorMsg);
			return null;
		};

		return chosen;
	}

	public List<Patient> getFilteredList(boolean isAdmitted) {
		if(isAdmitted)
			return manager.filter(p -> !p.isDismissed());
		else
			return manager.filter(p -> p.isDismissed());
	}

	public Patient getElemByID(boolean isAdmitted) {
		List<Patient> patients = getFilteredList(isAdmitted);
		if(patients.isEmpty()) {
			System.out.println(emptyListErrorMsg);
			return null;
		}
		
		viewPatients(isAdmitted);
		Patient chosen = null;

		String id = input.askLine(toColor("Inserire l'ID", Colors.PURPLE));
		chosen = manager.get(id);
		if(chosen == null) {
			System.out.println(notFoundErrorMsg);
			return null;
		};
		return chosen;
	}
	
	public MedCode getCode() {
		String[] codes = {"Bianco", "Verde", "Giallo", "Rosso"};
		System.out.println("Inserire codice: ");
		for (int i = 0; i < codes.length; i++) {
			System.out.println("\t" + (i+1) + ". " + codes[i]);
		}
		String codeInput = input.askLine("");
		MedCode code;
		if(getInput.isNumber(codeInput))
			code = MedCode.toMedCode(Integer.parseInt(codeInput));
		else
			code = MedCode.toMedCode(codeInput);
		return code;
	}
}
