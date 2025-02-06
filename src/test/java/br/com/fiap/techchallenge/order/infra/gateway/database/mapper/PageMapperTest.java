package br.com.fiap.techchallenge.order.infra.gateway.database.mapper;

import br.com.fiap.techchallenge.order.domain.models.pageable.CustomPageable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PageMapperTest {

    private PageMapper<String> pageMapper;

    @BeforeEach
    void setUp() {
        pageMapper = new PageMapper<>();
    }

    @Test
    void shouldConvertPageToCustomPageable() {
        List<String> content = List.of("item1", "item2", "item3");
        Pageable pageable = PageRequest.of(1, 3);
        Page<String> page = new PageImpl<>(content, pageable, 10);

        CustomPageable<String> customPageable = pageMapper.toDomainPage(page);

        assertNotNull(customPageable);
        assertEquals(content, customPageable.content());
        assertNotNull(customPageable.page());
        assertEquals(4, customPageable.page().totalPages());
        assertEquals(10, customPageable.page().totalElements());
        assertEquals(3, customPageable.page().size());
        assertEquals(1, customPageable.page().number());
        assertFalse(customPageable.page().first());
        assertFalse(customPageable.page().last());
        assertEquals(3, customPageable.page().numberOfElements());
        assertFalse(customPageable.page().empty());
    }

    @Test
    void shouldHandleEmptyPage() {
        Page<String> emptyPage = Page.empty();

        CustomPageable<String> customPageable = pageMapper.toDomainPage(emptyPage);

        assertNotNull(customPageable);
        assertTrue(customPageable.content().isEmpty());
        assertNotNull(customPageable.page());
        assertEquals(0, customPageable.page().totalElements());
        assertEquals(0, customPageable.page().totalElements());
        assertEquals(0, customPageable.page().size());
        assertEquals(0, customPageable.page().number());
        assertTrue(customPageable.page().first());
        assertTrue(customPageable.page().last());
        assertEquals(0, customPageable.page().numberOfElements());
        assertTrue(customPageable.page().empty());
    }
}
