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
import java.util.UUID;

import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 **/
@Embeddable
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class AddressDto implements Serializable, Cloneable {

    private static final long serialVersionUID = 7324969542509922787L;

    @Size(max = 80)
    protected String address1;

    @Size(max = 80)
    protected String address2;

    @Size(max = 80)
    protected String address3;

    @Size(max = 10)
    protected String zipCode;

    private UUID countryId;
    private UUID stateId;
    private UUID cityId;
}
