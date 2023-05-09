package be.one16.barka.klant.port.in.verkoop;

import be.one16.barka.klant.port.in.verkoop.UpdateVerkoopCommand;
import org.springframework.transaction.annotation.Transactional;

public interface UpdateVerkoopUnitOfWork {

    @Transactional
    void updateVerkoop(UpdateVerkoopCommand updateVerkoopCommand);

}
