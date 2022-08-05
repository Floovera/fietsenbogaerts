package be.one16.barka.magazijn.adapters.mapper;

import be.one16.barka.magazijn.adapters.out.LeverancierJpaEntity;
import be.one16.barka.magazijn.domain.Leverancier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LeverancierJpaEntityMapper {

    @Mapping(source = "uuid", target = "leverancierId")
    Leverancier mapJpaEntityToLeverancier(LeverancierJpaEntity leverancierJpaEntity);

}
