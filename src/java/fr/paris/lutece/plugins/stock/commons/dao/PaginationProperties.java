package fr.paris.lutece.plugins.stock.commons.dao;


/**
 * The Interface PaginationProperties.
 */
public interface PaginationProperties
{

    /**
     * Returns index of the first result requested.
     * 
     * @return index of the first result requested
     */
    int getFirstResult( );

    /**
     * Returns number of results per page requested.
     * 
     * @return number of results per page requested
     */
    int getPageSize( );

}