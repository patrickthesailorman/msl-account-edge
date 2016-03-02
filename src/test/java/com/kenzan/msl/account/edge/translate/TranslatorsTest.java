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
public class TranslatorsTest {

  private TestConstants tc = TestConstants.getInstance();

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
    assertEquals(model.getAlbumId(), tc.ALBUM_UUID);
    assertEquals(model.getAlbumName(), tc.ALBUM_NAME);
    assertEquals(model.getAlbumYear(), tc.ALBUM_YEAR);
    assertEquals(model.getArtistId(), tc.ARTIST_UUID);
    assertEquals(model.getArtistMbid(), tc.ARTIST_MBID);
    assertEquals(model.getArtistName(), tc.ARTIST_NAME);
    assertEquals(model.getContentType(), "Album");
    assertEquals(model.getFavoritesTimestamp(), tc.FAVORITES_TIMESTAMP);
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
    assertEquals(model.getArtistId(), tc.ARTIST_UUID);
    assertEquals(model.getArtistMbid(), tc.ARTIST_MBID);
    assertEquals(model.getArtistName(), tc.ARTIST_NAME);
    assertEquals(model.getContentType(), "Artist");
    assertEquals(model.getFavoritesTimestamp(), tc.FAVORITES_TIMESTAMP);
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

    assertEquals(model.getSongId(), tc.SONG_UUID);
    assertEquals(model.getSongName(), tc.SONG_NAME);
    assertEquals(model.getSongDuration(), tc.SONG_DURATION);

    assertEquals(model.getArtistId(), tc.ARTIST_UUID);
    assertEquals(model.getArtistName(), tc.ARTIST_NAME);
    assertEquals(model.getArtistMbid(), tc.ARTIST_MBID);

    assertEquals(model.getAlbumId(), tc.ALBUM_UUID);
    assertEquals(model.getAlbumName(), tc.ALBUM_NAME);
    assertEquals(model.getAlbumYear(), tc.SONG_YEAR);

    assertEquals(model.getFavoritesTimestamp(), tc.FAVORITES_TIMESTAMP);
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
    albumBo.setAlbumId(tc.ALBUM_UUID);
    albumBo.setAlbumName(tc.ALBUM_NAME);
    albumBo.setYear(tc.ALBUM_YEAR);
    albumBo.setArtistId(tc.ARTIST_UUID);
    albumBo.setArtistName(tc.ARTIST_NAME);
    albumBo.setArtistMbid(tc.ARTIST_MBID);
    albumBo.setFavoritesTimestamp(Long.toString(tc.FAVORITES_TIMESTAMP.getTime()));
  }

  private void initArtistBo() {
    artistBo.setArtistId(tc.ARTIST_UUID);
    artistBo.setArtistName(tc.ARTIST_NAME);
    artistBo.setArtistMbid(tc.ARTIST_MBID);
    artistBo.setFavoritesTimestamp(Long.toString(tc.FAVORITES_TIMESTAMP.getTime()));
  }

  private void initSongBo() {
    songBo.setSongId(tc.SONG_UUID);
    songBo.setSongName(tc.SONG_NAME);
    songBo.setDuration(tc.SONG_DURATION);
    songBo.setYear(tc.SONG_YEAR);
    songBo.setArtistId(tc.ARTIST_UUID);
    songBo.setArtistName(tc.ARTIST_NAME);
    songBo.setAlbumId(tc.ALBUM_UUID);
    songBo.setAlbumName(tc.ALBUM_NAME);
    songBo.setArtistMbid(tc.ARTIST_MBID);
    songBo.setFavoritesTimestamp(Long.toString(tc.FAVORITES_TIMESTAMP.getTime()));
  }
}
