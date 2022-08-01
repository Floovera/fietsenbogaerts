package be.one16.barka.magazijn.adapters.out;

import be.one16.barka.domain.events.ArtikelCreatedEvent;
import be.one16.barka.magazijn.domain.Artikel;
import be.one16.barka.magazijn.ports.out.ArtikelCreatePort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ArtikelBroadCaster implements ArtikelCreatePort {

    private final ApplicationEventPublisher applicationEventPublisher;

    public ArtikelBroadCaster(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void artikelCreated(Artikel artikel) {
        BigDecimal actuelePrijs = artikel.getActuelePrijs() != null ? artikel.getActuelePrijs() : artikel.getVerkoopPrijs();
        applicationEventPublisher.publishEvent(new ArtikelCreatedEvent(artikel.getArtikelId(), artikel.getCode(), artikel.getMerk(), artikel.getOmschrijving(), actuelePrijs));
    }
}
