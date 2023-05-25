package be.one16.barka.klant.adapter.mapper.materiaal;

import be.one16.barka.klant.adapter.in.materiaal.MateriaalDto;
import be.one16.barka.klant.domain.Materiaal;
import be.one16.barka.klant.ports.in.materiaal.CreateMateriaalCommand;
import be.one16.barka.klant.ports.in.materiaal.UpdateMateriaalCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface MateriaalDtoMapper {

    @Mapping(source = "verkoopId", target = "verkoopId")
    CreateMateriaalCommand mapDtoToCreateMateriaalCommand(MateriaalDto materiaal, UUID verkoopId);
    @Mapping(source = "materiaalId", target = "materiaalId")
    @Mapping(source = "verkoopId", target = "verkoopId")
    UpdateMateriaalCommand mapDtoToUpdateMateriaalCommand(MateriaalDto materiaal, UUID materiaalId, UUID verkoopId);

    MateriaalDto mapMateriaalToDto(Materiaal materiaal);

}
