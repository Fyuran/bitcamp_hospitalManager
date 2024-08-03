package prontoSoccorso;

import java.util.ArrayList;
import java.util.List;

public class EmergencyManager {
	
	List<Emergency> emergencies = new ArrayList<>();
	
	void addEmergency() {}
	void addEmergency(Patient patient) {
		Emergency emergency = new Emergency();
		patient.setEmergency(emergency);
	}
	boolean addPersonnel(Emergency emergency) {
		return false;
	}
}
