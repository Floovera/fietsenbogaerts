package be.one16.barka.leverancier.ports.in.contact;

import be.one16.barka.leverancier.domain.Contact;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface ContactenQuery {

    @Transactional(readOnly = true)
    Contact retrieveContactById(UUID id, UUID leverancierId);

    @Transactional(readOnly = true)
    List<Contact> retrieveContactenOfLeverancier(UUID leverancierId);

}
