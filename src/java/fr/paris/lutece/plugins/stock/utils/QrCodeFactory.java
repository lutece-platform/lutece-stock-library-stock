/*
 * Copyright (c) 2002-2021, City of Paris
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

import fr.paris.lutece.portal.service.util.AppPropertiesService;

import java.awt.Image;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * Utility class used for generating qr code image.
 * 
 * @author abataille
 */
public final class QrCodeFactory
{

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger( QrCodeFactory.class );

    /** The Constant QR_CODE_SIZE. */
    private static final Integer QR_CODE_SIZE = AppPropertiesService.getPropertyInt( "stock.qrcode.size", 80 );

    /**
     * Utility class
     */
    private QrCodeFactory( )
    {

    }

    /**
     * Generates a qr code image of the provided content.
     * 
     * @param content
     *            string of content contained in qr code
     * @return image of qr code
     */
    public static Image generate( String content )
    {
        try
        {
            MultiFormatWriter writer = new MultiFormatWriter( );
            HashMap<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>( );
            hints.put( EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q );
            return MatrixToImageWriter.toBufferedImage( writer.encode( content, BarcodeFormat.QR_CODE, QR_CODE_SIZE, QR_CODE_SIZE, hints ) );
        }
        catch( Exception e )
        {
            LOGGER.error( "Erreur lors de la génération d'un qr code : " + e.getMessage( ), e );
        }
        return null;

    }
}
