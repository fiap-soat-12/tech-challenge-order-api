package br.com.fiap.techchallenge.order.infra.gateway.database.repository.impl;

import br.com.fiap.techchallenge.order.domain.models.Customer;
import br.com.fiap.techchallenge.order.infra.gateway.database.entities.CustomerEntity;
import br.com.fiap.techchallenge.order.infra.gateway.database.repository.CustomerRepository;
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
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class CustomerPersistenceImplTest {

    @Mock
    private CustomerRepository repository;

    @InjectMocks
    private CustomerPersistenceImpl customerPersistence;

    private Customer customer;

    private Customer customerExpected;

    @BeforeEach
    void setUp() {
        this.buildArranges();
    }

    @Test
    @DisplayName("Should create and save a new Customer")
    void shouldCreateAndSaveNewCustomer(){
        when(repository.save(any())).thenReturn(new CustomerEntity(customerExpected));

        var created = customerPersistence.create(customer);

        verify(repository,times(1)).save(any());
        verifyNoMoreInteractions(repository);

        assertEquals(customerExpected.getId(), created.getId());
        assertEquals(customerExpected.getName(), created.getName());
        assertEquals(customerExpected.getDocument(), created.getDocument());
        assertEquals(customerExpected.getEmail(), created.getEmail());
    }

    @Test
    @DisplayName("Should Find Customer by document")
    void shouldFindCustomerByDocument(){
        when(repository.findByDocument(customerExpected.getDocument()))
                .thenReturn(Optional.of(new CustomerEntity(customerExpected)));

        var customerFoundOpt = customerPersistence.findByDocument(customerExpected.getDocument());
        var customerFound = customerFoundOpt.orElse(null);

        verify(repository,times(1)).findByDocument(any());
        verifyNoMoreInteractions(repository);

        assertNotNull(customerFound);
        assertNotNull(customerFound.getId());
        assertNotNull(customerFound.getName());
        assertNotNull(customerFound.getDocument());
        assertNotNull(customerFound.getEmail());
    }

    @Test
    @DisplayName("Should Find Customer by ID")
    void shouldFindCustomerById() {
        when(repository.findById(customerExpected.getId()))
                .thenReturn(Optional.of(new CustomerEntity(customerExpected)));

        var customerFoundOpt = customerPersistence.findById(customerExpected.getId());
        var customerFound = customerFoundOpt.orElse(null);

        verify(repository,times(1)).findById(any());
        verifyNoMoreInteractions(repository);

        assertNotNull(customerFound);
        assertNotNull(customerFound.getId());
        assertNotNull(customerFound.getName());
        assertNotNull(customerFound.getDocument());
        assertNotNull(customerFound.getEmail());
    }

    private void buildArranges() {
        customer = new Customer("Walter White", "31739380037", "heisenberg@gmail.com");

        customerExpected = new Customer(UUID.randomUUID(), "Walter White", "31739380037", "heisenberg@gmail.com");
    }

}