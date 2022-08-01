package be.one16.barka.adapter.in;

import be.one16.barka.adapter.mapper.LeverancierMapper;
import be.one16.barka.domain.Leverancier;
import be.one16.barka.ports.in.*;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class LeverancierController {

    private CreateLeverancierUnitOfWork createLeverancierUnitOfWork;
    private UpdateLeverancierUnitOfWork updateLeverancierUnitOfWork;
    private DeleteLeverancierUnitOfWork deleteLeverancierUnitOfWork;
    private LeverancierMapper leverancierMapper;

    public LeverancierController(CreateLeverancierUnitOfWork createLeverancierUnitOfWork, UpdateLeverancierUnitOfWork updateLeverancierUnitOfWork, DeleteLeverancierUnitOfWork deleteLeverancierUnitOfWork, LeverancierMapper leverancierMapper) {
        this.createLeverancierUnitOfWork = createLeverancierUnitOfWork;
        this.updateLeverancierUnitOfWork = updateLeverancierUnitOfWork;
        this.deleteLeverancierUnitOfWork = deleteLeverancierUnitOfWork;
        this.leverancierMapper = leverancierMapper;
    }

    @PostMapping("/leveranciers/add")
    void addSingleLeverancier(@RequestBody LeverancierDto leverancier) {
        createLeverancierUnitOfWork.createLeverancier(new CreateLeverancierCommand(leverancier));
    }

    @PostMapping("/leveranciers/update")
    void updateSingleLeverancier(@RequestBody Leverancier leverancier) {
        updateLeverancierUnitOfWork.updateLeverancier(new UpdateLeverancierCommand(leverancier));
    }

    @DeleteMapping("/leveranciers/remove/{leverancierId}")
    void removeSingleLeverancier(@PathVariable UUID leverancierId){
        deleteLeverancierUnitOfWork.deleteLeverancier(new DeleteLeverancierCommand(leverancierId));
    }
}
