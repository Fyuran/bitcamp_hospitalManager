package prontoSoccorsoMenu;

import java.time.LocalDateTime;

import consoleMenuUI.Colors;
import consoleMenuUI.MenuUI;
import prontoSoccorsoManagers.EmergencyManager;
import scannerChecks.getInput;

public class EmergencyMenu {
	private final static String notFoundErrorMsg = toColor("Personale non trovato", Colors.RED);
	private final static String emptyListErrorMsg = toColor("Lista personale vuota", Colors.RED);
	
	private final static String modifiedMsg = toColor("Personale modificato", Colors.GREEN);
	private final static String removedMsg = toColor("Personale rimosso", Colors.GREEN);
	
	private static String toColor(String text, Colors color) {
		return Colors.toColor(text, color);
	}
	
	private getInput input = new getInput();
	private EmergencyManager manager;
	
	public EmergencyMenu(EmergencyManager manager) {
		this.manager = manager;	
		MenuUI menu = new MenuUI("Gestione Personale");
		
		menu.addCmd("Registra Emergenza", () -> addEmergencyMenu());
		menu.showCmds();
		
	}
	
	private void addEmergencyMenu() {
		System.out.println(manager.filterByTimeSlot(LocalDateTime.now()));
	}
}
