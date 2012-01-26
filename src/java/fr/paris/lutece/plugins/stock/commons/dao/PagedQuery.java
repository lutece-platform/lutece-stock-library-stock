/*
 * Copyright (c) 2002-2008, Mairie de Paris
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
package fr.paris.lutece.plugins.stock.commons.dao;

import fr.paris.lutece.plugins.stock.commons.ResultList;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Parameter;
import javax.persistence.Query;
import javax.persistence.TemporalType;


/**
 * Adaptater for paginated query
 * 
 * @author abataille
 */
public class PagedQuery implements Query
{

    private Query query;
    private Query countQuery;
    private PaginationProperties paginationProperties;

    /**
     * Creates a new PaginatedQuery.java object.
     * @param query query for getting results
     * @param countQuery query for counting total result
     * @param paginationProperties
     */
    public PagedQuery( Query query, Query countQuery, PaginationProperties paginationProperties )
    {
        super( );
        this.query = query;
        this.countQuery = countQuery;
        this.paginationProperties = paginationProperties;
        
        if ( this.paginationProperties != null )
        {
	        //Calculate pagination properties for query
	        int maxResult = this.paginationProperties.getFirstResult( ) + this.paginationProperties.getPageSize( );
	        this.query.setMaxResults( maxResult );
	        this.query.setFirstResult( this.paginationProperties.getFirstResult( ) );
        }
    }

    /* (non-Javadoc)
     * @see javax.persistence.Query#getResultList()
     */
    public ResultList getResultList( )
    {
        ResultList resultList = new ResultList( );
        Long nbTotalResults = -1L;
        if ( this.countQuery != null )
        {
            nbTotalResults = (Long) this.countQuery.getSingleResult( );
        }
        resultList.addAll( query.getResultList( ) );
        resultList.setTotalResult( nbTotalResults.intValue( ) );
        return resultList;
    }

    /* (non-Javadoc)
     * @see javax.persistence.Query#getSingleResult()
     */
    public Object getSingleResult( )
    {
        return query.getSingleResult( );
    }

    /* (non-Javadoc)
     * @see javax.persistence.Query#executeUpdate()
     */
    public int executeUpdate( )
    {
        return query.executeUpdate( );
    }

    /* (non-Javadoc)
     * @see javax.persistence.Query#setMaxResults(int)
     */
    public Query setMaxResults( int maxResult )
    {
        return query.setMaxResults( maxResult );
    }

    /* (non-Javadoc)
     * @see javax.persistence.Query#getMaxResults()
     */
    public int getMaxResults( )
    {
        return query.getMaxResults( );
    }

    /* (non-Javadoc)
     * @see javax.persistence.Query#setFirstResult(int)
     */
    public Query setFirstResult( int startPosition )
    {
        return query.setFirstResult( startPosition );
    }

    /* (non-Javadoc)
     * @see javax.persistence.Query#getFirstResult()
     */
    public int getFirstResult( )
    {
        return query.getFirstResult( );
    }

    /* (non-Javadoc)
     * @see javax.persistence.Query#setHint(java.lang.String, java.lang.Object)
     */
    public Query setHint( String hintName, Object value )
    {
        return query.setHint( hintName, value );
    }

    /* (non-Javadoc)
     * @see javax.persistence.Query#getHints()
     */
    public Map<String, Object> getHints( )
    {
        return query.getHints( );
    }

    /* (non-Javadoc)
     * @see javax.persistence.Query#setParameter(javax.persistence.Parameter, java.lang.Object)
     */
    public <T> Query setParameter( Parameter<T> param, T value )
    {
        return query.setParameter( param, value );
    }

    /* (non-Javadoc)
     * @see javax.persistence.Query#setParameter(javax.persistence.Parameter, java.util.Calendar, javax.persistence.TemporalType)
     */
    public Query setParameter( Parameter<Calendar> param, Calendar value, TemporalType temporalType )
    {
        return query.setParameter( param, value, temporalType );
    }

    /* (non-Javadoc)
     * @see javax.persistence.Query#setParameter(javax.persistence.Parameter, java.util.Date, javax.persistence.TemporalType)
     */
    public Query setParameter( Parameter<Date> param, Date value, TemporalType temporalType )
    {
        return query.setParameter( param, value, temporalType );
    }

    /* (non-Javadoc)
     * @see javax.persistence.Query#setParameter(java.lang.String, java.lang.Object)
     */
    public Query setParameter( String name, Object value )
    {
        return query.setParameter( name, value );
    }

    /* (non-Javadoc)
     * @see javax.persistence.Query#setParameter(java.lang.String, java.util.Calendar, javax.persistence.TemporalType)
     */
    public Query setParameter( String name, Calendar value, TemporalType temporalType )
    {
        return query.setParameter( name, value, temporalType );
    }

    /* (non-Javadoc)
     * @see javax.persistence.Query#setParameter(java.lang.String, java.util.Date, javax.persistence.TemporalType)
     */
    public Query setParameter( String name, Date value, TemporalType temporalType )
    {
        return query.setParameter( name, value, temporalType );
    }

    /* (non-Javadoc)
     * @see javax.persistence.Query#setParameter(int, java.lang.Object)
     */
    public Query setParameter( int position, Object value )
    {
        return query.setParameter( position, value );
    }

    /* (non-Javadoc)
     * @see javax.persistence.Query#setParameter(int, java.util.Calendar, javax.persistence.TemporalType)
     */
    public Query setParameter( int position, Calendar value, TemporalType temporalType )
    {
        return query.setParameter( position, value, temporalType );
    }

    /* (non-Javadoc)
     * @see javax.persistence.Query#setParameter(int, java.util.Date, javax.persistence.TemporalType)
     */
    public Query setParameter( int position, Date value, TemporalType temporalType )
    {
        return query.setParameter( position, value, temporalType );
    }

    /* (non-Javadoc)
     * @see javax.persistence.Query#getParameters()
     */
    public Set<Parameter<?>> getParameters( )
    {
        return query.getParameters( );
    }

    /* (non-Javadoc)
     * @see javax.persistence.Query#getParameter(java.lang.String)
     */
    public Parameter<?> getParameter( String name )
    {
        return query.getParameter( name );
    }

    /* (non-Javadoc)
     * @see javax.persistence.Query#getParameter(java.lang.String, java.lang.Class)
     */
    public <T> Parameter<T> getParameter( String name, Class<T> type )
    {
        return query.getParameter( name, type );
    }

    /* (non-Javadoc)
     * @see javax.persistence.Query#getParameter(int)
     */
    public Parameter<?> getParameter( int position )
    {
        return query.getParameter( position );
    }

    /* (non-Javadoc)
     * @see javax.persistence.Query#getParameter(int, java.lang.Class)
     */
    public <T> Parameter<T> getParameter( int position, Class<T> type )
    {
        return query.getParameter( position, type );
    }

    /* (non-Javadoc)
     * @see javax.persistence.Query#isBound(javax.persistence.Parameter)
     */
    public boolean isBound( Parameter<?> param )
    {
        return query.isBound( param );
    }

    /* (non-Javadoc)
     * @see javax.persistence.Query#getParameterValue(javax.persistence.Parameter)
     */
    public <T> T getParameterValue( Parameter<T> param )
    {
        return query.getParameterValue( param );
    }

    /* (non-Javadoc)
     * @see javax.persistence.Query#getParameterValue(java.lang.String)
     */
    public Object getParameterValue( String name )
    {
        return query.getParameterValue( name );
    }

    /* (non-Javadoc)
     * @see javax.persistence.Query#getParameterValue(int)
     */
    public Object getParameterValue( int position )
    {
        return query.getParameterValue( position );
    }

    /* (non-Javadoc)
     * @see javax.persistence.Query#setFlushMode(javax.persistence.FlushModeType)
     */
    public Query setFlushMode( FlushModeType flushMode )
    {
        return query.setFlushMode( flushMode );
    }

    /* (non-Javadoc)
     * @see javax.persistence.Query#getFlushMode()
     */
    public FlushModeType getFlushMode( )
    {
        return query.getFlushMode( );
    }

    /* (non-Javadoc)
     * @see javax.persistence.Query#setLockMode(javax.persistence.LockModeType)
     */
    public Query setLockMode( LockModeType lockMode )
    {
        return query.setLockMode( lockMode );
    }

    /* (non-Javadoc)
     * @see javax.persistence.Query#getLockMode()
     */
    public LockModeType getLockMode( )
    {
        return query.getLockMode( );
    }

    /* (non-Javadoc)
     * @see javax.persistence.Query#unwrap(java.lang.Class)
     */
    public <T> T unwrap( Class<T> cls )
    {
        return query.unwrap( cls );
    }

    /**
     * @return the query
     */
    public Query getQuery( )
    {
        return query;
    }

    /**
     * @param query the query to set
     */
    public void setQuery( Query query )
    {
        this.query = query;
    }

}
