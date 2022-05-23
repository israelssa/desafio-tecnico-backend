package br.com.desafio.mapper;

import java.util.List;
import java.util.stream.Collectors;

public interface Converter<D, M> {

    D toDto(M model);

    M toModel(D dto);

    default List<D> toListDto(List<M> entities) {
        return entities
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    default List<M> toListModel(List<D> dtos) {
        return dtos
                .stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

}
