package be.one16.barka.klant.ports.in.materiaal;
import org.springframework.transaction.annotation.Transactional;

public interface DeleteMateriaalUnitOfWork {

    @Transactional
    void deleteMateriaal(DeleteMateriaalCommand deleteMateriaalCommand);

}
