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
package com.broodcamp.business.service;

import java.io.Serializable;

import com.broodcamp.data.entity.EnableEntity;
import com.broodcamp.data.repository.EnableRepository;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
public abstract class AbstractEnableService<E extends EnableEntity, ID extends Serializable> extends AbstractAuditableService<E, ID> {

	public AbstractEnableService(EnableRepository<E, ID> repository) {

		super(repository);
	}

}
