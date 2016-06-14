package com.kenzan.msl.account.edge.services;

import com.kenzan.msl.account.client.services.CassandraAccountService;
import com.kenzan.msl.account.edge.TestConstants;
import com.kenzan.msl.common.ContentType;
import io.swagger.model.MyLibrary;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import rx.Observable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountEdgeServiceTest extends TestConstants {

  @Mock
  private MyLibrary myLibrary;

  @Mock
  private LibraryService libraryService;

  @Mock
  private CassandraAccountService cassandraAccountService;

  @InjectMocks
  private AccountEdgeService accountEdgeService;

  @Test
  public void registerUserTest() {
    when(cassandraAccountService.getUserByUsername(USER_DTO.getUsername())).thenReturn(
        Observable.empty()).thenReturn(Observable.just(USER_DTO));
    when(cassandraAccountService.getUserByUUID(USER_DTO.getUserId()))
        .thenReturn(Observable.empty());

    Observable<Void> response = accountEdgeService.registerUser(USER_DTO);
    verify(cassandraAccountService, times(1)).addOrUpdateUser(USER_DTO);
    assertTrue(response.isEmpty().toBlocking().first());
  }

  @Test(expected = RuntimeException.class)
  public void registerUserTestEmptyUser() {
    when(cassandraAccountService.getUserByUsername(USER_DTO.getUsername())).thenReturn(
        Observable.empty());
    accountEdgeService.registerUser(USER_DTO);
  }

  @Test(expected = RuntimeException.class)
  public void registerUserTestUnableToAdd() {
    when(cassandraAccountService.getUserByUsername(USER_DTO.getUsername())).thenReturn(
        Observable.empty()).thenReturn(Observable.empty());
    when(cassandraAccountService.getUserByUUID(USER_DTO.getUserId()))
        .thenReturn(Observable.empty());

    accountEdgeService.registerUser(USER_DTO);
    verify(cassandraAccountService, times(1)).addOrUpdateUser(USER_DTO);
  }

  @Test
  public void getMyLibraryTest() {
    when(libraryService.get(SESSION_TOKEN.toString())).thenReturn(myLibrary);
    Observable<MyLibrary> response = accountEdgeService.getMyLibrary(SESSION_TOKEN.toString());
    assertEquals(response.toBlocking().first(), myLibrary);
  }

  @Test
  public void addToLibraryTest() {
    accountEdgeService.addToLibrary(SONG_UUID.toString(), SESSION_TOKEN.toString(),
        ContentType.SONG.value);
    verify(libraryService, times(1)).add(SONG_UUID.toString(), SESSION_TOKEN.toString(),
        ContentType.SONG.value);
  }

  @Test
  public void removeFromLibraryTest() {
    accountEdgeService.removeFromLibrary(SONG_UUID.toString(), FAVORITES_TIMESTAMP.toString(),
        SESSION_TOKEN.toString(), ContentType.SONG.value);
    verify(libraryService, times(1)).remove(SONG_UUID.toString(), FAVORITES_TIMESTAMP.toString(),
        SESSION_TOKEN.toString(), ContentType.SONG.value);
  }

}
