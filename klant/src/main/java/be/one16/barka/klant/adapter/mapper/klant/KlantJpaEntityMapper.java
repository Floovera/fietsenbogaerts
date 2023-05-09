package be.one16.barka.klant.adapter.mapper.klant;

import be.one16.barka.klant.adapter.out.klant.KlantJpaEntity;
import be.one16.barka.klant.domain.Klant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface KlantJpaEntityMapper {

    @Mapping(source = "uuid", target = "klantId")
    Klant mapJpaEntityToKlant(KlantJpaEntity klantJpaEntity);

}
