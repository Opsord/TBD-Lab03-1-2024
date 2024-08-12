package G1TBD.LABTBD.mongo.user.repositories;

import G1TBD.LABTBD.mongo.user.models.UserSkill;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends MongoRepository<UserSkill, String> {


    // Find by´s
    UserSkill findUserSkillByName(String name);
    UserSkill findUserSkillByDescription(String description);
    //UserSkill findUserSkillBySkillCode(String code);
    UserSkill findBySkillCode(String skillCode);

}
