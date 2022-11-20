package be.one16.barka.leverancier.adapter.mapper;

import be.one16.barka.leverancier.adapter.out.ContactJpaEntity;
import be.one16.barka.leverancier.domain.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContactJpaEntityMapper {

    @Mapping(source = "uuid", target = "contactId")
    @Mapping(source = "leverancier.uuid", target = "leverancierId")
    Contact mapJpaEntityToContact(ContactJpaEntity contactJpaEntity);

}
