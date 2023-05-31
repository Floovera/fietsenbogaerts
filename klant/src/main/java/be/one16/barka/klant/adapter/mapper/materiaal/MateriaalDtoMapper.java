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

    @Mapping(source = "orderId", target = "orderId")
    CreateMateriaalCommand mapDtoToCreateMateriaalCommand(MateriaalDto materiaal, UUID orderId);
    @Mapping(source = "materiaalId", target = "materiaalId")
    @Mapping(source = "orderId", target = "orderId")
    UpdateMateriaalCommand mapDtoToUpdateMateriaalCommand(MateriaalDto materiaal, UUID materiaalId, UUID orderId);

    MateriaalDto mapMateriaalToDto(Materiaal materiaal);

}
