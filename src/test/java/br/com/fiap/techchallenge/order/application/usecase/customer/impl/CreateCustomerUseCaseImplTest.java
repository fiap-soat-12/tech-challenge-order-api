package br.com.fiap.techchallenge.order.application.usecase.customer.impl;

import br.com.fiap.techchallenge.order.application.exceptions.AlreadyExistsException;
import br.com.fiap.techchallenge.order.domain.models.Customer;
import br.com.fiap.techchallenge.order.infra.gateway.database.repository.impl.CustomerPersistenceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class CreateCustomerUseCaseImplTest {

    @Mock
    private CustomerPersistenceImpl customerPersistence;

    @InjectMocks
    private CreateCustomerUseCaseImpl createCustomerUseCase;

    private Customer customer;

    private Customer customerExpectedCreated;

    @BeforeEach
    void setUp() {
        this.buildArranges();
    }

    @Test
    @DisplayName("Should be able create and return a new Customer")
    void shouldBeAbleCreateAndReturnANewCustomer(){
        when(customerPersistence.findByDocument(customer.getDocument())).thenReturn(Optional.empty());
        when(customerPersistence.create(customer)).thenReturn(customerExpectedCreated);

        var created = createCustomerUseCase.create(customer);

        verify(customerPersistence,times(1)).findByDocument(any());
        verify(customerPersistence,times(1)).create(any());

        assertEquals(customerExpectedCreated.getId(), created.getId());
        assertEquals(customerExpectedCreated.getName(), created.getName());
        assertEquals(customerExpectedCreated.getDocument(), created.getDocument());
        assertEquals(customerExpectedCreated.getEmail(), created.getEmail());
    }

    @Test
    @DisplayName("Should Return AlreadyExistsException when document already exists in database and not create a new Customer")
    void shouldReturnAlreadyExistsExceptionWhenDocumentAlreadyExistsInDatabaseAndNotCreateNewCustomer(){
        when(customerPersistence.findByDocument(customer.getDocument())).thenReturn(Optional.of(customerExpectedCreated));

        assertThrows(AlreadyExistsException.class, () -> {
            createCustomerUseCase.create(customer);
        });

        verify(customerPersistence,times(1)).findByDocument(any());
        verify(customerPersistence,times(0)).create(any());
    }

    private void buildArranges() {
        customer = new Customer("Walter White", "31739380037", "heisenberg@gmail.com");

        customerExpectedCreated = new Customer(UUID.randomUUID(), "Walter White", "31739380037",
                "heisenberg@gmail.com");
    }

}