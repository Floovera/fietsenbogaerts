package be.one16.barka.klant.ports.in.verkoop;

import be.one16.barka.klant.common.exceptions.KlantNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface CreateVerkoopUnitOfWork {

    @Transactional
    UUID createVerkoop(CreateVerkoopCommand createVerkoopCommand) throws KlantNotFoundException;

}
