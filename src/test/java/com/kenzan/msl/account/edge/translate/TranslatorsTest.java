/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.account.edge.translate;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.kenzan.msl.account.client.dto.AlbumsByUserDto;
import com.kenzan.msl.account.client.dto.ArtistsByUserDto;
import com.kenzan.msl.account.client.dto.SongsByUserDto;
import com.kenzan.msl.account.edge.TestConstants;
import com.kenzan.msl.common.bo.AlbumBo;
import com.kenzan.msl.common.bo.ArtistBo;
import com.kenzan.msl.common.bo.SongBo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TranslatorsTest extends TestConstants {

  private AlbumBo albumBo = new AlbumBo();
  private ArtistBo artistBo = new ArtistBo();
  private SongBo songBo = new SongBo();

  /*
   * ==============================================================================================
   * ALBUMS ==========================================================================
   */

  @Test
  public void testTranslateAlbumBoToAlbumsByUserDto_HappyPath() {
    initAlbumBo();

    AlbumsByUserDto model = Translators.translate(albumBo);

    assertNotNull(model);
    assertEquals(model.getAlbumId(), ALBUM_UUID);
    assertEquals(model.getAlbumName(), ALBUM_NAME);
    assertEquals(model.getAlbumYear(), ALBUM_YEAR);
    assertEquals(model.getArtistId(), ARTIST_UUID);
    assertEquals(model.getArtistMbid(), ARTIST_MBID);
    assertEquals(model.getArtistName(), ARTIST_NAME);
    assertEquals(model.getContentType(), "Album");
    assertEquals(model.getFavoritesTimestamp(), FAVORITES_TIMESTAMP);
  }

  @Test
  public void testTranslateAlbumBoToAlbumsByUserDto_EverythingEmpty() {
    AlbumBo bo = new AlbumBo();

    AlbumsByUserDto model = Translators.translate(bo);

    assertNotNull(model);
    assertNull(model.getAlbumId());
    assertNull(model.getAlbumName());
    assertNull(model.getAlbumYear());
    assertNull(model.getArtistId());
    assertNull(model.getArtistName());
    assertNull(model.getArtistMbid());
    assertNotNull(model.getContentType());
    assertNull(model.getFavoritesTimestamp());
  }

  /*
   * ==============================================================================================
   * ARTISTS ==========================================================================
   */

  @Test
  public void testTranslateArtistBoToArtistsByUserDto_HappyPath() {
    initArtistBo();

    ArtistsByUserDto model = Translators.translate(artistBo);

    assertNotNull(model);
    assertEquals(model.getArtistId(), ARTIST_UUID);
    assertEquals(model.getArtistMbid(), ARTIST_MBID);
    assertEquals(model.getArtistName(), ARTIST_NAME);
    assertEquals(model.getContentType(), "Artist");
    assertEquals(model.getFavoritesTimestamp(), FAVORITES_TIMESTAMP);
  }

  @Test
  public void testTranslateArtistBoToArtistsByUserDto_EverythingEmpty() {
    ArtistBo bo = new ArtistBo();

    ArtistsByUserDto model = Translators.translate(bo);

    assertNotNull(model);
    assertNull(model.getArtistId());
    assertNull(model.getArtistName());
    assertNull(model.getArtistMbid());
    assertNotNull(model.getContentType());
    assertNull(model.getFavoritesTimestamp());
  }

  /*
   * ==============================================================================================
   * SONGS ==========================================================================
   */

  @Test
  public void testTranslateSongBoToSongsByUserDto_HappyPath() {
    initSongBo();

    SongsByUserDto model = Translators.translate(songBo);

    assertNotNull(model);

    assertEquals(model.getSongId(), SONG_UUID);
    assertEquals(model.getSongName(), SONG_NAME);
    assertEquals(model.getSongDuration(), SONG_DURATION);

    assertEquals(model.getArtistId(), ARTIST_UUID);
    assertEquals(model.getArtistName(), ARTIST_NAME);
    assertEquals(model.getArtistMbid(), ARTIST_MBID);

    assertEquals(model.getAlbumId(), ALBUM_UUID);
    assertEquals(model.getAlbumName(), ALBUM_NAME);
    assertEquals(model.getAlbumYear(), SONG_YEAR);

    assertEquals(model.getFavoritesTimestamp(), FAVORITES_TIMESTAMP);
  }

  @Test
  public void testTranslateSongBoToSongsByUserDto_EverythingEmpty() {
    SongBo bo = new SongBo();

    SongsByUserDto model = Translators.translate(bo);

    assertNotNull(model);
    assertNull(model.getSongId());
    assertNull(model.getSongName());
    assertNull(model.getSongDuration());

    assertNull(model.getArtistId());
    assertNull(model.getArtistName());
    assertNull(model.getArtistMbid());

    assertNull(model.getAlbumId());
    assertNull(model.getAlbumName());
    assertNull(model.getAlbumYear());
    assertNotNull(model.getContentType());

    assertNull(model.getFavoritesTimestamp());
  }

  private void initAlbumBo() {
    albumBo.setAlbumId(ALBUM_UUID);
    albumBo.setAlbumName(ALBUM_NAME);
    albumBo.setYear(ALBUM_YEAR);
    albumBo.setArtistId(ARTIST_UUID);
    albumBo.setArtistName(ARTIST_NAME);
    albumBo.setArtistMbid(ARTIST_MBID);
    albumBo.setFavoritesTimestamp(Long.toString(FAVORITES_TIMESTAMP.getTime()));
  }

  private void initArtistBo() {
    artistBo.setArtistId(ARTIST_UUID);
    artistBo.setArtistName(ARTIST_NAME);
    artistBo.setArtistMbid(ARTIST_MBID);
    artistBo.setFavoritesTimestamp(Long.toString(FAVORITES_TIMESTAMP.getTime()));
  }

  private void initSongBo() {
    songBo.setSongId(SONG_UUID);
    songBo.setSongName(SONG_NAME);
    songBo.setDuration(SONG_DURATION);
    songBo.setYear(SONG_YEAR);
    songBo.setArtistId(ARTIST_UUID);
    songBo.setArtistName(ARTIST_NAME);
    songBo.setAlbumId(ALBUM_UUID);
    songBo.setAlbumName(ALBUM_NAME);
    songBo.setArtistMbid(ARTIST_MBID);
    songBo.setFavoritesTimestamp(Long.toString(FAVORITES_TIMESTAMP.getTime()));
  }
}
