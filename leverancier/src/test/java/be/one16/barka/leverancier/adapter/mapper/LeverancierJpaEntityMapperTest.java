package be.one16.barka.leverancier.adapter.mapper;

import be.one16.barka.leverancier.adapter.out.LeverancierJpaEntity;
import be.one16.barka.leverancier.common.TestDataBuilder;
import be.one16.barka.leverancier.domain.Leverancier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class LeverancierJpaEntityMapperTest {

    @InjectMocks
    private LeverancierJpaEntityMapperImpl leverancierJpaEntityMapper;

    @Test
    public void testMapJpaEntityToLeverancier() {
        LeverancierJpaEntity leverancierJpaEntity = TestDataBuilder.generateTestLeverancierJpaEntity("AGU Europe");

        Leverancier leverancier = leverancierJpaEntityMapper.mapJpaEntityToLeverancier(leverancierJpaEntity);

        assertEquals(leverancierJpaEntity.getUuid(), leverancier.getLeverancierId());
        assertEquals(leverancierJpaEntity.getNaam(), leverancier.getNaam());
        assertEquals(leverancierJpaEntity.getStraat(), leverancier.getStraat());
        assertEquals(leverancierJpaEntity.getHuisnummer(), leverancier.getHuisnummer());
        assertEquals(leverancierJpaEntity.getBus(), leverancier.getBus());
        assertEquals(leverancierJpaEntity.getPostcode(), leverancier.getPostcode());
        assertEquals(leverancierJpaEntity.getGemeente(), leverancier.getGemeente());
        assertEquals(leverancierJpaEntity.getLand(), leverancier.getLand());
        assertEquals(leverancierJpaEntity.getTelefoonnummer(), leverancier.getTelefoonnummer());
        assertEquals(leverancierJpaEntity.getMobiel(), leverancier.getMobiel());
        assertEquals(leverancierJpaEntity.getFax(), leverancier.getFax());
        assertEquals(leverancierJpaEntity.getEmail(), leverancier.getEmail());
        assertEquals(leverancierJpaEntity.getBtwNummer(), leverancier.getBtwNummer());
        assertEquals(leverancierJpaEntity.getOpmerkingen(), leverancier.getOpmerkingen());
    }
}
