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
package adm.com.broodcamp.web.application;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.broodcamp.web.application.AbstractBaseController;

import adm.com.broodcamp.business.domain.CityDto;
import adm.com.broodcamp.data.entity.City;
import adm.com.broodcamp.data.repository.CityRepository;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class CityListController extends AbstractBaseController<City, CityDto, UUID> {

    @GetMapping(path = "/adm/states/{stateId}/cities")
    public CollectionModel<EntityModel<CityDto>> findByStateId(@PathVariable UUID stateId, @RequestParam(required = false) Integer size,
            @RequestParam(required = false) Integer page) {

        Pageable pageable = initPage(page, size);

        List<City> cities = ((CityRepository) repository).findByStateId(stateId, pageable);

        List<EntityModel<CityDto>> entities = cities.stream().map(e -> modelAssembler.toModel(genericMapper.toDto(e))).collect(Collectors.toList());

        return new CollectionModel<>(entities, linkTo(methodOn(CityListController.class).findByStateId(stateId, size, page)).withSelfRel());
    }
}
