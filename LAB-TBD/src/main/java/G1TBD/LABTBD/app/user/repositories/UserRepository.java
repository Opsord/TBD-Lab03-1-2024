package G1TBD.LABTBD.app.user.repositories;

import G1TBD.LABTBD.app.user.entities.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, String> {

        // --------------------------CREATE--------------------------

        @Query(value = "INSERT INTO person (rut, email, name, last_name, birth_date, sex, password, role, availability) "
                        +
                        "VALUES (:rut, :email, :name, :last_name, :birth_date, :sex, :password, :role, :availability)", nativeQuery = true)
        @Modifying
        @Transactional
        void create(@Param("rut") String rut,
                        @Param("email") String email,
                        @Param("name") String name,
                        @Param("last_name") String last_name,
                        @Param("birth_date") Date birth_date,
                        @Param("sex") String sex,
                        @Param("password") String password,
                        @Param("role") String role,
                        @Param("availability") boolean availability);

        // ---------------------------READ---------------------------

        @Query(value = "SELECT * FROM person", nativeQuery = true)
        List<UserEntity> getAll();

        @Query(value = "SELECT * FROM person WHERE rut = :rut", nativeQuery = true)
        Optional<UserEntity> getByRut(@Param("rut") String rut);

        @Query(value = "SELECT * FROM person WHERE email = :email", nativeQuery = true)
        Optional<UserEntity> getByEmail(@Param("email") String email);

        @Query(value = "SELECT * FROM person WHERE role = :role", nativeQuery = true)
        List<UserEntity> getByRole(@Param("role") String role);

        @Query(value = "SELECT * FROM person WHERE availability = :availability", nativeQuery = true)
        List<UserEntity> getByAvailability(@Param("availability") boolean availability);

        @Query(value = "SELECT * FROM person WHERE role = 'VOLUNTEER'", nativeQuery = true)
        List<UserEntity> getVolunteers();

        @Query(value = "SELECT * FROM person WHERE role = 'COORDINATOR'", nativeQuery = true)
        List<UserEntity> getCoordinators();






        // --------------------------UPDATE--------------------------

        @Query(value = "UPDATE person SET email = :email, name = :name, " +
                        "last_name = :last_name, birth_date = :birth_date, " +
                        "sex = :sex, password = :password, role = :role, availability = :availability " +
                        "WHERE rut = :rut", nativeQuery = true)
        @Modifying
        @Transactional
        void update(@Param("rut") String rut,
                        @Param("email") String email,
                        @Param("name") String name,
                        @Param("last_name") String last_name,
                        @Param("birth_date") Date birth_date,
                        @Param("sex") String sex,
                        @Param("password") String password,
                        @Param("role") String role,
                        @Param("availability") boolean availability);

        // --------------------------DELETE--------------------------

        @Query(value = "DELETE FROM person WHERE rut = :rut", nativeQuery = true)
        @Modifying
        @Transactional
        void delete(@Param("rut") String rut);

}