package G1TBD.LABTBD.controllers;

import G1TBD.LABTBD.entities.RankingEntity;
import G1TBD.LABTBD.services.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/rankings")
@CrossOrigin(origins = "http://localhost:8090/rankings")
public class RankingController {

    private final RankingService rankingService;
    private static final Logger logger = Logger.getLogger(RankingController.class.getName());

    @Autowired
    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    String homeLinkRedirect = "redirect:/rankings";

    //--------------------------CREATE--------------------------
    @PostMapping("/create")
    public String create(@RequestBody RankingEntity ranking) {
        rankingService.create(ranking);
        logger.info("Ranking created: ");
        logger.info(ranking.toString());
        return homeLinkRedirect;
    }


    //--------------------------UPDATE--------------------------
    @PutMapping("/update")
    public String update(@RequestBody RankingEntity ranking) {
        rankingService.update(ranking);
        logger.info("Ranking updated: ");
        logger.info(ranking.toString());
        return homeLinkRedirect;
    }


    //---------------------------READ---------------------------
    @GetMapping("/all")
    public List<RankingEntity> getAll() {return rankingService.getAll();}

    @GetMapping("/id/{id}")
    public RankingEntity getById(@PathVariable long id) {return rankingService.getById(id);}

    @GetMapping("/task_id/{id}")
    public List<RankingEntity> getBytask_id(@PathVariable long id) {return rankingService.getByTaskId(id);}


    //--------------------------DELETE--------------------------
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable long id) {
        rankingService.delete(id);
        logger.info("Ranking deleted: ");
        logger.info(String.valueOf(id));
        return homeLinkRedirect;
    }
}
