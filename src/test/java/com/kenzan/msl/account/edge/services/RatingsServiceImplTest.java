/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.account.edge.services;

import com.google.common.base.Optional;
import com.kenzan.msl.account.edge.TestConstants;
import com.kenzan.msl.account.edge.services.impl.RatingsServiceImpl;
import com.kenzan.msl.common.ContentType;
import com.kenzan.msl.ratings.client.services.CassandraRatingsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import rx.Observable;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RatingsServiceImplTest extends TestConstants {

  @Mock
  private CassandraRatingsService cassandraRatingsService;

  @InjectMocks
  private RatingsServiceImpl ratingsServiceImpl;

  @Test
  public void processAlbumRatingsTest() {
    when(cassandraRatingsService.getAverageRating(ALBUM_UUID, ContentType.ALBUM.value)).thenReturn(
        Observable.just(Optional.of(AVERAGE_RATINGS_DTO)));
    when(cassandraRatingsService.getUserRating(USER_ID, ContentType.ALBUM.value, ALBUM_UUID))
        .thenReturn(Observable.just(Optional.of(USER_RATINGS_DTO)));
    ratingsServiceImpl.processAlbumRatings(albumList, USER_ID);
    assertTrue(albumList.get(0).getPersonalRating() == USER_RATINGS_DTO.getRating());
    assertTrue(albumList.get(0).getAverageRating() == AVERAGE_RATINGS_DTO.getNumRating()
        / AVERAGE_RATINGS_DTO.getSumRating());
  }

  @Test
  public void processArtistRatings() {
    when(cassandraRatingsService.getAverageRating(ARTIST_UUID, ContentType.ARTIST.value))
        .thenReturn(Observable.just(Optional.of(AVERAGE_RATINGS_DTO)));
    when(cassandraRatingsService.getUserRating(USER_ID, ContentType.ARTIST.value, ARTIST_UUID))
        .thenReturn(Observable.just(Optional.of(USER_RATINGS_DTO)));
    ratingsServiceImpl.processArtistRatings(artistList, USER_ID);
    assertTrue(artistList.get(0).getPersonalRating() == USER_RATINGS_DTO.getRating());
    assertTrue(artistList.get(0).getAverageRating() == AVERAGE_RATINGS_DTO.getNumRating()
        / AVERAGE_RATINGS_DTO.getSumRating());
  }

  @Test
  public void processSongRatings() {
    when(cassandraRatingsService.getAverageRating(SONG_UUID, ContentType.SONG.value)).thenReturn(
        Observable.just(Optional.of(AVERAGE_RATINGS_DTO)));
    when(cassandraRatingsService.getUserRating(USER_ID, ContentType.SONG.value, SONG_UUID))
        .thenReturn(Observable.just(Optional.of(USER_RATINGS_DTO)));
    ratingsServiceImpl.processSongRatings(songList, USER_ID);
    assertTrue(songList.get(0).getPersonalRating() == USER_RATINGS_DTO.getRating());
    assertTrue(songList.get(0).getAverageRating() == AVERAGE_RATINGS_DTO.getNumRating()
        / AVERAGE_RATINGS_DTO.getSumRating());
  }
}
