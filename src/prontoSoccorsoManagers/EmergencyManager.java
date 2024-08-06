package prontoSoccorsoManagers;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import prontoSoccorso.Emergency;
import prontoSoccorso.StaffMember;
import prontoSoccorso.Turn;
import prontoSoccorso.Turn.TimeSlot;

public class EmergencyManager implements CRUD<Emergency>{
	
	List<Emergency> emergencies = new ArrayList<>();
	
	StaffManager staffManager;
	TurnManager turnManager;

	public EmergencyManager(StaffManager staffManager, TurnManager turnManager) {
		this.staffManager = staffManager;
		this.turnManager = turnManager;
	}

	@Override
	public void add(Emergency o) {
		emergencies.add(o);
	}		
	
	
	@Override
	public Emergency get(int index) {
		return emergencies.get(index);
	}

	@Override
	public boolean remove(int index) {
		emergencies.remove(index);
		return false;
	}

	@Override
	public boolean update(int index, Emergency o) {
		if(index >= 0 && index <= emergencies.size()-1)
		{
			emergencies.set(index, o);
			return true;
		}
		return false;
	}

	@Override
	public boolean isEmpty() {
		return emergencies.size() == 0;
	}

	@Override
	public List<Emergency> filter(Predicate<Emergency> p) {		
		List<Emergency> temp = new ArrayList<>();
    	for(Emergency emergency : emergencies) {
    		if(p.test(emergency))
    			temp.add(emergency);	
    	}
		return temp;
	}

	public List<StaffMember> filterByTimeSlot(LocalDateTime date) {
		TimeSlot slot = TimeSlot.getTimeSlot(date);
		List<Turn> turns = turnManager.filter(p -> p.getSlot().equals(slot));
		for(Turn turn : turns) {
			if(date.isAfter(turn.getStart()) && date.isBefore(turn.getEnd())) { //if date is between two dates
				List<StaffMember> staffMembers = turn.getAssignedStaff();
				if(staffMembers.isEmpty())
					return Collections.emptyList();
				return staffMembers;
			}
		}
		return null;
	}
	
	@Override
	public void forEach(Consumer<Emergency> e) {
		emergencies.forEach(e);
	}
}