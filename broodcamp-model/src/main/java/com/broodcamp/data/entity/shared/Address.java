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
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import adm.com.broodcamp.data.entity.City;
import adm.com.broodcamp.data.entity.Country;
import adm.com.broodcamp.data.entity.State;
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
public class Address implements Serializable, Cloneable {

    private static final long serialVersionUID = 7324969542509922787L;

    @Column(name = "address_1", length = 50)
    @Size(max = 80)
    protected String address1;

    @Column(name = "address_2", length = 50)
    @Size(max = 80)
    protected String address2;

    @Column(name = "address_3", length = 50)
    @Size(max = 80)
    protected String address3;

    @Column(name = "address_zipcode", length = 10)
    @Size(max = 10)
    protected String zipCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    private State state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;
}
