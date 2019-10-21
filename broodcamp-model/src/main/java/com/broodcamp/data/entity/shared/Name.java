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
package com.broodcamp.data.entity.shared;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Embeddable
public class Name implements Serializable, Cloneable {

    private static final long serialVersionUID = -3598681841460533412L;

    @NotNull
    @Column(name = "firstname", length = 50, nullable = false)
    @Size(max = 50)
    private String firstName;

    @NotNull
    @Column(name = "lastname", length = 50, nullable = false)
    @Size(max = 50)
    private String lastName;

    @Column(name = "middlename", length = 50)
    @Size(max = 50)
    private String middleName;

    @Column(name = "suffix", length = 10)
    @Size(max = 10)
    private String suffix;

    public Name(Name name) {
        init(name);
    }

    public Name(String firstName, String lastName) {
        
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void init(Name name) {
        
        if (name != null) {
            if (!StringUtils.isBlank(name.getFirstName())) {
                this.firstName = name.getFirstName();
            }
            if (!StringUtils.isBlank(name.getLastName())) {
                this.lastName = name.getLastName();
            }
            if (!StringUtils.isBlank(name.getMiddleName())) {
                this.middleName = name.getMiddleName();
            }
            if (!StringUtils.isBlank(name.getSuffix())) {
                this.suffix = name.getSuffix();
            }
        }
    }

    public String getFullName() {
        
        StringBuilder sb = new StringBuilder();
        if (!StringUtils.isBlank(firstName)) {
            sb.append(firstName);
        }
        if (!StringUtils.isBlank(middleName)) {
            if (!StringUtils.isBlank(firstName)) {
                sb.append(" ");
            }
            sb.append(middleName);
        }
        if (!StringUtils.isBlank(lastName)) {
            if (!StringUtils.isBlank(sb.toString())) {
                sb.append(" ");
            }
            sb.append(lastName);
        }
        if (!StringUtils.isBlank(suffix)) {
            if (!StringUtils.isBlank(sb.toString())) {
                sb.append(" ");
            }
            sb.append(suffix);
        }

        return sb.toString();
    }
}
