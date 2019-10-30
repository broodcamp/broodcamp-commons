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
package com.broodcamp.data.entity.adm;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.broodcamp.data.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 **/
@Entity
@Cacheable
@Table(name = "adm_social_account")
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class SocialAccount extends BaseEntity {

    private static final long serialVersionUID = -6868804229424530068L;

    @Column(name = "facebook", length = 250)
    private String facebook;

    @Column(name = "instagram", length = 250)
    private String instagram;

    @Column(name = "pinterest", length = 250)
    private String pinterest;

    @Column(name = "google", length = 250)
    private String google;

    @Column(name = "youtube", length = 250)
    private String youtube;

    @Column(name = "twitter", length = 250)
    private String twitter;

    @Column(name = "website", length = 250)
    private String website;

    @Column(name = "blog", length = 250)
    private String blog;
}
