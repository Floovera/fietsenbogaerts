package be.one16.barka.leverancier.core.contact;

import be.one16.barka.domain.annotations.UnitOfWork;
import be.one16.barka.leverancier.domain.Contact;
import be.one16.barka.leverancier.ports.in.contact.UpdateContactCommand;
import be.one16.barka.leverancier.ports.in.contact.UpdateContactUnitOfWork;
import be.one16.barka.leverancier.ports.out.contact.ContactUpdatePort;

import java.util.List;

@UnitOfWork
public class DefaultUpdateContactUnitOfWork implements UpdateContactUnitOfWork {

    private final List<ContactUpdatePort> contactUpdatePorts;

    public DefaultUpdateContactUnitOfWork(List<ContactUpdatePort> contactUpdatePorts) {
        this.contactUpdatePorts = contactUpdatePorts;
    }

    @Override
    public void updateContact(UpdateContactCommand updateContactCommand) {
        Contact contact = Contact.builder()
                .contactId(updateContactCommand.contactId())
                .naam(updateContactCommand.naam())
                .onderwerp(updateContactCommand.onderwerp())
                .contactMethode(updateContactCommand.contactMethode())
                .gegevens(updateContactCommand.gegevens())
                .leverancierId(updateContactCommand.leverancierId())
                .build();

        contactUpdatePorts.forEach(port -> port.updateContact(contact));
    }

}
