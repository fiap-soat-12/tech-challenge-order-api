package br.com.fiap.techchallenge.order.infra.entrypoint.controller.mapper;

import br.com.fiap.techchallenge.order.infra.entrypoint.controller.dto.CustomerRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerMapperTest {

    private CustomerMapper customerMapper;

    @BeforeEach
    void setUp() {
        customerMapper = new CustomerMapper();
    }

    @Test
    @DisplayName("Should Map CustomerRequestDTO to Customer")
    void shouldMapCustomerRequestDTOToCustomer() {
        var dto = new CustomerRequestDTO("John Doe", "12345678901", "john.doe@example.com");

        var customer = customerMapper.toCustomer(dto);

        assertNotNull(customer);
        assertEquals(dto.name(), customer.getName());
        assertEquals(dto.document(), customer.getDocument());
        assertEquals(dto.email(), customer.getEmail());
    }
}