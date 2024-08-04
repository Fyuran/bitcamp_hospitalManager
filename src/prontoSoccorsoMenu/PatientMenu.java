package prontoSoccorsoMenu;

import java.util.List;

import consoleMenuUI.Colors;
import consoleMenuUI.MenuUI;
import prontoSoccorso.MedCode;
import prontoSoccorso.Patient;
import prontoSoccorsoManagers.CRUD;
import prontoSoccorsoManagers.PatientManager;
import scannerChecks.getInput;

public class PatientMenu {
	private final static String notFoundErrorMsg = toColor("Paziente non trovato", Colors.RED);
	private final static String emptyListErrorMsg = toColor("Lista Pazienti vuota", Colors.RED);
	
	private final static String modifiedMsg = toColor("Paziente modificato", Colors.GREEN);
	private final static String removedMsg = toColor("Paziente rimosso", Colors.GREEN);
	private final static String dismissedMsg = toColor("Paziente dimesso", Colors.GREEN);
	
	private final static String[] titles = { "Nome", "Cognome"}; //used for inputs
	
	private static String toColor(String text, Colors color) {
		return Colors.toColor(text, color);
	}
	
	private PatientManager manager;
	private getInput input = new getInput();
	
	public PatientMenu(PatientManager manager) {
		this.manager = manager;
		
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
	
	private void viewPatients(boolean isAdmitted) {
		if(manager.isEmpty()) {
			System.out.println(emptyListErrorMsg);
			return;
		}
		if(isAdmitted)
			System.out.println("Lista Personale amesso:\n" + CRUD.listToString(manager.filter(p -> !p.isDismissed())));
		else
			System.out.println("Lista Personale dimesso:\n" + CRUD.listToString(manager.filter(p -> p.isDismissed())));
	}
	
	private void deletePatientsMenu(boolean isAdmitted) {
	
		String comand = isAdmitted ? "Dimetti" : "Cancella";
		MenuUI dismissMenu = new MenuUI(comand + " Paziente");
		dismissMenu.addCmd(comand + " per indice", ()->{
			
			Patient patient = getElemByIndex(isAdmitted);
			if(manager.remove(patient))
				System.out.println(patient + " " + dismissedMsg);
		});
		dismissMenu.addCmd(comand + " per ID", ()->{
			
			Patient patient = getElemByID(isAdmitted);
			if(manager.remove(patient))
				System.out.println(patient + " " + dismissedMsg);
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
	
			if(manager.update(patient)) {
				System.out.println(modifiedMsg + " " + patient);
				viewPatients(isAdmitted);
			}
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
				System.out.println(modifiedMsg + " " + patient);
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
		int choice = input.askInt(toColor("Inserire l'indice", Colors.PURPLE));
		while(chosen == null) {
			chosen = patients.get(choice-1);
			if(chosen == null)
				System.out.println(notFoundErrorMsg);
		}
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
		while(chosen == null) {
			chosen = manager.get(id);
			if(chosen == null)
				System.out.println(notFoundErrorMsg);
		}
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
