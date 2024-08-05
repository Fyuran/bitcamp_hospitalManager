package prontoSoccorsoMenu;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import consoleMenuUI.Colors;
import consoleMenuUI.MenuUI;
import prontoSoccorso.StaffMember;
import prontoSoccorso.Turn;
import prontoSoccorsoManagers.StaffManager;
import prontoSoccorsoManagers.TurnManager;
import scannerChecks.getInput;

public class TurnMenu {
	private static String toColor(String text, Colors color) {
		return Colors.toColor(text, color);
	}
	
	private TurnManager manager;
	private final StaffManager staffManager;
	private getInput input = new getInput();
	
	private final static String datePattern = "dd/MM/yyyy HH:mm";
	private final static DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern(datePattern);
	
	private final static String notFoundErrorMsg = toColor("Turno non trovato", Colors.RED);
	private final static String staffNotFoundErrorMsg = toColor("Personale non trovato", Colors.RED);
	private final static String emptyListErrorMsg = toColor("Lista turni vuota", Colors.RED);
	private final static String emptyStaffListErrorMsg = toColor("Lista personale vuota", Colors.RED);
	private final static String staffAlreadyInTurnErrorMsg = toColor("Personale già in questo turno", Colors.RED);
	
	private final static String addedMsg = toColor("Turno aggiunto", Colors.GREEN);
	private final static String modifiedMsg = toColor("Turno modificato", Colors.GREEN);
	private final static String removedMsg = toColor("Turno rimosso", Colors.GREEN);
	private final static String assignedMsg = toColor("Turno assegnato", Colors.GREEN);
	
	public TurnMenu(TurnManager manager, StaffManager staffManager) {
		this.manager = manager;
		this.staffManager = staffManager;
		
		MenuUI menu = new MenuUI("Gestione Personale");
		
		menu.addCmd("Aggiungi turno", () -> addTurnMenu());
		menu.addCmd("Rimuovi turno", () -> deleteTurnMenu());
		menu.addCmd("Assegna turni", () -> assignTurnMenu());
		menu.addCmd("Modifica turno", () -> editTurnMenu());
		menu.addCmd("Visualizza elenco turni", () -> viewTurns());
		menu.showCmds();
		
	}

	private void addTurnMenu() {
		LocalDateTime start = getStartDate();
		LocalDateTime end = getEndDate(start);
		
		Turn turn = new Turn(start,end);
		manager.add(turn);
		
		System.out.println(addedMsg);
	}

	private void viewTurns() {
		if(manager.isEmpty()) {
			System.out.println(emptyListErrorMsg);
			return;
		}
		System.out.println(manager);
	}

	private void editTurnMenu() {
		if(manager.isEmpty()) {
			System.out.println(emptyListErrorMsg);
			return;
		}
		System.out.println(manager);	
		
		int choice = input.askInt(toColor("Inserire l'indice da modificare", Colors.PURPLE)) - 1;
		Turn turn = manager.get(choice);
		if(turn == null) {
			System.out.println(notFoundErrorMsg);
			return;
		};
		
		LocalDateTime start = getStartDate();
		LocalDateTime end = getEndDate(start);	
    	turn.setStart(start);
    	turn.setEnd(end);
    	turn.setSlot(Turn.TimeSlot.getTimeSlot(turn)); //renew slot

		System.out.println(modifiedMsg);

	}

	private void assignTurnMenu() {
		if(manager.isEmpty()) {
			System.out.println(emptyListErrorMsg);
			return;
		}
		if(staffManager.isEmpty()) {
			System.out.println(emptyStaffListErrorMsg);
			return;
		}
		MenuUI assignMenu = new MenuUI("Assegna turno a personale");
		assignMenu.addCmd("Assegna per indice", ()->{
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
				int choice = input.askInt(toColor("Inserire l'indice del personale a cui assegnare il turno", Colors.PURPLE)) - 1;
				chosenStaff = staffManager.get(choice);
				if(chosenStaff == null)
					System.out.println(staffNotFoundErrorMsg);
			}
			
			System.out.println(manager);
			Turn chosenTurn = null;
			while(chosenTurn == null) {
				int choice = input.askInt(toColor("Inserire l'indice del turno", Colors.PURPLE)) - 1;
				chosenTurn = manager.get(choice);
				if(chosenTurn == null)
					System.out.println(notFoundErrorMsg);
			}
			
			if(!chosenTurn.getAssignedStaff().contains(chosenStaff)) {
				manager.assignStaffToTurn(chosenStaff, chosenTurn);
				System.out.println(assignedMsg);				
			} else {
				System.out.println(staffAlreadyInTurnErrorMsg);
			}
			
		});
		assignMenu.addCmd("Assegna per ID", ()->{
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
				String id = input.askLine(toColor("Inserire l'ID del personale a cui assegnare il turno", Colors.PURPLE));
				chosenStaff = staffManager.get(id);
				if(chosenStaff == null)
					System.out.println(staffNotFoundErrorMsg);
			}
			
			System.out.println(manager);
			Turn chosenTurn = null;
			while(chosenTurn == null) {
				int choice = input.askInt(toColor("Inserire l'indice del turno", Colors.PURPLE)) - 1;
				chosenTurn = manager.get(choice);
				if(chosenTurn == null)
					System.out.println(notFoundErrorMsg);
			}
			
			if(!chosenTurn.getAssignedStaff().contains(chosenStaff)) {
				manager.assignStaffToTurn(chosenStaff, chosenTurn);
				System.out.println(assignedMsg);				
			} else {
				System.out.println(staffAlreadyInTurnErrorMsg);
			}
		});
		
		assignMenu.showCmds();

	}

	private void deleteTurnMenu() {
		if(manager.isEmpty()) {
			System.out.println(emptyListErrorMsg);
			return;
		}
		System.out.println(manager);	
		int choice = input.askInt(toColor("Inserire l'indice da rimuovere", Colors.PURPLE)) - 1;
		if(manager.remove(choice))
			System.out.println(removedMsg);
		else
			System.out.println(notFoundErrorMsg);
	}
	
	private LocalDateTime getStartDate() {
		LocalDateTime start = null;
		boolean isValidInput = false;
		while(!isValidInput) {
			try {
				String date = input.askLine("Inserisci la data di inizio nel formato " + datePattern);
				start = LocalDateTime.parse(date, dateFmt);
				if(start.isBefore(LocalDateTime.now())) { //check if startDate is before than now()
					throw new DateTimeParseException("La data di inizio è antecedente alla corrente", date, 5);
				}

				isValidInput = true;
			}catch (DateTimeParseException e) {
				System.out.println(toColor("*Inserisci una data valida*", Colors.RED));
			}
		}
		
		return start;
	}
	private LocalDateTime getEndDate(LocalDateTime start) {
		LocalDateTime end = null;
		boolean isValidInput = false;
		while(!isValidInput) {
			try {
				String date = input.askLine("Inserisci la data di termine nel formato " + datePattern);
				end = LocalDateTime.parse(date, dateFmt);
				if(end.isBefore(start)) { //check if endDate is before than start
					throw new DateTimeParseException("La data di termine è antecedente alla corrente", date, 5);
				}

				isValidInput = true;
			}catch (DateTimeParseException e) {
				System.out.println(toColor("*Inserisci una data valida*", Colors.RED));
			}
		}
		
		return end;
	}
}
