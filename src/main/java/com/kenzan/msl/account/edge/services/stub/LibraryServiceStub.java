package com.kenzan.msl.account.edge.services.stub;

import com.kenzan.msl.account.edge.services.LibraryService;
import io.swagger.model.MyLibrary;

/**
 * @author Kenzan
 */
public class LibraryServiceStub implements LibraryService {
  public MyLibrary get(final String sessionToken) {
    return new MyLibrary();
  }

  public void add(final String id, final String sessionToken, final String contentType) {
    // TODO
  }

  public void remove(final String id, final String timestamp, final String sessionToken,
      final String contentType) {
    // TODO
  }
}
