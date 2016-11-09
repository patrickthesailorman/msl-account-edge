package com.kenzan.msl.account.edge.services;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.mapping.Result;
import com.google.common.base.Optional;
import com.kenzan.msl.account.client.dto.AlbumsByUserDto;
import com.kenzan.msl.account.client.dto.ArtistsByUserDto;
import com.kenzan.msl.account.client.dto.SongsByUserDto;
import com.kenzan.msl.account.client.services.AccountDataClientService;
import com.kenzan.msl.account.edge.TestConstants;
import com.kenzan.msl.account.edge.services.impl.LibraryServiceHelperImpl;
import com.kenzan.msl.account.edge.services.impl.LibraryServiceImpl;
import com.kenzan.msl.account.edge.services.impl.RatingsServiceImpl;
import com.kenzan.msl.account.edge.translate.Translators;
import com.kenzan.msl.common.ContentType;
import io.swagger.model.MyLibrary;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import rx.Observable;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Translators.class)
public class LibraryServiceImplTest extends TestConstants {

  private ResultSet resultSet;
  private Result<AlbumsByUserDto> albumsByUserDtoResult;
  private Result<ArtistsByUserDto> artistByUserDtoResult;
  private Result<SongsByUserDto> songsByUserDtoResult;

  private AccountDataClientService accountDataClientService;
  private RatingsServiceImpl ratingsServiceImpl;
  private LibraryServiceHelperImpl libraryServiceHelperImpl;
  private LibraryServiceImpl libraryServiceImpl;

  @Before
  public void init() {
    albumsByUserDtoResult = Mockito.mock(Result.class);
    artistByUserDtoResult = Mockito.mock(Result.class);
    songsByUserDtoResult = Mockito.mock(Result.class);
    accountDataClientService = Mockito.mock(AccountDataClientService.class);

    ratingsServiceImpl = Mockito.mock(RatingsServiceImpl.class);
    libraryServiceHelperImpl = Mockito.mock(LibraryServiceHelperImpl.class);
    libraryServiceImpl =
        new LibraryServiceImpl(accountDataClientService, libraryServiceHelperImpl,
            ratingsServiceImpl);
    PowerMockito.mockStatic(Translators.class);

    mockGetMyLibraryAlbums();
    mockGetMyLibraryArtists();
    mockGetMyLibrarySongs();
  }

  @Test
  public void getTest() {
    MyLibrary response = libraryServiceImpl.get(SESSION_TOKEN.toString());

    verify(ratingsServiceImpl, times(1)).processAlbumRatings(albumList, SESSION_TOKEN);
    assertEquals(response.getAlbums(), albumList);
    assertEquals(response.getArtists(), artistList);
    assertEquals(response.getSongs(), songList);
  }

  @Test
  public void addArtistTest() {
    when(libraryServiceHelperImpl.getArtist(ARTIST_UUID)).thenReturn(Optional.of(ARTIST_BO));
    PowerMockito.when(Translators.translate(ARTIST_BO)).thenCallRealMethod();

    libraryServiceImpl.add(ARTIST_UUID.toString(), SESSION_TOKEN.toString(),
        ContentType.ARTIST.value);
    verify(accountDataClientService, times(1)).addOrUpdateArtistsByUser(anyObject());
  }

  @Test
  public void addAlbumTest() {
    when(libraryServiceHelperImpl.getAlbum(ALBUM_UUID)).thenReturn(Optional.of(ALBUM_BO));
    PowerMockito.when(Translators.translate(ALBUM_BO)).thenCallRealMethod();

    libraryServiceImpl
        .add(ALBUM_UUID.toString(), SESSION_TOKEN.toString(), ContentType.ALBUM.value);
    verify(accountDataClientService, times(1)).addOrUpdateAlbumsByUser(anyObject());
  }

  @Test
  public void addSongTest() {
    when(libraryServiceHelperImpl.getSong(SONG_UUID)).thenReturn(Optional.of(SONG_BO));
    PowerMockito.when(Translators.translate(SONG_BO)).thenCallRealMethod();

    libraryServiceImpl.add(SONG_UUID.toString(), SESSION_TOKEN.toString(), ContentType.SONG.value);
    verify(accountDataClientService, times(1)).addOrUpdateSongsByUser(anyObject());
  }

  @Test(expected = RuntimeException.class)
  public void addArtistTestEmptyArtist() {
    when(libraryServiceHelperImpl.getArtist(ARTIST_UUID)).thenReturn(Optional.absent());

    libraryServiceImpl.add(ARTIST_UUID.toString(), SESSION_TOKEN.toString(),
        ContentType.ARTIST.value);
  }

  @Test(expected = RuntimeException.class)
  public void addAlbumTestEmptyAlbum() {
    when(libraryServiceHelperImpl.getAlbum(ALBUM_UUID)).thenReturn(Optional.absent());

    libraryServiceImpl
        .add(ALBUM_UUID.toString(), SESSION_TOKEN.toString(), ContentType.ALBUM.value);
  }

  @Test(expected = RuntimeException.class)
  public void addSongTestEmptySong() {
    when(libraryServiceHelperImpl.getSong(SONG_UUID)).thenReturn(Optional.absent());

    libraryServiceImpl.add(SONG_UUID.toString(), SESSION_TOKEN.toString(), ContentType.SONG.value);
  }

  private void mockGetMyLibraryAlbums() {
    when(
        accountDataClientService.getAlbumsByUser(any(UUID.class), eq(Optional.absent()),
            eq(Optional.absent()))).thenReturn(Observable.just(resultSet));
    when(accountDataClientService.mapAlbumsByUser(anyObject())).thenReturn(
        Observable.just(albumsByUserDtoResult));
    PowerMockito.stub(PowerMockito.method(Translators.class, "translateAlbumsByUserDto")).toReturn(
        albumList);
  }

  private void mockGetMyLibraryArtists() {
    when(
        accountDataClientService.getArtistsByUser(any(UUID.class), eq(Optional.absent()),
            eq(Optional.absent()))).thenReturn(Observable.just(resultSet));
    when(accountDataClientService.mapArtistByUser(anyObject())).thenReturn(
        Observable.just(artistByUserDtoResult));
    PowerMockito.stub(PowerMockito.method(Translators.class, "translateArtistByUserDto")).toReturn(
        artistList);
  }

  private void mockGetMyLibrarySongs() {
    when(
        accountDataClientService.getSongsByUser(any(UUID.class), eq(Optional.absent()),
            eq(Optional.absent()))).thenReturn(Observable.just(resultSet));
    when(accountDataClientService.mapSongsByUser(anyObject())).thenReturn(
        Observable.just(songsByUserDtoResult));
    PowerMockito.stub(PowerMockito.method(Translators.class, "translateSongsByUserDto")).toReturn(
        songList);
  }
}
