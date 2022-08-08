package be.one16.barka.leverancier.adapter.mapper;

import be.one16.barka.leverancier.adapter.out.LeverancierJpaEntity;
import be.one16.barka.leverancier.domain.Leverancier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LeverancierJpaEntityMapper {

    @Mapping(source = "uuid", target = "leverancierId")
    Leverancier mapJpaEntityToLeverancier(LeverancierJpaEntity leverancierJpaEntity);

}
