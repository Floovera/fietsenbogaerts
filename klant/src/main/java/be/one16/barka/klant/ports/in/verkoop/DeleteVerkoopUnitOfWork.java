package be.one16.barka.klant.ports.in.verkoop;

import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface DeleteVerkoopUnitOfWork {

    @Transactional
    void deleteVerkoop(UUID id);

}
