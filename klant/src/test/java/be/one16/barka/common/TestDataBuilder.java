package be.one16.barka.common;

import be.one16.barka.adapter.in.KlantDto;
import be.one16.barka.adapter.out.KlantJpaEntity;

import static be.one16.barka.common.KlantType.ONBEKEND;
import static be.one16.barka.common.KlantType.ZELFSTANDIGE;
import static java.util.UUID.randomUUID;

public final class TestDataBuilder {

    public static KlantDto generateTestKlantDto(String naam) {
        KlantDto klantDto = new KlantDto();

        klantDto.setNaam(naam);
        klantDto.setKlantType(ZELFSTANDIGE);
        klantDto.setStraat("Hemelstraat");
        klantDto.setHuisnummer("12");
        klantDto.setBus("A");
        klantDto.setPostcode("3200");
        klantDto.setGemeente("Zonnedorp");
        klantDto.setLand("BE");
        klantDto.setTelefoonnummer("091111111");
        klantDto.setMobiel("0411111111");
        klantDto.setEmail(naam + "@hotmail.com");
        klantDto.setBtwNummer("BE123456789");
        klantDto.setOpmerkingen("Betaalt snel");

        return klantDto;
    }

    public static KlantJpaEntity generateTestKlantJpaEntity(String naam) {
        KlantJpaEntity klantJpaEntity = new KlantJpaEntity();

        klantJpaEntity.setUuid(randomUUID());
        klantJpaEntity.setNaam(naam);
        klantJpaEntity.setKlantType(ONBEKEND);
        klantJpaEntity.setStraat("Warmoesstraat");
        klantJpaEntity.setHuisnummer("66");
        klantJpaEntity.setBus("B");
        klantJpaEntity.setPostcode("1012 JH");
        klantJpaEntity.setGemeente("Amsterdam");
        klantJpaEntity.setLand("NL");
        klantJpaEntity.setTelefoonnummer("092222222");
        klantJpaEntity.setMobiel("0422222222");
        klantJpaEntity.setEmail(naam + "@hotmail.com");
        klantJpaEntity.setBtwNummer("NL123456789");
        klantJpaEntity.setOpmerkingen("Betaalt niet graag");

        return klantJpaEntity;
    }

}
