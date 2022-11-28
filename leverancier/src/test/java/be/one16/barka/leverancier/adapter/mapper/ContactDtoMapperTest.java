package be.one16.barka.leverancier.adapter.mapper;

import be.one16.barka.leverancier.adapter.in.ContactDto;
import be.one16.barka.leverancier.common.ContactMethode;
import be.one16.barka.leverancier.common.TestDataBuilder;
import be.one16.barka.leverancier.domain.Contact;
import be.one16.barka.leverancier.ports.in.contact.CreateContactCommand;
import be.one16.barka.leverancier.ports.in.contact.UpdateContactCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ContactDtoMapperTest {

    @InjectMocks
    private ContactDtoMapperImpl contactDtoMapper;

    @Test
    public void testMapDtoToCreateContactCommand() {
        ContactDto contactDto = TestDataBuilder.generateTestContactDto("Iljo Keise", "Tester", ContactMethode.EMAIL, "iljo.keise@koga.com");
        UUID leverancierId = UUID.randomUUID();

        CreateContactCommand createContactCommand = contactDtoMapper.mapDtoToCreateContactCommand(contactDto, leverancierId);

        assertEquals(contactDto.getNaam(), createContactCommand.naam());
        assertEquals(contactDto.getOnderwerp(), createContactCommand.onderwerp());
        assertEquals(contactDto.getContactMethode(), createContactCommand.contactMethode());
        assertEquals(contactDto.getGegevens(), createContactCommand.gegevens());
        assertEquals(leverancierId, createContactCommand.leverancierId());
    }

    @Test
    public void testMapDtoToCreateContactCommandInvalidContact() {
        ContactDto contactDto = TestDataBuilder.generateTestContactDto("Iljo Keise", "Tester", ContactMethode.EMAIL, "iljo.keise@koga.com");
        UUID leverancierId = UUID.randomUUID();

        contactDto.setNaam(null);
        assertThrows(IllegalArgumentException.class, () -> contactDtoMapper.mapDtoToCreateContactCommand(contactDto, leverancierId));

        contactDto.setNaam("");
        assertThrows(IllegalArgumentException.class, () -> contactDtoMapper.mapDtoToCreateContactCommand(contactDto, leverancierId));

        contactDto.setNaam("Iljo Keise");
        contactDto.setContactMethode(null);
        assertThrows(IllegalArgumentException.class, () -> contactDtoMapper.mapDtoToCreateContactCommand(contactDto, leverancierId));

        contactDto.setContactMethode(ContactMethode.EMAIL);
        contactDto.setGegevens(null);
        assertThrows(IllegalArgumentException.class, () -> contactDtoMapper.mapDtoToCreateContactCommand(contactDto, leverancierId));

        contactDto.setGegevens("");
        assertThrows(IllegalArgumentException.class, () -> contactDtoMapper.mapDtoToCreateContactCommand(contactDto, leverancierId));
    }

    @Test
    public void testMapDtoToUpdateContactCommand() {
        ContactDto contactDto = TestDataBuilder.generateTestContactDto("Iljo Keise", "Tester", ContactMethode.EMAIL, "iljo.keise@koga.com");
        UUID contactId = UUID.randomUUID();
        UUID leverancierId = UUID.randomUUID();

        UpdateContactCommand updateContactCommand = contactDtoMapper.mapDtoToUpdateContactCommand(contactDto, contactId, leverancierId);

        assertEquals(contactId, updateContactCommand.contactId());
        assertEquals(contactDto.getNaam(), updateContactCommand.naam());
        assertEquals(contactDto.getOnderwerp(), updateContactCommand.onderwerp());
        assertEquals(contactDto.getContactMethode(), updateContactCommand.contactMethode());
        assertEquals(contactDto.getGegevens(), updateContactCommand.gegevens());
        assertEquals(leverancierId, updateContactCommand.leverancierId());
    }

    @Test
    public void testMapDtoToUpdateContactCommandInvalidContact() {
        ContactDto contactDto = TestDataBuilder.generateTestContactDto("Iljo Keise", "Tester", ContactMethode.EMAIL, "iljo.keise@koga.com");
        UUID contactId = UUID.randomUUID();
        UUID leverancierId = UUID.randomUUID();

        contactDto.setNaam(null);
        assertThrows(IllegalArgumentException.class, () -> contactDtoMapper.mapDtoToUpdateContactCommand(contactDto, contactId, leverancierId));

        contactDto.setNaam("");
        assertThrows(IllegalArgumentException.class, () -> contactDtoMapper.mapDtoToUpdateContactCommand(contactDto, contactId, leverancierId));

        contactDto.setNaam("Iljo Keise");
        contactDto.setContactMethode(null);
        assertThrows(IllegalArgumentException.class, () -> contactDtoMapper.mapDtoToUpdateContactCommand(contactDto, contactId, leverancierId));

        contactDto.setContactMethode(ContactMethode.EMAIL);
        contactDto.setGegevens(null);
        assertThrows(IllegalArgumentException.class, () -> contactDtoMapper.mapDtoToUpdateContactCommand(contactDto, contactId, leverancierId));

        contactDto.setGegevens("");
        assertThrows(IllegalArgumentException.class, () -> contactDtoMapper.mapDtoToUpdateContactCommand(contactDto, contactId, leverancierId));
    }

    @Test
    public void testMapContactToDto() {
        Contact contact = TestDataBuilder.generateTestContact(UUID.randomUUID(), "Iljo Keise", "Tester", ContactMethode.EMAIL, "iljo.keise@koga.com", UUID.randomUUID());

        ContactDto contactDto = contactDtoMapper.mapContactToDto(contact);

        assertEquals(contact.getContactId(), contactDto.getContactId());
        assertEquals(contact.getNaam(), contactDto.getNaam());
        assertEquals(contact.getOnderwerp(), contactDto.getOnderwerp());
        assertEquals(contact.getContactMethode(), contactDto.getContactMethode());
        assertEquals(contact.getGegevens(), contactDto.getGegevens());
    }

}