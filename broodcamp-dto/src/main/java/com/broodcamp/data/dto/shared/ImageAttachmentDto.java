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
package com.broodcamp.data.dto.shared;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
@Embeddable
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ImageAttachmentDto implements Serializable {

    private static final long serialVersionUID = -459371026063434553L;

    private String filename;
    private byte[] attachment;
    private String prefix;

    @Transient
    public String getContentType() {
        return prefix.substring(prefix.indexOf(":") + 1, prefix.lastIndexOf(";"));
    }
}
