package be.one16.barka.klant.adapter.mapper.werkuur;

import be.one16.barka.klant.adapter.in.werkuur.WerkuurDto;
import be.one16.barka.klant.domain.Werkuur;
import be.one16.barka.klant.ports.in.werkuur.CreateWerkuurCommand;
import be.one16.barka.klant.ports.in.werkuur.UpdateWerkuurCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface WerkuurDtoMapper {

    @Mapping(source = "verkoopId", target = "verkoopId")
    CreateWerkuurCommand mapDtoToCreateWerkuurCommand(WerkuurDto werkuurDto, UUID verkoopId);
    @Mapping(source = "werkuurId", target = "werkuurId")
    @Mapping(source = "verkoopId", target = "verkoopId")
    UpdateWerkuurCommand mapDtoToUpdateWerkuurCommand(WerkuurDto werkuurDto, UUID werkuurId, UUID verkoopId);

    WerkuurDto mapWerkuurToDto(Werkuur werkuur);

}
