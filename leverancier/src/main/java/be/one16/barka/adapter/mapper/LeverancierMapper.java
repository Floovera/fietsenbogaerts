package be.one16.barka.adapter.mapper;

import be.one16.barka.adapter.in.LeverancierDto;
import be.one16.barka.ports.in.CreateLeverancierCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LeverancierMapper {
        CreateLeverancierCommand map(LeverancierDto leverancier);
}
