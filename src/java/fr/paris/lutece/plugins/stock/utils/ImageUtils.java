/*
 * Copyright (c) 2002-2013, Mairie de Paris
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

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.imgscalr.Scalr;


/**
 * Class for manipulating images
 * 
 * @author abataille
 */
public final class ImageUtils
{
    private static final Logger LOGGER = Logger.getLogger( ImageUtils.class );

    /**
     * 
     * Creates a new ImageUtils.java object.
     */
    private ImageUtils( )
    {

    }
    /**
     * Create a thumbnail and write it into a new file prefixed by tb_
     * (images/lutece.jpg => images/lutece_tb.jpg)
     * 
     * @param fImage source image file
     * @param width max width of the thumbnail
     * @param height the height
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static File createThumbnail( File fImage, int width, int height ) throws IOException
    {
        BufferedImage image = ImageIO.read( fImage );
        BufferedImage resizedImage;
        // Resize image if width is superior to target thumb width
        if ( image.getWidth( ) > width || image.getHeight( ) > height )
        {
            resizedImage = Scalr.resize( image, Scalr.Mode.AUTOMATIC, width, height );
        }
        else
        {
            LOGGER.debug( "Image " + fImage.getName( ) + " non redimensionnée car déjà plus petite que " + width + "x"
                    + height );
            resizedImage = image;
        }
        // Create a new file
        File fThumb = new File( fImage.getParent( ), "tb_" + fImage.getName( ) );
        ImageIO.write( resizedImage, "jpg", fThumb );

        LOGGER.debug( "Thumbnail créé " + fThumb.getAbsolutePath( ) );
        return fThumb;
    }
}
