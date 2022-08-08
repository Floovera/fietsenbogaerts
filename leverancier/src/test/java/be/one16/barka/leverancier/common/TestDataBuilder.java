package be.one16.barka.leverancier.common;

import be.one16.barka.leverancier.adapter.in.LeverancierDto;
import be.one16.barka.leverancier.adapter.out.LeverancierJpaEntity;

import static java.util.UUID.randomUUID;

public final class TestDataBuilder {

    public static LeverancierDto generateTestLeverancierDto(String naam) {
        LeverancierDto leverancierDto = new LeverancierDto();

        leverancierDto.setNaam(naam);
        leverancierDto.setStraat("Hemelstraat");
        leverancierDto.setHuisnummer("12");
        leverancierDto.setBus("A");
        leverancierDto.setPostcode("3200");
        leverancierDto.setGemeente("Zonnedorp");
        leverancierDto.setLand("BE");
        leverancierDto.setTelefoonnummer("091111111");
        leverancierDto.setMobiel("0411111111");
        leverancierDto.setFax("44-208-1234569");
        leverancierDto.setEmail(naam + "@info.com");
        leverancierDto.setBtwNummer("BE123456789");
        leverancierDto.setOpmerkingen("Levert fietsen");

        return leverancierDto;
    }

    public static LeverancierJpaEntity generateTestLeverancierJpaEntity(String naam) {
        LeverancierJpaEntity leverancierJpaEntity = new LeverancierJpaEntity();

        leverancierJpaEntity.setUuid(randomUUID());
        leverancierJpaEntity.setNaam(naam);
        leverancierJpaEntity.setStraat("Warmoesstraat");
        leverancierJpaEntity.setHuisnummer("66");
        leverancierJpaEntity.setBus("B");
        leverancierJpaEntity.setPostcode("1012 JH");
        leverancierJpaEntity.setGemeente("Amsterdam");
        leverancierJpaEntity.setLand("NL");
        leverancierJpaEntity.setTelefoonnummer("092222222");
        leverancierJpaEntity.setMobiel("0422222222");
        leverancierJpaEntity.setFax("33-208-1234570");
        leverancierJpaEntity.setEmail(naam + "@hotmail.com");
        leverancierJpaEntity.setBtwNummer("NL123456789");
        leverancierJpaEntity.setOpmerkingen("Levert kleding");

        return leverancierJpaEntity;
    }

}
