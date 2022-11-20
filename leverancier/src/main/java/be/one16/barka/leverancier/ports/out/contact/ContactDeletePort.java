package be.one16.barka.leverancier.ports.out.contact;

import java.util.UUID;

public interface ContactDeletePort {

    void deleteContact(UUID contactId, UUID leverancierId);

}
