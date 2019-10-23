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

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.validation.Validator;

import com.broodcamp.data.dto.EnableEntityDto;
import com.broodcamp.data.entity.EnableEntity;
import com.broodcamp.data.repository.EnableRepository;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
public abstract class AbstractEnableController<E extends EnableEntity, D extends EnableEntityDto, I extends Serializable> extends AbstractAuditableController<E, D, I> {

    @SuppressWarnings("rawtypes")
    public AbstractEnableController(EnableRepository<E, I> repository, RepresentationModelAssembler<D, EntityModel<D>> modelAssembler, Validator validator,
            Class<IController> iController) {

        super(repository, modelAssembler, validator, iController);
    }

}
