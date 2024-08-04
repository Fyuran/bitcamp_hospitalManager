package prontoSoccorso;

public class StaffId {

	private static final String prefix = "OP-";
	private static int idCounter = 1;
	private int id = -1;

	public StaffId() {
		id = idCounter++;
	}

	public StaffId(StaffId staffId) { //copy constructor for old members when editing
		this.id = staffId.id;
	}
	
	public static int reduceCounter() {
		return idCounter--;
	}
	public static int increaseCounter() {
		return idCounter++;
	}
	public static void resetCounter() {
		idCounter = 1;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return prefix + String.format("%03d", id);
	}
}