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

import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.broodcamp.data.entity.EnableEntity;
import com.broodcamp.data.entity.shared.ContactInformation;
import com.broodcamp.data.entity.shared.Name;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
@Entity
@Cacheable
@Table(name = "adm_user_account", uniqueConstraints = { @UniqueConstraint(columnNames = { "email" }) })
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class UserAccount extends EnableEntity {

    private static final long serialVersionUID = -234791854519231738L;

    @NotNull
    @Column(name = "external_reference", length = 50, updatable = false)
    private String externalReference;

    @NotNull
    @Column(name = "username", length = 50, updatable = false)
    private String username;

    @Embedded
    private Name name = new Name();

    @Embedded
    private ContactInformation contactInformation = new ContactInformation();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "social_account_id")
    private SocialAccount socialAccount;

    @Column(name = "profile_image_file", length = 200)
    private String profileImageFile;

    @Temporal(TemporalType.DATE)
    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "employee_number", length = 50)
    private String employeeNo;

    public String getNameOrUsername() {
        if (name != null && name.getFullName().length() > 0) {
            return name.getFullName();
        }

        return username;
    }

    public void setEmail(String email) {
        if (getContactInformation() == null) {
            setContactInformation(new ContactInformation());
        }

        getContactInformation().setEmail(email);
    }

    public String getEmail() {
        if (getContactInformation() == null) {
            setContactInformation(new ContactInformation());
        }

        return getContactInformation().getEmail();
    }
}
