package be.one16.barka.leverancier.ports.in.contact;

import java.util.UUID;

public record DeleteContactCommand (UUID contactId, UUID leverancierId) {
}
