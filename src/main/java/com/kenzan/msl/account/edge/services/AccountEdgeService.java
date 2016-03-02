/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.account.edge.services;

import com.kenzan.msl.account.client.services.CassandraAccountService;
import io.swagger.model.MyLibrary;
import rx.Observable;

/**
 * Implementation of the AccountEdge interface that retrieves its data from a Cassandra cluster.
 */
public class AccountEdgeService implements AccountEdge {

  private LibraryService libraryService;
  private CassandraAccountService cassandraAccountService;

  public AccountEdgeService(LibraryService _libraryService) {
    cassandraAccountService = CassandraAccountService.getInstance();
    libraryService = _libraryService;
  }

  /**
   * Retrieves the user library data
   *
   * @param sessionToken user uuid
   * @return Observable&lt;MyLibrary&gt;
   */
  public Observable<MyLibrary> getMyLibrary(String sessionToken) {
    return Observable.just(libraryService.get(cassandraAccountService, sessionToken));
  }

  /**
   * Adds content on a user library
   *
   * @param object_id album/artist/song uuid
   * @param sessionToken uuid of user who's library we are adding content on
   * @param contentType album/artist/song content type
   */
  public void addToLibrary(String object_id, String sessionToken, String contentType) {
    libraryService.add(cassandraAccountService, object_id, sessionToken, contentType);
  }

  /**
   * Removes content from a user library
   *
   * @param object_id album/artist/song uuid
   * @param timestamp referenced object timestamp
   * @param sessionToken uuid of user who's library we are adding content on
   * @param contentType album/artist/song content type
   */
  public void removeFromLibrary(String object_id, String timestamp, String sessionToken,
      String contentType) {
    libraryService.remove(cassandraAccountService, object_id, timestamp, sessionToken, contentType);
  }
}
