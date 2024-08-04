package prontoSoccorsoManagers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import prontoSoccorso.MedCode;
import prontoSoccorso.Patient;
import prontoSoccorso.PatientId;

public class PatientManager implements CRUD<Patient>{

	List<Patient> list = new ArrayList<>();
	
	@Override
	public void add(Patient patient) {
		list.add(patient);
	}
	public void add(String name, String surname, MedCode code) {
		Patient patient = new Patient(name, surname, code);
		list.add(patient);
	}
	
	@Override
	public Patient get(int index) {
		if (index >= 0 && index < list.size())
			return list.get(index);
		return null;
	}
	
	public Patient get(Patient patient) {
		int index = list.indexOf(patient);
		if (index >= 0 && index < list.size())
			return list.get(index);
		return null;
	}
	
	public Patient get(String id) {
		for(Patient patient : list)
	       	if(patient.getId().toString().equalsIgnoreCase(id))
	       		return patient;
		return null;
	}
	
    @Override
    public boolean remove(int index) {
		if (index >= 0 && index < list.size()) {
			if (list.remove(index) != null) {
				PatientId.reduceCounter();

				// no rearrange is needed if last one is removed
				if (index != (list.size() - 1)) {
					rearrangeIds();
				}
				return true;
			}
		}
		return false;
    }

    public boolean remove(Patient patient) {
    	int index = list.indexOf(patient);
		if (index >= 0 && index < list.size()) {
			if (list.remove(index) != null) {
				PatientId.reduceCounter();

				// no rearrange is needed if last one is removed
				if (index != (list.size() - 1)) {
					rearrangeIds();
				}
				return true;
			}
		}
		return false;
    }
    
    public boolean remove(String id) { //ID are composed as PAX-XXX
        for(Patient patient : list) {
        	if(patient.getId().toString().equals(id)) {
        		list.remove(patient);
        		PatientId.reduceCounter();
        		
        		//no rearrange is needed if last one is removed
        		if(!patient.equals(list.get(list.size()-1))) {
        			rearrangeIds();
        		}       		
        		return true;
        	}
        }
        return false;
    }
    
    private void rearrangeIds() { //IDs will be invalid once an element inside is removed
    	PatientId.resetCounter();
    	for(Patient p : list)
    		p.setId(PatientId.increaseCounter());
    }
    
	@Override
	public boolean update(int index, Patient o) {
		if (index >= 0 && index < list.size()) {
			list.set(index, o);
			return true;
		}
		return false;
	}
	public boolean update(Patient o) {
		int index = list.indexOf(o);
		if (index >= 0 && index < list.size()) {
			list.set(index, o);
			return true;
		}
		return false;
	}
    @Override
    public boolean isEmpty() {
    	return list.size() == 0;
    }

	public void assignPatientToEmergency(Patient patient) {
		
	}
	

	public String toString() {
		return "Lista pazienti:" + CRUD.listToString(list);
	}
	
    @Override
	public List<Patient> filter(Predicate<Patient> p) {
    	List<Patient> temp = new ArrayList<>();
    	for(Patient patient : list) {
    		if(p.test(patient))
    			temp.add(patient);	
    	}
		return temp;
	}

}
