package be.one16.barka.leverancier.adapter.mapper;

import be.one16.barka.leverancier.adapter.in.LeverancierDto;
import be.one16.barka.leverancier.common.TestDataBuilder;
import be.one16.barka.leverancier.domain.Leverancier;
import be.one16.barka.leverancier.ports.in.leverancier.CreateLeverancierCommand;
import be.one16.barka.leverancier.ports.in.leverancier.UpdateLeverancierCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class LeverancierDtoMapperTest {

    @InjectMocks
    private LeverancierDtoMapperImpl leverancierDtoMapper;

    @Test
    public void testMapDtoToCreateLeverancierCommand() {
        LeverancierDto leverancierDto = TestDataBuilder.generateTestLeverancierDto("Agu Benelux");

        CreateLeverancierCommand createLeverancierCommand = leverancierDtoMapper.mapDtoToCreateLeverancierCommand(leverancierDto);

        assertEquals(leverancierDto.getNaam(), createLeverancierCommand.naam());
        assertEquals(leverancierDto.getStraat(), createLeverancierCommand.straat());
        assertEquals(leverancierDto.getHuisnummer(), createLeverancierCommand.huisnummer());
        assertEquals(leverancierDto.getBus(), createLeverancierCommand.bus());
        assertEquals(leverancierDto.getPostcode(), createLeverancierCommand.postcode());
        assertEquals(leverancierDto.getGemeente(), createLeverancierCommand.gemeente());
        assertEquals(leverancierDto.getLand(), createLeverancierCommand.land());
        assertEquals(leverancierDto.getTelefoonnummer(), createLeverancierCommand.telefoonnummer());
        assertEquals(leverancierDto.getMobiel(), createLeverancierCommand.mobiel());
        assertEquals(leverancierDto.getFax(), createLeverancierCommand.fax());
        assertEquals(leverancierDto.getEmail(), createLeverancierCommand.email());
        assertEquals(leverancierDto.getBtwNummer(), createLeverancierCommand.btwNummer());
        assertEquals(leverancierDto.getOpmerkingen(), createLeverancierCommand.opmerkingen());
    }

    @Test
    public void testMapDtoToCreateLeverancierCommandInvalidLeverancier() {
        LeverancierDto leverancierDto = TestDataBuilder.generateTestLeverancierDto("Agu Benelux");

        leverancierDto.setNaam(null);
        assertThrows(IllegalArgumentException.class, () -> leverancierDtoMapper.mapDtoToCreateLeverancierCommand(leverancierDto));

        leverancierDto.setNaam("");
        assertThrows(IllegalArgumentException.class, () -> leverancierDtoMapper.mapDtoToCreateLeverancierCommand(leverancierDto));
    }

    @Test
    public void testMapDtoToUpdateLeverancierCommand() {
        LeverancierDto leverancierDto = TestDataBuilder.generateTestLeverancierDto("Agu Benelux");
        UUID leverancierId = UUID.randomUUID();

        UpdateLeverancierCommand updateLeverancierCommand = leverancierDtoMapper.mapDtoToUpdateLeverancierCommand(leverancierDto, leverancierId);

        assertEquals(leverancierId, updateLeverancierCommand.leverancierId());
        assertEquals(leverancierDto.getNaam(), updateLeverancierCommand.naam());
        assertEquals(leverancierDto.getStraat(), updateLeverancierCommand.straat());
        assertEquals(leverancierDto.getHuisnummer(), updateLeverancierCommand.huisnummer());
        assertEquals(leverancierDto.getBus(), updateLeverancierCommand.bus());
        assertEquals(leverancierDto.getPostcode(), updateLeverancierCommand.postcode());
        assertEquals(leverancierDto.getGemeente(), updateLeverancierCommand.gemeente());
        assertEquals(leverancierDto.getLand(), updateLeverancierCommand.land());
        assertEquals(leverancierDto.getTelefoonnummer(), updateLeverancierCommand.telefoonnummer());
        assertEquals(leverancierDto.getMobiel(), updateLeverancierCommand.mobiel());
        assertEquals(leverancierDto.getFax(), updateLeverancierCommand.fax());
        assertEquals(leverancierDto.getEmail(), updateLeverancierCommand.email());
        assertEquals(leverancierDto.getBtwNummer(), updateLeverancierCommand.btwNummer());
        assertEquals(leverancierDto.getOpmerkingen(), updateLeverancierCommand.opmerkingen());
    }

    @Test
    public void testMapDtoToUpdateLeverancierCommandInvalidLeverancier() {
        LeverancierDto leverancierDto = TestDataBuilder.generateTestLeverancierDto("Agu Benelux");
        UUID leverancierId = UUID.randomUUID();

        leverancierDto.setNaam(null);
        assertThrows(IllegalArgumentException.class, () -> leverancierDtoMapper.mapDtoToUpdateLeverancierCommand(leverancierDto, leverancierId));

        leverancierDto.setNaam("");
        assertThrows(IllegalArgumentException.class, () -> leverancierDtoMapper.mapDtoToUpdateLeverancierCommand(leverancierDto, leverancierId));
    }

    @Test
    public void testMapLeverancierToDto() {
        Leverancier leverancier = TestDataBuilder.generateTestLeverancier(UUID.randomUUID(), "Agu Benelux");

        LeverancierDto leverancierDto = leverancierDtoMapper.mapLeverancierToDto(leverancier);

        assertEquals(leverancier.getLeverancierId(), leverancierDto.getLeverancierId());
        assertEquals(leverancier.getNaam(), leverancierDto.getNaam());
        assertEquals(leverancier.getStraat(), leverancierDto.getStraat());
        assertEquals(leverancier.getHuisnummer(), leverancierDto.getHuisnummer());
        assertEquals(leverancier.getBus(), leverancierDto.getBus());
        assertEquals(leverancier.getPostcode(), leverancierDto.getPostcode());
        assertEquals(leverancier.getGemeente(), leverancierDto.getGemeente());
        assertEquals(leverancier.getLand(), leverancierDto.getLand());
        assertEquals(leverancier.getTelefoonnummer(), leverancierDto.getTelefoonnummer());
        assertEquals(leverancier.getMobiel(), leverancierDto.getMobiel());
        assertEquals(leverancier.getFax(), leverancierDto.getFax());
        assertEquals(leverancier.getEmail(), leverancierDto.getEmail());
        assertEquals(leverancier.getBtwNummer(), leverancierDto.getBtwNummer());
        assertEquals(leverancier.getOpmerkingen(), leverancierDto.getOpmerkingen());
    }
}
