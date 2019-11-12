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

import java.io.Serializable;

import javax.transaction.NotSupportedException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.broodcamp.business.exception.ResourceFoundException;
import com.broodcamp.data.dto.BusinessEntityDto;
import com.broodcamp.data.entity.BusinessEntity;
import com.broodcamp.data.repository.BusinessRepository;

import lombok.NoArgsConstructor;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
@NoArgsConstructor
public abstract class AbstractBusinessController<E extends BusinessEntity, D extends BusinessEntityDto, I extends Serializable> extends AbstractEnableController<E, D, I> {

    @Autowired
    protected BusinessRepository<E, I> businessRepository;

    @GetMapping(path = "/code/{code}")
    public EntityModel<D> findByCode(@PathVariable @Size(min = 2, max = 50) /* @ApiParam(value = "entity code", required = true) */ String code) {

        E entity = businessRepository.findByCode(code).orElseThrow(() -> createNewResourceNotFoundException(code));

        return modelAssembler.toModel(genericMapper.toDto(entity));
    }

    @Override
    @PostMapping
    public ResponseEntity<EntityModel<D>> create(@RequestBody @NotNull @Valid D dto) throws NotSupportedException {

        if (businessRepository.findByCode(dto.getCode()).isPresent()) {
            throw new ResourceFoundException(entityClass, dto.getCode());
        }

        return super.create(dto);
    }
}
