package be.one16.barka.magazijn.adapters.mapper;

import be.one16.barka.magazijn.adapters.in.ArtikelDto;
import be.one16.barka.magazijn.domain.Artikel;
import be.one16.barka.magazijn.ports.in.CreateArtikelCommand;
import be.one16.barka.magazijn.ports.in.UpdateArtikelCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ArtikelDtoMapper {

    CreateArtikelCommand mapDtoToCreateArtikelCommand(ArtikelDto artikel);
    UpdateArtikelCommand mapDtoToUpdateArtikelCommand(ArtikelDto artikel);

    @Mapping(source = "leverancier.leverancierId", target = "leverancierId")
    @Mapping(source = "leverancier.naam", target = "leverancier")
    ArtikelDto mapArtikelToDto(Artikel artikel);
}
