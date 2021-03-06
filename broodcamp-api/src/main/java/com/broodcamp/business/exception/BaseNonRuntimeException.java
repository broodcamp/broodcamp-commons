/**
 * An Open Source Inventory and Sales Management System
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

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 * 
 * @since
 * @version
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BaseNonRuntimeException extends Exception {

    private static final long serialVersionUID = 7864489501431607019L;
    private String code;

    public BaseNonRuntimeException() {
        super();
        registerEvent();
    }

    public BaseNonRuntimeException(String code, String message, Throwable cause) {
        super(code + ": " + message, cause);
        registerEvent();
    }

    public BaseNonRuntimeException(String code, String message) {
        super(code + ": " + message);
        this.code = code;
        registerEvent();
    }

    public BaseNonRuntimeException(Throwable cause) {
        super(cause);
        registerEvent();
    }

    public BaseNonRuntimeException(String code) {
        super("");
        this.code = code;
    }

    /**
     * Catch and record the error event.
     */
    public void registerEvent() {

    }
}
