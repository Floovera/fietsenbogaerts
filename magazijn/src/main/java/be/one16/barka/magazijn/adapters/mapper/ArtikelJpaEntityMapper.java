package be.one16.barka.magazijn.adapters.mapper;

import be.one16.barka.magazijn.adapters.out.ArtikelJpaEntity;
import be.one16.barka.magazijn.domain.Artikel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ArtikelJpaEntityMapper {

    @Mapping(source = "uuid", target = "artikelId")
    @Mapping(source = "leverancier.uuid", target = "leverancier.leverancierId")
    Artikel mapJpaEntityToArtikel(ArtikelJpaEntity artikelJpaEntity);

}
