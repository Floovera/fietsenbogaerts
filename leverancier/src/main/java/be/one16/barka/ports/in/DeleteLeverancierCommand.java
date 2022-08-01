package be.one16.barka.ports.in;

import java.util.UUID;

public record DeleteLeverancierCommand (UUID leverancierUuid){
    public DeleteLeverancierCommand{
        //Validate here
    }
}
