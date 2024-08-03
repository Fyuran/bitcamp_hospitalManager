package prontoSoccorso;

public class StaffId {

	private String prefix = "OP-";
	static int id = 1;

	public StaffId() {
		id++;
	}

	@Override
	public String toString() {
		return "ID: " + prefix + id;
	}
}