package be.one16.barka.klant.ports.in.materiaal;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

public interface CreateMateriaalUnitOfWork {

    @Transactional
    UUID createMateriaal(CreateMateriaalCommand createMateriaalCommand);

}
