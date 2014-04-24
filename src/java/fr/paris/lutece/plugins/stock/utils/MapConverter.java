/*
 * Copyright (c) 2002-2014, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.stock.utils;


import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;


/**
 * Convertisseu de {@link Timestamp}
 * 
 */
public class MapConverter implements Converter
{
    /**
     * Méthode permettant de convertir une date d'un type à l'autre
     * @param type Type de la date passée en paramètre
     *            Les types suivants peuvent être précisés :
     *            - {@link String} - {@link Timestamp} - {@link Date} -
     *            {@link Calendar}
     * @param value date à convertir en {@link Timestamp}
     * @return une date de type {@link Timestamp} ou null en cas d'erreur
     */
    public Object convert( Class type, Object value ) throws ConversionException
    {
        if ( value != null )
        {
            // Support Calendar and Timestamp conversion
            if ( value instanceof String )
            {
                if ( !( (String) value ).trim( ).equals( "" ) )
                {
                    Timestamp date = DateUtils.getDate( (String) value, true );

                    if ( date == null )
                    {
                        TimestampValidation timestampValidation = new TimestampValidation( (String) value );
                        timestampValidation.setIdTypeError( TimestampValidation.ERROR_DATE_FORMAT );

                        return timestampValidation;
                    }

                    return date;
                }
            }

            if ( value instanceof Timestamp )
            {
                return value;
            }
            else if ( value instanceof Date )
            {
                return new Timestamp( ( (Date) value ).getTime( ) );
            }
            else if ( value instanceof Calendar )
            {
                return new Timestamp( ( (Calendar) value ).getTime( ).getTime( ) );
            }
        }

        return null;
    }
}
