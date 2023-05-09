package be.one16.barka.klant.port.in.verkoop;

import org.springframework.data.domain.Pageable;

public record RetrieveVerkoopFilterAndSortCommand(String naam, Pageable pageable) {
}
