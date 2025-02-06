package br.com.fiap.techchallenge.order.infra.config.bean;

import br.com.fiap.techchallenge.order.application.usecase.order.impl.evolve.EvolveToFinished;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EvolveToFinishedConfigTest {

    @InjectMocks
    private EvolveToFinishedConfig evolveToFinishedConfig;

    @Test
    @DisplayName("Should Create a Singleton Instance Of EvolveToFinished")
    void shouldCreateSingletonInstanceOfEvolveToFinished() {
        var evolveToFinished = evolveToFinishedConfig.evolveToFinished();

        assertNotNull(evolveToFinished);
        assertInstanceOf(EvolveToFinished.class, evolveToFinished);
    }
}