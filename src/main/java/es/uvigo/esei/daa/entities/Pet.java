package es.uvigo.esei.daa.entities;

import static java.util.Objects.requireNonNull;

/**
 * An entity that represents a person.
 * 
 * @author Miguel Reboiro Jato
 */
public class Pet {
	private int id;
	private String name;
	private String especie;
	private int person_id;
	
	// Constructor needed for the JSON conversion
	Pet() {}
	
	/**
	 * Constructs a new instance of {@link Person}.
	 *
	 * @param id identifier of the person.
	 * @param name name of the person.
	 * @param surname surname of the person.
	 */
	public Pet(int id, String name, String especie,int person_id) {
		this.id = id;
		this.setName(name);
		this.setEspecie(especie);
		this.setPersonId(person_id);
	}
	
	/**
	 * Returns the identifier of the person.
	 * 
	 * @return the identifier of the person.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Returns the name of the person.
	 * 
	 * @return the name of the person.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of this person.
	 * 
	 * @param name the new name of the person.
	 * @throws NullPointerException if the {@code name} is {@code null}.
	 */
	public void setName(String name) {
		this.name = requireNonNull(name, "Name can't be null");
	}

	/**
	 * Returns the surname of the person.
	 * 
	 * @return the surname of the person.
	 */
	public String getEspecie() {
		return especie;
	}

	/**
	 * Set the surname of this person.
	 * 
	 * @param surname the new surname of the person.
	 * @throws NullPointerException if the {@code surname} is {@code null}.
	 */
	public void setEspecie(String especie) {
		this.especie = requireNonNull(especie, "Especie can't be null");
	}

	public int getPersonId() {
		return person_id;
	}
	
	public void setPersonId(int person_id) {
		this.person_id = requireNonNull(person_id, "Person ID can't be null");
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Pet))
			return false;
		Pet other = (Pet) obj;
		if (id != other.id)
			return false;
		return true;
	}
}