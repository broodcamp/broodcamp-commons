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
package com.broodcamp.web.assembler.adm;

import org.springframework.stereotype.Component;

import com.broodcamp.business.domain.adm.CountryDto;
import com.broodcamp.web.application.adm.CountryController;
import com.broodcamp.web.assembler.AbstractNamedResourceAssembler;

/**
 * @author Edward P. Legaspi | czetsuya@gmail.com
 */
@Component
public class CountryResourceAssembler extends AbstractNamedResourceAssembler<CountryDto> {

    public CountryResourceAssembler() {

        super(CountryController.class);
    }

}
