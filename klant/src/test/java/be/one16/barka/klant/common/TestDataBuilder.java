package be.one16.barka.klant.common;

import be.one16.barka.klant.adapter.in.klant.KlantDto;
import be.one16.barka.klant.adapter.in.order.OrderDto;
import be.one16.barka.klant.adapter.out.klant.KlantJpaEntity;
import be.one16.barka.klant.adapter.out.order.OrderJpaEntity;

import java.time.LocalDate;
import java.util.UUID;

import static be.one16.barka.klant.common.KlantType.ONBEKEND;
import static be.one16.barka.klant.common.KlantType.ZELFSTANDIGE;
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
    public static OrderDto generateTestOrderDto(String naam) {
        OrderDto orderDto = new OrderDto();
        LocalDate date = LocalDate.of(2023, 1, 8);

        orderDto.setOrderType(OrderType.VERKOOP);
        orderDto.setNaam(naam);
        orderDto.setOpmerkingen("Inclusief installatie op stuur");
        orderDto.setDatum(date);

        return orderDto;
    }

    public static OrderDto generateTestOrderWithClientDto(String naam, UUID klant) {
        OrderDto orderDto = new OrderDto();
        LocalDate date = LocalDate.of(2023, 1, 8);

        orderDto.setOrderType(OrderType.VERKOOP);
        orderDto.setNaam(naam);
        orderDto.setOpmerkingen("Inclusief installatie op stuur");
        orderDto.setDatum(date);
        orderDto.setKlantId(klant);

        return orderDto;
    }

    public static OrderJpaEntity generateTestOrderJpaEntity(String naam) {
        OrderJpaEntity orderJpaEntity = new OrderJpaEntity();
        LocalDate date = LocalDate.of(2023, 1, 8);

        orderJpaEntity.setUuid(randomUUID());
        orderJpaEntity.setOrderType(OrderType.VERKOOP);
        orderJpaEntity.setNaam(naam);
        orderJpaEntity.setOpmerkingen("Super kwaliteit");
        orderJpaEntity.setDatum(date);

        return orderJpaEntity;
    }

    public static OrderJpaEntity generateTestOrderWithClientJpaEntity(String naam, UUID klant){
        OrderJpaEntity orderJpaEntity = new OrderJpaEntity();
        LocalDate date = LocalDate.of(2023, 1, 8);

        orderJpaEntity.setUuid(randomUUID());
        orderJpaEntity.setOrderType(OrderType.VERKOOP);
        orderJpaEntity.setNaam(naam);
        orderJpaEntity.setOpmerkingen("Super kwaliteit");
        orderJpaEntity.setDatum(date);
        orderJpaEntity.setKlantId(klant);

        return orderJpaEntity;
    }


}
