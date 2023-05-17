package be.one16.barka.magazijn.ports.out;

import be.one16.barka.magazijn.domain.Artikel;
import be.one16.barka.magazijn.domain.Leverancier;

public interface ArtikelLeverancierCreatePort {

    void createArtikelLeverancier(Leverancier leverancier);

}
