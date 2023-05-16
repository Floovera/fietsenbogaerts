package be.one16.barka.domain.events.leverancier;

import org.springframework.context.ApplicationEvent;

import java.util.UUID;

public class LeverancierDeletedEvent extends ApplicationEvent {
    public LeverancierDeletedEvent(Object leveranciermessage) {
        super(leveranciermessage);
    }

}
