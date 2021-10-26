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
/**
 * 
 */
package fr.paris.lutece.plugins.stock.utils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;

import org.apache.log4j.Logger;

/**
 * <code>MultiPartFormOutputStream</code> is used to write "multipart/form-data" to a <code>java.net.URLConnection</code> for POSTing. This is primarily for
 * file uploading to HTTP servers.
 * 
 * @since JDK1.3
 */
public class MultiPartFormOutputStream
{
    private static final Logger LOGGER = Logger.getLogger( MultiPartFormOutputStream.class );
    /**
     * The line end characters.
     */
    private static final String NEWLINE = "\r\n";

    /**
     * The boundary prefix.
     */
    private static final String PREFIX = "--";

    /**
     * The output stream to write to.
     */
    private DataOutputStream _out;

    /**
     * The multipart boundary string.
     */
    private String _boundary;

    /**
     * Creates a new <code>MultiPartFormOutputStream</code> object using the specified output stream and boundary. The boundary is required to be created before
     * using this method, as described in the description for the <code>getContentType(String)</code> method. The boundary is only checked for <code>null</code>
     * or empty string, but it is recommended to be at least 6 characters. (Or use the static createBoundary() method to create one.)
     * 
     * @param os
     *            the output stream
     * @param boundary
     *            the boundary
     * @see #createBoundary()
     * @see #getContentType(String)
     */
    public MultiPartFormOutputStream( final OutputStream os, final String boundary )
    {
        if ( os == null )
        {
            throw new IllegalArgumentException( "Output stream is required." );
        }
        if ( boundary == null || boundary.length( ) == 0 )
        {
            throw new IllegalArgumentException( "Boundary stream is required." );
        }
        this._out = new DataOutputStream( os );
        this._boundary = boundary;
    }

    /**
     * Writes an boolean field value.
     * 
     * @param name
     *            the field name (required)
     * @param value
     *            the field value
     * @throws java.io.IOException
     *             on input/output errors
     */
    public void writeField( final String name, final boolean value ) throws java.io.IOException
    {
        writeField( name, new Boolean( value ).toString( ) );
    }

    /**
     * Writes an double field value.
     * 
     * @param name
     *            the field name (required)
     * @param value
     *            the field value
     * @throws java.io.IOException
     *             on input/output errors
     */
    public void writeField( final String name, final double value ) throws java.io.IOException
    {
        writeField( name, Double.toString( value ) );
    }

    /**
     * Writes an float field value.
     * 
     * @param name
     *            the field name (required)
     * @param value
     *            the field value
     * @throws java.io.IOException
     *             on input/output errors
     */
    public void writeField( final String name, final float value ) throws java.io.IOException
    {
        writeField( name, Float.toString( value ) );
    }

    /**
     * Writes an long field value.
     * 
     * @param name
     *            the field name (required)
     * @param value
     *            the field value
     * @throws java.io.IOException
     *             on input/output errors
     */
    public void writeField( final String name, final long value ) throws java.io.IOException
    {
        writeField( name, Long.toString( value ) );
    }

    /**
     * Writes an int field value.
     * 
     * @param name
     *            the field name (required)
     * @param value
     *            the field value
     * @throws java.io.IOException
     *             on input/output errors
     */
    public void writeField( final String name, final int value ) throws java.io.IOException
    {
        writeField( name, Integer.toString( value ) );
    }

    /**
     * Writes an short field value.
     * 
     * @param name
     *            the field name (required)
     * @param value
     *            the field value
     * @throws java.io.IOException
     *             on input/output errors
     */
    public void writeField( final String name, final short value ) throws java.io.IOException
    {
        writeField( name, Short.toString( value ) );
    }

    /**
     * Writes an char field value.
     * 
     * @param name
     *            the field name (required)
     * @param value
     *            the field value
     * @throws java.io.IOException
     *             on input/output errors
     */
    public void writeField( final String name, final char value ) throws java.io.IOException
    {
        writeField( name, new Character( value ).toString( ) );
    }

    /**
     * Writes an string field value. If the value is null, an empty string is sent ("").
     * 
     * @param name
     *            the field name (required)
     * @param value
     *            the field value
     * @throws java.io.IOException
     *             on input/output errors
     */
    public void writeField( final String name, String value ) throws java.io.IOException
    {
        if ( name == null )
        {
            throw new IllegalArgumentException( "Name cannot be null or empty." );
        }
        if ( value == null )
        {
            value = "";
        }
        /*
         * --boundary\r\n Content-Disposition: form-data; name="<fieldName>"\r\n \r\n <value>\r\n
         */
        // write boundary
        _out.writeBytes( PREFIX );
        _out.writeBytes( _boundary );
        _out.writeBytes( NEWLINE );
        // write content header
        _out.writeBytes( "Content-Disposition: form-data; name=\"" + name + "\"" );
        _out.writeBytes( NEWLINE );
        _out.writeBytes( NEWLINE );
        // write content
        _out.writeBytes( value );
        _out.writeBytes( NEWLINE );
        _out.flush( );
    }

    /**
     * Writes a file's contents. If the file is null, does not exists, or is a directory, a <code>java.lang.IllegalArgumentException</code> will be thrown.
     * 
     * @param name
     *            the field name
     * @param mimeType
     *            the file content type (optional, recommended)
     * @param file
     *            the file (the file must exist)
     * @throws java.io.IOException
     *             on input/output errors
     */
    public void writeFile( final String name, final String mimeType, final File file ) throws java.io.IOException
    {
        if ( file == null )
        {
            throw new IllegalArgumentException( "File cannot be null." );
        }
        if ( !file.exists( ) )
        {
            throw new IllegalArgumentException( "File does not exist." );
        }
        if ( file.isDirectory( ) )
        {
            throw new IllegalArgumentException( "File cannot be a directory." );
        }
        writeFile( name, mimeType, file.getName( ), new FileInputStream( file ) );
    }

    /**
     * Writes a input stream's contents. If the input stream is null, a <code>java.lang.IllegalArgumentException</code> will be thrown.
     * 
     * @param name
     *            the field name
     * @param mimeType
     *            the file content type (optional, recommended)
     * @param fileName
     *            the file name (required)
     * @param is
     *            the input stream
     * @throws java.io.IOException
     *             on input/output errors
     */
    public void writeFile( final String name, final String mimeType, final String fileName, final InputStream is ) throws java.io.IOException
    {
        if ( is == null )
        {
            throw new IllegalArgumentException( "Input stream cannot be null." );
        }
        if ( fileName == null || fileName.length( ) == 0 )
        {
            throw new IllegalArgumentException( "File name cannot be null or empty." );
        }

        Calendar cal = Calendar.getInstance( );
        String uUID = String.valueOf( cal.get( Calendar.YEAR ) ) + String.valueOf( cal.get( Calendar.MONTH ) )
                + String.valueOf( cal.get( Calendar.DAY_OF_MONTH ) ) + String.valueOf( cal.get( Calendar.HOUR_OF_DAY ) )
                + String.valueOf( cal.get( Calendar.MINUTE ) ) + String.valueOf( cal.get( Calendar.SECOND ) );

        /*
         * --boundary\r\n Content-Disposition: form-data; name="<fieldName>"; filename="<filename>"\r\n Content-Type: <mime-type>\r\n \r\n <file-data>\r\n
         */
        // write boundary
        _out.writeBytes( PREFIX );
        _out.writeBytes( _boundary );
        _out.writeBytes( NEWLINE );
        // write content header
        _out.writeBytes( "Content-Disposition: form-data; name=\"" + name + "\"; filename=\"" + uUID + "_" + fileName + "\"" );
        _out.writeBytes( NEWLINE );
        if ( mimeType != null )
        {
            _out.writeBytes( "Content-Type: " + mimeType );
            _out.writeBytes( NEWLINE );
        }
        _out.writeBytes( NEWLINE );
        // write content
        byte [ ] data = new byte [ 1024];
        int r = 0;
        while ( ( r = is.read( data, 0, data.length ) ) != -1 )
        {
            _out.write( data, 0, r );
        }
        // close input stream, but ignore any possible exception for it
        try
        {
            is.close( );
        }
        catch( Exception e )
        {
            LOGGER.warn( e.getMessage( ), e );
        }
        _out.writeBytes( NEWLINE );
        _out.flush( );
    }

    /**
     * Writes the given bytes. The bytes are assumed to be the contents of a file, and will be sent as such. If the data is null, a
     * <code>java.lang.IllegalArgumentException</code> will be thrown.
     * 
     * @param name
     *            the field name
     * @param mimeType
     *            the file content type (optional, recommended)
     * @param fileName
     *            the file name (required)
     * @param data
     *            the file data
     * @throws java.io.IOException
     *             on input/output errors
     */
    public void writeFile( final String name, final String mimeType, final String fileName, final byte [ ] data ) throws java.io.IOException
    {
        if ( data == null )
        {
            throw new IllegalArgumentException( "Data cannot be null." );
        }
        if ( fileName == null || fileName.length( ) == 0 )
        {
            throw new IllegalArgumentException( "File name cannot be null or empty." );
        }

        Calendar cal = Calendar.getInstance( );
        String uUID = String.valueOf( cal.get( Calendar.YEAR ) ) + String.valueOf( cal.get( Calendar.MONTH ) )
                + String.valueOf( cal.get( Calendar.DAY_OF_MONTH ) ) + String.valueOf( cal.get( Calendar.HOUR_OF_DAY ) )
                + String.valueOf( cal.get( Calendar.MINUTE ) ) + String.valueOf( cal.get( Calendar.SECOND ) );

        /*
         * --boundary\r\n Content-Disposition: form-data; name="<fieldName>"; filename="<filename>"\r\n Content-Type: <mime-type>\r\n \r\n <file-data>\r\n
         */
        // write boundary
        _out.writeBytes( PREFIX );
        _out.writeBytes( _boundary );
        _out.writeBytes( NEWLINE );
        // write content header
        _out.writeBytes( "Content-Disposition: form-data; name=\"" + name + "\"; filename=\"" + uUID + "_" + fileName + "\"" );
        _out.writeBytes( NEWLINE );
        if ( mimeType != null )
        {
            _out.writeBytes( "Content-Type: " + mimeType );
            _out.writeBytes( NEWLINE );
        }
        _out.writeBytes( NEWLINE );
        // write content
        _out.write( data, 0, data.length );
        _out.writeBytes( NEWLINE );
        _out.flush( );
    }

    /**
     * Flushes the stream. Actually, this method does nothing, as the only write methods are highly specialized and automatically flush.
     * 
     * @throws java.io.IOException
     *             on input/output errors
     */
    public void flush( ) throws java.io.IOException
    {
        // out.flush();
    }

    /**
     * Closes the stream. <br />
     * <br />
     * <b>NOTE:</b> This method <b>MUST</b> be called to finalize the multipart stream.
     * 
     * @throws java.io.IOException
     *             on input/output errors
     */
    public void close( ) throws java.io.IOException
    {
        // write final boundary
        _out.writeBytes( PREFIX );
        _out.writeBytes( _boundary );
        _out.writeBytes( PREFIX );
        _out.writeBytes( NEWLINE );
        _out.flush( );
        _out.close( );
    }

    /**
     * Gets the multipart boundary string being used by this stream.
     * 
     * @return the boundary
     */
    public String getBoundary( )
    {
        return this._boundary;
    }

    /**
     * Creates a new <code>java.net.URLConnection</code> object from the specified <code>java.net.URL</code>. This is a convenience method which will set the
     * <code>doInput</code>, <code>doOutput</code>, <code>useCaches</code> and <code>defaultUseCaches</code> fields to the appropriate settings in the correct
     * order.
     * 
     * @param url
     *            the url
     * @return a <code>java.net.URLConnection</code> object for the URL
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public static URLConnection createConnection( final URL url ) throws java.io.IOException
    {
        URLConnection urlConn = url.openConnection( );
        if ( urlConn instanceof HttpURLConnection )
        {
            HttpURLConnection httpConn = (HttpURLConnection) urlConn;
            httpConn.setRequestMethod( "POST" );
        }
        urlConn.setDoInput( true );
        urlConn.setDoOutput( true );
        urlConn.setUseCaches( false );
        urlConn.setDefaultUseCaches( false );
        return urlConn;
    }

    /**
     * Creates a multipart boundary string by concatenating 20 hyphens (-) and the hexadecimal (base-16) representation of the current time in milliseconds.
     * 
     * @return a multipart boundary string
     * @see #getContentType(String)
     */
    public static String createBoundary( )
    {
        return "--------------------" + Long.toString( System.currentTimeMillis( ), 16 );
    }

    /**
     * Gets the content type string suitable for the <code>java.net.URLConnection</code> which includes the multipart boundary string. <br />
     * <br />
     * This method is static because, due to the nature of the <code>java.net.URLConnection</code> class, once the output stream for the connection is acquired,
     * it's too late to set the content type (or any other request parameter). So one has to create a multipart boundary string first before using this class,
     * such as with the <code>createBoundary()</code> method.
     * 
     * @param boundary
     *            the boundary string
     * @return the content type string
     * @see #createBoundary()
     */
    public static String getContentType( final String boundary )
    {
        return "multipart/form-data; charset=iso-8859-1; boundary=" + boundary;
    }
}
