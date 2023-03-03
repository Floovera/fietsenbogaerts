package be.one16.barka.leverancier.common;

import be.one16.barka.leverancier.adapter.in.ContactDto;
import be.one16.barka.leverancier.adapter.in.LeverancierDto;
import be.one16.barka.leverancier.adapter.out.ContactJpaEntity;
import be.one16.barka.leverancier.adapter.out.LeverancierJpaEntity;
import be.one16.barka.leverancier.domain.Contact;
import be.one16.barka.leverancier.domain.Leverancier;

import java.util.UUID;

import static java.util.UUID.randomUUID;

public final class TestDataBuilder {

    public static Leverancier generateTestLeverancier(UUID leverancierId, String naam) {
        return Leverancier.builder()
                .leverancierId(leverancierId)
                .naam(naam)
                .straat("Hemelstraat")
                .huisnummer("12")
                .bus("A")
                .postcode("3200")
                .gemeente("Zonnedorp")
                .land("BE")
                .telefoonnummer("091111111")
                .mobiel("0411111111")
                .fax("44-208-1234569")
                .email(naam + "@info.com")
                .btwNummer("BE123456789")
                .opmerkingen("Levert fietsen")
                .build();
    }

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

    public static Contact generateTestContact(UUID contactId, String naam, String onderwerp, ContactMethode contactMethode, String gegevens, UUID leverancierId) {
        return Contact.builder()
                .contactId(contactId)
                .naam(naam)
                .onderwerp(onderwerp)
                .contactMethode(contactMethode)
                .gegevens(gegevens)
                .leverancierId(leverancierId)
                .build();
    }

    public static ContactDto generateTestContactDto(String naam, String onderwerp, ContactMethode contactMethode, String gegevens) {
        ContactDto contactDto = new ContactDto();

        contactDto.setNaam(naam);
        contactDto.setOnderwerp(onderwerp);
        contactDto.setContactMethode(contactMethode);
        contactDto.setGegevens(gegevens);

        return contactDto;
    }

    public static ContactJpaEntity generateTestContactJpaEntity(String naam, String onderwerp, ContactMethode contactMethode, String gegevens, LeverancierJpaEntity leverancier) {
        ContactJpaEntity contactJpaEntity = new ContactJpaEntity();

        contactJpaEntity.setUuid(randomUUID());
        contactJpaEntity.setNaam(naam);
        contactJpaEntity.setOnderwerp(onderwerp);
        contactJpaEntity.setContactMethode(contactMethode);
        contactJpaEntity.setGegevens(gegevens);
        contactJpaEntity.setLeverancier(leverancier);

        return contactJpaEntity;
    }

}
