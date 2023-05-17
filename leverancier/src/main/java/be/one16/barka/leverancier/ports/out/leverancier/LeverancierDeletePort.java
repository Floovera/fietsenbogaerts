package be.one16.barka.leverancier.ports.out.leverancier;

import be.one16.barka.leverancier.domain.Leverancier;

import java.util.UUID;

public interface LeverancierDeletePort {

    void deleteLeverancier(Leverancier leverancier);

}
