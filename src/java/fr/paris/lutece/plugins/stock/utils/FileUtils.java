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

import fr.paris.lutece.plugins.stock.commons.exception.TechnicalException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.FilenameUtils;


/**
 * The Class FileUtils.
 */
public class FileUtils
{

    /**
     * 
     * Creates a new FileUtils.java object.
     */
    private FileUtils( )
    {

    }
    /**
     * Write content from input stream to the disk.
     * @param is input stream
     * @param file file where data will be stored
     */
    public static void writeInputStreamToFile( InputStream is, File file )
    {
        try
        {
            OutputStream out = new FileOutputStream( file );
            byte[] buf = new byte[1024];
            int len;
            while ( ( len = is.read( buf ) ) > 0 )
            {
                out.write( buf, 0, len );
            }
            out.close( );
            is.close( );
        }
        catch ( IOException e )
        {
            throw new TechnicalException( "Erreur lors de l'écriture du flux dans le fichier " + file.getName( ), e );
        }
    }

    /**
     * If file exists, generate a file with non existing file name (for example
     * fileexist-1.jpg)
     * 
     * @param file file to rename
     * @return the unique file
     */
    public static File getUniqueFile( File file )
    {
        // Generate unique name
        int i = 1;
        String newFileName;
        String baseName = FilenameUtils.getBaseName( file.getName( ) );
        String ext = FilenameUtils.getExtension( file.getName( ) );
        while ( file.exists( ) )
        {
            newFileName = baseName + "-" + i + "." + ext;
            file = new File( file.getParentFile( ), newFileName );
            i++;
        }
        return file;
    }
}
