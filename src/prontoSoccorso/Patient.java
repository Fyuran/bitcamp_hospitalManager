package prontoSoccorso;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import prontoSoccorsoManagers.CRUD;

public class Patient extends Person {

	private PatientId id;
	private LocalDate entryDate;
	private LocalDate exitDate;
	private MedCode code;
	private List<StaffMember> assignedStaff = new ArrayList<>();
	private boolean isDismissed = false;

	public Patient(String name, String surname, MedCode code) {
		super(name, surname);
		this.id = new PatientId();
		this.entryDate = LocalDate.now();
		this.code = code;
	}
	public Patient(String name, String surname, LocalDate entryDate, MedCode code) {
		super(name, surname);
		this.id = new PatientId();
		this.entryDate = entryDate;
		this.code = code;
	}
	
	public PatientId getId() {
		return id;
	}

	public void setId(int id) {
		this.id.setId(id);
	}
	
	public LocalDate getEntry() {
		return entryDate;
	}

	public LocalDate getExit() {
		return exitDate;
	}

	public MedCode getCode() {
		return code;
	}

	public void setCode(MedCode code) {
		this.code = code;
	}
	
	public void assignStaff(StaffMember staff) {
		assignedStaff.add(staff);
	}
	
	public boolean isDismissed() {
		return isDismissed;
	}

	public void setDismissed(boolean isDismissed) {
		this.isDismissed = isDismissed;
	}

	public void setExit(LocalDate exitDate) {
		this.exitDate = exitDate;
	}

	@Override
	public String toString() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yy");
		return "Paziente: id=" + id + ", Nome=" + getName() + ", Cognome=" + getSurname() + 
				", Ammesso=" + entryDate.format(format) + ", Dimesso=" + (exitDate==null?"N/A":exitDate.format(format)) +
						", codice=" + code + ", Personale assegnato=" + CRUD.listToString(assignedStaff);
	}

}