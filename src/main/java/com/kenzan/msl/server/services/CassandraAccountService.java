/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.server.services;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.MappingManager;
import com.kenzan.msl.server.cassandra.CassandraConstants;
import com.kenzan.msl.server.cassandra.QueryAccessor;
import com.kenzan.msl.server.cassandra.query.LibraryQuery;
import io.swagger.model.MyLibrary;
import rx.Observable;

/**
 * Implementation of the AccountService interface that retrieves its data from a Cassandra cluster.
 */
public class CassandraAccountService
        implements AccountService {

    private QueryAccessor queryAccessor;
    private MappingManager mappingManager;

    public CassandraAccountService() {
        // TODO: Get the contact point from config param
        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();

        // TODO: Get the keyspace from config param
        Session session = cluster.connect(CassandraConstants.MSL_KEYSPACE);

        mappingManager = new MappingManager(session);
        queryAccessor = mappingManager.createAccessor(QueryAccessor.class);
    }

    /**
     * Retrieves the user library data
     *
     * @param sessionToken user uuid
     * @return Observable<MyLibrary>
     */
    public Observable<MyLibrary> getMyLibrary(String sessionToken) {
        return Observable.just(LibraryQuery.get(queryAccessor, mappingManager, sessionToken));
    }

    /**
     * Adds content on a user library
     *
     * @param object_id    album/artist/song uuid
     * @param sessionToken uuid of user who's library we are adding content on
     * @param contentType  album/artist/song content type
     */
    public void addToLibrary(String object_id, String sessionToken, String contentType) {
        try {
            LibraryQuery.add(queryAccessor, mappingManager, object_id, sessionToken, contentType);
        } catch (RuntimeException err) {
            throw err;
        }
    }

    /**
     * Removes content from a user library
     *
     * @param object_id    album/artist/song uuid
     * @param timestamp    referenced object timestamp
     * @param sessionToken uuid of user who's library we are adding content on
     * @param contentType  album/artist/song content type
     */
    public void removeFromLibrary(String object_id, String timestamp, String sessionToken, String contentType) {
        try {
            LibraryQuery.remove(queryAccessor, mappingManager, object_id, timestamp, sessionToken, contentType);
        } catch (RuntimeException err) {
            throw err;
        }
    }
}
