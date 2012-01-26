/*
 * Copyright (c) 2002-2008, Mairie de Paris
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
package fr.paris.lutece.plugins.stock.utils.dozer;

import java.lang.reflect.InvocationTargetException;

import javax.inject.Inject;

import org.apache.commons.beanutils.BeanUtils;
import org.dozer.CustomConverter;
import org.dozer.DozerBeanMapper;

import fr.paris.lutece.plugins.stock.commons.exception.TechnicalException;
import fr.paris.lutece.plugins.stock.utils.EntityBean;


/**
 * Convertisseur dozer permettant de convertir un objet dans un DTO vers id et
 * inversement.
 * Permet de gérer le problème des id négatifs et null. Dans ce cas, l'objet
 * n'est pas instancié.
 * 
 * @author aBataille
 */
public class CustomDTODozerConverter implements CustomConverter
{
	@Inject
    DozerBeanMapper dozerMapper;

    /**
     * Convertit un id en entité initialisée avec l'id et inversement.
     * {@inheritDoc}
     */
    public Object convert( Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass,
            Class<?> sourceClass )
    {
        if ( sourceFieldValue == null )
        {
            return null;
        }
        else
        {
            if ( !( sourceFieldValue instanceof EntityBean ) )
            {
                Long id;
                try
                {
                    id = Long.valueOf( BeanUtils.getProperty( sourceFieldValue, "id" ) );
                    if ( id == null || id <= 0 )
                    {
                        return null;
                    }
                }
                catch ( NumberFormatException e )
                {
                    throw new TechnicalException(
                            "Problème lors de la conversion d'un " + sourceFieldValue.getClass( ), e );
                }
                catch ( IllegalAccessException e )
                {
                    throw new TechnicalException(
                            "Problème lors de la conversion d'un " + sourceFieldValue.getClass( ), e );
                }
                catch ( InvocationTargetException e )
                {
                    throw new TechnicalException(
                            "Problème lors de la conversion d'un " + sourceFieldValue.getClass( ), e );
                }
                catch ( NoSuchMethodException e )
                {
                    throw new TechnicalException(
                            "Problème lors de la conversion d'un " + sourceFieldValue.getClass( ), e );
                }
            }
            return dozerMapper.map( sourceFieldValue, destinationClass );
        }
    }

}
