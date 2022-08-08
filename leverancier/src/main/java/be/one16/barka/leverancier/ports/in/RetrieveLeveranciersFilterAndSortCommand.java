package be.one16.barka.leverancier.ports.in;

import org.springframework.data.domain.Pageable;

public record RetrieveLeveranciersFilterAndSortCommand (String naam, Pageable pageable) {

}
