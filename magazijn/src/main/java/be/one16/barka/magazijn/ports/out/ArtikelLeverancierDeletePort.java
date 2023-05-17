package be.one16.barka.magazijn.ports.out;

import be.one16.barka.magazijn.domain.Leverancier;

import java.util.UUID;

public interface ArtikelLeverancierDeletePort {

    void deleteArtikelLeverancier(UUID uuid);

}
