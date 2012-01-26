/*
 * Copyright (c) 2002-2010, Mairie de Paris
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

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.util.ReferenceList;


/**
 * Utilitaire servant à la manipulation des listes
 * 
 */
public class ListUtils
{

    private static final String PROPERTY_LIST_SEPARATOR = ";";
    private static final Logger LOGGER = Logger.getLogger( ListUtils.class );

    /**
     * Conversion d'une liste de type {@link List} vers une
     * {@link ReferenceList}
     * @param list la liste à convertir
     * @param key la valeur de la propriété du bean servant de clé dans la
     *            {@link ReferenceList}
     * @param value la valeur de la propriété du bean servant de valeur dans la
     *            {@link ReferenceList}
     * @param firstItem valeur de la première ligne dans la
     *            {@link ReferenceList} (exemple, en vue d'afficher la
     *            ReferenceList dans une liste déroulante :
     *            " -- Sélectionnez une valeur --").
     * @return La {@link ReferenceList} peuplée avec les données de la Liste
     */
    public static ReferenceList toReferenceList( List<?> list, String key, String value, String firstItem )
    {
        ReferenceList referenceList = new ReferenceList( );
        String valeurKey;
        String valeurValue;

        try
        {
            if ( firstItem != null )
            {
                referenceList.addItem( "-1", firstItem );
            }

            for ( Object element : list )
            {
                valeurKey = BeanUtils.getSimpleProperty( element, key );
                valeurValue = BeanUtils.getSimpleProperty( element, value );
                referenceList.addItem( valeurKey, valeurValue );
            }
        }
        catch ( IllegalAccessException e )
        {
            LOGGER.warn( "Erreur lors de la création d'une liste pour combo : " + e.getMessage( ), e );
        }
        catch ( InvocationTargetException e )
        {
            LOGGER.warn( "Erreur lors de la création d'une liste pour combo : " + e.getMessage( ), e );
        }
        catch ( NoSuchMethodException e )
        {
            LOGGER.warn( "Erreur lors de la création d'une liste pour combo : " + e.getMessage( ), e );
        }
        catch ( Exception e )
        {
            LOGGER.warn( "Erreur lors de la création d'une liste pour combo : " + e.getMessage( ), e );
        }

        return referenceList;
    }

    /**
     * Return
     * @param propertyKey
     * @return
     */
    public static List<String> getPropertyList( String propertyKey )
    {
        String property = AppPropertiesService.getProperty( propertyKey );
        if ( property != null )
        {
            String[] items = property.split( PROPERTY_LIST_SEPARATOR );
            if ( items != null )
            {
                return Arrays.asList( items );
            }
        }
        return null;
    }
}
