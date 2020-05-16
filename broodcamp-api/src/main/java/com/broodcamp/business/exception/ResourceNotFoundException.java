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

import java.io.Serializable;

import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.broodcamp.util.StringUtils;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends InvalidConfigurationPropertyValueException {

    private static final long serialVersionUID = -3959094123571588527L;
    private final String errorCode;
    private static final String REASON = "not found";

    public ResourceNotFoundException() {
        super(null, null, null);
        errorCode = "";
    }

    public ResourceNotFoundException(String simpleName, Serializable id) {

        this(null, simpleName, id);
    }

    public ResourceNotFoundException(Class<?> clazz, String code) {

        this(null, clazz.getSimpleName(), code);
    }

    public ResourceNotFoundException(String errorCode, String simpleName, Serializable id) {

        super(simpleName, id, REASON);
        this.errorCode = errorCode;
    }

    @Override
    public String getLocalizedMessage() {

        return (StringUtils.isBlank(errorCode) ? "" : errorCode + ": ") + super.getLocalizedMessage();
    }
}
