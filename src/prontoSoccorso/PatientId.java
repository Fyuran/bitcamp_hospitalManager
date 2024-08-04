package prontoSoccorso;

public class PatientId {

	private static final String prefix = "PAX-";
	private static int idCounter = 1;
	private int id = -1;

	public PatientId() {
		id = idCounter++;
	}

	public PatientId(PatientId patientId) { //copy constructor for old members when editing
		this.id = patientId.id;
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