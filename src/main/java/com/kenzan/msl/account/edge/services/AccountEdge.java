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
   *
   * @param user userDto
   * @return Observable&lt;Void&gt;
   */
  Observable<Void> registerUser(UserDto user);

  /**
   * Gets the MyLibrary object
   *
   * @param sessionToken String
   * @return Observable&lt;MyLibrary&gt;
   */
  Observable<MyLibrary> getMyLibrary(String sessionToken);

  /**
   * Add a data to a specific user library
   *
   * @param id String
   * @param sessionToken String
   * @param contentType String
   */
  void addToLibrary(String id, String sessionToken, String contentType);

  /**
   * Remove data from a user library
   *
   * @param object_id String
   * @param timestamp String
   * @param sessionToken String
   * @param contentType String
   */
  void removeFromLibrary(String object_id, String timestamp, String sessionToken, String contentType);

}
