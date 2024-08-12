package G1TBD.LABTBD.mongo.user.controllers;

import G1TBD.LABTBD.mongo.user.models.UserSkill;
import G1TBD.LABTBD.mongo.user.services.SkillService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/skillsMongo")
public class UserSkillController {
    private final SkillService skillService;


    public UserSkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    //--------------------------CREATE--------------------------

    @PostMapping("/")
    public ResponseEntity<String> saveSkill(@RequestBody UserSkill skill){
        try {
            skillService.saveSkill(skill);
            return new ResponseEntity<>("Habilidad guardada", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //---------------------------READ---------------------------

    @GetMapping("/")
    public ResponseEntity<Optional<List<UserSkill>>> getSkills() {
        Optional<List<UserSkill>> skills = skillService.getSkills();
        return ResponseEntity.ok(skills);
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserSkill> findSkillById(@PathVariable String id) {
        try {
            Optional<UserSkill> user = Optional.ofNullable(skillService.getSkillBySkillId(id));
            return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //--------------------------UPDATE--------------------------

    @PutMapping("/")
    public String updateSkill(@RequestBody UserSkill skill){
        skillService.updateSkill(skill);
        return "Habilidad actualizada";
    }

    //--------------------------DELETE--------------------------
    @DeleteMapping("/{name}")
    public String deleteUserByName(@PathVariable String name) throws Exception {
        skillService.deleteSkillBySkillName(name);
        return "Habilidad eliminada";
    }


}
