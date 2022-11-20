package be.one16.barka.leverancier.ports.out.contact;

import be.one16.barka.leverancier.domain.Contact;

import java.util.List;
import java.util.UUID;

public interface LoadContactenPort {

    Contact retrieveContactOfLeverancier(UUID id, UUID leverancierId);

    List<Contact> retrieveContactenOfLeverancier(UUID leverancierId);

}
