package be.one16.barka.klant.adapter.in.verkoop;

import be.one16.barka.klant.adapter.mapper.verkoop.VerkoopDtoMapper;
import be.one16.barka.klant.common.exceptions.KlantNotFoundException;
import be.one16.barka.klant.port.in.verkoop.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/verkopen")
public class VerkoopController {

    private final VerkopenQuery verkopenQuery;
    private final CreateVerkoopUnitOfWork createVerkoopUnitOfWork;
    private final UpdateVerkoopUnitOfWork updateVerkoopUnitOfWork;
    private final DeleteVerkoopUnitOfWork deleteVerkoopUnitOfWork;
    private final VerkoopDtoMapper verkoopDtoMapper;

    public VerkoopController(VerkopenQuery verkopenQuery, CreateVerkoopUnitOfWork createVerkoopUnitOfWork, UpdateVerkoopUnitOfWork updateVerkoopUnitOfWork, DeleteVerkoopUnitOfWork deleteVerkoopUnitOfWork, VerkoopDtoMapper verkoopDtoMapper) {
        this.verkopenQuery = verkopenQuery;
        this.createVerkoopUnitOfWork = createVerkoopUnitOfWork;
        this.updateVerkoopUnitOfWork = updateVerkoopUnitOfWork;
        this.deleteVerkoopUnitOfWork = deleteVerkoopUnitOfWork;
        this.verkoopDtoMapper = verkoopDtoMapper;
    }

    @GetMapping("/{id}")
    VerkoopDto getVerkoopById(@PathVariable("id") UUID verkoopId) {
        return verkoopDtoMapper.mapVerkoopToDto(verkopenQuery.retrieveVerkoopById(verkoopId));
    }

    @GetMapping
    Page<VerkoopDto> getVerkopenFiltered(@RequestParam(name = "naam", required = false) String naam, Pageable pageable) {
        return verkopenQuery.retrieveVerkopenByFilterAndSort(new RetrieveVerkoopFilterAndSortCommand(naam, pageable))
                .map(verkoopDtoMapper::mapVerkoopToDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UUID createVerkoop(@RequestBody VerkoopDto verkoop) throws KlantNotFoundException {
        CreateVerkoopCommand createVerkoopCommand = verkoopDtoMapper.mapDtoToCreateVerkoopCommand(verkoop);
        return createVerkoopUnitOfWork.createVerkoop(createVerkoopCommand);
    }

    @PutMapping("/{id}")
    void updateVerkoop(@PathVariable("id") UUID verkoopId, @RequestBody VerkoopDto verkoop) throws KlantNotFoundException {
        updateVerkoopUnitOfWork.updateVerkoop(verkoopDtoMapper.mapDtoToUpdateVerkoopCommand(verkoop,verkoopId));
    }

    @DeleteMapping("/{id}")
    void deleteVerkoop(@PathVariable("id") UUID verkoopId) {
        deleteVerkoopUnitOfWork.deleteVerkoop(verkoopId);
    }

}
