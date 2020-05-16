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
package com.broodcamp.web.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import com.broodcamp.data.dto.BusinessEntityDto;
import com.broodcamp.web.application.AbstractBusinessController;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
public abstract class AbstractBusinessResourceAssembler<D extends BusinessEntityDto> extends AbstractEnableResourceAssember<D>
        implements RepresentationModelAssembler<D, EntityModel<D>> {

    @SuppressWarnings("rawtypes")
    private Class<? extends AbstractBusinessController> controllerClass;

    @SuppressWarnings("rawtypes")
    public AbstractBusinessResourceAssembler(Class<? extends AbstractBusinessController> controllerClass) {

        super(controllerClass);
        this.controllerClass = controllerClass;
    }

    @Override
    public EntityModel<D> toModel(D entity) {

        EntityModel<D> result = super.toModel(entity);
        result = result.add(linkTo(methodOn(controllerClass).findByCode(entity.getCode())).withRel("code"));

        return result;
    }

}