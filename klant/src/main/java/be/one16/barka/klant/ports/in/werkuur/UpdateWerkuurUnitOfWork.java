package be.one16.barka.klant.ports.in.werkuur;

import org.springframework.transaction.annotation.Transactional;

public interface UpdateWerkuurUnitOfWork {

    @Transactional
    void updateWerkuur(UpdateWerkuurCommand updateWerkuurCommand);

}
