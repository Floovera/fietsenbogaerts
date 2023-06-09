package be.one16.barka.magazijn.ports.in;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateArtikelCommandTest {

    @Test
    public void testCreateArtikelCommandValidWithoutAankoopPrijs() {
        CreateArtikelCommand createArtikelCommand = new CreateArtikelCommand(
                "ABUS URBAN-I 3.0 FIETSHELM",
                "AUI30F",
                "ABUS URBAN-I 3.0 FIETSHELM - Zwart - 52-58cm",
                UUID.randomUUID(),
                5,
                3,
                BigDecimal.valueOf(40.50),
                BigDecimal.valueOf(89.99),
                null);

        assertEquals(createArtikelCommand.verkoopPrijs(), createArtikelCommand.actuelePrijs());
    }

    @Test
    public void testCreateArtikelCommandInvalid() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new CreateArtikelCommand(
                "",
                "AUI30F",
                "ABUS URBAN-I 3.0 FIETSHELM - Zwart - 52-58cm",
                UUID.randomUUID(),
                5,
                3,
                BigDecimal.valueOf(40.50),
                BigDecimal.valueOf(89.99),
                BigDecimal.valueOf(79.99)));

        Assertions.assertThrows(IllegalArgumentException.class, () -> new CreateArtikelCommand(
                "ABUS URBAN-I 3.0 FIETSHELM",
                null,
                "ABUS URBAN-I 3.0 FIETSHELM - Zwart - 52-58cm",
                UUID.randomUUID(),
                5,
                3,
                BigDecimal.valueOf(40.50),
                BigDecimal.valueOf(89.99),
                BigDecimal.valueOf(79.99)));

        Assertions.assertThrows(IllegalArgumentException.class, () -> new CreateArtikelCommand(
                "ABUS URBAN-I 3.0 FIETSHELM",
                "AUI30F",
                "",
                UUID.randomUUID(),
                5,
                3,
                BigDecimal.valueOf(40.50),
                BigDecimal.valueOf(89.99),
                BigDecimal.valueOf(79.99)));

        Assertions.assertThrows(IllegalArgumentException.class, () -> new CreateArtikelCommand(
                "ABUS URBAN-I 3.0 FIETSHELM",
                "AUI30F",
                "ABUS URBAN-I 3.0 FIETSHELM - Zwart - 52-58cm",
                null,
                5,
                3,
                BigDecimal.valueOf(40.50),
                BigDecimal.valueOf(89.99),
                BigDecimal.valueOf(79.99)));

        Assertions.assertThrows(IllegalArgumentException.class, () -> new CreateArtikelCommand(
                "ABUS URBAN-I 3.0 FIETSHELM",
                "AUI30F",
                "ABUS URBAN-I 3.0 FIETSHELM - Zwart - 52-58cm",
                UUID.randomUUID(),
                -5,
                3,
                BigDecimal.valueOf(40.50),
                BigDecimal.valueOf(89.99),
                BigDecimal.valueOf(79.99)));

        Assertions.assertThrows(IllegalArgumentException.class, () -> new CreateArtikelCommand(
                "ABUS URBAN-I 3.0 FIETSHELM",
                "AUI30F",
                "ABUS URBAN-I 3.0 FIETSHELM - Zwart - 52-58cm",
                UUID.randomUUID(),
                5,
                -3,
                BigDecimal.valueOf(40.50),
                BigDecimal.valueOf(89.99),
                BigDecimal.valueOf(79.99)));

        Assertions.assertThrows(IllegalArgumentException.class, () -> new CreateArtikelCommand(
                "ABUS URBAN-I 3.0 FIETSHELM",
                "AUI30F",
                "ABUS URBAN-I 3.0 FIETSHELM - Zwart - 52-58cm",
                UUID.randomUUID(),
                5,
                3,
                BigDecimal.valueOf(40.50),
                null,
                BigDecimal.valueOf(79.99)));

        Assertions.assertThrows(IllegalArgumentException.class, () -> new CreateArtikelCommand(
                "ABUS URBAN-I 3.0 FIETSHELM",
                "AUI30F",
                "ABUS URBAN-I 3.0 FIETSHELM - Zwart - 52-58cm",
                UUID.randomUUID(),
                5,
                3,
                BigDecimal.valueOf(40.50),
                BigDecimal.valueOf(-89.99),
                BigDecimal.valueOf(79.99)));

        Assertions.assertThrows(IllegalArgumentException.class, () -> new CreateArtikelCommand(
                "ABUS URBAN-I 3.0 FIETSHELM",
                "AUI30F",
                "ABUS URBAN-I 3.0 FIETSHELM - Zwart - 52-58cm",
                UUID.randomUUID(),
                5,
                3,
                BigDecimal.valueOf(-40.50),
                BigDecimal.valueOf(89.99),
                BigDecimal.valueOf(79.99)));

        Assertions.assertThrows(IllegalArgumentException.class, () -> new CreateArtikelCommand(
                "ABUS URBAN-I 3.0 FIETSHELM",
                "AUI30F",
                "ABUS URBAN-I 3.0 FIETSHELM - Zwart - 52-58cm",
                UUID.randomUUID(),
                5,
                3,
                BigDecimal.valueOf(40.50),
                BigDecimal.valueOf(89.99),
                BigDecimal.valueOf(-79.99)));
    }

}
