package br.com.fiap.techchallenge.order.infra.entrypoint.controller.dto;

import br.com.fiap.techchallenge.order.domain.models.pageable.CustomPage;
import io.swagger.v3.oas.annotations.media.Schema;

public record PageResponseDTO(@Schema(example = "1") Long totalPages, @Schema(example = "1") Long totalElements,
                              @Schema(example = "10") Long size, @Schema(example = "0") Long number, @Schema(example = "true") Boolean first,
                              @Schema(example = "true") Boolean last, @Schema(example = "1") Long numberOfElements,
                              @Schema(example = "false") Boolean empty) {
	public PageResponseDTO(CustomPage page) {
		this(page.totalPages(), page.totalElements(), page.size(), page.number(), page.first(), page.last(),
				page.numberOfElements(), page.empty());
	}
}
