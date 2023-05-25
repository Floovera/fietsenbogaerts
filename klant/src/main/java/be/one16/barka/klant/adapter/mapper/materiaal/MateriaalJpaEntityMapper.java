package be.one16.barka.klant.adapter.mapper.materiaal;
import be.one16.barka.klant.adapter.out.materiaal.MateriaalJpaEntity;
import be.one16.barka.klant.domain.Materiaal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MateriaalJpaEntityMapper {

    @Mapping(source = "uuid", target = "materiaalId")
    @Mapping(source = "verkoopuuid", target = "verkoopId")
    Materiaal mapJpaEntityToMateriaal(MateriaalJpaEntity materiaalJpaEntity);

}
