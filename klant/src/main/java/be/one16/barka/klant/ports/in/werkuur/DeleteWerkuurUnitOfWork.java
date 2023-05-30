package be.one16.barka.klant.ports.in.werkuur;

import org.springframework.transaction.annotation.Transactional;

public interface DeleteWerkuurUnitOfWork {

    @Transactional
    void deleteWerkuur(DeleteWerkuurCommand deleteWerkuurCommand);

}
