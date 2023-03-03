package be.one16.barka.leverancier.ports.in.contact;

import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface CreateContactUnitOfWork {

    @Transactional
    UUID createContact(CreateContactCommand createContactCommand);

}
