package be.one16.barka.klant.ports.in.werkuur;

import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface CreateWerkuurUnitOfWork {

    @Transactional
    UUID createWerkuur(CreateWerkuurCommand createWerkuurCommand);

}
