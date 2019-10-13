package com.broodcamp.data.entity.adm;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.broodcamp.data.entity.NamedEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Edward P. Legaspi
 * 
 *         City or Town.
 **/
@Entity
@Cacheable
@Table(name = "adm_city", uniqueConstraints = @UniqueConstraint(columnNames = { "state_id", "name", "district" }))
@NamedQuery(name = "City.listByStateId", query = "SELECT c FROM City c WHERE c.state.id=:stateId")
@Data
@EqualsAndHashCode(callSuper = false, of = { "state", "name" })
@AllArgsConstructor
@NoArgsConstructor
public class City extends NamedEntity {

	private static final long serialVersionUID = -3565472427670689063L;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "state_id")
	private State state;

	@Column(name = "district", length = 100)
	private String district;

	@Column(name = "longitude")
	private Double longitude;

	@Column(name = "latitude")
	private Double latitude;

	@Column(name = "is_city")
	private Boolean isCity;

	public String getStateAndCity() {
		if (state != null) {
			return getName() + "," + state.getName();
		}

		return getName();
	}

}
