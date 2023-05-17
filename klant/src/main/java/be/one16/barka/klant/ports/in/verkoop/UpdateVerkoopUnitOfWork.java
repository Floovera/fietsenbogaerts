package be.one16.barka.klant.ports.in.verkoop;

import be.one16.barka.klant.common.exceptions.KlantNotFoundException;
import org.springframework.transaction.annotation.Transactional;

public interface UpdateVerkoopUnitOfWork {

    @Transactional
    void updateVerkoop(UpdateVerkoopCommand updateVerkoopCommand) throws KlantNotFoundException;

}
