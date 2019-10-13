package com.broodcamp.data.entity.adm;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.broodcamp.data.entity.NamedEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Edward P. Legaspi
 * @created 14 Jan 2018
 **/
@Entity
@Cacheable
@Table(name = "adm_region", uniqueConstraints = @UniqueConstraint(columnNames = { "country_id", "name" }))
@Data
@EqualsAndHashCode(callSuper = false, of = { "country", "name" })
@AllArgsConstructor
@NoArgsConstructor
//@ApiModel
public class Region extends NamedEntity {

	private static final long serialVersionUID = -5624009746303674978L;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "country_id", nullable = false)
	private Country country;

}
