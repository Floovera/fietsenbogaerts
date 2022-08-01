package be.one16.barka.ports.in;

import be.one16.barka.domain.Leverancier;

public record UpdateLeverancierCommand(Leverancier leverancier) {
    public UpdateLeverancierCommand{
        //validation here
    }
}
