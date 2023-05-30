package be.one16.barka.klant.core.verkoop;

import be.one16.barka.domain.annotations.UnitOfWork;
import be.one16.barka.domain.exceptions.EntityNotFoundException;
import be.one16.barka.klant.adapter.out.repository.VerkoopRepository;
import be.one16.barka.klant.adapter.out.verkoop.VerkoopJpaEntity;
import be.one16.barka.klant.common.OrderType;
import be.one16.barka.klant.common.exceptions.KlantNotFoundException;
import be.one16.barka.klant.domain.Klant;
import be.one16.barka.klant.domain.Verkoop;
import be.one16.barka.klant.ports.in.klant.KlantenQuery;
import be.one16.barka.klant.ports.in.verkoop.CreateVerkoopCommand;
import be.one16.barka.klant.ports.in.verkoop.CreateVerkoopUnitOfWork;
import be.one16.barka.klant.ports.out.verkoop.CreateVerkoopPort;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@UnitOfWork
public class DefaultCreateVerkoopUnitOfWork implements CreateVerkoopUnitOfWork {

    private final List<CreateVerkoopPort> createVerkoopPorts;

    private final KlantenQuery klantenquery;

    private final VerkoopRepository verkoopRepository;
    private static final String REPARATIENUMMER_REGEX = "^(\\d){6}$";

    public DefaultCreateVerkoopUnitOfWork(List<CreateVerkoopPort> createVerkoopPorts, KlantenQuery klantenquery, VerkoopRepository verkoopRepository) {
        this.createVerkoopPorts = createVerkoopPorts;
        this.klantenquery = klantenquery;
        this.verkoopRepository = verkoopRepository;
    }

    @Override
    @Transactional
    public UUID createVerkoop(CreateVerkoopCommand createVerkoopCommand) throws KlantNotFoundException {
        UUID calculatedKlantId = null;
        String orderNummer = createVerkoopCommand.orderNummer();
        if(createVerkoopCommand.orderType().equals(OrderType.FACTUUR)){orderNummer = createOrderNummer();}

        if(!createVerkoopCommand.reparatieNummer().matches(REPARATIENUMMER_REGEX)){
            throw new IllegalArgumentException("Value for 'ReparatieNummer' must be 6 digits");
        }

        if (createVerkoopCommand.klantId() != null) {
            try {
                Klant klantRetrieved = getKlant(createVerkoopCommand.klantId());
                calculatedKlantId = klantRetrieved.getKlantId();
            } catch (EntityNotFoundException e) {
                throw new KlantNotFoundException(String.format("Did not find the klant for the provided UUID: %s", createVerkoopCommand.klantId()));
            }
        }

        Verkoop verkoop = Verkoop.builder()
                .verkoopId(UUID.randomUUID())
                .orderType(createVerkoopCommand.orderType())
                .naam(createVerkoopCommand.naam())
                .opmerkingen(createVerkoopCommand.opmerkingen())
                .datum(createVerkoopCommand.datum())
                .klantId(calculatedKlantId)
                .reparatieNummer(createVerkoopCommand.reparatieNummer())
                .orderNummer(orderNummer)
                .build();

        createVerkoopPorts.forEach(port -> port.createVerkoop(verkoop));

        return verkoop.getVerkoopId();
    }

    private Klant getKlant(UUID klantID) {
        return klantenquery.retrieveKlantById(klantID);
    }

    private Long retrieveLastVerkoop(){
        Optional<VerkoopJpaEntity> verkoopJpaEntity =  verkoopRepository.findTopByOrderByIdDesc();
        if(verkoopJpaEntity.isEmpty()){
            return null;
        }else{
            return verkoopJpaEntity.get().getId();}

    }

    private String createOrderNummer(){
        OffsetDateTime offsetDateTime = OffsetDateTime.now();
        int year = offsetDateTime.getYear();
        int lastVerkoopPlusOne = 1;
        if(retrieveLastVerkoop()!= null){lastVerkoopPlusOne = retrieveLastVerkoop().intValue() + 1; }

        String orderNummer = Integer.toString(year) + "/" + Integer.toString(lastVerkoopPlusOne) ;
        return orderNummer;
    }
}
