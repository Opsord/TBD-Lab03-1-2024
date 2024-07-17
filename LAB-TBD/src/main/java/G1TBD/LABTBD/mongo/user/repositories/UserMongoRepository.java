package G1TBD.LABTBD.mongo.user.repositories;

import G1TBD.LABTBD.mongo.user.models.UserMongo;
import G1TBD.LABTBD.mongo.user.models.UserRole;
import G1TBD.LABTBD.mongo.user.models.UserSkill;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserMongoRepository extends MongoRepository<UserMongo, String> {

    // Find by´s
    Optional<UserMongo> findUserMongoByRut(String rut);
    Optional<UserMongo> findUserMongoByEmail(String email);

    // Find all
    Optional<List<UserMongo>> findAllByRole(UserRole role);
    Optional<List<UserMongo>> findAllByAvailability(boolean availability);
    Optional<List<UserMongo>> findAllBySkills(List<UserSkill> skills);

}
