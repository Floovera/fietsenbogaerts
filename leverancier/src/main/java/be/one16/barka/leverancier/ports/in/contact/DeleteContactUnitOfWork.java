package be.one16.barka.leverancier.ports.in.contact;

import org.springframework.transaction.annotation.Transactional;

public interface DeleteContactUnitOfWork {

    @Transactional
    void deleteContact(DeleteContactCommand deleteContactCommand);

}
