package be.one16.barka.klant.ports.out.order;

import be.one16.barka.klant.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface LoadOrdersPort {

    Page<Order> retrieveOrdersByFilterAndSort(String naam, Pageable pageable);

    Order retrieveOrderById(UUID id);


}
