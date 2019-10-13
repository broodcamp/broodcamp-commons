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

import com.broodcamp.data.entity.AuditableEntity;
import com.broodcamp.data.repository.AuditableRepository;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
public abstract class AbstractAuditableController<E extends AuditableEntity, I extends Serializable> extends AbstractBaseController<E, I> {

	@SuppressWarnings("rawtypes")
    public AbstractAuditableController(AuditableRepository<E, I> repository, RepresentationModelAssembler<E, EntityModel<E>> modelAssembler, Validator validator,
			Class<IController> iController) {

		super(repository, modelAssembler, validator, iController);
	}

}
