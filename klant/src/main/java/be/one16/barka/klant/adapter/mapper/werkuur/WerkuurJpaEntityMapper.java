package be.one16.barka.klant.adapter.mapper.werkuur;

import be.one16.barka.klant.adapter.out.werkuur.WerkuurJpaEntity;
import be.one16.barka.klant.domain.Werkuur;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WerkuurJpaEntityMapper {

    @Mapping(source = "uuid", target = "werkuurId")
    @Mapping(source = "verkoopuuid", target = "verkoopId")
    Werkuur mapJpaEntityToWerkuur(WerkuurJpaEntity werkuurJpaEntity);

}
