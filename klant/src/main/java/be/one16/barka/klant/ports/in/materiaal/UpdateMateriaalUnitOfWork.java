package be.one16.barka.klant.ports.in.materiaal;
import org.springframework.transaction.annotation.Transactional;

public interface UpdateMateriaalUnitOfWork {

    @Transactional
    void updateMateriaal(UpdateMateriaalCommand updateMateriaalCommand);

}
