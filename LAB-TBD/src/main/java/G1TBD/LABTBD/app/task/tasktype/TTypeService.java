package G1TBD.LABTBD.app.task.tasktype;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TTypeService {

    private final TTypeRepository tTypeRepository;

    public TTypeService(TTypeRepository tTypeRepository) {
        this.tTypeRepository = tTypeRepository;
    }

    // --------------------------CREATE--------------------------

    private void create(TTypeEntity tType) {
        tTypeRepository.create(tType.getType());
    }

    // --------------------------READ--------------------------

    private List<TTypeEntity> getAll() {
        return tTypeRepository.getAll();
    }

    private TTypeEntity getById(Long id) {
        return tTypeRepository.getById(id);
    }

    // --------------------------UPDATE--------------------------

    private void update(TTypeEntity tType) {
        tTypeRepository.update(tType.getTtype_id(), tType.getType());
    }

    // --------------------------DELETE--------------------------

    private void delete(Long id) {
        tTypeRepository.delete(id);
    }

}
