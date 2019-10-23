package com.broodcamp.data.dto.mapper;

import java.util.List;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
public interface GenericMapper<S, T> {

    T toDto(S source);

    S toModel(T target);

    List<T> toDto(List<S> sourceList);

    List<S> toModel(List<T> targetList);
}