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
package com.broodcamp.data.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Auditable implements Serializable {
	
	private static final long serialVersionUID = 6534747210502098594L;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created", nullable = false, updatable = false)
	private Date created;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated")
	private Date updated;

	@Column(name = "creator_ref")
	private String creatorRef;

	@Column(name = "updater_ref")
	private String updaterRef;

	public Auditable(String userRef) {
		super();
		this.creatorRef = userRef;
		this.created = new Date();
	}

	public void updateWith(String userRef) {
		this.updated = new Date();
		this.updaterRef = userRef;

		if (this.creatorRef == null) {
			this.creatorRef = userRef;
		}
		if (this.created == null) {
			this.created = this.updated;
		}

	}

}
