package com.broodcamp.data.dto.mapper;

import java.util.List;

import org.mapstruct.Mapping;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
public interface GenericMapper<S, T> {

    T toDto(S source);

    @Mapping(target = "id", ignore = true)
    S toModel(T target);

    List<T> toDto(List<S> sourceList);

    List<S> toModel(List<T> targetList);
}