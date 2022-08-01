package be.one16.barka.ports.in;

public interface DeleteLeverancierUnitOfWork {
    void deleteLeverancier(DeleteLeverancierCommand deleteLeverancierCommand);
}
