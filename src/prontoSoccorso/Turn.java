package prontoSoccorso;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Predicate;

import prontoSoccorsoManagers.CRUD;

public class Turn implements CRUD<StaffMember>{
	private LocalDateTime start;
	private LocalDateTime end;
	private TimeSlot slot;
	private List<StaffMember> list = new ArrayList<>();
	
	public enum TimeSlot {
		MORNING, //Il turno di mattina solitamente inizia alle 7:00 e finisce alle 14:00. 
		AFTERNOON, //Il turno pomeridiano inizia alle 14:00 e si conclude alle 21:00 
		NIGHT; //Il turno di notte inizia alle 21:00 per concludersi alle 7:00 del giorno successivo
		
		private static final String currentLocale = Locale.getDefault().toLanguageTag();
		
		public static TimeSlot getTimeSlot(Turn turn) {
			LocalDateTime start = turn.getStart();
			return getTimeSlot(start);
		}
		
		public static TimeSlot getTimeSlot(LocalDateTime date) {
			long hour = date.getLong(ChronoField.HOUR_OF_DAY);
			
			if(hour >= 7 && hour < 14)
				return TimeSlot.MORNING;
			if(hour >= 14 && hour < 21)
				return TimeSlot.AFTERNOON;
			if(hour >= 21 || hour < 7)
				return TimeSlot.NIGHT;
			
			return null;
		}
		
		@Override
		public String toString() {
			switch(this) {
				case MORNING:
					if(currentLocale.contentEquals("it-IT")) return "Mattutino";
					return "Morning";
				case AFTERNOON:
					if(currentLocale.contentEquals("it-IT")) return "Pomeridiano";
					return "Afternoon";
				case NIGHT:
					if(currentLocale.contentEquals("it-IT")) return "Notturno";
					return "Night";
				default:
					return "Undefined";
			}
		}
	}
	public Turn(LocalDateTime start, LocalDateTime end) {
		this.start = start;
		this.end = end;
		slot = TimeSlot.getTimeSlot(this);
		if(slot == null)
			throw new IllegalArgumentException("TimeSlot assigned to null");
	}
	
	
	public TimeSlot getSlot() {
		return slot;
	}


	public void setSlot(TimeSlot slot) {
		this.slot = slot;
	}


	public List<StaffMember> getAssignedStaff() {
		return list;
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

    @Override
    public void add(StaffMember staff) {
        list.add(staff);
    }
    
    @Override
    public boolean remove(int index) {
		if (index >= 0 && index < list.size()) {
			if (list.remove(index) != null) {
				return true;
			}
		}
		return false;
    }

    
    public boolean remove(String id) { //ID are composed as OP-XXX
        for(StaffMember staff : list) {
        	if(staff.getId().toString().equals(id)) {
        		list.remove(staff);	
        		return true;
        	}
        }
        return false;
    }

	@Override
	public StaffMember get(int index) {
		if (index >= 0 && index < list.size())
			return list.get(index);
		return null;
	}

	public StaffMember get(String id) {
		 for(StaffMember staff : list)
        	if(staff.getId().toString().equalsIgnoreCase(id))
        		return staff;

		return null;
	}
	
	@Override
	public boolean update(int index, StaffMember o) {
		if (index >= 0 && index < list.size()) {
			list.set(index, o);
			return true;
		}
		return false;
	}
	public boolean update(String id, StaffMember o) {
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).getId().toString().equals(id)) {
				list.set(i, o);
				return true;
			}
		}
		return false;
	}
	
    @Override
	public List<StaffMember> filter(Predicate<StaffMember> p) {
    	List<StaffMember> temp = new ArrayList<>();
    	for(StaffMember staff : list) {
    		if(p.test(staff))
    			temp.add(staff);	
    	}
		return temp;
	}
	
    public List<StaffMember> getList() {
		return list;
	}


	public void setList(List<StaffMember> list) {
		this.list = list;
	}

	@Override
    public boolean isEmpty() {
    	return list.size() == 0;
    }

	@Override
	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		return "Inizio turno= " + start.format(formatter) + ", Fine turno= "
				+ end.format(formatter) + ", Fascia Oraria=" + slot + ", Personale assegnato=" + CRUD.listToString(list, 2);
	}


	@Override
	public void forEach(Consumer<StaffMember> c) {
		list.forEach(c);
	}
}