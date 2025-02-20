package prontoSoccorsoManagers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import prontoSoccorso.StaffMember;
import prontoSoccorso.Turn;

public class TurnManager implements CRUD<Turn> {
    private List<Turn> list = new ArrayList<>();

    public List<Turn> getTurns() {
        return list;
    }
    
    public void assignStaffToTurn(StaffMember staff, int index) {
    	if (index >= 0 && index < list.size())
    		list.get(index).add(staff);
    }
    public void assignStaffToTurn(StaffMember staff, Turn turn) {
    	int index = list.indexOf(turn);
    	if (index >= 0 && index < list.size())
    		list.get(index).add(staff);
    }
    
	@Override
	public void add(Turn o) {
		list.add(o);
	}

	@Override
	public Turn get(int index) {
		if (index >= 0 && index < list.size())
			return list.get(index);
		return null;
		
	}

	@Override
	public boolean remove(int index) {
        if (index >= 0 && index < list.size()) {
        	list.remove(index);
        	return true;
        }
        return false;
	}

	@Override
	public boolean update(int index, Turn o) {
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
    
	@Override
	public String toString() {
		return "Lista dei turni:" + CRUD.listToString(list, 1);
	}

    @Override
	public List<Turn> filter(Predicate<Turn> p) {
    	List<Turn> temp = new ArrayList<>();
    	for(Turn turn : list) {
    		if(p.test(turn))
    			temp.add(turn);	
    	}
		return temp;
	}

	@Override
	public void forEach(Consumer<Turn> c) {
		list.forEach(c);
	}
}