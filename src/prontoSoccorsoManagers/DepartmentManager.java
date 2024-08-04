package prontoSoccorsoManagers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import consoleMenuUI.Colors;
import consoleMenuUI.MenuUI;
import prontoSoccorso.MedCode;
import prontoSoccorso.Patient;
import prontoSoccorso.StaffMember;
import prontoSoccorsoMenu.*;

public class DepartmentManager {
	private final static String datePattern = "dd/MM/yyyy";
	private final static DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern(datePattern);
	
	private static String toColor(String text, Colors color) {
		return Colors.toColor(text, color);
	}
	
	private StaffManager sMngr = new StaffManager();
	private PatientManager pMngr = new PatientManager();
	private TurnManager tMngr = new TurnManager();
	private EmergencyManager eMngr = new EmergencyManager();
	private AmbulatoryManager aMngr = new AmbulatoryManager();
	
	public static void main(String[] args) {
		DepartmentManager department = new DepartmentManager();
		
		MenuUI mainMenu = new MenuUI("Gestione Pronto Soccorso");
		mainMenu.addCmd("Gestione Personale", () -> department.ManageStaffMenu());
		mainMenu.addCmd("Gestione Pazienti", () -> department.managePatientsMenu());
		mainMenu.addCmd("Gestione Turni", () -> {});
		mainMenu.addCmd("Gestione Emergenze", () -> {});
		mainMenu.addCmd("Gestione Ambulatori", () -> {});
		mainMenu.addCmd("Test Gestione", () -> department.Test());
		
		mainMenu.showCmds();
	}
	
	private void ManageStaffMenu() {
		new StaffMenu(sMngr);
	}
	private void managePatientsMenu() {
		new PatientMenu(pMngr);
	}
	
	private void Test() {
		
		Random random = new Random();
		
		sMngr.add(new StaffMember("Daniel", "Camuffo", "Test", "test@info.com"));
		sMngr.add(new StaffMember("Mauro", "Gariazzo", "Test", "test@info.com"));
		sMngr.add(new StaffMember("Marta", "Chiofalo", "Test", "test@info.com"));
		sMngr.add(new StaffMember("Alessio", "Cappai", "Test", "test@info.com"));
		sMngr.add(new StaffMember("Vincenzo", "Tito", "Test", "test@info.com"));
		sMngr.add(new StaffMember("Emanuele", "", "Test", "test@info.com"));
		sMngr.add(new StaffMember("Daniele", "", "Test", "test@info.com"));
		
		pMngr.add(new Patient("Daniel", "Camuffo", LocalDate.now().minusDays(random.nextLong(30)), MedCode.RED));
		pMngr.add(new Patient("Mauro", "Gariazzo", LocalDate.now().minusDays(random.nextLong(30)), MedCode.WHITE));
		pMngr.add(new Patient("Marta", "Chiofalo", LocalDate.now().minusDays(random.nextLong(30)), MedCode.YELLOW));
		pMngr.add(new Patient("Alessio", "Cappai", LocalDate.now().minusDays(random.nextLong(30)), MedCode.GREEN));
		pMngr.add(new Patient("Vincenzo", "Tito", LocalDate.now().minusDays(random.nextLong(30)), MedCode.GREEN));
		pMngr.add(new Patient("Emanuele", "", LocalDate.now().minusDays(random.nextLong(30)), MedCode.RED));
		pMngr.add(new Patient("Daniele", "",  LocalDate.now().minusDays(random.nextLong(30)), MedCode.YELLOW));
		System.out.println(toColor("Valori di test inizializzati", Colors.GREEN));
	}
}
