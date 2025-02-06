package br.com.fiap.techchallenge.order.infra.entrypoint.controller;

import br.com.fiap.techchallenge.order.application.usecase.product.FindProductsByCategoryUseCase;
import br.com.fiap.techchallenge.order.domain.models.Product;
import br.com.fiap.techchallenge.order.domain.models.enums.ProductCategoryEnum;
import br.com.fiap.techchallenge.order.domain.models.enums.ProductStatusEnum;
import br.com.fiap.techchallenge.order.domain.models.pageable.CustomPage;
import br.com.fiap.techchallenge.order.domain.models.pageable.CustomPageable;
import br.com.fiap.techchallenge.order.infra.entrypoint.controller.dto.PageResponseDTO;
import br.com.fiap.techchallenge.order.infra.entrypoint.controller.dto.ProductPageResponseDTO;
import br.com.fiap.techchallenge.order.infra.entrypoint.controller.dto.ProductResponseDTO;
import br.com.fiap.techchallenge.order.infra.entrypoint.controller.handler.ControllerAdvice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ProductsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FindProductsByCategoryUseCase findProductsByCategoryUseCase;

    @InjectMocks
    private ProductsController productController;

    private Product product;
    private CustomPage domainPage;
    private ProductPageResponseDTO productPageResponseDto;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController)
                .setControllerAdvice(new ControllerAdvice())
                .build();
        this.buildArranges();
    }

    @Test
    @DisplayName("Should get Products by category successfully.")
    void shouldGetProductsByCategory() throws Exception {
        when(findProductsByCategoryUseCase.findByCategory(ProductCategoryEnum.MAIN_COURSE, 0, 10))
                .thenReturn(new CustomPageable<>(List.of(product), domainPage));

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/products")
                        .queryParam("category", ProductCategoryEnum.MAIN_COURSE.toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalPages").value(productPageResponseDto.page().totalPages()))
                .andExpect(jsonPath("$.page.totalElements").value(productPageResponseDto.page().totalElements()))
                .andExpect(jsonPath("$.content[0].id").value(productPageResponseDto.content().getFirst().id().toString()))
                .andExpect(jsonPath("$.content[0].name").value(productPageResponseDto.content().getFirst().name()))
                .andExpect(jsonPath("$.content[0].category").value(productPageResponseDto.content().getFirst().category().toString()))
                .andExpect(jsonPath("$.content[0].price").value(productPageResponseDto.content().getFirst().price().toString()))
                .andExpect(jsonPath("$.content[0].description").value(productPageResponseDto.content().getFirst().description()));
    }

    private void buildArranges() {
        product = new Product(UUID.randomUUID(), "Sanduíche de Frango", ProductCategoryEnum.MAIN_COURSE,
                new BigDecimal("99.99"), "Sanduíche de frango com salada", ProductStatusEnum.ACTIVE,
                LocalDateTime.now());

        domainPage = new CustomPage(1L, 1L, 1L, 1L, true, true, 1L, false);

        productPageResponseDto = new ProductPageResponseDTO(List.of(new ProductResponseDTO(product)),
                new PageResponseDTO(1L, 1L, 1L, 1L, true, true, 1L, false));
    }
}