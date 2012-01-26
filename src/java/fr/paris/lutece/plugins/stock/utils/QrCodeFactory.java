package fr.paris.lutece.plugins.stock.utils;

import java.awt.Image;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import fr.paris.lutece.portal.service.util.AppPropertiesService;


/**
 * Utility class used for generating qr code image
 * 
 * @author abataille
 */
public class QrCodeFactory
{
    private static final Logger LOGGER = Logger.getLogger( QrCodeFactory.class );

    private static final Integer QR_CODE_SIZE = AppPropertiesService.getPropertyInt( "stock.qrcode.size", 80 );

    /**
     * Generates a qr code image of the provided content
     * @param content string of content contained in qr code
     * @return image of qr code
     */
    public static Image generate( String content )
    {
        try
        {
            MultiFormatWriter writer = new MultiFormatWriter( );
            HashMap hints = new HashMap( );
            hints.put( EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q );
            return MatrixToImageWriter
.toBufferedImage( writer.encode( content, BarcodeFormat.QR_CODE, QR_CODE_SIZE,
                    QR_CODE_SIZE, hints ) );
        }
        catch ( Exception e )
        {
            LOGGER.error( "Erreur lors de la génération d'un qr code : " + e.getMessage( ), e );
        }
        return null;

    }
}
