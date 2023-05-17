package be.one16.barka.leverancier.adapter.out;

import be.one16.barka.domain.events.leverancier.LeverancierCreatedEvent;
import be.one16.barka.domain.events.leverancier.LeverancierDeletedEvent;
import be.one16.barka.domain.events.leverancier.LeverancierUpdatedEvent;
import be.one16.barka.leverancier.adapter.mapper.LeverancierDtoMapper;
import be.one16.barka.leverancier.domain.Leverancier;
import be.one16.barka.leverancier.ports.in.leverancier.LeveranciersQuery;
import be.one16.barka.leverancier.ports.out.leverancier.LeverancierCreatePort;
import be.one16.barka.leverancier.ports.out.leverancier.LeverancierDeletePort;
import be.one16.barka.leverancier.ports.out.leverancier.LeverancierUpdatePort;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Log4j2
public class LeverancierBroadCaster implements LeverancierCreatePort, LeverancierUpdatePort, LeverancierDeletePort {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final LeverancierDtoMapper leverancierDtoMapper;

    private final LeveranciersQuery leveranciersQuery;

    public LeverancierBroadCaster(ApplicationEventPublisher applicationEventPublisher, LeverancierDtoMapper leverancierDtoMapper, LeveranciersQuery leveranciersQuery) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.leverancierDtoMapper = leverancierDtoMapper;
        this.leveranciersQuery = leveranciersQuery;
    }

    @Override
    public void createLeverancier(Leverancier leverancier) {
        applicationEventPublisher.publishEvent(new LeverancierCreatedEvent(leverancierDtoMapper.mapLeverancierToMessage(leverancier)));
    }

    @Override
    public void updateLeverancier(Leverancier leverancier) {
        applicationEventPublisher.publishEvent(new LeverancierUpdatedEvent(leverancierDtoMapper.mapLeverancierToMessage(leverancier)));
    }

    @Override
    public void deleteLeverancier(Leverancier leverancier) {
        log.info("Leverancier to be broadcasted - " + (leverancier.getNaam()));
        applicationEventPublisher.publishEvent(new LeverancierDeletedEvent(leverancierDtoMapper.mapLeverancierToMessage(leverancier)));
    }

}
