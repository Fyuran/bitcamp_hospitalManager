package prontoSoccorsoManagers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import prontoSoccorso.StaffId;
import prontoSoccorso.StaffMember;

public class StaffManager implements CRUD<StaffMember>{

	private List<StaffMember> list = new ArrayList<>();

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
    
    public List<StaffMember> getStaff() {
        return list;
    }

    @Override
    public boolean isEmpty() {
    	return list.size() == 0;
    }
    
	@Override
	public String toString() {
		return "Lista del personale:" + CRUD.listToString(list, 1);
	}

	@Override
	public void forEach(Consumer<StaffMember> c) {
		list.forEach(c);
	}

}
