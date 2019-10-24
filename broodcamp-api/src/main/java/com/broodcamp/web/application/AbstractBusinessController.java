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
import java.net.URISyntaxException;
import java.util.UUID;

import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

    /**
     * Updates a business entity.
     * <p>
     * ID type cannot be use as a parameters as HATEOAS will not be able to produce
     * a converter.
     * </p>
     * 
     * @param newEntity
     * @param uid
     * @return
     * @throws URISyntaxException
     */
//	@ApiOperation(value = "Update new entity" //
//			, notes = "Updates new entity. Returns the updated entity.")
    @PutMapping(path = "/{uid}")
    public ResponseEntity<E> update(@RequestBody D newDto, @PathVariable /* @ApiParam(value = "entity uid", required = true) */ I uid) {

        E newEntity = getGenericMapper().toModel(newDto);

        E updatedEntity = businessRepository.findById(uid).map(entity -> {
            entity.setDescription(newEntity.getDescription());
            return businessRepository.save(entity);

        }).orElseGet(() -> {
            newEntity.setId((UUID) uid);
            return businessRepository.save(newEntity);
        });

        return ResponseEntity.ok().body(updatedEntity);
    }

//	@ApiOperation(value = "Get entity by code" //
//			, notes = "Returns the entity for the code specified.")
//	@ApiResponses(value = { @ApiResponse(code = 404, message = "Entity not found") })
    @GetMapping(path = "/code/{code}")
    public EntityModel<D> findByCode(@PathVariable @Size(min = 2, max = 50) /* @ApiParam(value = "entity code", required = true) */ String code) {

        E entity = businessRepository.findByCode(code).orElseThrow(() -> createNewResourceNotFoundException(code));

        return modelAssembler.toModel(getGenericMapper().toDto(entity));
    }
}
