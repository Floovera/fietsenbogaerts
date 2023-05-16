package be.one16.barka.klant.adapter.mapper.verkoop;

import be.one16.barka.klant.adapter.out.klant.KlantJpaEntity;
import be.one16.barka.klant.adapter.out.verkoop.VerkoopJpaEntity;
import be.one16.barka.klant.domain.Klant;
import be.one16.barka.klant.domain.Verkoop;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VerkoopJpaEntityMapper {

    @Mapping(source = "uuid", target = "verkoopId")
    Verkoop mapJpaEntityToVerkoop(VerkoopJpaEntity verkoopJpaEntity);

}
