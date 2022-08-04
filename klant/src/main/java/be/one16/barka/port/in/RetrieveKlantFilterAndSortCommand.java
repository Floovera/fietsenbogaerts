package be.one16.barka.port.in;

import org.springframework.data.domain.Pageable;

public record RetrieveKlantFilterAndSortCommand (String naam, Pageable pageable) {
}
