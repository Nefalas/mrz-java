/**
 * Java parser for the MRZ records, as specified by the ICAO organization.
 * Copyright (C) 2011 Innovatrics s.r.o.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package com.innovatrics.mrz.records;

import com.innovatrics.mrz.MrzParser;
import com.innovatrics.mrz.MrzRange;
import com.innovatrics.mrz.MrzRecord;
import com.innovatrics.mrz.types.MrzFormat;

/**
 * MRTD td2 format: A two line long, 36 characters per line format.
 * @author Martin Vysny
 */
public class MrtdTd2 extends MrzRecord {
    private static final long serialVersionUID = 1L;

    public MrtdTd2() {
        super(MrzFormat.MRTD_TD2);
    }
    /**
     * For use of the issuing State or 
    organization. Unused character positions 
    shall be completed with filler characters (&lt;)
    repeated up to position 35 as required. 
     */
    public String optional;

    @Override
    public void fromMrz(String mrz) {
        super.fromMrz(mrz);
        final MrzParser p = new MrzParser(mrz);
        setName(p.parseName(new MrzRange(5, 36, 0)));
        documentNumber = p.parseString(new MrzRange(0, 9, 1));
        p.checkDigit(9, 1, new MrzRange(0, 9, 1), "document number");
        nationality = p.parseString(new MrzRange(10, 13, 1));
        dateOfBirth = p.parseDate(new MrzRange(13, 19, 1));
        p.checkDigit(19, 1, new MrzRange(13, 19, 1), "date of birth");
        sex = p.parseSex(20, 1);
        expirationDate = p.parseDate(new MrzRange(21, 27, 1));
        p.checkDigit(27, 1, new MrzRange(21, 27, 1), "expiration date");
        optional = p.parseString(new MrzRange(28, 35, 1));
        p.checkDigit(35, 1, p.rawValue(new MrzRange(0, 10, 1), new MrzRange(13, 20, 1), new MrzRange(21, 35, 1)), "mrz");
    }

    @Override
    public String toString() {
        return "MRTD-TD2{" + super.toString() + ", optional=" + optional + '}';
    }
}
