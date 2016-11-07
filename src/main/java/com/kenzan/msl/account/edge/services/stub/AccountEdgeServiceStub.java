package com.kenzan.msl.account.edge.services.stub;

import com.kenzan.msl.account.client.dto.UserDto;
import com.kenzan.msl.account.edge.services.AccountEdgeService;
import io.swagger.model.MyLibrary;
import rx.Observable;

/**
 * @author Kenzan
 */
public class AccountEdgeServiceStub implements AccountEdgeService {
  /**
   * Registers a user
   *
   * @param user userDto
   * @return Observable&lt;Void&gt;
   */
  public Observable<Void> registerUser(UserDto user) {
    return Observable.empty();
  }

  /**
   * Gets the MyLibrary object
   *
   * @param sessionToken String
   * @return Observable&lt;MyLibrary&gt;
   */
  public Observable<MyLibrary> getMyLibrary(String sessionToken) {
    return Observable.empty();
  }

  /**
   * Add a data to a specific user library
   *
   * @param id String
   * @param sessionToken String
   * @param contentType String
   */
  public void addToLibrary(String id, String sessionToken, String contentType) {}

  /**
   * Remove data from a user library
   *
   * @param object_id String
   * @param timestamp String
   * @param sessionToken String
   * @param contentType String
   */
  public void removeFromLibrary(String object_id, String timestamp, String sessionToken,
      String contentType) {}
}
