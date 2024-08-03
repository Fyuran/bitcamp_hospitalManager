package prontoSoccorso;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Turn {
	private int id;
	private StaffId staffId;
	private LocalDateTime start;
	private LocalDateTime end;
	private Ambulatory ambulatory;
	private static int idCounter = 1;

	public Turn(StaffId staffId, LocalDateTime start, LocalDateTime end, Ambulatory ambulatory) {
		this.id = idCounter++;
		this.staffId = staffId;
		this.start = start;
		this.end = end;
		this.ambulatory = ambulatory;
	}

	// Getter e Setter
	public int getId() {
		return id;
	}

	public StaffId getStaffId() {
		return staffId;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public Ambulatory getAmbulatory() {
		return ambulatory;
	}

	public void setStaffId(StaffId staffId) {
		this.staffId = staffId;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	public void setEnd(LocalDateTime end) {
		this.end = end;
	}

	public void setAmbulatory(Ambulatory ambulatory) {
		this.ambulatory = ambulatory;
	}

	@Override
	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		return "ID Turno: " + id + ", ID Staff: " + staffId + ", Inizio: " + start.format(formatter) + ", Fine: "
				+ end.format(formatter) + ", Ambulatorio: " + ambulatory.getName();
	}
}