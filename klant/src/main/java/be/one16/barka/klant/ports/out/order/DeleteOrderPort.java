package be.one16.barka.klant.ports.out.order;

import java.util.UUID;

public interface DeleteOrderPort {

    void deleteOrder(UUID id);

}
