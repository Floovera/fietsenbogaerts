package be.one16.barka.leverancier.adapter.in;

import be.one16.barka.leverancier.adapter.mapper.LeverancierDtoMapper;
import be.one16.barka.leverancier.ports.in.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/leveranciers")
public class LeverancierController {

    private final LeveranciersQuery leveranciersQuery;
    private final CreateLeverancierUnitOfWork createLeverancierUnitOfWork;
    private final UpdateLeverancierUnitOfWork updateLeverancierUnitOfWork;
    private final DeleteLeverancierUnitOfWork deleteLeverancierUnitOfWork;

    private final LeverancierDtoMapper leverancierDtoMapper;

    public LeverancierController(LeveranciersQuery leveranciersQuery, CreateLeverancierUnitOfWork createLeverancierUnitOfWork, UpdateLeverancierUnitOfWork updateLeverancierUnitOfWork, DeleteLeverancierUnitOfWork deleteLeverancierUnitOfWork, LeverancierDtoMapper leverancierDtoMapper) {
        this.leveranciersQuery = leveranciersQuery;
        this.createLeverancierUnitOfWork = createLeverancierUnitOfWork;
        this.updateLeverancierUnitOfWork = updateLeverancierUnitOfWork;
        this.deleteLeverancierUnitOfWork = deleteLeverancierUnitOfWork;
        this.leverancierDtoMapper = leverancierDtoMapper;
    }

    @GetMapping("/{id}")
    LeverancierDto getLeverancierById(@PathVariable("id") UUID leverancierId) {
        return leverancierDtoMapper.mapLeverancierToDto(leveranciersQuery.retrieveLeverancierById(leverancierId));
    }

    @GetMapping
    Page<LeverancierDto> getLeveranciersFiltered(@RequestParam(name = "naam", required = false) String naaam, Pageable pageable) {
        return leveranciersQuery.retrieveLeverancierByFilterAndSort(new RetrieveLeveranciersFilterAndSortCommand(naaam, pageable))
                .map(leverancierDtoMapper::mapLeverancierToDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UUID createLeverancier(@RequestBody LeverancierDto leverancier) {
        return createLeverancierUnitOfWork.createLeverancier(leverancierDtoMapper.mapDtoToCreateLeverancierCommand(leverancier));
    }

    @PutMapping("/{id}")
    void updateLeverancier(@PathVariable("id") UUID leverancierId, @RequestBody LeverancierDto leverancier) {
        updateLeverancierUnitOfWork.updateLeverancier(leverancierDtoMapper.mapDtoToUpdateLeverancierCommand(leverancier, leverancierId));
    }

    @DeleteMapping("/{id}")
    void deleteLeverancier(@PathVariable("id") UUID leverancierId){
        deleteLeverancierUnitOfWork.deleteLeverancier(leverancierId);
    }

}
