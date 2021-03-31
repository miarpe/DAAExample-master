package es.uvigo.esei.daa.entities;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class PetUnitTest {
	@Test
	public void testPetIntStringStringInt() {
		final int id = 2;
		final String name = "Kike";
		final String especie = "lobo";
		final int person_id = 1;
		
		final Pet pet = new Pet(id, name, especie,person_id);
		
		assertThat(pet.getId(), is(equalTo(id)));
		assertThat(pet.getName(), is(equalTo(name)));
		assertThat(pet.getEspecie(), is(equalTo(especie)));
		assertThat(pet.getPersonId(), is(equalTo(person_id)));
	}

	@Test(expected = NullPointerException.class)
	public void testPersonIntStringStringIntNullName() {
		new Pet(2, null, "lobo",1);
	}
	
	@Test(expected = NullPointerException.class)
	public void testPersonIntStringStringIntNullSurname() {
		new Pet(2, "Kike", null,1);
	}


	@Test
	public void testSetName() {
		final int id = 2;
		final String especie = "lobo";
		final int person_id = 1;
		
		final Pet pet = new Pet(id, "Kike", especie,person_id);
		pet.setName("Kike");
		
		assertThat(pet.getId(), is(equalTo(id)));
		assertThat(pet.getName(), is(equalTo("Kike")));
		assertThat(pet.getEspecie(), is(equalTo(especie)));
		assertThat(pet.getPersonId(), is(equalTo(person_id)));
	}

	@Test(expected = NullPointerException.class)
	public void testSetNullName() {
		final Pet pet = new Pet(2, "Kike", "lobo",1);
		
		pet.setName(null);
	}

	@Test
	public void testSetEspecie() {
		final int id = 2;
		final String name = "Kike";
		final int person_id = 1;
		
		final Pet pet = new Pet(id, name, "lobo",person_id);
		pet.setEspecie("grifo");
		
		assertThat(pet.getId(), is(equalTo(id)));
		assertThat(pet.getName(), is(equalTo(name)));
		assertThat(pet.getEspecie(), is(equalTo("grifo")));
		assertThat(pet.getPersonId(), is(equalTo(person_id)));
	}

	@Test(expected = NullPointerException.class)
	public void testSetNullEspecie() {
		final Pet pet = new Pet(2, "Kike", "lobo",1);
		
		pet.setEspecie(null);
	}

	@Test
	public void testEqualsObject() {
		final Pet petA = new Pet(2, "Name A", "Especie A",1);
		final Pet petB = new Pet(2, "Name B", "Especie B",1);
		
		assertTrue(petA.equals(petB));
	}

	@Test
	public void testEqualsHashcode() {
		EqualsVerifier.forClass(Pet.class)
			.withIgnoredFields("name", "especie","person_id")
			.suppress(Warning.STRICT_INHERITANCE)
			.suppress(Warning.NONFINAL_FIELDS)
		.verify();
	}
}