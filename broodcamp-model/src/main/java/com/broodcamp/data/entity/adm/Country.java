package com.broodcamp.data.entity.adm;

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
 * @author Edward P. Legaspi
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
