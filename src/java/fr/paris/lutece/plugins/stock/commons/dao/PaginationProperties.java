package fr.paris.lutece.plugins.stock.commons.dao;


public interface PaginationProperties
{

    /**
     * Returns index of the first result requested
     * @return index of the first result requested
     */
    public abstract int getFirstResult( );

    /**
     * Returns number of results per page requested
     * @return number of results per page requested
     */
    public abstract int getPageSize( );

}