package be.one16.barka.adapter.in;

import be.one16.barka.adapter.mapper.KlantDtoMapper;
import be.one16.barka.port.in.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/klanten")
public class KlantController {

    @Autowired
    private RetrieveKlantByIdUnitOfWork retrieveKlantByIdUnitOfWork;

    @Autowired
    private RetrieveKlantByFilterAndSortUnitOfWork retrieveKlantByFilterAndSortUnitOfWork;

    @Autowired
    private CreateKlantUnitOfWork createKlantUnitOfWork;

    @Autowired
    private UpdateKlantUnitOfWork updateKlantUnitOfWork;

    @Autowired
    private DeleteKlantUnitOfWork deleteKlantUnitOfWork;

    @Autowired
    private KlantDtoMapper klantDtoMapper;

    @GetMapping("/{id}")
    KlantDto getKlantById(@PathVariable("id") UUID klantId) {
        return klantDtoMapper.mapKlantToDto(retrieveKlantByIdUnitOfWork.retrieveKlantById(klantId));
    }

    // UUID: technical
    // ID: functional

    @GetMapping
    Page<KlantDto> getKlantenFiltered(@RequestParam(name = "naam", required = false) String naam, Pageable pageable) {
        return retrieveKlantByFilterAndSortUnitOfWork.retrieveKlantByFilterAndSort(new RetrieveKlantFilterAndSortCommand(naam, pageable))
                .map(klantDtoMapper::mapKlantToDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UUID createKlant(@RequestBody KlantDto klant) {
        return createKlantUnitOfWork.createKlant(klantDtoMapper.mapDtoToCreateKlantCommand(klant));
    }

    @PutMapping("/{id}")
    void updateKlant(@PathVariable("id") UUID klantId, @RequestBody KlantDto klant) {
        UpdateKlantCommand updateKlantCommand = klantDtoMapper.mapDtoToUpdateKlantCommand(klant);
        updateKlantCommand.setKlantId(klantId);

        updateKlantUnitOfWork.updateKlant(updateKlantCommand);
    }

    @DeleteMapping("/{id}")
    void deleteKlant(@PathVariable("id") UUID klantId) {
        deleteKlantUnitOfWork.deleteKlant(klantId);
    }

}
