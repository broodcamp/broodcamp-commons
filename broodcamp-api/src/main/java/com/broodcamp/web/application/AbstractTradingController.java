/**
 * An Open Source Inventory and Sales Management System
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

import java.io.Serializable;
import java.util.UUID;

import javax.transaction.NotSupportedException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.broodcamp.business.exception.ResourceNotFoundException;
import com.broodcamp.data.dto.EnableEntityDto;
import com.broodcamp.data.entity.EnableEntity;
import com.broodcamp.data.mapper.GenericMapper;
import com.broodcamp.data.mapper.GenericMapperService;
import com.broodcamp.data.repository.BaseRepository;
import com.broodcamp.util.ReflectionUtils;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
public abstract class AbstractTradingController<E extends EnableEntity, D extends EnableEntityDto, I extends Serializable> {

    @Autowired
    protected @Qualifier("validator") Validator validator;

    @Autowired
    protected MessageSource messageSource;

    @Autowired
    protected GenericMapperService<E, D> genericMapperService;

    @Autowired
    protected BaseRepository<E, I> repository;

    @Autowired
    protected RepresentationModelAssembler<D, EntityModel<D>> modelAssembler;

    @Autowired
    protected GenericMapper<E, D> genericMapper;

    @SuppressWarnings("rawtypes")
    protected Class<IController> controllerClass;
    protected Class<E> entityClass;
    protected Class<D> dtoClass;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public AbstractTradingController() {

        Class clazz = getClass();
        entityClass = (Class<E>) ReflectionUtils.getParameterTypeClass(clazz, 0);
        dtoClass = (Class<D>) ReflectionUtils.getParameterTypeClass(clazz, 1);
        controllerClass = IController.class;
    }

    @PostMapping
    public ResponseEntity<EntityModel<D>> create(@RequestBody @NotNull @Valid D dto) throws NotSupportedException {

        E entity = genericMapper.toModel(dto);

        final EntityModel<D> resource = modelAssembler.toModel(genericMapper.toDto(repository.save(entity)));
        return ResponseEntity.created(linkTo(controllerClass).slash(entity.getId()).withSelfRel().toUri()).body(resource);
    }

    @SuppressWarnings("unchecked")
    @GetMapping(path = "/{uid}")
    public EntityModel<D> findById(@PathVariable UUID uid) throws NotSupportedException {

        E entity = repository.findById((I) uid).orElseThrow(() -> createNewResourceNotFoundException(uid));

        return modelAssembler.toModel(genericMapper.toDto(entity));
    }

    @DeleteMapping(path = "/{uid}")
    public ResponseEntity<D> delete(@PathVariable /* @ApiParam(value = "entity uid", required = true) */ I uid) throws NotSupportedException {

        repository.deleteById(uid);

        return ResponseEntity.noContent().build();
    }

    protected ResourceNotFoundException createNewResourceNotFoundException(Serializable id) {

        return new ResourceNotFoundException(entityClass.getSimpleName(), id);
    }
}
