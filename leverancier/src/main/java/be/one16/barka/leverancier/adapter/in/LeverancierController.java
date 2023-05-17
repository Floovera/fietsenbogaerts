package be.one16.barka.leverancier.adapter.in;

import be.one16.barka.leverancier.adapter.mapper.ContactDtoMapper;
import be.one16.barka.leverancier.adapter.mapper.LeverancierDtoMapper;
import be.one16.barka.leverancier.domain.Leverancier;
import be.one16.barka.leverancier.ports.in.contact.*;
import be.one16.barka.leverancier.ports.in.leverancier.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/leveranciers")
public class LeverancierController {

    private final LeveranciersQuery leveranciersQuery;
    private final CreateLeverancierUnitOfWork createLeverancierUnitOfWork;
    private final UpdateLeverancierUnitOfWork updateLeverancierUnitOfWork;
    private final DeleteLeverancierUnitOfWork deleteLeverancierUnitOfWork;

    private final ContactenQuery contactenQuery;
    private final CreateContactUnitOfWork createContactUnitOfWork;
    private final UpdateContactUnitOfWork updateContactUnitOfWork;
    private final DeleteContactUnitOfWork deleteContactUnitOfWork;

    private final LeverancierDtoMapper leverancierDtoMapper;
    private final ContactDtoMapper contactDtoMapper;

    public LeverancierController(LeveranciersQuery leveranciersQuery, CreateLeverancierUnitOfWork createLeverancierUnitOfWork, UpdateLeverancierUnitOfWork updateLeverancierUnitOfWork, DeleteLeverancierUnitOfWork deleteLeverancierUnitOfWork, ContactenQuery contactenQuery, CreateContactUnitOfWork createContactUnitOfWork, UpdateContactUnitOfWork updateContactUnitOfWork, DeleteContactUnitOfWork deleteContactUnitOfWork, LeverancierDtoMapper leverancierDtoMapper, ContactDtoMapper contactDtoMapper) {
        this.leveranciersQuery = leveranciersQuery;
        this.createLeverancierUnitOfWork = createLeverancierUnitOfWork;
        this.updateLeverancierUnitOfWork = updateLeverancierUnitOfWork;
        this.deleteLeverancierUnitOfWork = deleteLeverancierUnitOfWork;
        this.contactenQuery = contactenQuery;
        this.createContactUnitOfWork = createContactUnitOfWork;
        this.updateContactUnitOfWork = updateContactUnitOfWork;
        this.deleteContactUnitOfWork = deleteContactUnitOfWork;
        this.leverancierDtoMapper = leverancierDtoMapper;
        this.contactDtoMapper = contactDtoMapper;
    }

    @GetMapping("/{id}")
    LeverancierDto getLeverancierById(@PathVariable("id") UUID leverancierId) {
        return leverancierDtoMapper.mapLeverancierToDto(leveranciersQuery.retrieveLeverancierById(leverancierId));
    }

    @GetMapping("/{id}/contacten")
    List<ContactDto> getContactenOfLeverancier(@PathVariable("id") UUID leverancierId) {
        return contactenQuery.retrieveContactenOfLeverancier(leverancierId).stream().map(contactDtoMapper::mapContactToDto).toList();
    }

    @GetMapping("/{id}/contacten/{contactId}")
    ContactDto getContactOfLeverancier(@PathVariable("id") UUID leverancierId, @PathVariable("contactId") UUID contactId) {
        return contactDtoMapper.mapContactToDto(contactenQuery.retrieveContactById(contactId, leverancierId));
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

    @PostMapping("/{id}/contacten")
    @ResponseStatus(HttpStatus.CREATED)
    UUID createContactOfLeverancier(@PathVariable("id") UUID leverancierId, @RequestBody ContactDto contact) {
        return createContactUnitOfWork.createContact(contactDtoMapper.mapDtoToCreateContactCommand(contact, leverancierId));
    }

    @PutMapping("/{id}")
    void updateLeverancier(@PathVariable("id") UUID leverancierId, @RequestBody LeverancierDto leverancier) {
        updateLeverancierUnitOfWork.updateLeverancier(leverancierDtoMapper.mapDtoToUpdateLeverancierCommand(leverancier, leverancierId));
    }

    @PutMapping("/{id}/contacten/{contactId}")
    void updateContactOfLeverancier(@PathVariable("id") UUID leverancierId, @PathVariable("contactId") UUID contactId, @RequestBody ContactDto contact) {
        updateContactUnitOfWork.updateContact(contactDtoMapper.mapDtoToUpdateContactCommand(contact, contactId, leverancierId));
    }

    @DeleteMapping("/{id}")
    void deleteLeverancier(@PathVariable("id") UUID leverancierId){
        Leverancier leverancier = leveranciersQuery.retrieveLeverancierById(leverancierId);
        deleteLeverancierUnitOfWork.deleteLeverancier(leverancier);
    }

    @DeleteMapping("/{id}/contacten/{contactId}")
    void deleteContactOfLeverancier(@PathVariable("id") UUID leverancierId, @PathVariable("contactId") UUID contactId) {
        deleteContactUnitOfWork.deleteContact(new DeleteContactCommand(contactId, leverancierId));
    }

}
