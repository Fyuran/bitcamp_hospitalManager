package prontoSoccorso;

public class Ambulatory {
	private int id;
	private String name;
	private boolean available = true;

	public Ambulatory(int id, String name, boolean available) {
		this.id = id;
		this.name = name;
		this.available = available;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
		return "Ambulatory [id=" + id + ", name=" + name + ", available=" + available + "]";
	}

}
