package br.com.fiap.techchallenge.order.infra.config.bean;

import br.com.fiap.techchallenge.order.application.persistence.CustomerPersistence;
import br.com.fiap.techchallenge.order.application.usecase.customer.impl.FindCustomerByDocumentUseCaseImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FindCustomerByDocumentUseCaseConfigTest {

    @Mock
    CustomerPersistence persistence;

    @InjectMocks
    private FindCustomerByDocumentUseCaseConfig findCustomerByDocumentUseCaseConfig;

    @Test
    @DisplayName("Should Create a Singleton Instance Of FindCustomerByDocumentUseCaseImpl")
    void shouldCreateSingletonInstanceOfFindCustomerByDocumentUseCaseImpl() {
        var findCustomerByDocumentUseCaseImpl = findCustomerByDocumentUseCaseConfig
                .findCustomerByDocumentUseCaseImpl(persistence);

        assertNotNull(findCustomerByDocumentUseCaseImpl);
        assertNotNull(persistence);
        assertInstanceOf(FindCustomerByDocumentUseCaseImpl.class, findCustomerByDocumentUseCaseImpl);
    }

}