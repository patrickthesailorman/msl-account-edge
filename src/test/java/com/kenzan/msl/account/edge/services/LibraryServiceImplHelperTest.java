/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.account.edge.services;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.mapping.Result;
import com.google.common.base.Optional;
import com.kenzan.msl.account.edge.TestConstants;
import com.kenzan.msl.account.edge.services.impl.LibraryServiceHelperImpl;
import com.kenzan.msl.catalog.client.dto.AlbumArtistBySongDto;
import com.kenzan.msl.catalog.client.dto.SongsAlbumsByArtistDto;
import com.kenzan.msl.catalog.client.dto.SongsArtistByAlbumDto;
import com.kenzan.msl.catalog.client.services.CatalogDataClientService;
import com.kenzan.msl.common.bo.AlbumBo;
import com.kenzan.msl.common.bo.ArtistBo;
import com.kenzan.msl.common.bo.SongBo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import rx.Observable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LibraryServiceImplHelperTest extends TestConstants {

  @Mock
  private ResultSet resultSet;

  @Mock
  private Result<SongsAlbumsByArtistDto> songsAlbumsByArtistDtoResult;

  @Mock
  private Result<SongsArtistByAlbumDto> songsArtistByAlbumDtoResult;

  @Mock
  private Result<AlbumArtistBySongDto> albumArtistBySongDtoResult;

  @Mock
  private CatalogDataClientService catalogDataClientService;

  @InjectMocks
  private LibraryServiceHelperImpl libraryServiceHelperImpl;

  @Test
  public void getArtistTest() {
    when(catalogDataClientService.getSongsAlbumsByArtist(ARTIST_UUID, Optional.absent()))
        .thenReturn(Observable.just(resultSet));
    when(catalogDataClientService.mapSongsAlbumsByArtist(anyObject())).thenReturn(
        Observable.just(songsAlbumsByArtistDtoResult));
    when(songsAlbumsByArtistDtoResult.one()).thenReturn(songsAlbumsByArtistDto);
    Optional<ArtistBo> results = libraryServiceHelperImpl.getArtist(ARTIST_UUID);

    assertTrue(results.isPresent());
    assertEquals(results.get().getArtistId(), songsAlbumsByArtistDto.getArtistId());
    assertEquals(results.get().getArtistName(), songsAlbumsByArtistDto.getArtistName());
  }

  @Test
  public void getArtistTestEmptyMapping() {
    when(catalogDataClientService.getSongsAlbumsByArtist(ARTIST_UUID, Optional.absent()))
        .thenReturn(Observable.just(resultSet));
    when(catalogDataClientService.mapSongsAlbumsByArtist(anyObject())).thenReturn(
        Observable.just(null));
    Optional<ArtistBo> results = libraryServiceHelperImpl.getArtist(ARTIST_UUID);
    assertFalse(results.isPresent());
  }

  @Test
  public void getArtistTestEmptySongsAlbumsByArtistDto() {
    when(catalogDataClientService.getSongsAlbumsByArtist(ARTIST_UUID, Optional.absent()))
        .thenReturn(Observable.just(resultSet));
    when(catalogDataClientService.mapSongsAlbumsByArtist(anyObject())).thenReturn(
        Observable.just(songsAlbumsByArtistDtoResult));
    when(songsAlbumsByArtistDtoResult.one()).thenReturn(null);
    Optional<ArtistBo> results = libraryServiceHelperImpl.getArtist(ARTIST_UUID);
    assertFalse(results.isPresent());
  }

  @Test
  public void getAlbumTest() {
    when(catalogDataClientService.getSongsArtistByAlbum(ALBUM_UUID, Optional.absent())).thenReturn(
        Observable.just(resultSet));
    when(catalogDataClientService.mapSongsArtistByAlbum(anyObject())).thenReturn(
        Observable.just(songsArtistByAlbumDtoResult));
    when(songsArtistByAlbumDtoResult.one()).thenReturn(songsArtistByAlbumDto);
    Optional<AlbumBo> results = libraryServiceHelperImpl.getAlbum(ALBUM_UUID);
    assertTrue(results.isPresent());
    assertEquals(results.get().getAlbumId(), songsArtistByAlbumDto.getAlbumId());
    assertEquals(results.get().getAlbumName(), songsArtistByAlbumDto.getAlbumName());
    assertEquals(results.get().getArtistId(), songsArtistByAlbumDto.getArtistId());
    assertEquals(results.get().getArtistName(), songsArtistByAlbumDto.getArtistName());
    assertEquals(results.get().getImageLink(), songsArtistByAlbumDto.getImageLink());
  }

  @Test
  public void getAlbumTestEmptyMappingResults() {
    when(catalogDataClientService.getSongsArtistByAlbum(ALBUM_UUID, Optional.absent())).thenReturn(
        Observable.just(resultSet));
    when(catalogDataClientService.mapSongsArtistByAlbum(anyObject())).thenReturn(
        Observable.just(null));
    Optional<AlbumBo> results = libraryServiceHelperImpl.getAlbum(ALBUM_UUID);
    assertFalse(results.isPresent());
  }

  @Test
  public void getAlbumTestEmptySongsArtistByAlbumDto() {
    when(catalogDataClientService.getSongsArtistByAlbum(ALBUM_UUID, Optional.absent())).thenReturn(
        Observable.just(resultSet));
    when(catalogDataClientService.mapSongsArtistByAlbum(anyObject())).thenReturn(
        Observable.just(songsArtistByAlbumDtoResult));
    when(songsArtistByAlbumDtoResult.one()).thenReturn(null);
    Optional<AlbumBo> results = libraryServiceHelperImpl.getAlbum(ALBUM_UUID);
    assertFalse(results.isPresent());
  }

  @Test
  public void getSongTest() {
    when(catalogDataClientService.getAlbumArtistBySong(SONG_UUID, Optional.absent())).thenReturn(
        Observable.just(resultSet));
    when(catalogDataClientService.mapAlbumArtistBySong(anyObject())).thenReturn(
        Observable.just(albumArtistBySongDtoResult));
    when(albumArtistBySongDtoResult.one()).thenReturn(albumArtistBySongDto);

    Optional<SongBo> results = libraryServiceHelperImpl.getSong(SONG_UUID);
    assertTrue(results.isPresent());

    assertEquals(results.get().getSongId(), albumArtistBySongDto.getSongId());
    assertEquals(results.get().getSongName(), albumArtistBySongDto.getSongName());
    assertEquals(results.get().getAlbumId(), albumArtistBySongDto.getAlbumId());
    assertEquals(results.get().getAlbumName(), albumArtistBySongDto.getAlbumName());
    assertEquals(results.get().getArtistId(), albumArtistBySongDto.getArtistId());
    assertEquals(results.get().getArtistName(), albumArtistBySongDto.getArtistName());
    assertEquals(results.get().getDuration(), albumArtistBySongDto.getSongDuration());
    assertEquals(results.get().getYear(), albumArtistBySongDto.getAlbumYear());
  }

  @Test
  public void getSongTestEmptyMappingResults() {
    when(catalogDataClientService.getAlbumArtistBySong(SONG_UUID, Optional.absent())).thenReturn(
        Observable.just(resultSet));
    when(catalogDataClientService.mapAlbumArtistBySong(anyObject())).thenReturn(
        Observable.just(null));

    Optional<SongBo> results = libraryServiceHelperImpl.getSong(SONG_UUID);
    assertFalse(results.isPresent());
  }

  @Test
  public void getSongTestEmptyAlbumArtistBySongDto() {
    when(catalogDataClientService.getAlbumArtistBySong(SONG_UUID, Optional.absent())).thenReturn(
        Observable.just(resultSet));
    when(catalogDataClientService.mapAlbumArtistBySong(anyObject())).thenReturn(
        Observable.just(albumArtistBySongDtoResult));
    when(albumArtistBySongDtoResult.one()).thenReturn(null);

    Optional<SongBo> results = libraryServiceHelperImpl.getSong(SONG_UUID);
    assertFalse(results.isPresent());
  }

}
