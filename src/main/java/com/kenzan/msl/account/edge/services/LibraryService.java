package com.kenzan.msl.account.edge.services;

import io.swagger.model.MyLibrary;

public interface LibraryService {

  MyLibrary get(final String sessionToken);

  void add(final String id, final String sessionToken, final String contentType);

  void remove(final String id, final String timestamp, final String sessionToken,
      final String contentType);
}
