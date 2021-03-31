package es.uvigo.esei.daa.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import es.uvigo.esei.daa.entities.Person;
import es.uvigo.esei.daa.entities.Pet;

/**
 * DAO class for the {@link Person} entities.
 * 
 * @author Miguel Reboiro Jato
 *
 */
public class PetDAO extends DAO {
	private final static Logger LOG = Logger.getLogger(PetDAO.class.getName());
	
	/**
	 * Returns a person stored persisted in the system.
	 * 
	 * @param id identifier of the person.
	 * @return a person with the provided identifier.
	 * @throws DAOException if an error happens while retrieving the person.
	 * @throws IllegalArgumentException if the provided id does not corresponds
	 * with any persisted person.
	 */
	public Pet get(int id)
	throws DAOException, IllegalArgumentException {
		try (final Connection conn = this.getConnection()) {
			final String query = "SELECT * FROM pets WHERE id=?";
			
			try (final PreparedStatement statement = conn.prepareStatement(query)) {
				statement.setInt(1, id);
				
				try (final ResultSet result = statement.executeQuery()) {
					if (result.next()) {
						return rowToEntity(result);
					} else {
						throw new IllegalArgumentException("Invalid id");
					}
				}
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error getting a pet", e);
			throw new DAOException(e);
		}
	}
	
	/**
	 * Returns a list with all the people persisted in the system.
	 * 
	 * @return a list with all the people persisted in the system.
	 * @throws DAOException if an error happens while retrieving the people.
	 */
	public List<Pet> list(int person_id) throws DAOException {
		try (final Connection conn = this.getConnection()) {
			final String query = "SELECT * FROM pets WHERE person_id=?";
			
			try (final PreparedStatement statement = conn.prepareStatement(query)) {
				statement.setInt(1, person_id);
				try (final ResultSet result = statement.executeQuery()) {
					final List<Pet> pets = new LinkedList<>();
					
					while (result.next()) {
						pets.add(rowToEntity(result));
					}
					
					return pets;
				}
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error listing pets", e);
			throw new DAOException(e);
		}
	}
	
	/**
	 * Persists a new person in the system. An identifier will be assigned
	 * automatically to the new person.
	 * 
	 * @param name name of the new person. Can't be {@code null}.
	 * @param especie surname of the new person. Can't be {@code null}.
	 * @return a {@link Person} entity representing the persisted person.
	 * @throws DAOException if an error happens while persisting the new person.
	 * @throws IllegalArgumentException if the name or surname are {@code null}.
	 */
	public Pet add(String name, String especie,int person_id )
	throws DAOException, IllegalArgumentException {
		if (name == null || especie == null || person_id == 0) {
			throw new IllegalArgumentException("name,especie and person id can't be null");
		}
		
		try (Connection conn = this.getConnection()) {
			final String query = "INSERT INTO pets VALUES(null, ?, ?, ?)";
			
			try (PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
				statement.setString(1, name);
				statement.setString(2, especie);
				statement.setInt(3, person_id);
				
				if (statement.executeUpdate() == 1) {
					try (ResultSet resultKeys = statement.getGeneratedKeys()) {
						if (resultKeys.next()) {
							return new Pet(resultKeys.getInt(1), name, especie, person_id);
						} else {
							LOG.log(Level.SEVERE, "Error retrieving inserted id");
							throw new SQLException("Error retrieving inserted id");
						}
					}
				} else {
					LOG.log(Level.SEVERE, "Error inserting value");
					throw new SQLException("Error inserting value");
				}
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error adding a pet", e);
			throw new DAOException(e);
		}
	}
	
	/**
	 * Modifies a person previously persisted in the system. The person will be
	 * retrieved by the provided id and its current name and surname will be
	 * replaced with the provided.
	 * 
	 * @param person a {@link Person} entity with the new data.
	 * @throws DAOException if an error happens while modifying the new person.
	 * @throws IllegalArgumentException if the person is {@code null}.
	 */
	public void modify(Pet pet)
	throws DAOException, IllegalArgumentException {
		if (pet == null) {
			throw new IllegalArgumentException("pet can't be null");
		}
		
		try (Connection conn = this.getConnection()) {
			final String query = "UPDATE pets SET name=?, especie=? WHERE id=?";
			
			try (PreparedStatement statement = conn.prepareStatement(query)) {
				statement.setString(1, pet.getName());
				statement.setString(2, pet.getEspecie());
				statement.setInt(3, pet.getId());
				
				if (statement.executeUpdate() != 1) {
					throw new IllegalArgumentException("name, especie and person_id can't be null");
				}
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error modifying a pet", e);
			throw new DAOException();
		}
	}
	
	/**
	 * Removes a persisted person from the system.
	 * 
	 * @param id identifier of the person to be deleted.
	 * @throws DAOException if an error happens while deleting the person.
	 * @throws IllegalArgumentException if the provided id does not corresponds
	 * with any persisted person.
	 */
	public void delete(int id)
	throws DAOException, IllegalArgumentException {
		try (final Connection conn = this.getConnection()) {
			final String query = "DELETE FROM pets WHERE id=?";
			
			try (final PreparedStatement statement = conn.prepareStatement(query)) {
				statement.setInt(1, id);
				
				if (statement.executeUpdate() != 1) {
					throw new IllegalArgumentException("Invalid id");
				}
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error deleting a pet", e);
			throw new DAOException(e);
		}
	}
	
	private Pet rowToEntity(ResultSet row) throws SQLException {
		return new Pet(
			row.getInt("id"),
			row.getString("name"),
			row.getString("especie"),
			row.getInt("person_id")
		);
	}
}