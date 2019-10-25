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
package com.broodcamp.data.mapper.adm;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import com.broodcamp.business.domain.adm.CityDto;
import com.broodcamp.data.entity.adm.City;
import com.broodcamp.data.mapper.GenericMapper;
import com.broodcamp.data.repository.adm.StateRepository;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
@Mapper
public abstract class CityMapper implements GenericMapper<City, CityDto> {

    @Autowired
    private StateRepository stateRepository;

    @Override
    @Mapping(source = "state.id", target = "stateId")
    public abstract CityDto toDto(City source);

    @AfterMapping
    public void afterMapping(CityDto source, @MappingTarget City target) {

        if (source.getStateId() != null) {
            stateRepository.findById(source.getStateId()).ifPresent(target::setState);
        }
    }
}
