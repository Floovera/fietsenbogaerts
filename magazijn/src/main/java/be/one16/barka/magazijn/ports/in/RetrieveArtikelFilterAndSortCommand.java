package be.one16.barka.magazijn.ports.in;

import org.springframework.data.domain.Pageable;

import java.util.UUID;

public record RetrieveArtikelFilterAndSortCommand (String code, String merk, String omschrijving, UUID leverancierId, Pageable pageable){
}
