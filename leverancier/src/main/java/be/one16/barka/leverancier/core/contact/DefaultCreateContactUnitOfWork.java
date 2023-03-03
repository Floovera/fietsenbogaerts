package be.one16.barka.leverancier.core.contact;

import be.one16.barka.domain.annotations.UnitOfWork;
import be.one16.barka.leverancier.domain.Contact;
import be.one16.barka.leverancier.ports.in.contact.CreateContactCommand;
import be.one16.barka.leverancier.ports.in.contact.CreateContactUnitOfWork;
import be.one16.barka.leverancier.ports.out.contact.ContactCreatePort;

import java.util.List;
import java.util.UUID;

@UnitOfWork
public class DefaultCreateContactUnitOfWork implements CreateContactUnitOfWork {

    private final List<ContactCreatePort> contactCreatePorts;

    public DefaultCreateContactUnitOfWork(List<ContactCreatePort> contactCreatePorts) {
        this.contactCreatePorts = contactCreatePorts;
    }

    @Override
    public UUID createContact(CreateContactCommand createContactCommand) {
        Contact contact = Contact.builder()
                .contactId(UUID.randomUUID())
                .naam(createContactCommand.naam())
                .onderwerp(createContactCommand.onderwerp())
                .contactMethode(createContactCommand.contactMethode())
                .gegevens(createContactCommand.gegevens())
                .leverancierId(createContactCommand.leverancierId())
                .build();

        contactCreatePorts.forEach(port -> port.createContact(contact));

        return contact.getContactId();
    }

}
