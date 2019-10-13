package com.broodcamp.data.entity.adm;

import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
 *         State or Province.
 **/
@Entity
@Cacheable
@Table(name = "adm_state", uniqueConstraints = @UniqueConstraint(columnNames = { "country_id", "name" }))
@NamedQuery(name = "State.countState", query = "SELECT COUNT(c) FROM State c WHERE c.country.code=:countryCode")
@NamedQuery(name = "State.listByCountryIds", query = "SELECT c FROM State c WHERE c.country.id IN (:countryIds) ORDER BY code")
@Data
@EqualsAndHashCode(callSuper = false, of = { "region", "name" })
@AllArgsConstructor
@NoArgsConstructor
//@ApiModel
public class State extends NamedEntity {

    private static final long serialVersionUID = -2379096302409970903L;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    /**
     * Country can be directly connected to State.
     */
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", nullable = true)
    private Region region;

    @OneToMany(mappedBy = "state")
    private List<City> cities;

}
