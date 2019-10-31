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
package com.broodcamp.data.repository.adm;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.broodcamp.data.entity.adm.State;
import com.broodcamp.data.repository.base.NamedRepository;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
@Repository
public interface StateRepository extends NamedRepository<State, UUID> {

    List<State> findByCountryCode(String countryCode, Pageable pageable);

    List<State> findByRegionId(UUID regionId, Pageable pageable);

    List<State> findByCountryId(UUID countryId, Pageable pageable);
}
