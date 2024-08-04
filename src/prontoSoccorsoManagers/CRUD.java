package prontoSoccorsoManagers;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

public interface CRUD<T>{
	void add(T o);
	T get(int index);
	boolean remove(int index);
	boolean update(int index, T o);
	boolean isEmpty();
	List<T> filter(Predicate<T> p);
	
	static String listToString(List<?> list) {
		if(list.size() == 0) 
			return "Nessuno";
		
		Iterator<?> it = list.listIterator();
		StringBuilder sb = new StringBuilder();
		
		int index = 0;
		while(it.hasNext()) {
			sb.append("\n\t" + (++index) + ". " + it.next());
			
			if(it.hasNext()) //do not add newline char if there are no elements next
				sb.append("\n");
		}
		
		return sb.toString();
	}
}
