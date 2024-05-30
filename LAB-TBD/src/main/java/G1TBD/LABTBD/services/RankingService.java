package G1TBD.LABTBD.services;

import G1TBD.LABTBD.entities.RankingEntity;
import G1TBD.LABTBD.repositories.RankingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class RankingService {

    private final RankingRepository rankingRepository;
    private static final Logger logger = Logger.getLogger("InfoLogging");

    @Autowired
    public RankingService(RankingRepository rankingRepository) {
        this.rankingRepository = rankingRepository;
    }

    //--------------------------CREATE--------------------------
    public void create(RankingEntity ranking) {
        rankingRepository.create(
                ranking.getUser().getRut(),
                ranking.getTask().getTask_id(),
                ranking.getValue());
        logger.info("Ranking created");
    }


    //--------------------------UPDATE--------------------------
    public void update(RankingEntity ranking) {
        rankingRepository.update(
                ranking.getRanking_id(),
                ranking.getUser().getRut(),
                ranking.getTask().getTask_id(),
                ranking.getValue());
        logger.info("Ranking updated");
    }


    //---------------------------READ---------------------------
    public List<RankingEntity> getAll() {return rankingRepository.getAll();}

    public RankingEntity getById(long id) {return rankingRepository.getById(id);}

    public List<RankingEntity> getByTaskId(long id) {return rankingRepository.getByTaskId(id);}


    //--------------------------DELETE--------------------------
    public void delete(long id) {
        rankingRepository.delete(id);
        logger.info("Ranking deleted");
    }


}
