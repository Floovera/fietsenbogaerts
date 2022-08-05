package be.one16.barka.klant.adapter.in;

import be.one16.barka.klant.adapter.mapper.KlantDtoMapper;
import be.one16.barka.klant.port.in.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/klanten")
public class KlantController {

    private final KlantenQuery klantenQuery;
    private final CreateKlantUnitOfWork createKlantUnitOfWork;
    private final UpdateKlantUnitOfWork updateKlantUnitOfWork;
    private final DeleteKlantUnitOfWork deleteKlantUnitOfWork;

    private final KlantDtoMapper klantDtoMapper;

    public KlantController(KlantenQuery klantenQuery, CreateKlantUnitOfWork createKlantUnitOfWork, UpdateKlantUnitOfWork updateKlantUnitOfWork, DeleteKlantUnitOfWork deleteKlantUnitOfWork, KlantDtoMapper klantDtoMapper) {
        this.klantenQuery = klantenQuery;
        this.createKlantUnitOfWork = createKlantUnitOfWork;
        this.updateKlantUnitOfWork = updateKlantUnitOfWork;
        this.deleteKlantUnitOfWork = deleteKlantUnitOfWork;
        this.klantDtoMapper = klantDtoMapper;
    }

    @GetMapping("/{id}")
    KlantDto getKlantById(@PathVariable("id") UUID klantId) {
        return klantDtoMapper.mapKlantToDto(klantenQuery.retrieveKlantById(klantId));
    }

    @GetMapping
    Page<KlantDto> getKlantenFiltered(@RequestParam(name = "naam", required = false) String naam, Pageable pageable) {
        return klantenQuery.retrieveKlantenByFilterAndSort(new RetrieveKlantFilterAndSortCommand(naam, pageable))
                .map(klantDtoMapper::mapKlantToDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UUID createKlant(@RequestBody KlantDto klant) {
        return createKlantUnitOfWork.createKlant(klantDtoMapper.mapDtoToCreateKlantCommand(klant));
    }

    @PutMapping("/{id}")
    void updateKlant(@PathVariable("id") UUID klantId, @RequestBody KlantDto klant) {
        updateKlantUnitOfWork.updateKlant(klantDtoMapper.mapDtoToUpdateKlantCommand(klant, klantId));
    }

    @DeleteMapping("/{id}")
    void deleteKlant(@PathVariable("id") UUID klantId) {
        deleteKlantUnitOfWork.deleteKlant(klantId);
    }

}
