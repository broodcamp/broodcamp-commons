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
package com.broodcamp.data.mapper.adm;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import com.broodcamp.business.domain.adm.RegionDto;
import com.broodcamp.data.entity.adm.Region;
import com.broodcamp.data.mapper.GenericMapper;
import com.broodcamp.data.repository.adm.CountryRepository;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
@Mapper
public abstract class RegionMapper implements GenericMapper<Region, RegionDto> {

    @Autowired
    private CountryRepository countryRepository;

    @Override
    @Mapping(source = "country.id", target = "countryId")
    public abstract RegionDto toDto(Region source);

    @AfterMapping
    public void afterMapping(RegionDto source, @MappingTarget Region target) {

        if (source.getCountryId() != null) {
            countryRepository.findById(source.getCountryId()).ifPresent(target::setCountry);
        }
    }
}
