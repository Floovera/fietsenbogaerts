package be.one16.barka.domain.events.leverancier;
import org.springframework.context.ApplicationEvent;

public class LeverancierUpdatedEvent extends ApplicationEvent {

    public LeverancierUpdatedEvent(Object leveranciermessage) {
        super(leveranciermessage);
    }

}
