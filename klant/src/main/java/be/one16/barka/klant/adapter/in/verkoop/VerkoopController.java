package be.one16.barka.klant.adapter.in.verkoop;

import be.one16.barka.klant.adapter.in.materiaal.MateriaalDto;
import be.one16.barka.klant.adapter.mapper.materiaal.MateriaalDtoMapper;
import be.one16.barka.klant.adapter.mapper.verkoop.VerkoopDtoMapper;
import be.one16.barka.klant.common.exceptions.KlantNotFoundException;
import be.one16.barka.klant.domain.Materiaal;
import be.one16.barka.klant.ports.in.materiaal.*;
import be.one16.barka.klant.ports.in.verkoop.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/verkopen")
public class VerkoopController {

    private final VerkopenQuery verkopenQuery;
    private final CreateVerkoopUnitOfWork createVerkoopUnitOfWork;
    private final UpdateVerkoopUnitOfWork updateVerkoopUnitOfWork;
    private final DeleteVerkoopUnitOfWork deleteVerkoopUnitOfWork;
    private final VerkoopDtoMapper verkoopDtoMapper;


    private final MaterialenQuery materialenQuery;
    private final MateriaalDtoMapper materiaalDtoMapper;
    private final CreateMateriaalUnitOfWork createMateriaalUnitOfWork;
    private final UpdateMateriaalUnitOfWork updateMateriaalUnitOfWork;
    private final DeleteMateriaalUnitOfWork deleteMateriaalUnitOfWork;

    public VerkoopController(VerkopenQuery verkopenQuery, CreateVerkoopUnitOfWork createVerkoopUnitOfWork, UpdateVerkoopUnitOfWork updateVerkoopUnitOfWork, DeleteVerkoopUnitOfWork deleteVerkoopUnitOfWork, VerkoopDtoMapper verkoopDtoMapper, MaterialenQuery materialenQuery, MateriaalDtoMapper materiaalDtoMapper, CreateMateriaalUnitOfWork createMateriaalUnitOfWork, UpdateMateriaalUnitOfWork updateMateriaalUnitOfWork, DeleteMateriaalUnitOfWork deleteMateriaalUnitOfWork) {
        this.verkopenQuery = verkopenQuery;
        this.createVerkoopUnitOfWork = createVerkoopUnitOfWork;
        this.updateVerkoopUnitOfWork = updateVerkoopUnitOfWork;
        this.deleteVerkoopUnitOfWork = deleteVerkoopUnitOfWork;
        this.verkoopDtoMapper = verkoopDtoMapper;
        this.materialenQuery = materialenQuery;
        this.materiaalDtoMapper = materiaalDtoMapper;
        this.createMateriaalUnitOfWork = createMateriaalUnitOfWork;
        this.updateMateriaalUnitOfWork = updateMateriaalUnitOfWork;
        this.deleteMateriaalUnitOfWork = deleteMateriaalUnitOfWork;
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


    @GetMapping("/{id}/materialen")
    List<MateriaalDto> getMaterialenOfVerkoop(@PathVariable("id") UUID verkoopId) {
        return materialenQuery.retrieveMaterialenOfVerkoop(verkoopId).stream().map(materiaalDtoMapper::mapMateriaalToDto).toList();
    }

    @GetMapping("/{id}/materialen/{materiaalId}")
    MateriaalDto getMateriaalOfVerkoop(@PathVariable("id") UUID verkoopId, @PathVariable("materiaalId") UUID materiaalId) {
        Materiaal materiaal = materialenQuery.retrieveMateriaalById(materiaalId,verkoopId);
        return materiaalDtoMapper.mapMateriaalToDto(materiaal);

    }

    @PostMapping("/{id}/materialen")
    @ResponseStatus(HttpStatus.CREATED)
    UUID createMateriaalOfVerkoop(@PathVariable("id") UUID verkoopId, @RequestBody MateriaalDto materiaalDto) {
        return createMateriaalUnitOfWork.createMateriaal(materiaalDtoMapper.mapDtoToCreateMateriaalCommand(materiaalDto,verkoopId));
    }

    @PutMapping("/{id}/materialen/{materiaalId}")
    void updateMateriaalOfVerkoop(@PathVariable("id") UUID verkoopId, @PathVariable("materiaalId") UUID materiaalId, @RequestBody MateriaalDto materiaalDto) {
        updateMateriaalUnitOfWork.updateMateriaal(materiaalDtoMapper.mapDtoToUpdateMateriaalCommand(materiaalDto,materiaalId,verkoopId));
    }

    @DeleteMapping("/{id}/materialen/{materiaalId}")
    void deleteMateriaalOfVerkoop(@PathVariable("id") UUID verkoopId, @PathVariable("materiaalId") UUID materiaalId) {
        deleteMateriaalUnitOfWork.deleteMateriaal(new DeleteMateriaalCommand(materiaalId,verkoopId));
    }

}
