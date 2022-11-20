package be.one16.barka.leverancier.core.contact;

import be.one16.barka.domain.annotations.UnitOfWork;
import be.one16.barka.leverancier.domain.Contact;
import be.one16.barka.leverancier.ports.in.contact.ContactenQuery;
import be.one16.barka.leverancier.ports.out.contact.LoadContactenPort;

import java.util.List;
import java.util.UUID;

@UnitOfWork
public class DefaultContactenQuery implements ContactenQuery {

    private final LoadContactenPort loadContactenPort;

    public DefaultContactenQuery(LoadContactenPort loadContactenPort) {
        this.loadContactenPort = loadContactenPort;
    }

    @Override
    public Contact retrieveContactById(UUID id, UUID leverancierId) {
        return loadContactenPort.retrieveContactOfLeverancier(id, leverancierId);
    }

    @Override
    public List<Contact> retrieveContactenOfLeverancier(UUID leverancierId) {
        return loadContactenPort.retrieveContactenOfLeverancier(leverancierId);
    }

}
