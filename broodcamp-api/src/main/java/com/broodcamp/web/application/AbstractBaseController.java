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
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.broodcamp.business.exception.ResourceNotFoundException;
import com.broodcamp.data.annotation.EntityCode;
import com.broodcamp.data.dto.BaseEntityDto;
import com.broodcamp.data.entity.BaseEntity;
import com.broodcamp.data.mapper.GenericMapper;
import com.broodcamp.data.mapper.GenericMapperService;
import com.broodcamp.data.repository.BaseRepository;
import com.broodcamp.data.utils.DateUtils;
import com.broodcamp.util.ReflectionUtils;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
public abstract class AbstractBaseController<E extends BaseEntity, D extends BaseEntityDto, I extends Serializable> {

    public static final int DEFAULT_PAGE_SIZE = 10;

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
    public AbstractBaseController() {

        Class clazz = getClass();
        entityClass = (Class<E>) ReflectionUtils.getParameterTypeClass(clazz, 0);
        dtoClass = (Class<D>) ReflectionUtils.getParameterTypeClass(clazz, 1);
        controllerClass = IController.class;
    }

    /**
     * Binds the validator and default date format. For example, if we passed a
     * string {@linkplain Date} with the given format it is automatically parsed.
     * 
     * @param binder data binder
     * @see CustomDateEditor
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {

        binder.setValidator(validator);

        SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.SDF_STRING);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    protected Pageable initPage(Integer page, Integer size) {

        if (size == null) {
            size = DEFAULT_PAGE_SIZE;
        }
        if (page == null) {
            page = 0;
        }

        return PageRequest.of(page, size);
    }

    protected ResourceNotFoundException createNewResourceNotFoundException(Serializable id) {

        String errorCode = "";
        try {
            errorCode = ((EntityCode) entityClass.getAnnotation(EntityCode.class)).value();

        } catch (NullPointerException npe) {
        }
        
        return new ResourceNotFoundException(errorCode, entityClass.getSimpleName(), id);
    }
}
