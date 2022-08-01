package be.one16.barka.ports.in;

import be.one16.barka.adapter.in.LeverancierDto;

public record CreateLeverancierCommand(LeverancierDto leverancier) {
    public CreateLeverancierCommand {
        //validation
    }
}
