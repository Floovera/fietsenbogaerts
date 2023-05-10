package be.one16.barka.klant.adapter.mapper.klant;

import be.one16.barka.klant.adapter.in.klant.KlantDto;
import be.one16.barka.klant.domain.Klant;
import be.one16.barka.klant.port.in.klant.CreateKlantCommand;
import be.one16.barka.klant.port.in.klant.UpdateKlantCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface KlantDtoMapper {

    CreateKlantCommand mapDtoToCreateKlantCommand(KlantDto klant);
    @Mapping(source = "klantId", target = "klantId")
    UpdateKlantCommand mapDtoToUpdateKlantCommand(KlantDto klant, UUID klantId);
    KlantDto mapKlantToDto(Klant klant);

}
