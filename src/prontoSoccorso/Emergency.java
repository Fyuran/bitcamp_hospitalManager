package prontoSoccorso;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class Emergency {
	
	private String description;
	private LocalDate startDate;
	private Ambulatory ambulatory;
	private List<StaffMember>assignedStaff = new ArrayList<>();
	
	public Emergency(String description, List<StaffMember>staff, Ambulatory ambulatory) {
		this.description = description;
		this.startDate = LocalDate.now();
		this.ambulatory = ambulatory;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public Ambulatory getAmbulatory() {
		return ambulatory;
	}

	public void setAmbulatory(Ambulatory ambulatory) {
		this.ambulatory = ambulatory;
	}

	public List<StaffMember> getAssignedStaff() {
		return assignedStaff;
	}

	public void assignStaff(StaffMember staff) {
		assignedStaff.add(staff);
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
		return "Emergenza descrizione=" + description + ", Registrata=" + startDate + ", Ambulatorio=" + ambulatory
				+ ", Personale assegnato=\n" + staffToString();
	}
	
	
}