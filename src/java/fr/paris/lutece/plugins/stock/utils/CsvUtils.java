package fr.paris.lutece.plugins.stock.utils;

import fr.paris.lutece.plugins.stock.commons.AbstractDTO;
import fr.paris.lutece.plugins.stock.commons.ResultList;
import fr.paris.lutece.plugins.stock.commons.exception.TechnicalException;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.util.Properties;

import org.apache.commons.beanutils.BeanUtils;

import au.com.bytecode.opencsv.CSVWriter;


public class CsvUtils
{

    private static final String PROPERTY_RESOURCES_LIBRARY_CSV_PROPERTIES;
    private static final char PROPERTY_SEPARATEUR_CSV;
    private static final String PROPERTY_ENCODING_CSV = "UTF-8";
    private static final String PARAMETER_HEADER_CSV = ".entete";
    private static final String PARAMETER_SPLIT_CSV = ",";
    private static final String PARAMETER_FIELD_CSV = ".champs";

    static
    {
        PROPERTY_RESOURCES_LIBRARY_CSV_PROPERTIES = AppPropertiesService
                .getProperty( "stock-billetterie.csv.configuration.path" );
        PROPERTY_SEPARATEUR_CSV = AppPropertiesService.getProperty( "stock-billetterie.csv.separato", ";" ).charAt( 0 );
    }

    /**
     * Ecrit sur la sortie passée en paramètre, les lignes de csv.
     * Attention : ne gère pas l'écriture des en-têtes (content type par
     * exemple) ni la fermeture du flux de sortie.
     * @param cle
     * @param <R> type du DTO résultat
     * @param businessDTO
     * @param listeResultat
     * @param out
     */
    public static <R extends AbstractDTO> void ecrireCsv( String cle, ResultList<R> listeResultat, OutputStream out )
    {
        if ( listeResultat != null )
        {
            String nomDTO = cle;

            //Chargement du fichier de properties
            InputStream isProprieteCsv = CsvUtils.class.getClassLoader( ).getResourceAsStream(
                    PROPERTY_RESOURCES_LIBRARY_CSV_PROPERTIES );
            if ( isProprieteCsv == null )
            {
                throw new TechnicalException( "Fichier " + PROPERTY_RESOURCES_LIBRARY_CSV_PROPERTIES + " non trouvé." );
            }
            else
            {
                try
                {
                    Properties proprieteCsv = new Properties( );
                    proprieteCsv.load( isProprieteCsv );
                    CSVWriter csvWriter = new CSVWriter( new OutputStreamWriter( out, PROPERTY_ENCODING_CSV ),
                            PROPERTY_SEPARATEUR_CSV );

                    //Récupération des libellés d'en-tête
                    String sListeEntete = proprieteCsv.getProperty( nomDTO + PARAMETER_HEADER_CSV );
                    String[] listeEntete = sListeEntete.split( PARAMETER_SPLIT_CSV );

                    for ( int i = 0; i < listeEntete.length; i++ )
                    {
                        listeEntete[i] = I18nService.getLocalizedString( listeEntete[i], Locale.getDefault( ) );

                    }
                    csvWriter.writeNext( listeEntete );

                    if ( !listeResultat.isEmpty( ) )
                    {
                        //Récupération des libellés des champs
                        String sListeChamps = proprieteCsv.getProperty( nomDTO + PARAMETER_FIELD_CSV );
                        String[] listeChamps = sListeChamps.split( PARAMETER_SPLIT_CSV );

                        //Ecriture des lignes de résultat
                        for ( R beanDTO : listeResultat )
                        {
                            String[] ligneCsv = new String[listeChamps.length];
                            int i = 0;
                            for ( String colonne : listeChamps )
                            {
                                ligneCsv[i] = BeanUtils.getNestedProperty( beanDTO, colonne );
                                i++;

                            }
                            csvWriter.writeNext( ligneCsv );

                        }
                    }

                    csvWriter.flush( );
                    csvWriter.close( );

                }
                catch ( IOException e )
                {
                    throw new TechnicalException( "Problème lors de l'édition du fichier CSV : " + e.getMessage( ), e );
                }
                catch ( IllegalAccessException e )
                {
                    throw new TechnicalException( "Problème lors de l'édition du fichier CSV : " + e.getMessage( ), e );
                }
                catch ( InvocationTargetException e )
                {
                    throw new TechnicalException( "Problème lors de l'édition du fichier CSV : " + e.getMessage( ), e );
                }
                catch ( NoSuchMethodException e )
                {
                    throw new TechnicalException( "Problème lors de l'édition du fichier CSV : " + e.getMessage( ), e );
                }
            }

        }
    }
}
