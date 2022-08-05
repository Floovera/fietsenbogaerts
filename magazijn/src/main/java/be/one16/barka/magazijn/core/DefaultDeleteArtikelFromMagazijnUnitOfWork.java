package be.one16.barka.magazijn.core;

import be.one16.barka.magazijn.ports.in.DeleteArtikelFromMagazijnUnitOfWork;
import be.one16.barka.magazijn.ports.out.ArtikelDeletePort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DefaultDeleteArtikelFromMagazijnUnitOfWork implements DeleteArtikelFromMagazijnUnitOfWork {

    private final List<ArtikelDeletePort> artikelDeletePorts;

    public DefaultDeleteArtikelFromMagazijnUnitOfWork(List<ArtikelDeletePort> artikelDeletePorts) {
        this.artikelDeletePorts = artikelDeletePorts;
    }

    @Override
    public void deleteArtikelFromMagazijn(UUID id) {
        artikelDeletePorts.forEach(port -> port.deleteArtikel(id));
    }
}
