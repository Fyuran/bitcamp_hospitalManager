package prontoSoccorsoManagers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import prontoSoccorso.Emergency;
import prontoSoccorso.Patient;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public Emergency get(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(int index) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(int index, Emergency o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Emergency> filter(Predicate<Emergency> p) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<StaffMember> filterByTimeSlot(LocalDateTime date) {
		TimeSlot slot = TimeSlot.getTimeSlot(date);
		List<Turn> turns = turnManager.filter(p -> p.getSlot().equals(slot));
		for(Turn turn : turns) {
			if(date.isAfter(turn.getStart()) && date.isBefore(turn.getEnd())) { //if date is between two dates
				List<StaffMember> staffMembers = turn.getAssignedStaff();
				if(staffMembers.isEmpty()) 
					return null;
				return staffMembers;
			}
		}
		return null;
	}
	@Override
	public void forEach(Consumer<Emergency> c) {
		// TODO Auto-generated method stub
		
	}
}
