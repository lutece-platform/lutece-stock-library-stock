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
package fr.paris.lutece.plugins.stock.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;


/**
 * 
 * Provides utility methods for numbers
 */
public final class NumberUtils
{
    private static final String PADDING = "0";

    /**
     * 
     * Creates a new NumberUtils.java object.
     */
    private NumberUtils( )
    {

    }
    /**
     * Ajouter des 0 à gauche jusqu'à ce que la taille de la chaine soit (au
     * moins) de la taille spécifiée. Si la longueur de la chaine est supérieur
     * à la taille finale demandée elle n'est pas modifiée.
     * @param str Chaine à modifier
     * @param tailleFinale Longueure de la chaine une fois modifiée
     * @return La chaine à modifier plus une série de '0' à gauche pour
     *         atteindre la longueure demandée.
     */
    public static String fillWithZeros( String str, int tailleFinale )
    {
        if ( null != str )
        {
            int diff = tailleFinale - str.length( ); // calcule le nombre de 0 à ajouter;

            if ( diff > 0 )
            {
                str = StringUtils.repeat( NumberUtils.PADDING, diff ) + str;
            }
        }

        return str;
    }

    /**
     * Applique le même comportement que fillWithZeros mais vérifie avant
     * qu'aucun caractère
     * '%' n'est présent dans la chaine et si la chaine n'est pas vide.. Si ce
     * caractère est trouvé la chaine
     * est retournée tel quel sans modifications.
     * @param str Chaine à modifier
     * @param tailleFinale Longueure de la chaine une fois modifiée
     * @return La chaine à modifier plus une série de '0' à gauche pour
     *         atteindre la longueure demandée.
     */
    public static String fillWithZerosIfNoPercentCharFoundAndNotEmpty( String str, int tailleFinale )
    {
        String ret = str;
        if ( StringUtils.isNotEmpty( str ) && !str.contains( "%" ) )
        {
            ret = NumberUtils.fillWithZeros( str, tailleFinale );
        }
        return ret;
    }

    /**
     * Parses the float or return null if exception.
     * 
     * @param value the value
     * @return the float
     */
    public static Float parseFloatOrNull( String value )
    {
        Float ret;
        try
        {
            ret = Float.parseFloat( value );
        }
        catch ( NumberFormatException e )
        {
            ret = null;
        }
        return ret;
    }

    /**
     * Parses the long or null if exception.
     * 
     * @param value the value
     * @return the long
     */
    public static Long parseLongOrNull( String value )
    {
        Long ret;
        try
        {
            ret = Long.parseLong( value );
        }
        catch ( NumberFormatException e )
        {
            ret = null;
        }
        return ret;
    }

    /**
     * Round float.
     * 
     * @param a the a
     * @param n the n
     * @return the float
     */
    public static Float round( Float a, int n )
    {
        double p = Math.pow( 10.0, n );

        return (float) ( Math.floor( ( a * p ) + 0.5 ) / p );
    }

    /**
     * 
     * @param a Un nombre.
     * @return Le nombre tronqué au centième.
     */
    public static String truncateAndAdd2Digits( Float a )
    {
        NumberFormat nf = NumberFormat.getNumberInstance( java.util.Locale.ENGLISH );
        DecimalFormat df = (DecimalFormat) nf;
        df.applyPattern( "#.###" );
        nf.setMaximumFractionDigits( 3 );
        nf.setMinimumFractionDigits( 3 );
        String format = nf.format( a );
        return format.substring( 0, format.length( ) - 1 );
    }

    /**
     * Retourne vrai si la string contient un entier
     * @param number la string
     * @return vrai ou faux
     */
    public static boolean validateInt( String number )
    {
        Pattern p = Pattern.compile( "^[0-9]*?$" );
        Matcher m = p.matcher( number );

        return m.find( );
    }

    /**
     * Retourne vrai si la string contient un nombre
     * @param number le nombre
     * @return vrai ou faux
     */
    public static boolean validateNumber( String number )
    {
        Pattern p = Pattern.compile( "^[0-9]*(\\.[0-9]*)?$" );
        Matcher m = p.matcher( number );

        return m.find( );
    }

    /**
     * @param value Valeur à formater.
     * @return Tente de parser le string en float et le met à zéro si la
     *         conversion ne fonctionne pas.
     */
    public static String truncateAndAdd2Digits( String value )
    {
        String ret;
        try
        {
            ret = NumberUtils.truncateAndAdd2Digits( Float.parseFloat( value ) );
        }
        catch ( NumberFormatException e )
        {
            ret = "";
        }
        return ret;
    }
}
