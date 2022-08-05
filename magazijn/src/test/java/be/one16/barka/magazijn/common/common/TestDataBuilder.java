package be.one16.barka.magazijn.common.common;

import be.one16.barka.magazijn.adapters.in.ArtikelDto;
import be.one16.barka.magazijn.adapters.out.ArtikelJpaEntity;
import be.one16.barka.magazijn.adapters.out.LeverancierJpaEntity;
import be.one16.barka.magazijn.domain.Artikel;
import be.one16.barka.magazijn.domain.Leverancier;

import java.math.BigDecimal;
import java.util.UUID;

import static java.util.UUID.randomUUID;

public final class TestDataBuilder {

    public static ArtikelDto generateTestArtikelDto(UUID leverancierId, String code, String merk, String omschrijving) {
        ArtikelDto artikelDto = new ArtikelDto();

        artikelDto.setMerk(merk);
        artikelDto.setCode(code);
        artikelDto.setOmschrijving(omschrijving);
        artikelDto.setLeverancierId(leverancierId);
        artikelDto.setAantalInStock(100);
        artikelDto.setMinimumInStock(20);
        artikelDto.setAankoopPrijs(BigDecimal.valueOf(5.99));
        artikelDto.setVerkoopPrijs(BigDecimal.valueOf(15.99));
        artikelDto.setActuelePrijs(BigDecimal.valueOf(14.99));

        return artikelDto;
    }

    public static ArtikelJpaEntity generateTestArtikelJpaEntity(LeverancierJpaEntity leverancier, String code, String merk, String omschrijving) {
        ArtikelJpaEntity artikelJpaEntity = new ArtikelJpaEntity();

        artikelJpaEntity.setUuid(randomUUID());
        artikelJpaEntity.setMerk(merk);
        artikelJpaEntity.setCode(code);
        artikelJpaEntity.setOmschrijving(omschrijving);
        artikelJpaEntity.setLeverancier(leverancier);
        artikelJpaEntity.setAantalInStock(50);
        artikelJpaEntity.setMinimumInStock(10);
        artikelJpaEntity.setAankoopPrijs(BigDecimal.valueOf(6.99));
        artikelJpaEntity.setVerkoopPrijs(BigDecimal.valueOf(16.99));
        artikelJpaEntity.setActuelePrijs(BigDecimal.valueOf(15.99));

        return artikelJpaEntity;
    }

    public static Artikel generateTestArtikel(UUID leverancierId, String code, String merk, String omschrijving) {
        return Artikel.builder()
                .artikelId(UUID.randomUUID())
                .merk(merk)
                .code(code)
                .omschrijving(omschrijving)
                .leverancier(Leverancier.builder().leverancierId(leverancierId).build())
                .aantalInStock(5)
                .minimumInStock(2)
                .aankoopPrijs(BigDecimal.valueOf(1.99))
                .verkoopPrijs(BigDecimal.valueOf(3.99))
                .actuelePrijs(BigDecimal.valueOf(2.99))
                .build();
    }

    public static LeverancierJpaEntity generateTestLeverancierJpaEntity(String naam) {
        LeverancierJpaEntity leverancierJpaEntity = new LeverancierJpaEntity();

        leverancierJpaEntity.setUuid(randomUUID());
        leverancierJpaEntity.setNaam(naam);

        return leverancierJpaEntity;
    }
}