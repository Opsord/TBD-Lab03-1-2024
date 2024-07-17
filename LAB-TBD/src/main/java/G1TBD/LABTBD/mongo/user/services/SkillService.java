package G1TBD.LABTBD.mongo.user.services;

import G1TBD.LABTBD.mongo.user.models.UserSkill;
import G1TBD.LABTBD.mongo.user.repositories.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SkillService {

    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    // --------------------------CREATE--------------------------

    public UserSkill saveSkill(UserSkill skill) {
        return skillRepository.insert(skill);
    }

    // ---------------------------READ---------------------------

    public Optional<List<UserSkill>> getSkills() {
        return Optional.of(skillRepository.findAll());
    }

    public UserSkill getSkillBySkillId(String skill_id) {
        return skillRepository.findById(skill_id).orElse(null);
    }

    public UserSkill getSkillBySkillName(String skill_name) {
        return skillRepository.findUserSkillByName(skill_name);
    }

    public UserSkill getSkillByDescription(String description) {
        return skillRepository.findUserSkillByDescription(description);
    }

    // --------------------------UPDATE--------------------------

    public UserSkill updateSkill(UserSkill skill) {
        if (skill.getSkill_id() == null) {
            throw new IllegalArgumentException("El código de la habilidad no puede ser nulo para la actualización.");
        }
        return skillRepository.save(skill);
    }

    // --------------------------DELETE--------------------------

    public void deleteSkill(UserSkill skill) {
        skillRepository.delete(skill);
    }

    public void deleteSkillBySkillCode(String skill_code) {
        skillRepository.delete(getSkillBySkillId(skill_code));
    }

    public void deleteSkillBySkillName(String skill_name) {
        skillRepository.delete(getSkillBySkillName(skill_name));
    }

}
