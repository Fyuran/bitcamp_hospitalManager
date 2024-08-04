package prontoSoccorso;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Turn {
	private LocalDateTime start;
	private LocalDateTime end;
	private List<StaffMember> assignedStaff = new ArrayList<>();

	public Turn(LocalDateTime start, LocalDateTime end) {
		this.start = start;
		this.end = end;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	public void setEnd(LocalDateTime end) {
		this.end = end;
	}

	public void addStaff(StaffMember staff) {
		assignedStaff.add(staff);
		staff.setTurn(this);
	}
	
	public String staffToString() {
		if(assignedStaff.size() == 0) 
			return "Nessuno";
		
		StringBuilder sb = new StringBuilder();
		Iterator<StaffMember> it = assignedStaff.listIterator();
		while(it.hasNext()) {
			sb.append("\t" + it.next());
			
			if(it.hasNext()) //do not add newline char if there are no elements next
				sb.append("\n");
		}
		
		return sb.toString();
	}
	
	@Override
	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		return "Inizio turno= " + start.format(formatter) + ", Fine turno= "
				+ end.format(formatter) + ", Personale assegnato=\n" + staffToString();
	}
}