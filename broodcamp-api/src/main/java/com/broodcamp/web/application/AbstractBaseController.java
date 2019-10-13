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
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

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
import com.broodcamp.data.entity.BaseEntity;
import com.broodcamp.data.repository.BaseRepository;

import lombok.NoArgsConstructor;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
@NoArgsConstructor
public abstract class AbstractBaseController<E extends BaseEntity, I extends Serializable> {

    public static final int DEFAULT_PAGE_SIZE = 10;

    protected Class<E> entityClass;
    @SuppressWarnings("rawtypes")
    protected Class<IController> controllerClass;
    protected Validator validator;
    protected RepresentationModelAssembler<E, EntityModel<E>> modelAssembler;
    protected BaseRepository<E, I> repository;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public AbstractBaseController(BaseRepository<E, I> repository, RepresentationModelAssembler<E, EntityModel<E>> modelAssembler, Validator validator,
            Class<IController> iController) {

        this.repository = repository;
        this.modelAssembler = modelAssembler;
        this.validator = validator;
        this.controllerClass = iController;

        Class clazz = getClass();
        while (!(clazz.getGenericSuperclass() instanceof ParameterizedType)) {
            clazz = clazz.getSuperclass();
        }

        Object o = ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[0];

        if (o instanceof TypeVariable) {
            this.entityClass = (Class<E>) ((TypeVariable) o).getBounds()[0];

        } else {
            this.entityClass = (Class<E>) o;
        }
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    // @ApiOperation(value = "Create new entity" //
    // , notes = "Creates new entity. Returns the created entity with uid.")
    @PostMapping
    public ResponseEntity<EntityModel<E>> create(@RequestBody @Valid E entity) {

        final EntityModel<E> resource = modelAssembler.toModel(repository.save(entity));
        return ResponseEntity.created(linkTo(controllerClass).slash(entity.getId()).withSelfRel().toUri()).body(resource);
    }

    @PutMapping(path = "/{uid}")
    public abstract ResponseEntity<E> update(@RequestBody E newEntity, @PathVariable /* @ApiParam(value = "entity uid", required = true) */ I uid);

    @PostMapping(path = "/{uid}/createOrUpdate")
    public ResponseEntity<?> createOrUpdate(@RequestBody @Valid E newEntity, @PathVariable("uuid") I uid) {

        return repository.findById(uid).isPresent() ? update(newEntity, uid) : create(newEntity);
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
    public EntityModel<E> findById(@PathVariable /* @ApiParam(value = "entity identifier", required = true) */ UUID uid) {

        E entity = repository.findById((I) uid).orElseThrow(() -> createNewResourceNotFoundException(uid));

        return modelAssembler.toModel(entity);
    }

    // @ApiOperation(value = "Get all categories" //
    // , notes = "Returns first N categories specified by the size parameter with
    // page offset specified by page parameter.")
    @GetMapping
    public CollectionModel<EntityModel<E>> findAll(/*
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

        List<EntityModel<E>> entities = repository.findAll(pageable).stream().map(modelAssembler::toModel).collect(Collectors.toList());

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
