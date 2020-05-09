/*
 * Copyright (C) 2020 lamontdozierjr
 *
 * This program is licensed under a Creative Commons Attribution-Share 
 * Alike 3.0 Unported License ("CC-BY-SA"). An explanation of CC-BY-SA can 
 * be found at:
 *
 * <http://creativecommons.org/license/by-sa/3.0/>.
 *
 * Unless required by applicable law or agreed to in writing, this software
 * is distributed on an "AS IS" BASIS, WITHOUT ANY WARRANTY or even 
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 */
package com.lamontd.lahmans.reader.services;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author lamontdozierjr
 */
@Converter(autoApply = true)
public class BooleanConverter implements AttributeConverter<Boolean, String> {

    @Override
    public String convertToDatabaseColumn(Boolean x) {
        if (x != null) {
            return x ? "Y" : "N";
        }
        return null;
    }

    @Override
    public Boolean convertToEntityAttribute(String y) {
        return y == null ? null : y.equals("Y");
    }

}
