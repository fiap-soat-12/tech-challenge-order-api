package br.com.fiap.techchallenge.order.domain.models.pageable;

import java.util.List;

public record CustomPageable<T>(List<T> content, CustomPage page) {

}