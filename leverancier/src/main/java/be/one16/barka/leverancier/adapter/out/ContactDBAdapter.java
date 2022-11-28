package be.one16.barka.leverancier.adapter.out;

import be.one16.barka.domain.exceptions.EntityNotFoundException;
import be.one16.barka.leverancier.adapter.mapper.ContactJpaEntityMapper;
import be.one16.barka.leverancier.adapter.out.repository.ContactRepository;
import be.one16.barka.leverancier.adapter.out.repository.LeverancierRepository;
import be.one16.barka.leverancier.domain.Contact;
import be.one16.barka.leverancier.ports.out.contact.ContactCreatePort;
import be.one16.barka.leverancier.ports.out.contact.ContactDeletePort;
import be.one16.barka.leverancier.ports.out.contact.ContactUpdatePort;
import be.one16.barka.leverancier.ports.out.contact.LoadContactenPort;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ContactDBAdapter implements LoadContactenPort, ContactCreatePort, ContactUpdatePort, ContactDeletePort {

    private final ContactRepository contactRepository;
    private final LeverancierRepository leverancierRepository;

    private final ContactJpaEntityMapper contactJpaEntityMapper;

    public ContactDBAdapter(ContactRepository contactRepository, LeverancierRepository leverancierRepository, ContactJpaEntityMapper contactJpaEntityMapper) {
        this.contactRepository = contactRepository;
        this.leverancierRepository = leverancierRepository;
        this.contactJpaEntityMapper = contactJpaEntityMapper;
    }

    @Override
    public Contact retrieveContactOfLeverancier(UUID id, UUID leverancierId) {
        ContactJpaEntity contactJpaEntity = getContactJpaEntityById(id);

        if (!contactJpaEntity.getLeverancier().getUuid().equals(leverancierId)) {
            throw new IllegalArgumentException(String.format("Contact with uuid %s doesn't belong to the Leverancier with uuid %s", id, leverancierId));
        }

        return contactJpaEntityMapper.mapJpaEntityToContact(contactJpaEntity);
    }

    @Override
    public List<Contact> retrieveContactenOfLeverancier(UUID leverancierId) {
        return contactRepository.findAllByLeverancierUuid(leverancierId).stream().map(contactJpaEntityMapper::mapJpaEntityToContact).toList();
    }

    @Override
    public void createContact(Contact contact) {
        ContactJpaEntity contactJpaEntity = new ContactJpaEntity();

        contactJpaEntity.setUuid(contact.getContactId());
        contactJpaEntity.setLeverancier(getLeverancierJpaEntityById(contact.getLeverancierId()));
        fillJpaEntityWithContactData(contactJpaEntity, contact);

        contactRepository.save(contactJpaEntity);
    }

    @Override
    public void updateContact(Contact contact) {
        ContactJpaEntity contactJpaEntity = getContactJpaEntityById(contact.getContactId());

        if (!contactJpaEntity.getLeverancier().getUuid().equals(contact.getLeverancierId())) {
            throw new IllegalArgumentException(String.format("Contact with uuid %s doesn't belong to the Leverancier with uuid %s", contact.getContactId(), contact.getLeverancierId()));
        }

        fillJpaEntityWithContactData(contactJpaEntity, contact);

        contactRepository.save(contactJpaEntity);
    }

    @Override
    public void deleteContact(UUID contactId, UUID leverancierId) {
        ContactJpaEntity contactJpaEntity = getContactJpaEntityById(contactId);

        if (!contactJpaEntity.getLeverancier().getUuid().equals(leverancierId)) {
            throw new IllegalArgumentException(String.format("Contact with uuid %s doesn't belong to the Leverancier with uuid %s", contactJpaEntity.getUuid(), leverancierId));
        }

        contactRepository.delete(contactJpaEntity);
    }

    private ContactJpaEntity getContactJpaEntityById(UUID id) {
        return contactRepository.findByUuid(id).orElseThrow(() -> new EntityNotFoundException(String.format("Contact with uuid %s doesn't exist", id)));
    }

    private LeverancierJpaEntity getLeverancierJpaEntityById(UUID id) {
        return leverancierRepository.findByUuid(id).orElseThrow(() -> new EntityNotFoundException(String.format("Leverancier with uuid %s doesn't exist", id)));
    }

    private void fillJpaEntityWithContactData(ContactJpaEntity contactJpaEntity, Contact contact) {
        contactJpaEntity.setNaam(contact.getNaam());
        contactJpaEntity.setOnderwerp(contact.getOnderwerp());
        contactJpaEntity.setContactMethode(contact.getContactMethode());
        contactJpaEntity.setGegevens(contact.getGegevens());
    }
}
