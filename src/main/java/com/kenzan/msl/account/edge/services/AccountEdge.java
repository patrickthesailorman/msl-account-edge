/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.account.edge.services;

import com.kenzan.msl.account.client.dto.UserDto;
import io.swagger.model.MyLibrary;
import rx.Observable;

public interface AccountEdge {

  /**
   * Registers a user
   */
  Observable<Void> registerUser(UserDto user);

  /**
   * Gets the MyLibrary object
   */
  Observable<MyLibrary> getMyLibrary(String sessionToken);

  /**
   * Add a data to a specific user library
   */
  void addToLibrary(String id, String sessionToken, String contentType);

  /**
   * Remove data from a user library
   */
  void removeFromLibrary(String object_id, String timestamp, String sessionToken, String contentType);

}
