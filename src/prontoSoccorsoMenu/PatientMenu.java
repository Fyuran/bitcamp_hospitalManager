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
	private final static String emptyListErrorMsg = toColor("Lista Paziente vuota", Colors.RED);
	
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
			chooseMenu.addCmd("Cancella", () -> deletePatientsMenu(false));
			chooseMenu.addCmd("Dimetti", () -> deletePatientsMenu(true)); //true if we want to manage admittedPatients
			
			chooseMenu.showCmds();
		});
		menu.addCmd("Visualizza elenco pazienti", () -> viewPatients()); //admitted and dismissed patients choice is a submenu
		menu.addCmd("Modifica informazioni pazienti", () -> editPatientsMenu());
		
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
		String codeInput = input.askLine("");
		MedCode code;
		if(getInput.isNumber(codeInput))
			code = MedCode.toMedCode(Integer.parseInt(codeInput));
		else
			code = MedCode.toMedCode(codeInput);
			
		
		Patient patient = new Patient(params[0], params[1], code);
		manager.add(patient);
		System.out.println(toColor("Registrato paziente", Colors.GREEN));
	}
	
	private void viewPatients() {
		if(manager.isEmpty()) {
			System.out.println(emptyListErrorMsg);
			return;
		}
		System.out.println(manager);
	}
	
	private void deletePatientsMenu(boolean isAdmitted) {
	
		String comand = isAdmitted ? "Dimetti" : "Cancella";
		MenuUI dismissMenu = new MenuUI(comand + " Paziente");
		dismissMenu.addCmd(comand + "per indice", ()->{
			List<Patient> patients;
			if(isAdmitted)
				patients = manager.filter(p -> !p.isDismissed());
			else
				patients = manager.filter(p -> p.isDismissed());
			
			System.out.println("Lista pazienti:\n" + CRUD.listToString(patients));	
			int choice = input.askInt(toColor("Inserire l'indice", Colors.PURPLE));
			Patient patient = patients.get(choice);
			if(manager.remove(patient))
				System.out.println(dismissedMsg);
			else
				System.out.println(notFoundErrorMsg);
		});
		dismissMenu.addCmd(comand + "per ID", ()->{
			List<Patient> patients;
			if(isAdmitted)
				patients = manager.filter(p -> !p.isDismissed());
			else
				patients = manager.filter(p -> p.isDismissed());
			
			System.out.println("Lista pazienti:\n" + CRUD.listToString(patients));	
			String id = input.askLine(toColor("Inserire l'ID", Colors.PURPLE));
			Patient chosen = manager.get(id);
			if(chosen == null) {
				System.out.println(notFoundErrorMsg);
				return;
			};
			
			if(manager.remove(id))
				System.out.println(dismissedMsg);
			else
				System.out.println(notFoundErrorMsg);
		});
		
	}
	
	private void editPatientsMenu() {
		if(manager.isEmpty()) {
			System.out.println(emptyListErrorMsg);
			return;
		}
		
		
		MenuUI editMenu = new MenuUI("Modifica Paziente");
		editMenu.addCmd("Modifica per indice", ()->{
			System.out.println(manager);	
			
			int choice = input.askInt(toColor("Inserire l'indice da modificare", Colors.PURPLE));
			Patient chosen = manager.get(choice-1);
			if(chosen == null) {
				System.out.println(notFoundErrorMsg);
				return;
			};
			
			String[] params = new String[titles.length];

			for (int i = 0; i < titles.length; i++) {
				params[i] = input.askLine(toColor("Inserire " + titles[i], Colors.PURPLE));
			}
			
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
				
			
			Patient edited = new Patient(params[0], params[1], code);			
			if(manager.update(choice-1, edited))
				System.out.println(modifiedMsg);
			else
				System.out.println(notFoundErrorMsg);
		});
		editMenu.addCmd("Modifica per ID", ()->{
			System.out.println(manager);	
			String id = input.askLine(toColor("Inserire l'ID da modificare", Colors.PURPLE));
			Patient chosen = manager.get(id);
			if(chosen == null) {
				System.out.println(notFoundErrorMsg);
				return;
			};
			
			String[] params = new String[titles.length];

			for (int i = 0; i < titles.length; i++) {
				params[i] = input.askLine(toColor("Inserire " + titles[i], Colors.PURPLE));
			}
			


		});
		
		editMenu.showCmds();
	}
}
