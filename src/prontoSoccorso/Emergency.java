package prontoSoccorso;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class Emergency {
	private static int id;
	private String description;
	LocalDate startDate;
	Ambulatory ambulatory;
	List<Staff>assignedStaff;
	
	public Emergency(String description, ArrayList<Staff>assignedStaff, Ambulatory ambulatory) {
		id++;
		this.description = description;
		this.startDate = LocalDate.now();
		this.ambulatory = ambulatory;
		this.assignedStaff = assignedStaff;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}		
}