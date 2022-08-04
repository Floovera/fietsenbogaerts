package be.one16.barka.adapter.mapper;

import be.one16.barka.adapter.in.KlantDto;
import be.one16.barka.domain.Klant;
import be.one16.barka.port.in.CreateKlantCommand;
import be.one16.barka.port.in.UpdateKlantCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface KlantDtoMapper {

    CreateKlantCommand mapDtoToCreateKlantCommand(KlantDto klant);
    UpdateKlantCommand mapDtoToUpdateKlantCommand(KlantDto klant);

    KlantDto mapKlantToDto(Klant klant);

}
