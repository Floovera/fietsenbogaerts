package be.one16.barka.leverancier.adapter.out;

import be.one16.barka.leverancier.domain.Leverancier;
import be.one16.barka.leverancier.ports.out.leverancier.LeverancierCreatePort;
import org.springframework.stereotype.Component;

@Component
public class LogAdapter implements LeverancierCreatePort {
    @Override
    public void createLeverancier(Leverancier leverancier) {

        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!! Gelogged: " + leverancier.getNaam());
    }
}

