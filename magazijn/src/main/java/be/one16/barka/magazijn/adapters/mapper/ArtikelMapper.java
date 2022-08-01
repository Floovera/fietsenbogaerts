package be.one16.barka.magazijn.adapters.mapper;

import be.one16.barka.magazijn.adapters.in.ArtikelDto;
import be.one16.barka.magazijn.ports.in.CreateArtikelCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArtikelMapper {


    CreateArtikelCommand map(ArtikelDto artikel);
}
