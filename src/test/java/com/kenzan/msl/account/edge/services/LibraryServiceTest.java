package com.kenzan.msl.account.edge.services;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.mapping.Result;
import com.google.common.base.Optional;
import com.kenzan.msl.account.client.dto.AlbumsByUserDto;
import com.kenzan.msl.account.client.dto.ArtistsByUserDto;
import com.kenzan.msl.account.client.dto.SongsByUserDto;
import com.kenzan.msl.account.client.services.CassandraAccountService;
import com.kenzan.msl.account.edge.TestConstants;
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
public class LibraryServiceTest extends TestConstants {

  private ResultSet resultSet;
  private Result<AlbumsByUserDto> albumsByUserDtoResult;
  private Result<ArtistsByUserDto> artistByUserDtoResult;
  private Result<SongsByUserDto> songsByUserDtoResult;

  private CassandraAccountService cassandraAccountService;
  private RatingsHelper ratingsHelper;
  private LibraryServiceHelper libraryServiceHelper;
  private LibraryService libraryService;

  @Before
  public void init() {
    albumsByUserDtoResult = Mockito.mock(Result.class);
    artistByUserDtoResult = Mockito.mock(Result.class);
    songsByUserDtoResult = Mockito.mock(Result.class);
    cassandraAccountService = Mockito.mock(CassandraAccountService.class);

    ratingsHelper = Mockito.mock(RatingsHelper.class);
    libraryServiceHelper = Mockito.mock(LibraryServiceHelper.class);
    libraryService =
        new LibraryService(cassandraAccountService, libraryServiceHelper, ratingsHelper);
    PowerMockito.mockStatic(Translators.class);

    mockGetMyLibraryAlbums();
    mockGetMyLibraryArtists();
    mockGetMyLibrarySongs();
  }

  @Test
  public void getTest() {
    MyLibrary response = libraryService.get(SESSION_TOKEN.toString());

    verify(ratingsHelper, times(1)).processAlbumRatings(albumList, SESSION_TOKEN);
    assertEquals(response.getAlbums(), albumList);
    assertEquals(response.getArtists(), artistList);
    assertEquals(response.getSongs(), songList);
  }

  @Test
  public void addArtistTest() {
    when(libraryServiceHelper.getArtist(ARTIST_UUID)).thenReturn(Optional.of(ARTIST_BO));
    PowerMockito.when(Translators.translate(ARTIST_BO)).thenCallRealMethod();

    libraryService.add(ARTIST_UUID.toString(), SESSION_TOKEN.toString(), ContentType.ARTIST.value);
    verify(cassandraAccountService, times(1)).addOrUpdateArtistsByUser(anyObject());
  }

  @Test
  public void addAlbumTest() {
    when(libraryServiceHelper.getAlbum(ALBUM_UUID)).thenReturn(Optional.of(ALBUM_BO));
    PowerMockito.when(Translators.translate(ALBUM_BO)).thenCallRealMethod();

    libraryService.add(ALBUM_UUID.toString(), SESSION_TOKEN.toString(), ContentType.ALBUM.value);
    verify(cassandraAccountService, times(1)).addOrUpdateAlbumsByUser(anyObject());
  }

  @Test
  public void addSongTest() {
    when(libraryServiceHelper.getSong(SONG_UUID)).thenReturn(Optional.of(SONG_BO));
    PowerMockito.when(Translators.translate(SONG_BO)).thenCallRealMethod();

    libraryService.add(SONG_UUID.toString(), SESSION_TOKEN.toString(), ContentType.SONG.value);
    verify(cassandraAccountService, times(1)).addOrUpdateSongsByUser(anyObject());
  }

  @Test(expected = RuntimeException.class)
  public void addArtistTestEmptyArtist() {
    when(libraryServiceHelper.getArtist(ARTIST_UUID)).thenReturn(Optional.absent());

    libraryService.add(ARTIST_UUID.toString(), SESSION_TOKEN.toString(), ContentType.ARTIST.value);
  }

  @Test(expected = RuntimeException.class)
  public void addAlbumTestEmptyAlbum() {
    when(libraryServiceHelper.getAlbum(ALBUM_UUID)).thenReturn(Optional.absent());

    libraryService.add(ALBUM_UUID.toString(), SESSION_TOKEN.toString(), ContentType.ALBUM.value);
  }

  @Test(expected = RuntimeException.class)
  public void addSongTestEmptySong() {
    when(libraryServiceHelper.getSong(SONG_UUID)).thenReturn(Optional.absent());

    libraryService.add(SONG_UUID.toString(), SESSION_TOKEN.toString(), ContentType.SONG.value);
  }

  private void mockGetMyLibraryAlbums() {
    when(
        cassandraAccountService.getAlbumsByUser(any(UUID.class), eq(Optional.absent()),
            eq(Optional.absent()))).thenReturn(Observable.just(resultSet));
    when(cassandraAccountService.mapAlbumsByUser(anyObject())).thenReturn(
        Observable.just(albumsByUserDtoResult));
    PowerMockito.stub(PowerMockito.method(Translators.class, "translateAlbumsByUserDto")).toReturn(
        albumList);
  }

  private void mockGetMyLibraryArtists() {
    when(
        cassandraAccountService.getArtistsByUser(any(UUID.class), eq(Optional.absent()),
            eq(Optional.absent()))).thenReturn(Observable.just(resultSet));
    when(cassandraAccountService.mapArtistByUser(anyObject())).thenReturn(
        Observable.just(artistByUserDtoResult));
    PowerMockito.stub(PowerMockito.method(Translators.class, "translateArtistByUserDto")).toReturn(
        artistList);
  }

  private void mockGetMyLibrarySongs() {
    when(
        cassandraAccountService.getSongsByUser(any(UUID.class), eq(Optional.absent()),
            eq(Optional.absent()))).thenReturn(Observable.just(resultSet));
    when(cassandraAccountService.mapSongsByUser(anyObject())).thenReturn(
        Observable.just(songsByUserDtoResult));
    PowerMockito.stub(PowerMockito.method(Translators.class, "translateSongsByUserDto")).toReturn(
        songList);
  }
}
