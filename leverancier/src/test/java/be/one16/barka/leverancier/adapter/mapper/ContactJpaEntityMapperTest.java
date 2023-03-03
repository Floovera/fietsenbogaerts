package be.one16.barka.leverancier.adapter.mapper;

import be.one16.barka.leverancier.adapter.out.ContactJpaEntity;
import be.one16.barka.leverancier.adapter.out.LeverancierJpaEntity;
import be.one16.barka.leverancier.common.TestDataBuilder;
import be.one16.barka.leverancier.domain.Contact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static be.one16.barka.leverancier.common.ContactMethode.NUMMER;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ContactJpaEntityMapperTest {

    @InjectMocks
    private ContactJpaEntityMapperImpl contactJpaEntityMapper;

    @Test
    public void testMapJpaEntityToContact() {
        LeverancierJpaEntity leverancierJpaEntity = TestDataBuilder.generateTestLeverancierJpaEntity("AGU Europe");
        ContactJpaEntity contactJpaEntity = TestDataBuilder.generateTestContactJpaEntity("Iljo Keise", "Tester", NUMMER, "0422558899", leverancierJpaEntity);

        Contact contact = contactJpaEntityMapper.mapJpaEntityToContact(contactJpaEntity);

        assertEquals(contactJpaEntity.getUuid(), contact.getContactId());
        assertEquals(contactJpaEntity.getNaam(), contact.getNaam());
        assertEquals(contactJpaEntity.getOnderwerp(), contact.getOnderwerp());
        assertEquals(contactJpaEntity.getContactMethode(), contact.getContactMethode());
        assertEquals(contactJpaEntity.getGegevens(), contact.getGegevens());
        assertEquals(contactJpaEntity.getLeverancier().getUuid(), contact.getLeverancierId());
    }
}
