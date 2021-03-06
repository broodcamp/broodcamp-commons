/**
 * Broodcamp Library
 * Copyright (C) 2019 Edward P. Legaspi (https://github.com/czetsuya)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.broodcamp.web.application;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.NotSupportedException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.broodcamp.data.dto.BaseEntityDto;
import com.broodcamp.data.entity.BaseEntity;
import com.broodcamp.util.NullAwareBeanUtilsBean;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
@Slf4j
public abstract class AbstractController<E extends BaseEntity, D extends BaseEntityDto, I extends Serializable> extends AbstractBaseController<E, D, I> {

    @PostMapping
    public ResponseEntity<EntityModel<D>> create(@RequestBody @NotNull @Valid D dto) throws NotSupportedException {

        E entity = genericMapper.toModel(dto);

        final EntityModel<D> resource = modelAssembler.toModel(genericMapper.toDto(repository.save(entity)));
        return ResponseEntity.created(linkTo(controllerClass).slash(entity.getId()).withSelfRel().toUri()).body(resource);
    }

    @PutMapping(path = "/{uid}")
    public ResponseEntity<D> update(@RequestBody D newDto, @PathVariable @NotNull I uid) throws NotSupportedException {

        E newEntity = genericMapper.toModel(newDto);

        E updatedEntity = repository.findById(uid).map(entity -> {
            try {
                BeanUtilsBean bean = new NullAwareBeanUtilsBean();
                bean.copyProperties(entity, newEntity);

            } catch (IllegalAccessException | InvocationTargetException e) {
                log.warn("Update failed: {}", e.getMessage());
            }
            return repository.save(entity);

        }).orElseGet(() -> {
            newEntity.setId((UUID) uid);
            return repository.save(newEntity);
        });

        return ResponseEntity.ok().body(genericMapper.toDto(updatedEntity));
    }

    @PostMapping(path = "/{uid}/createOrUpdate")
    public ResponseEntity<?> createOrUpdate(@RequestBody @Valid D newDto, @NotNull @PathVariable("uuid") I uid) throws NotSupportedException {

        return repository.findById(uid).isPresent() ? update(newDto, uid) : create(newDto);
    }

    /**
     * Retrieves an entity by its id.
     * <p>
     * ID type cannot be use as a parameters as HATEOAS will not be able to produce
     * a converter.
     * </p>
     * 
     * @param uid must not be null
     * @return the entity with the given id wrap in an EntityModel or throw an
     *         exception if null
     */
    @SuppressWarnings("unchecked")
    @GetMapping(path = "/{uid}")
    public EntityModel<D> findById(@PathVariable UUID uid) throws NotSupportedException {

        E entity = repository.findById((I) uid).orElseThrow(() -> createNewResourceNotFoundException(uid));

        return modelAssembler.toModel(genericMapper.toDto(entity));
    }

    @GetMapping
    public CollectionModel<EntityModel<D>> findAll(@RequestParam(required = false) Integer size //
            , @RequestParam(required = false) Integer page) throws NotSupportedException {

        Pageable pageable = initPage(page, size);

        List<EntityModel<D>> entities = repository.findAll(pageable).stream().map(e -> modelAssembler.toModel(genericMapper.toDto(e))).collect(Collectors.toList());

        return new CollectionModel<>(entities, linkTo(methodOn(controllerClass).findAll(size, page)).withSelfRel());
    }

    @DeleteMapping(path = "/{uid}")
    public ResponseEntity<D> delete(@PathVariable @NotNull I uid) throws NotSupportedException {

        repository.deleteById(uid);

        return ResponseEntity.noContent().build();
    }
}
