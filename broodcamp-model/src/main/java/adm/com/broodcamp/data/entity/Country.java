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
package adm.com.broodcamp.data.entity;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;

import com.broodcamp.data.entity.NamedEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 * 
 *         This class is not a BusinessEntity.
 **/
@Entity
@Table(name = "adm_country", uniqueConstraints = @UniqueConstraint(columnNames = { "code" }))
@Cacheable
@Data
@EqualsAndHashCode(callSuper = false, of = { "code" })
@AllArgsConstructor
@NoArgsConstructor
//@ApiModel
public class Country extends NamedEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "code", nullable = false, length = 100)
	@Size(max = 100, min = 1)
	@NotNull
	private String code;

	public String getNameOrCode() {
		return !StringUtils.isBlank(getName()) ? getName() : code;
	}
}
