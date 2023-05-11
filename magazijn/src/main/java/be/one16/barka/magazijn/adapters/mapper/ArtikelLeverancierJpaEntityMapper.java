package be.one16.barka.magazijn.adapters.mapper;

import be.one16.barka.domain.events.leverancier.LeverancierMessage;
import be.one16.barka.magazijn.adapters.out.ArtikelLeverancierJpaEntity;
import be.one16.barka.magazijn.domain.Leverancier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ArtikelLeverancierJpaEntityMapper {

    @Mapping(source = "uuid", target = "leverancierId")
    Leverancier mapJpaEntityToLeverancier(ArtikelLeverancierJpaEntity artikelLeverancierJpaEntity);
    Leverancier mapMessageToLeverancier(LeverancierMessage leverancierMessage);

    ArtikelLeverancierJpaEntity mapArtikelLeverancierToJpaEntity(Leverancier leverancier);
}
