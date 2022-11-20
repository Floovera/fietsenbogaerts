package be.one16.barka.leverancier.adapter.mapper;

import be.one16.barka.leverancier.adapter.in.ContactDto;
import be.one16.barka.leverancier.domain.Contact;
import be.one16.barka.leverancier.ports.in.contact.CreateContactCommand;
import be.one16.barka.leverancier.ports.in.contact.UpdateContactCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ContactDtoMapper {

    @Mapping(source = "leverancierId", target = "leverancierId")
    CreateContactCommand mapDtoToCreateContactCommand(ContactDto contact, UUID leverancierId);
    @Mapping(source = "contactId", target = "contactId")
    @Mapping(source = "leverancierId", target = "leverancierId")
    UpdateContactCommand mapDtoToUpdateContactCommand(ContactDto contact, UUID contactId, UUID leverancierId);

    ContactDto mapContactToDto(Contact contact);

}
