package prontoSoccorso;

import java.util.ArrayList;
import java.util.List;

public class PatientManager {
	List<Patient> admittedPatients = new ArrayList<>();
	List<Patient> dismissedPatients = new ArrayList<>();
	

	Patient addPatient(String name, String surname) {
		Patient patient = new Patient(name, surname);
		admittedPatients.add(patient);
		
		return patient;
	}
	
	void addPatientToEmergency(int index, EmergencyManager em) { //index of patient in admittedPatients
		try {
			admittedPatients.get(index);
			em.addEmergency();
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Nessun paziente trovato");
		}
	}
	
	void addPatientToEmergency(PatientId id, EmergencyManager em) { //find patient in admittedPatients
		for(Patient patient : admittedPatients)
			if(patient.getId() == id)
				em.addEmergency(patient);
			else System.out.println("Nessun paziente trovato");
	}
	
	void addPatientToAmb(int index, AmbulatoryManager am) {
		try {
			admittedPatients.get(index);
			am.addAmbulatory(null);
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Nessun paziente trovato");
		}
	}
	void addPatientToAmb(PatientId id, AmbulatoryManager am) {
		for(Patient patient : admittedPatients)
			if(patient.getId() == id)
				am.addEmergency(patient);
			else System.out.println("Nessun paziente trovato");
	}
}
