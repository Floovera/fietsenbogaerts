package be.one16.barka.leverancier.adapter.mapper;

import be.one16.barka.leverancier.adapter.in.LeverancierDto;
import be.one16.barka.leverancier.domain.Leverancier;
import be.one16.barka.leverancier.ports.in.leverancier.CreateLeverancierCommand;
import be.one16.barka.leverancier.ports.in.leverancier.UpdateLeverancierCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface LeverancierDtoMapper {

        CreateLeverancierCommand mapDtoToCreateLeverancierCommand(LeverancierDto leverancier);
        @Mapping(source = "leverancierId", target = "leverancierId")
        UpdateLeverancierCommand mapDtoToUpdateLeverancierCommand(LeverancierDto leverancier, UUID leverancierId);

        LeverancierDto mapLeverancierToDto(Leverancier leverancier);

}
