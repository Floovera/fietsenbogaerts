package be.one16.barka.domain.events.leverancier;

import org.springframework.context.ApplicationEvent;

import java.util.UUID;

public class LeverancierDeletedEvent extends ApplicationEvent {
    public LeverancierDeletedEvent(UUID id) {
        super(id);
    }

}
