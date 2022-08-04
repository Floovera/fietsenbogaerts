package be.one16.barka.port.out;

import be.one16.barka.domain.Klant;

import java.util.UUID;

public interface RetrieveKlantByIdPort {

    Klant retrieveKlantById(UUID id);

}
