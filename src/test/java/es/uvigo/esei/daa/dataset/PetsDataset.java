package es.uvigo.esei.daa.dataset;

import static java.util.Arrays.binarySearch;
import static java.util.Arrays.stream;

import java.util.Arrays;
import java.util.function.Predicate;

import es.uvigo.esei.daa.entities.Pet;

public final class PetsDataset {
	private PetsDataset() {}
	
	public static Pet[] pets() {
		return new Pet[] {
			new Pet(1, "Puki", "perro",1),
			new Pet(2, "Poki", "gato",1),
			new Pet(3, "Paki", "caracol",2),
			new Pet(4, "Roberto", "vaca",3),
		};
	}
	
	public static Pet[] petsPerson1() {
		return new Pet[] {
			new Pet(1, "Puki", "perro",1),
			new Pet(2, "Poki", "gato",1),
		};
	}
	
	public static Pet[] petsWithout(int ... ids) {
		Arrays.sort(ids);
		
		final Predicate<Pet> hasValidId = pet ->
			binarySearch(ids, pet.getId()) < 0;
		
		return stream(pets())
			.filter(hasValidId)
		.toArray(Pet[]::new);
	}
	
	public static Pet pet(int id) {
		return stream(pets())
			.filter(pet -> pet.getId() == id)
			.findAny()
		.orElseThrow(IllegalArgumentException::new);
	}
	
	public static int existentId() {
		return 2;
	}
	
	public static int nonExistentId() {
		return 1234;
	}

	public static Pet existentPet() {
		return pet(existentId());
	}
	
	public static Pet nonExistentPet() {
		return new Pet(nonExistentId(), "Jane", "cabra",4);
	}
	
	public static String newName() {
		return "Kike";
	}
	
	public static String newEspecie() {
		return "lobo";
	}
	
	public static int newPersonId() {
		return 1;
	}
	
	public static Pet newPet() {
		return new Pet(pets().length + 1, newName(), newEspecie(),newPersonId());
	}
}
