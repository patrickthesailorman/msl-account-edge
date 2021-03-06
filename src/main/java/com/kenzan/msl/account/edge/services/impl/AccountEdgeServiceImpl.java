/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.account.edge.services.impl;

import com.google.inject.Inject;
import com.kenzan.msl.account.client.dto.UserDto;
import com.kenzan.msl.account.client.services.AccountDataClientService;
import com.kenzan.msl.account.edge.services.AccountEdgeService;
import com.kenzan.msl.account.edge.services.LibraryService;
import io.swagger.model.MyLibrary;
import rx.Observable;

/**
 * Implementation of the AccountEdgeService interface that retrieves its data from a Cassandra
 * cluster.
 */
public class AccountEdgeServiceImpl implements AccountEdgeService {

  private final LibraryService libraryService;
  private final AccountDataClientService accountDataClientService;

  /**
   * Constructor
   *
   * @param libraryService com.kenzan.msl.account.edge.services LibraryService
   * @param accountDataClientService com.kenzan.msl.account.client.services.AccountDataClientService
   */
  @Inject
  public AccountEdgeServiceImpl(final LibraryService libraryService,
      final AccountDataClientService accountDataClientService) {
    this.libraryService = libraryService;
    this.accountDataClientService = accountDataClientService;
  }

  /**
   * Registers a user
   *
   * @param user UserDto
   * @return Observable&lt;Void&gt;
   */
  @Override
  public Observable<Void> registerUser(UserDto user) {
    Observable<UserDto> userResults =
        accountDataClientService.getUserByUsername(user.getUsername());
    if (!userResults.isEmpty().toBlocking().first()) {
      throw new RuntimeException("User already exists with designated email address");
    } else {
      boolean isValidUUID = false;
      while (!isValidUUID) {
        isValidUUID =
            accountDataClientService.getUserByUUID(user.getUserId()).isEmpty().toBlocking().first();
      }
      accountDataClientService.addOrUpdateUser(user);
      if (accountDataClientService.getUserByUsername(user.getUsername()).isEmpty().toBlocking()
          .first()) {
        throw new RuntimeException("Unable to create user");
      }
    }
    return Observable.empty();
  }

  /**
   * Retrieves the user library data
   *
   * @param sessionToken user uuid
   * @return Observable&lt;MyLibrary&gt;
   */
  @Override
  public Observable<MyLibrary> getMyLibrary(String sessionToken) {
    return Observable.just(libraryService.get(sessionToken));
  }

  /**
   * Adds content on a user library
   *
   * @param object_id album/artist/song uuid
   * @param sessionToken uuid of user who's library we are adding content on
   * @param contentType album/artist/song content type
   */
  @Override
  public void addToLibrary(String object_id, String sessionToken, String contentType) {
    libraryService.add(object_id, sessionToken, contentType);
  }

  /**
   * Removes content from a user library
   *
   * @param object_id album/artist/song uuid
   * @param timestamp referenced object timestamp
   * @param sessionToken uuid of user who's library we are adding content on
   * @param contentType album/artist/song content type
   */
  @Override
  public void removeFromLibrary(String object_id, String timestamp, String sessionToken,
      String contentType) {
    libraryService.remove(object_id, timestamp, sessionToken, contentType);
  }
}
