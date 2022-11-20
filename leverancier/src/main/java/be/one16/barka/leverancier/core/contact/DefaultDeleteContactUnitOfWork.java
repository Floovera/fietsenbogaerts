package be.one16.barka.leverancier.core.contact;

import be.one16.barka.domain.annotations.UnitOfWork;
import be.one16.barka.leverancier.ports.in.contact.DeleteContactCommand;
import be.one16.barka.leverancier.ports.in.contact.DeleteContactUnitOfWork;
import be.one16.barka.leverancier.ports.out.contact.ContactDeletePort;

import java.util.List;

@UnitOfWork
public class DefaultDeleteContactUnitOfWork implements DeleteContactUnitOfWork {

    private final List<ContactDeletePort> contactDeletePorts;

    public DefaultDeleteContactUnitOfWork(List<ContactDeletePort> contactDeletePorts) {
        this.contactDeletePorts = contactDeletePorts;
    }

    @Override
    public void deleteContact(DeleteContactCommand deleteContactCommand) {
        contactDeletePorts.forEach(port -> port.deleteContact(deleteContactCommand.contactId(), deleteContactCommand.leverancierId()));
    }

}
