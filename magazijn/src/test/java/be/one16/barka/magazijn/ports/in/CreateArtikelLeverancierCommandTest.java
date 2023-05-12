package be.one16.barka.magazijn.ports.in;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateArtikelLeverancierCommandTest {


    @Test
    public void testCreateArtikelLeverancierInvalid() {

        Assertions.assertThrows(IllegalArgumentException.class, () -> new CreateArtikelLeverancierCommand(
                UUID.randomUUID(),""));
    }

}
