package be.one16.barka.magazijn.adapters.out;

import be.one16.barka.klant.domain.events.artikel.ArtikelCreatedEvent;
import be.one16.barka.klant.domain.events.artikel.ArtikelDeletedEvent;
import be.one16.barka.klant.domain.events.artikel.ArtikelUpdatedEvent;
import be.one16.barka.magazijn.domain.Artikel;
import be.one16.barka.magazijn.ports.out.ArtikelCreatePort;
import be.one16.barka.magazijn.ports.out.ArtikelDeletePort;
import be.one16.barka.magazijn.ports.out.ArtikelUpdatePort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ArtikelBroadCaster implements ArtikelCreatePort, ArtikelUpdatePort, ArtikelDeletePort {

    private final ApplicationEventPublisher applicationEventPublisher;

    public ArtikelBroadCaster(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void createArtikel(Artikel artikel) {;
        applicationEventPublisher.publishEvent(new ArtikelCreatedEvent(artikel.getArtikelId(), artikel.getCode(), artikel.getMerk(), artikel.getOmschrijving(), artikel.getActuelePrijs(), artikel.getLeverancier().getLeverancierId()));
    }

    @Override
    public void updateArtikel(Artikel artikel) {
        applicationEventPublisher.publishEvent(new ArtikelUpdatedEvent(artikel.getArtikelId(), artikel.getCode(), artikel.getMerk(), artikel.getOmschrijving(), artikel.getActuelePrijs(), artikel.getLeverancier().getLeverancierId()));
    }

    @Override
    public void deleteArtikel(UUID id) {
        applicationEventPublisher.publishEvent(new ArtikelDeletedEvent(id));
    }
}
