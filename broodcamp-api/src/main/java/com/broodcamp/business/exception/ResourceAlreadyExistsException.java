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
package com.broodcamp.business.exception;

import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
@ResponseStatus(value = HttpStatus.FOUND)
public class ResourceAlreadyExistsException extends InvalidConfigurationPropertyValueException {

    private static final long serialVersionUID = 7236295209124886220L;
    private static final String REASON = "found";

    public ResourceAlreadyExistsException(String className, String fieldName, String value) {

        super(className + "." + fieldName, value, REASON);
    }

    public ResourceAlreadyExistsException(Class<?> entityClass, String value) {

        this(entityClass.getSimpleName(), "code", value);
    }
}