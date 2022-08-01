package be.one16.barka.magazijn.ports.out;

import be.one16.barka.magazijn.domain.Artikel;

public interface ArtikelCreatePort {

    void artikelCreated(Artikel artikel);
}
