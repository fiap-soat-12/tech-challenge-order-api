package br.com.fiap.techchallenge.order.infra.entrypoint.consumer.mapper;

import br.com.fiap.techchallenge.order.domain.models.Product;
import br.com.fiap.techchallenge.order.domain.models.enums.ProductCategoryEnum;
import br.com.fiap.techchallenge.order.infra.entrypoint.consumer.dto.ProductCreateDTO;
import br.com.fiap.techchallenge.order.infra.entrypoint.consumer.dto.ProductUpdateDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

    private ProductMapper productMapper;

    @BeforeEach
    void setUp() {
        productMapper = new ProductMapper();
    }

    @Test
    @DisplayName("Should Map ProductCreateDTO to Product")
    void shouldMapProductCreateDTOToProduct() {
        UUID id = UUID.randomUUID();
        String name = "Product Test";
        ProductCategoryEnum category = ProductCategoryEnum.MAIN_COURSE;
        BigDecimal price = BigDecimal.valueOf(100.0);
        String description = "Description Test";

        ProductCreateDTO dto = new ProductCreateDTO(id, name, category, price, description);

        Product product = productMapper.toProduct(dto);

        assertNotNull(product);
        assertEquals(id, product.getId());
        assertEquals(name, product.getName());
        assertEquals(category, product.getCategory());
        assertEquals(price, product.getPrice());
        assertEquals(description, product.getDescription());
    }

    @Test
    @DisplayName("Should Map ProductUpdateDTO to Product")
    void shouldMapProductUpdateDTOToProduct() {
        UUID id = UUID.randomUUID();
        String name = "Updated Product";
        ProductCategoryEnum category = ProductCategoryEnum.MAIN_COURSE;
        BigDecimal price = BigDecimal.valueOf(150.0);
        String description = "Updated Description";

        ProductUpdateDTO dto = new ProductUpdateDTO(id, name, category, price, description);

        Product product = productMapper.toProduct(dto);

        assertNotNull(product);
        assertEquals(id, product.getId());
        assertEquals(name, product.getName());
        assertEquals(category, product.getCategory());
        assertEquals(price, product.getPrice());
        assertEquals(description, product.getDescription());
    }
}