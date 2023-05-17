package be.one16.barka.magazijn.adapters.in;

import be.one16.barka.domain.events.leverancier.LeverancierCreatedEvent;
import be.one16.barka.domain.events.leverancier.LeverancierMessage;
import be.one16.barka.magazijn.adapters.mapper.ArtikelDtoMapper;
import be.one16.barka.magazijn.ports.in.*;
import org.springframework.context.ApplicationListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController

@RequestMapping("api/artikels")
public class ArtikelController {

    private final ArtikelsInMagazijnQuery artikelsInMagazijnQuery;
    private final CreateArtikelInMagazijnUnitOfWork createArtikelInMagazijnUnitOfWork;
    private final UpdateArtikelInMagazijnUnitOfWork updateArtikelInMagazijnUnitOfWork;
    private final DeleteArtikelFromMagazijnUnitOfWork deleteArtikelFromMagazijnUnitOfWork;

    private final ArtikelDtoMapper artikelDtoMapper;

    public ArtikelController(ArtikelsInMagazijnQuery artikelsInMagazijnQuery, CreateArtikelInMagazijnUnitOfWork createArtikelInMagazijnUnitOfWork, UpdateArtikelInMagazijnUnitOfWork updateArtikelInMagazijnUnitOfWork, DeleteArtikelFromMagazijnUnitOfWork deleteArtikelFromMagazijnUnitOfWork, ArtikelDtoMapper artikelDtoMapper) {
        this.artikelsInMagazijnQuery = artikelsInMagazijnQuery;
        this.createArtikelInMagazijnUnitOfWork = createArtikelInMagazijnUnitOfWork;
        this.updateArtikelInMagazijnUnitOfWork = updateArtikelInMagazijnUnitOfWork;
        this.deleteArtikelFromMagazijnUnitOfWork = deleteArtikelFromMagazijnUnitOfWork;
        this.artikelDtoMapper = artikelDtoMapper;
    }

    @GetMapping("/{id}")
    ArtikelDto getArtikelFromMagazijnById(@PathVariable("id") UUID artikelId) {
        return artikelDtoMapper.mapArtikelToDto(artikelsInMagazijnQuery.retrieveArtikelFromMagazijnById(artikelId));
    }

    @GetMapping
    Page<ArtikelDto> getArtikelsFiltered(@RequestParam(name = "code", required = false) String code,
                                         @RequestParam(name = "merk", required = false) String merk,
                                         @RequestParam(name = "omschrijving", required = false) String omschrijving,
                                         @RequestParam(name = "leverancier", required = false) UUID leverancierId, Pageable pageable) {
        return artikelsInMagazijnQuery.retrieveArtikelsByFilterAndSort(new RetrieveArtikelFilterAndSortCommand(code, merk, omschrijving, leverancierId, pageable))
                .map(artikelDtoMapper::mapArtikelToDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UUID createArtikelInMagazijn(@RequestBody ArtikelDto artikel) {
        return createArtikelInMagazijnUnitOfWork.createArtikelInMagazijn(artikelDtoMapper.mapDtoToCreateArtikelCommand(artikel));
    }

    @PutMapping("/{id}")
    void updateArtikelInMagazijn(@PathVariable("id") UUID artikelId, @RequestBody ArtikelDto artikel) {
         updateArtikelInMagazijnUnitOfWork.updateArtikelInMagazijn(artikelDtoMapper.mapDtoToUpdateArtikelCommand(artikel, artikelId));
    }

    @DeleteMapping("/{id}")
    void deleteArtikelFromMagazijn(@PathVariable("id") UUID artikelId) {
        deleteArtikelFromMagazijnUnitOfWork.deleteArtikelFromMagazijn(artikelId);
    }

}
