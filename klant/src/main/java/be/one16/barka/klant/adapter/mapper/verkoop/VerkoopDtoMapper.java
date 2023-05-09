package be.one16.barka.klant.adapter.mapper.verkoop;


import be.one16.barka.klant.adapter.in.verkoop.VerkoopDto;
import be.one16.barka.klant.domain.Verkoop;
import be.one16.barka.klant.port.in.verkoop.CreateVerkoopCommand;
import be.one16.barka.klant.port.in.verkoop.UpdateVerkoopCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface VerkoopDtoMapper {

    CreateVerkoopCommand mapDtoToCreateVerkoopCommand(VerkoopDto verkoop);
    @Mapping(source = "verkoopId", target = "verkoopId")
    UpdateVerkoopCommand mapDtoToUpdateVerkoopCommand(VerkoopDto verkoop, UUID verkoopId);

    VerkoopDto mapVerkoopToDto(Verkoop verkoop);

}
