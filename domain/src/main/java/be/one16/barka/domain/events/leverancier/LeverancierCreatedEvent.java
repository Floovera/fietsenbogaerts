package be.one16.barka.domain.events.leverancier;

import org.springframework.context.ApplicationEvent;

import java.util.UUID;

/*public record LeverancierCreatedEvent(UUID leverancierId, String naam) {


}*/

public class LeverancierCreatedEvent extends ApplicationEvent {

    public LeverancierCreatedEvent(Object leveranciermessage) {
        super(leveranciermessage);
    }

}