package prontoSoccorso;

public class StaffMember extends Person {
	private StaffId id;

	private String specialization;
	private String mail;
	
	public StaffMember(String name, String surname, String specialization, String mail) {
		super(name, surname);
		id = new StaffId(); //increases counter

		this.specialization = specialization;
		this.mail = mail;
	}

	public StaffMember(String name, String surname, StaffId id, String specialization, String mail) {
		super(name, surname);
		
		this.id = new StaffId(id); //DOESN'T increase counter
		this.specialization = specialization;
		this.mail = mail;
	}

	public StaffId getId() {
		return id;
	}

	public void setId(int id) {
		this.id.setId(id);
	}
	
	public String getSpecialization() {
		return specialization;
	}

	public String getMail() {
		return mail;
	}
	
	
	@Override
	public String toString() {
		return "Personale: id=" + id + ", nome=" + getName() + ", cognome=" + getSurname() + 
				", specializzazione=" + specialization + ", mail=" + mail;
	}
	
}