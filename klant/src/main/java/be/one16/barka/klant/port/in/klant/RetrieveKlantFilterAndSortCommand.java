package be.one16.barka.klant.port.in.klant;

import org.springframework.data.domain.Pageable;

public record RetrieveKlantFilterAndSortCommand (String naam, Pageable pageable) {
}
