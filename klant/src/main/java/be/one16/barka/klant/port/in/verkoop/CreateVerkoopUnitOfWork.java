package be.one16.barka.klant.port.in.verkoop;

import be.one16.barka.klant.common.exceptions.KlantNotFoundException;
import be.one16.barka.klant.port.in.verkoop.CreateVerkoopCommand;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface CreateVerkoopUnitOfWork {

    @Transactional
    UUID createVerkoop(CreateVerkoopCommand createVerkoopCommand) throws KlantNotFoundException;

}
