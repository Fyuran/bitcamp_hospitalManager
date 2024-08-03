package prontoSoccorso;

public class PatientId {

	private String prefix = "PAX-";
	static int id = 1;

	public PatientId() {
		id++;
	}

	@Override
	public String toString() {
		return "ID: " + prefix + id;
	}
}