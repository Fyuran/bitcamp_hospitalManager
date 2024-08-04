package prontoSoccorsoMenu;

import consoleMenuUI.Colors;
import consoleMenuUI.MenuUI;
import prontoSoccorso.StaffMember;
import prontoSoccorsoManagers.StaffManager;
import scannerChecks.getInput;

public class StaffMenu {
	private final static String notFoundErrorMsg = toColor("Personale non trovato", Colors.RED);
	private final static String emptyListErrorMsg = toColor("Lista personale vuota", Colors.RED);
	
	private final static String modifiedMsg = toColor("Personale modificato", Colors.GREEN);
	private final static String removedMsg = toColor("Personale rimosso", Colors.GREEN);
	
	private final static String[] titles = { "Nome", "Cognome", "Specializzazione", "e-mail" }; //used for inputs
	
	private static String toColor(String text, Colors color) {
		return Colors.toColor(text, color);
	}
	
	private StaffManager manager;
	private getInput input = new getInput();
	
	
	public StaffMenu(StaffManager manager) {
			
		MenuUI menu = new MenuUI("Gestione Personale");
		
		menu.addCmd("Aggiungi personale", () -> addStaffMenu());
		menu.addCmd("Rimuovi personale", () -> deleteStaffMenu());
		menu.addCmd("Visualizza elenco personale", () -> viewStaff());
		menu.addCmd("Modifica informazioni personale", () -> editStaffMenu());
		menu.showCmds();
		
	}
	
	private void addStaffMenu() {	
		String[] params = new String[titles.length];

		for (int i = 0; i < titles.length; i++) {
			params[i] = input.askLine(toColor("Inserire " + titles[i], Colors.PURPLE));
		}

		StaffMember staff = new StaffMember(params[0], params[1], params[2], params[3]);
		manager.add(staff);
		System.out.println(toColor("Aggiunto nuovo membro del personale", Colors.GREEN));
	}
	
	private void viewStaff() {
		if(manager.isEmpty()) {
			System.out.println(emptyListErrorMsg);
			return;
		}
		System.out.println(manager);
	}
	
	private void deleteStaffMenu() {
		if(manager.isEmpty()) {
			System.out.println(emptyListErrorMsg);
			return;
		}
		
		MenuUI removeMenu = new MenuUI("Rimuovi Personale");
		removeMenu.addCmd("Rimuovi per indice", ()->{
			System.out.println(manager);	
			int choice = input.askInt(toColor("Inserire l'indice da rimuovere", Colors.PURPLE));
			if(manager.remove(choice-1))
				System.out.println(removedMsg);
			else
				System.out.println(notFoundErrorMsg);
		});
		removeMenu.addCmd("Rimuovi per ID", ()->{
			System.out.println(manager);	
			String id = input.askLine(toColor("Inserire l'ID da rimuovere", Colors.PURPLE));
			if(manager.remove(id))
				System.out.println(removedMsg);
			else
				System.out.println(notFoundErrorMsg);
		});
		
		removeMenu.showCmds();
	}
	
	private void editStaffMenu() {
		if(manager.isEmpty()) {
			System.out.println(emptyListErrorMsg);
			return;
		}
		
		MenuUI editMenu = new MenuUI("Modifica Personale");
		editMenu.addCmd("Modifica per indice", ()->{
			System.out.println(manager);	
			
			int choice = input.askInt(toColor("Inserire l'indice da modificare", Colors.PURPLE));
			StaffMember chosen = manager.get(choice-1);
			if(chosen == null) {
				System.out.println(notFoundErrorMsg);
				return;
			};
			
			String[] params = new String[titles.length];

			for (int i = 0; i < titles.length; i++) {
				params[i] = input.askLine(toColor("Inserire " + titles[i], Colors.PURPLE));
			}
			

			StaffMember edited = new StaffMember(params[0], params[1], chosen.getId(), params[2], params[3]);				
			if(manager.update(choice-1, edited))
				System.out.println(modifiedMsg);
			else
				System.out.println(notFoundErrorMsg);
		});
		editMenu.addCmd("Modifica per ID", ()->{
			System.out.println(manager);	
			String id = input.askLine(toColor("Inserire l'ID da modificare", Colors.PURPLE));
			StaffMember chosen = manager.get(id);
			if(chosen == null) {
				System.out.println(notFoundErrorMsg);
				return;
			};
			
			String[] params = new String[titles.length];

			for (int i = 0; i < titles.length; i++) {
				params[i] = input.askLine(toColor("Inserire " + titles[i], Colors.PURPLE));
			}
			

			StaffMember edited = new StaffMember(params[0], params[1], chosen.getId(), params[2], params[3]);				
			if(manager.update(id, edited))
				System.out.println(modifiedMsg);
			else
				System.out.println(notFoundErrorMsg);
		});
		
		editMenu.showCmds();
	}
}
