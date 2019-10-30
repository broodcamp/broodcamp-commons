package com.broodcamp.data.dto.adm;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.broodcamp.data.dto.BaseEntityDto;
import com.broodcamp.data.dto.shared.ContactInformationDto;
import com.broodcamp.data.dto.shared.NameDto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = false, of = { "username" })
@NoArgsConstructor
public class UserAccountDto extends BaseEntityDto implements Serializable {

    private static final long serialVersionUID = -145690419565926323L;

    @NotNull
    private String externalReference;

    @NotNull
    private String username;

    @NotNull
    private NameDto name;

    private ContactInformationDto contactInformation;
    private SocialAccountDto socialAccount;
    private String profileImageFile;
    private Date birthDate;
    private String employeeNo;
}
