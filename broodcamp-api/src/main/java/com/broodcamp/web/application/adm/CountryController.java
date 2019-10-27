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
package com.broodcamp.web.application.adm;

import java.util.UUID;

import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.broodcamp.business.domain.adm.CountryDto;
import com.broodcamp.data.entity.adm.Country;
import com.broodcamp.data.repository.adm.CountryRepository;
import com.broodcamp.web.application.AbstractNamedController;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
@RestController
@RequestMapping(path = "/adm/countries", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class CountryController extends AbstractNamedController<Country, CountryDto, UUID> {

    @Autowired
    protected CountryRepository countryRepository;

    @GetMapping(path = "/code/{code}")
    public EntityModel<CountryDto> findByCode(@PathVariable @Size(min = 2, max = 50) String code) {

        Country entity = countryRepository.findByCode(code).orElseThrow(() -> createNewResourceNotFoundException(code));

        return modelAssembler.toModel(genericMapper.toDto(entity));
    }
}
