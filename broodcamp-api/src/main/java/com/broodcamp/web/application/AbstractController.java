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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.broodcamp.business.exception.ResourceNotFoundException;
import com.broodcamp.data.dto.BaseEntityDto;
import com.broodcamp.data.dto.mapper.GenericMapper;
import com.broodcamp.data.dto.mapper.GenericMapperService;
import com.broodcamp.data.entity.BaseEntity;
import com.broodcamp.data.repository.BaseRepository;
import com.broodcamp.util.BeanUtils;
import com.broodcamp.util.ReflectionUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
@Slf4j
public abstract class AbstractController<E extends BaseEntity, D extends BaseEntityDto, I extends Serializable> {

    public static final int DEFAULT_PAGE_SIZE = 10;

    @Autowired
    protected GenericMapperService<E, D> genericMapperService;

    @Autowired
    protected @Qualifier("validator") Validator validator;

    @Autowired
    protected MessageSource messageSource;

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
    public AbstractController() {

        Class clazz = getClass();
        entityClass = (Class<E>) ReflectionUtils.getParameterTypeClass(clazz, 0);
        dtoClass = (Class<D>) ReflectionUtils.getParameterTypeClass(clazz, 1);
        controllerClass = IController.class;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    // @ApiOperation(value = "Create new entity" //
    // , notes = "Creates new entity. Returns the created entity with uid.")
    @PostMapping
    public ResponseEntity<EntityModel<D>> create(@RequestBody @NotNull @Valid D dto) throws NotSupportedException {

        E entity = genericMapper.toModel(dto);

        final EntityModel<D> resource = modelAssembler.toModel(genericMapper.toDto(repository.save(entity)));
        return ResponseEntity.created(linkTo(controllerClass).slash(entity.getId()).withSelfRel().toUri()).body(resource);
    }

    @PutMapping(path = "/{uid}")
    public ResponseEntity<D> update(@RequestBody D newDto, @PathVariable /* @ApiParam(value = "entity uid", required = true) */ I uid) {

        E newEntity = genericMapper.toModel(newDto);

        E updatedEntity = repository.findById(uid).map(entity -> {
            try {
                BeanUtils.copyProperties(newEntity, entity);

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
    public ResponseEntity<?> createOrUpdate(@RequestBody @Valid D newDto, @PathVariable("uuid") I uid) throws NotSupportedException {

        return repository.findById(uid).isPresent() ? update(newDto, uid) : create(newDto);
    }

    // @ApiOperation(value = "Get entity by uid" //
    // , notes = "Returns entity for uid specified.")
    // @ApiResponses(value = { @ApiResponse(code = 404, message = "Entity not
    // found") })
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
    public EntityModel<D> findById(@PathVariable /* @ApiParam(value = "entity identifier", required = true) */ UUID uid) {

        E entity = repository.findById((I) uid).orElseThrow(() -> createNewResourceNotFoundException(uid));

        return modelAssembler.toModel(genericMapper.toDto(entity));
    }

    // @ApiOperation(value = "Get all categories" //
    // , notes = "Returns first N categories specified by the size parameter with
    // page offset specified by page parameter.")
    @GetMapping
    public CollectionModel<EntityModel<D>> findAll(/*
                                                    * @ApiParam(value = "The size of the page to be returned", defaultValue = "" +
                                                    * DEFAULT_PAGE_SIZE) @RequestParam(required = false)
                                                    */ Integer size //
            , /*
               * @ApiParam(value = "Zero-based page index", defaultValue =
               * "0") @RequestParam(required = false)
               */ Integer page) {

        if (size == null) {
            size = DEFAULT_PAGE_SIZE;
        }
        if (page == null) {
            page = 0;
        }

        Pageable pageable = PageRequest.of(page, size);

        List<EntityModel<D>> entities = repository.findAll(pageable).stream().map(e -> modelAssembler.toModel(genericMapper.toDto(e))).collect(Collectors.toList());

        return new CollectionModel<>(entities, linkTo(methodOn(controllerClass).findAll(size, page)).withSelfRel());
    }

    // @ApiModelProperty(notes = "Delete an entity with a given id")
    // @ApiOperation(value = "Delete entity" //
    // , notes = "Deletes an entity for uid specified. No content is returned.")
    @DeleteMapping(path = "/{uid}")
    public ResponseEntity<E> delete(@PathVariable /* @ApiParam(value = "entity uid", required = true) */ I uid) {

        repository.deleteById(uid);

        return ResponseEntity.noContent().build();
    }

    protected ResourceNotFoundException createNewResourceNotFoundException(Serializable id) {

        return new ResourceNotFoundException(entityClass.getSimpleName(), id);
    }

}
