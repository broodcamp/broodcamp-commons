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
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;

import com.broodcamp.data.entity.base.BaseEntity;
import com.broodcamp.data.repository.base.BaseRepository;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
public abstract class AbstractBaseService<E extends BaseEntity, ID extends Serializable> {

	protected Class<E> entityClass;
	protected BaseRepository<E, ID> repository;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public AbstractBaseService(BaseRepository<E, ID> repository) {

		this.repository = repository;

		Class clazz = getClass();
		while (!(clazz.getGenericSuperclass() instanceof ParameterizedType)) {
			clazz = clazz.getSuperclass();
		}

		Object o = ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[0];

		if (o instanceof TypeVariable) {
			this.entityClass = (Class<E>) ((TypeVariable) o).getBounds()[0];

		} else {
			this.entityClass = (Class<E>) o;
		}
	}

}
