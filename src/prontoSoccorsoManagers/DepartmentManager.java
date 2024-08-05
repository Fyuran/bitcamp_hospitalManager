package prontoSoccorsoManagers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import consoleMenuUI.Colors;
import consoleMenuUI.MenuUI;
import prontoSoccorso.MedCode;
import prontoSoccorso.Patient;
import prontoSoccorso.StaffMember;
import prontoSoccorso.Turn;
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
		mainMenu.addCmd("Gestione Personale", () -> department.staffMenu());
		mainMenu.addCmd("Gestione Pazienti", () -> department.patientsMenu());
		mainMenu.addCmd("Gestione Turni", () -> department.turnsMenu());
		mainMenu.addCmd("Gestione Emergenze", () -> {});
		mainMenu.addCmd("Gestione Ambulatori", () -> {});
		mainMenu.addCmd("Test Gestione", () -> department.Test());
		
		mainMenu.showCmds();
	}
	
	private void staffMenu() {
		new StaffMenu(sMngr);
	}
	private void patientsMenu() {
		new PatientMenu(pMngr, sMngr);
	}
	private void turnsMenu() {
		new TurnMenu(tMngr, sMngr);
	}
	
	private void Test() {
		
		Random random = new Random();
		tMngr.add(new Turn( //Morning
				LocalDateTime.of(LocalDate.now(), LocalTime.of(7, 00)),
				LocalDateTime.of(LocalDate.now(), LocalTime.of(14, 00))
				));
		tMngr.add(new Turn( //Morning
				LocalDateTime.of(LocalDate.now(), LocalTime.of(14, 00)),
				LocalDateTime.of(LocalDate.now(), LocalTime.of(21, 00))
				));
		tMngr.add(new Turn( //Morning
				LocalDateTime.of(LocalDate.now(), LocalTime.of(21, 00)),
				LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(7, 00))
				));
		
		sMngr.add(new StaffMember("Marta", "Chiofalo", "Test", "test@info.com"));
		sMngr.add(new StaffMember("Alessio", "Cappai", "Test", "test@info.com"));
		sMngr.add(new StaffMember("Vincenzo", "Tito", "Test", "test@info.com"));
		sMngr.add(new StaffMember("Emanuele", "", "Test", "test@info.com"));
		sMngr.add(new StaffMember("Daniele", "", "Test", "test@info.com"));
		
		pMngr.add(new Patient("Daniel", "Camuffo", LocalDate.now().minusDays(random.nextLong(30)), MedCode.RED));
		pMngr.add(new Patient("Mauro", "Gariazzo", LocalDate.now().minusDays(random.nextLong(30)), MedCode.WHITE));

		System.out.println(toColor("Valori di test inizializzati", Colors.GREEN));
		
		
	}
}
