package prontoSoccorso;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Patient extends Person {

	private PatientId id;
	// private String idPaz;
	private LocalDate entryDate;
	private LocalDate exitDate;
	private Emergency emergency;
	private boolean isDismissed;
	private Staff assignedOp;

	// costruttore con tutti i dati
	public Patient(String name, String surname, LocalDate entryDate, LocalDate exitDate, Emergency emergency,
			Staff assignedOp) {
		super(name, surname);
		this.id = new PatientId();
		this.entryDate = entryDate;
		this.exitDate = exitDate;
		this.emergency = emergency;
		// se la data di uscita è già passata il Patient risulta dismesso
		this.isDismissed = (exitDate.compareTo(LocalDate.now()) < 0 ? true : false);
		this.assignedOp = assignedOp;
	}

	// costruttore senza data di dimissioni (impostata di default a una settimana
	// dall'ingresso)
	public Patient(String name, String surname, LocalDate entryDate, Emergency emergency, Staff assignedOp) {
		super(name, surname);
		this.id = new PatientId();
		this.entryDate = entryDate;
		this.exitDate = entryDate.plusWeeks(1);// da gestire
		this.emergency = emergency;
		this.isDismissed = (exitDate.compareTo(LocalDate.now()) < 0 ? true : false);
		this.assignedOp = assignedOp;
	}

	// costruttore senza data di ingresso (impostata di default a oggi) e senza data
	// di dimissioni
	public Patient(String name, String surname, Emergency emergency, Staff assignedOp) {
		super(name, surname);
		this.id = new PatientId();
		this.entryDate = LocalDate.now();
		this.exitDate = entryDate.plusWeeks(1);// da gestire
		this.emergency = emergency;
		this.assignedOp = assignedOp;
	}

	// costruttore di ingresso
	public Patient(String name, String surname) {
		super(name, surname);
		this.id = new PatientId();
		this.entryDate = LocalDate.now();
		this.exitDate = null;
		this.emergency = null;
		this.assignedOp = null;
	}
	
	public PatientId getId() {
		return id;
	}

	public LocalDate getEntry() {
		return entryDate;
	}

	public LocalDate getExit() {
		return exitDate;
	}

	public Emergency getEmergency() {
		return emergency;
	}

	public boolean isDismissed() {
		return isDismissed;
	}

	public void setExit(LocalDate exitDate) {
		this.exitDate = exitDate;
	}

	public void setEmergency(Emergency emergency) {
		this.emergency = emergency;
	}

	public void dismiss() {
		this.isDismissed = true;
	}

	@Override
	public String toString() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yy");
		return "Id Patient: " + id + "\nNome: " + getName() + " " + getSurname() + "\nData di ingresso: "
				+ entryDate.format(format) + "\nData di uscita: " + exitDate.format(format) + "\nTipo di emergenza: "
				+ emergency + "\nPreso in cura da: " + assignedOp.getName() + " " + assignedOp.getSurname();
	}

}