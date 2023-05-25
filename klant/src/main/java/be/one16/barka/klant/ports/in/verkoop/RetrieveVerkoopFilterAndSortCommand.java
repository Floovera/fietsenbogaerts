package be.one16.barka.klant.ports.in.verkoop;

import org.springframework.data.domain.Pageable;

public record RetrieveVerkoopFilterAndSortCommand(String naam, Pageable pageable) {
}
