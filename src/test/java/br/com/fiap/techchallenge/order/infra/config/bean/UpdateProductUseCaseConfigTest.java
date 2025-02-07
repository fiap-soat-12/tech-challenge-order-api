package br.com.fiap.techchallenge.order.infra.config.bean;

import br.com.fiap.techchallenge.order.application.persistence.ProductPersistence;
import br.com.fiap.techchallenge.order.application.usecase.product.impl.UpdateProductUseCaseImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UpdateProductUseCaseConfigTest {

    @Mock
    ProductPersistence persistence;

    @InjectMocks
    private UpdateProductUseCaseConfig updateProductUseCaseConfig;

    @Test
    @DisplayName("Should Create a Singleton Instance Of UpdateProductUseCaseImpl")
    void shouldCreateSingletonInstanceOfUpdateProductUseCaseImpl() {
        var updateProductUseCaseImpl = updateProductUseCaseConfig.updateProductUseCaseImpl(persistence);

        assertNotNull(updateProductUseCaseImpl);
        assertNotNull(persistence);
        assertInstanceOf(UpdateProductUseCaseImpl.class, updateProductUseCaseImpl);
    }

}