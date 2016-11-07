package com.kenzan.msl.account.edge.services;

import com.kenzan.msl.account.client.services.AccountDataClientService;
import com.kenzan.msl.account.edge.TestConstants;
import com.kenzan.msl.account.edge.services.impl.AccountEdgeServiceImpl;
import com.kenzan.msl.account.edge.services.impl.LibraryServiceImpl;
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
public class AccountEdgeServiceImplTest extends TestConstants {

  @Mock
  private MyLibrary myLibrary;

  @Mock
  private LibraryServiceImpl libraryServiceImpl;

  @Mock
  private AccountDataClientService accountDataClientService;

  @InjectMocks
  private AccountEdgeServiceImpl accountEdgeServiceImpl;

  @Test
  public void registerUserTest() {
    when(accountDataClientService.getUserByUsername(USER_DTO.getUsername())).thenReturn(
        Observable.empty()).thenReturn(Observable.just(USER_DTO));
    when(accountDataClientService.getUserByUUID(USER_DTO.getUserId())).thenReturn(
        Observable.empty());

    Observable<Void> response = accountEdgeServiceImpl.registerUser(USER_DTO);
    verify(accountDataClientService, times(1)).addOrUpdateUser(USER_DTO);
    assertTrue(response.isEmpty().toBlocking().first());
  }

  @Test(expected = RuntimeException.class)
  public void registerUserTestEmptyUser() {
    when(accountDataClientService.getUserByUsername(USER_DTO.getUsername())).thenReturn(
        Observable.empty());
    accountEdgeServiceImpl.registerUser(USER_DTO);
  }

  @Test(expected = RuntimeException.class)
  public void registerUserTestUnableToAdd() {
    when(accountDataClientService.getUserByUsername(USER_DTO.getUsername())).thenReturn(
        Observable.empty()).thenReturn(Observable.empty());
    when(accountDataClientService.getUserByUUID(USER_DTO.getUserId())).thenReturn(
        Observable.empty());

    accountEdgeServiceImpl.registerUser(USER_DTO);
    verify(accountDataClientService, times(1)).addOrUpdateUser(USER_DTO);
  }

  @Test
  public void getMyLibraryTest() {
    when(libraryServiceImpl.get(SESSION_TOKEN.toString())).thenReturn(myLibrary);
    Observable<MyLibrary> response = accountEdgeServiceImpl.getMyLibrary(SESSION_TOKEN.toString());
    assertEquals(response.toBlocking().first(), myLibrary);
  }

  @Test
  public void addToLibraryTest() {
    accountEdgeServiceImpl.addToLibrary(SONG_UUID.toString(), SESSION_TOKEN.toString(),
        ContentType.SONG.value);
    verify(libraryServiceImpl, times(1)).add(SONG_UUID.toString(), SESSION_TOKEN.toString(),
        ContentType.SONG.value);
  }

  @Test
  public void removeFromLibraryTest() {
    accountEdgeServiceImpl.removeFromLibrary(SONG_UUID.toString(), FAVORITES_TIMESTAMP.toString(),
        SESSION_TOKEN.toString(), ContentType.SONG.value);
    verify(libraryServiceImpl, times(1)).remove(SONG_UUID.toString(),
        FAVORITES_TIMESTAMP.toString(), SESSION_TOKEN.toString(), ContentType.SONG.value);
  }

}
