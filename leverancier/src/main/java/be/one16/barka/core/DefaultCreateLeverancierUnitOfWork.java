package be.one16.barka.core;

import be.one16.barka.common.UnitOfWork;
import be.one16.barka.ports.in.CreateLeverancierCommand;
import be.one16.barka.ports.in.DeleteLeverancierCommand;
import be.one16.barka.ports.in.CreateLeverancierUnitOfWork;
import be.one16.barka.ports.in.UpdateLeverancierCommand;

@UnitOfWork
public class DefaultCreateLeverancierUnitOfWork implements CreateLeverancierUnitOfWork {

    @Override
    public void createLeverancier(CreateLeverancierCommand createLeverancierCommand) {

    }
}
