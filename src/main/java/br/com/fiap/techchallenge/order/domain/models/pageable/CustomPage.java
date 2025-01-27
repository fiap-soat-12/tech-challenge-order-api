package br.com.fiap.techchallenge.order.domain.models.pageable;

public record CustomPage(Long totalPages, Long totalElements, Long size, Long number, Boolean first, Boolean last,
		Long numberOfElements, Boolean empty) {

}