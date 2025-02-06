package br.com.fiap.techchallenge.order.infra.config.bean;

import br.com.fiap.techchallenge.order.application.usecase.order.impl.evolve.EvolveToReady;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EvolveToReadyConfigTest {

    @InjectMocks
    private EvolveToReadyConfig evolveToReadyConfig;

    @Test
    @DisplayName("Should Create a Singleton Instance Of EvolveToReady")
    void shouldCreateSingletonInstanceOfEvolveToReady() {
        var evolveToReady = evolveToReadyConfig.evolveToReady();

        assertNotNull(evolveToReady);
        assertInstanceOf(EvolveToReady.class, evolveToReady);
    }
}