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
package fr.paris.lutece.plugins.stock.commons.dao;

import fr.paris.lutece.plugins.stock.commons.ResultList;
import fr.paris.lutece.portal.service.jpa.JPALuteceDAO;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


/**
 * Abstract class for stock DAO.
 * 
 * @param <K> the key type
 * @param <E> the entity type
 * @author abataille
 */
public abstract class AbstractStockDAO<K, E> extends JPALuteceDAO<K, E>
{

    /**
     * Generate count query from criteria query and return a paged query.
     * 
     * @param <T> the generic type of criteria query
     * @param criteriaQuery criteria query
     * @param paginationProperties pagination data
     * @return query paged
     */
    protected <T> PagedQuery createPagedQuery( CriteriaQuery<T> criteriaQuery, PaginationProperties paginationProperties )
    {

        // Generate count query
        EntityManager em = getEM( );
        CriteriaBuilder cb = em.getCriteriaBuilder( );
        CriteriaQuery<Long> countQuery = cb.createQuery( Long.class );
        countQuery.select( cb.count( countQuery.from( criteriaQuery.getResultType( ) ) ) );
        // Rebuild the roots if
        // if ( cq.getRoots( ).size( ) > 1 )
        // {
            countQuery.getRoots( ).clear( );
            for ( Root<?> root : criteriaQuery.getRoots( ) )
            {
                countQuery.getRoots( ).add( root );
            }
        // }
        if ( criteriaQuery.getRestriction( ) != null )
        {
            countQuery.where( criteriaQuery.getRestriction( ) ).distinct( true );
        }

        // Create the paged query
        PagedQuery pq = new PagedQuery( em.createQuery( criteriaQuery ), em.createQuery( countQuery ),
                paginationProperties );

        return pq;
    }

    /**
     * Return all entities paged.
     * 
     * @param paginationProperties properties for pagination
     * @return the result list
     */
    public ResultList<E> findAll( PaginationProperties paginationProperties )
    {

        Query query = getEM( ).createQuery( "SELECT e FROM " + getEntityClassName( ) + " e " );

        Query countQuery = getEM( ).createQuery( "SELECT count(e) FROM " + getEntityClassName( ) + " e " );

        PagedQuery pq = new PagedQuery( query, countQuery, paginationProperties );
        return pq.getResultList( );
    }

    /**
     * Add a predicate to an existing query
     * @param query existing query
     * @param exp restriction
     */
    protected void addRestriction( CriteriaQuery<?> query, Expression<Boolean> exp )
    {
        CriteriaBuilder builder = getEM( ).getCriteriaBuilder( );
        Predicate restriction = query.getRestriction( );
        if ( restriction == null )
        {
            query.where( exp );
        }
        else
        {
            query.where( builder.and( restriction, exp ) );
        }
    }
}
