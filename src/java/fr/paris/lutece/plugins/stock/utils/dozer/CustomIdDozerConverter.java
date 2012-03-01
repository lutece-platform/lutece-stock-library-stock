/*
 * Copyright (c) 2002-2012, Mairie de Paris
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

import fr.paris.lutece.plugins.stock.utils.EntityBean;

import javax.inject.Inject;

import org.dozer.CustomConverter;
import org.dozer.DozerBeanMapper;


/**
 * Convertisseur dozer permettant de convertir un objet dans un DTO vers id et
 * inversement.
 * Permet de gèrer le problème des id négatifs et null. Dans ce cas, l'objet
 * n'est pas instancié.
 * 
 * @author aBataille
 */
public class CustomIdDozerConverter implements CustomConverter
{
	@Inject
    private DozerBeanMapper _dozerMapper;

    /**
     * Convertit un id en entité initialisée avec l'id et inversement.
     * {@inheritDoc}
     */
    public Object convert( Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass,
            Class<?> sourceClass )
    {
        Object retour = null;
        if ( sourceFieldValue != null )
        {
            //Conversion ID -> entité
            if ( sourceClass == Integer.class )
            {
                Integer id = (Integer) sourceFieldValue;
                //Si -1 on instancie pas de nouvel objet
                if ( id <= 0 )
                {
                    retour = null;
                }
                else
                {
                    EntityBean entiteDest;
                    if ( existingDestinationFieldValue == null )
                    {
                        entiteDest = (EntityBean) _dozerMapper.map( sourceFieldValue, destinationClass );
                    }
                    else
                    {
                        entiteDest = (EntityBean) existingDestinationFieldValue;
                    }
                    entiteDest.setId( id );
                    retour = entiteDest;
                }
            }
            //Conversion entité -> ID
            else
            {
                EntityBean entiteSrc = (EntityBean) sourceFieldValue;
                int idDest = entiteSrc.getId( );
                retour = idDest;
            }
        }
        else
        {
            retour = null;
        }
        return retour;
    }

}
