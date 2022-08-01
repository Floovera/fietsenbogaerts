package be.one16.barka.magazijn.adapters.in;

import be.one16.barka.magazijn.adapters.mapper.ArtikelMapper;
import be.one16.barka.magazijn.ports.in.CreateArtikelToMagazijnUnitOfWork;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class ArtikelController {
    private CreateArtikelToMagazijnUnitOfWork createArtikelToMagazijnUnitOfWork;
    private ArtikelMapper artikelMapper;

    public ArtikelController(CreateArtikelToMagazijnUnitOfWork createArtikelToMagazijnUnitOfWork, ArtikelMapper artikelMapper) {
        this.createArtikelToMagazijnUnitOfWork = createArtikelToMagazijnUnitOfWork;
        this.artikelMapper = artikelMapper;
    }

    @PostMapping("/artikels")
    UUID createArtikelInMagazijn(@RequestBody ArtikelDto artikel) {
        return createArtikelToMagazijnUnitOfWork.createArtikelInMagazijn(artikelMapper.map(artikel));

    }







}
