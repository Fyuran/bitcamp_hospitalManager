package prontoSoccorsoManagers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
	private EmergencyManager eMngr = new EmergencyManager(sMngr, tMngr);
	private AmbulatoryManager aMngr = new AmbulatoryManager();
	
	public static void main(String[] args) {
		DepartmentManager department = new DepartmentManager();
		
		MenuUI mainMenu = new MenuUI("Gestione Pronto Soccorso");
		mainMenu.addCmd("Gestione Personale", () -> department.staffMenu());
		mainMenu.addCmd("Gestione Pazienti", () -> department.patientsMenu());
		mainMenu.addCmd("Gestione Turni", () -> department.turnsMenu());
		mainMenu.addCmd("Gestione Emergenze", () -> department.emergencyMenu());
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
	private void emergencyMenu() {
		new EmergencyMenu(eMngr);
	}
	private void Test() {
		Random random = new Random();
		
		List<StaffMember> testStaff = List.of(
				new StaffMember("Marta", "Chiofalo", "Test", "test@info.com"),
				new StaffMember("Alessio", "Cappai", "Test", "test@info.com"),
				new StaffMember("Vincenzo", "Tito", "Test", "test@info.com"),
				new StaffMember("Emanuele", "", "Test", "test@info.com"),
				new StaffMember("Daniele", "", "Test", "test@info.com")
				);
		
		List<Patient> testPatients = List.of(
				new Patient("Daniel", "Camuffo", LocalDate.now().minusDays(random.nextLong(30)), MedCode.RED),
				new Patient("Mauro", "Gariazzo", LocalDate.now().minusDays(random.nextLong(30)), MedCode.WHITE)
				);
		
		List<Turn> testTurns = List.of(
				new Turn( //Morning
						LocalDateTime.of(LocalDate.now(), LocalTime.of(7, 00)),
						LocalDateTime.of(LocalDate.now(), LocalTime.of(14, 00))
						),
				new Turn( //Morning
						LocalDateTime.of(LocalDate.now(), LocalTime.of(14, 00)),
						LocalDateTime.of(LocalDate.now(), LocalTime.of(21, 00))
						),
				new Turn( //Morning
						LocalDateTime.of(LocalDate.now(), LocalTime.of(21, 00)),
						LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(7, 00))
						)
				);
		
		testStaff.forEach(s -> sMngr.add(s));
		testPatients.forEach(s -> pMngr.add(s));
		testTurns.forEach(s -> tMngr.add(s));
		
		tMngr.assignStaffToTurn(
				sMngr.get(random.nextInt(sMngr.getStaff().size())), 
				tMngr.get(random.nextInt(tMngr.getTurns().size()))
				);
		tMngr.assignStaffToTurn(
				sMngr.get(random.nextInt(sMngr.getStaff().size())), 
				tMngr.get(random.nextInt(tMngr.getTurns().size()))
				);
		tMngr.assignStaffToTurn(
				sMngr.get(random.nextInt(sMngr.getStaff().size())), 
				tMngr.get(random.nextInt(tMngr.getTurns().size()))
				);
		tMngr.assignStaffToTurn(
				sMngr.get(random.nextInt(sMngr.getStaff().size())), 
				tMngr.get(random.nextInt(tMngr.getTurns().size()))
				);
		System.out.println(toColor("Valori di test inizializzati", Colors.GREEN));
		
		
	}
}
