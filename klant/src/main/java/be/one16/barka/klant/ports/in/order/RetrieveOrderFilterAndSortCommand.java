package be.one16.barka.klant.ports.in.order;

import org.springframework.data.domain.Pageable;

public record RetrieveOrderFilterAndSortCommand(String naam, Pageable pageable) {
}
