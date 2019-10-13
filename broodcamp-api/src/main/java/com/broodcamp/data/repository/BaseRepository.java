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
package com.broodcamp.data.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.broodcamp.data.entity.BaseEntity;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity, ID extends Serializable> extends JpaRepository<T, ID> {

	@Override
	<S extends T> S save(S entity);

	void detach(T entity);

	void refresh(T entity);

	Optional<T> retrieveIfNotManaged(T entity);

	List<T> retrieveIfNotManaged(List<T> entities);

	Set<T> retrieveIfNotManaged(Set<T> entities);

	Optional<T> refreshOrRetrieve(T entity);

	List<T> refreshOrRetrieve(List<T> entities);

	Set<T> refreshOrRetrieve(Set<T> entities);

}
