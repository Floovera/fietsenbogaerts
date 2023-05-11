package be.one16.barka.magazijn.adapters.in;
import be.one16.barka.magazijn.core.ManageLeverancierInMagazijnUnitOfWork;
import be.one16.barka.magazijn.domain.Leverancier;
import be.one16.barka.magazijn.ports.in.CreateArtikelLeverancierCommand;
import lombok.extern.log4j.Log4j2;
import be.one16.barka.domain.events.leverancier.LeverancierCreatedEvent;
import be.one16.barka.domain.events.leverancier.LeverancierMessage;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Log4j2
public class LeverancierEventListener {

    private final ManageLeverancierInMagazijnUnitOfWork manageLeverancierInMagazijnUnitOfWork;

    public LeverancierEventListener(ManageLeverancierInMagazijnUnitOfWork manageLeverancierInMagazijnUnitOfWork) {
        this.manageLeverancierInMagazijnUnitOfWork = manageLeverancierInMagazijnUnitOfWork;
    }

    @EventListener
    public void onApplicationEvent(LeverancierCreatedEvent event) {
        LeverancierMessage message = (LeverancierMessage)event.getSource();
        log.info("Received leverancier - " + (message.getNaam()));
        manageLeverancierInMagazijnUnitOfWork.createArtikelLeverancierInMagazijn(new CreateArtikelLeverancierCommand(message.getLeverancierId(),message.getNaam()));
    }


}
