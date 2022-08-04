package be.one16.barka.magazijn.adapters.out;

import be.one16.barka.magazijn.domain.Artikel;
import be.one16.barka.magazijn.ports.out.ArtikelCreatePort;
import org.springframework.stereotype.Component;

@Component
public class ArtikelDBAdapter implements ArtikelCreatePort {

    private final ArtikelRepository artikelRepository;

    public ArtikelDBAdapter(ArtikelRepository artikelRepository) {
        this.artikelRepository = artikelRepository;
    }

    @Override
    public void artikelCreated(Artikel artikel) {
        ArtikelJpaEntity artikelJpaEntity = new ArtikelJpaEntity();
        artikelJpaEntity.setUuid(artikel.getArtikelId());
        artikelJpaEntity.setCode(artikel.getCode());
        artikelJpaEntity.setOmschrijving(artikel.getOmschrijving());
        artikelJpaEntity.setMerk(artikel.getMerk());
        artikelJpaEntity.setAantalInStock(artikel.getAantalInStock());
        artikelJpaEntity.setMinimumInStock(artikel.getMinimumInStock());
        artikelJpaEntity.setAankoopPrijs(artikel.getAankoopPrijs());
        artikelJpaEntity.setVerkoopPrijs(artikel.getVerkoopPrijs());
        artikelJpaEntity.setActuelePrijs(artikel.getActuelePrijs());
        artikelRepository.save(artikelJpaEntity);
    }
}
