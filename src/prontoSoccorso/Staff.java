package prontoSoccorso;

public class Staff extends Person {
	private StaffId id;
	private String specialization;
	private String mail;

	public Staff(String name, String surname, String specialization, String mail) {
		super(name, surname);
		this.id = new StaffId();

		this.specialization = specialization;
		this.mail = mail;
	}

	public StaffId getIdStaff() {
		return id;
	}

	public String getSpecialization() {
		return specialization;
	}

	public String getMail() {
		return mail;
	}

	@Override
	public String toString() {
		return "\nNome: " + getName() + "\nCognome: " + getSurname() + "\nId staff: " + id + "\nSpecialization: " + specialization
				+ "\nMail: " + mail;

	}

}