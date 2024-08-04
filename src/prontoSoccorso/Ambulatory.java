package prontoSoccorso;

public class Ambulatory {
	private String name;
	private boolean available = true;

	public Ambulatory(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	@Override
	public String toString() {
		return "Ambulatorio nome=" + name + ", disponibile=" + (available?"Si":"No");
	}

}
