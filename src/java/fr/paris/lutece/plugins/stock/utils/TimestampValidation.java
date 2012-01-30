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

import java.sql.Timestamp;


/**
 * Validateur de {@link Timestamp}
 * 
 */
public class TimestampValidation extends Timestamp
{
    public static final int ERROR_DATE_FORMAT = 1;
    private static final long serialVersionUID = 1L;
    private String _strErrorValue;
    private int _nIdTypeError;

    /**
     * Constructeur de la classe
     * @param strErrorValue La valeur de l'erreur
     */
    public TimestampValidation( String strErrorValue )
    {
        super( 1 );
        setErrorValue( strErrorValue );
    }

    /**
     * Constructeur de la classe
     * @param arg0 Argument 1
     * @param arg1 Argument 2
     * @param arg2 Argument 3
     * @param arg3 Argument 4
     * @param arg4 Argument 5
     * @param arg5 Argument 6
     * @param arg6 Argument 7
     * @deprecated
     */
    public TimestampValidation( int arg0, int arg1, int arg2, int arg3, int arg4, int arg5, int arg6 )
    {
        super( arg0, arg1, arg2, arg3, arg4, arg5, arg6 );
    }

    /**
     * Retourne l'id du type de l'erreur
     * @return l'id du type de l'erreur
     */
    public int getIdTypeError( )
    {
        return _nIdTypeError;
    }

    /**
     * Initialise l'id du type de l'erreur
     * @param nIdTypeEror l'id du type de l'erreur
     */
    public void setIdTypeError( int nIdTypeEror )
    {
        _nIdTypeError = nIdTypeEror;
    }

    /**
     * Retourne la valeur de l'erreur
     * @return la valeur de l'erreur
     */
    public String getErrorValue( )
    {
        return _strErrorValue;
    }

    /**
     * Initialise la valeur de l'erreur
     * @param strErrorValue La valeur de l'erreur
     */
    public void setErrorValue( String strErrorValue )
    {
        _strErrorValue = strErrorValue;
    }

    @Override
    public boolean equals( Object o )
    {
        return super.equals( o );
    }

    @Override
    public int hashCode( )
    {
        return super.hashCode( );
    }
}
