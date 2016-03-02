/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.account.edge.translate;

import com.kenzan.msl.account.client.translate.AccountClientTranslators;

import com.kenzan.msl.account.client.dto.AlbumsByUserDto;
import com.kenzan.msl.account.client.dto.ArtistsByUserDto;
import com.kenzan.msl.account.client.dto.SongsByUserDto;
import com.kenzan.msl.common.bo.AlbumBo;
import com.kenzan.msl.common.bo.ArtistBo;
import com.kenzan.msl.common.bo.SongBo;

import java.util.Date;

public class Translators extends AccountClientTranslators {

  /**
   * Translates AlbumBo to AlbumsDto
   *
   * @param album AlbumBo
   * @return AlbumsDto
   */
  public static AlbumsByUserDto translate(AlbumBo album) {
    AlbumsByUserDto model = new AlbumsByUserDto();
    model.setAlbumId(album.getAlbumId());
    model.setAlbumName(album.getAlbumName());
    model.setAlbumYear(album.getYear());
    model.setArtistId(album.getArtistId());
    model.setArtistMbid(album.getArtistMbid());
    model.setArtistName(album.getArtistName());
    model.setContentType("Album");
    if (album.getFavoritesTimestamp() != null) {
      model.setFavoritesTimestamp(new Date(Long.parseLong(album.getFavoritesTimestamp())));
    }
    return model;
  }

  /**
   * Translates ArtistBo to ArtistsDto
   *
   * @param artist ArtistBo
   * @return ArtistsDto
   */
  public static ArtistsByUserDto translate(ArtistBo artist) {
    ArtistsByUserDto model = new ArtistsByUserDto();
    model.setArtistId(artist.getArtistId());
    model.setArtistMbid(artist.getArtistMbid());
    model.setArtistName(artist.getArtistName());
    model.setContentType("Artist");
    if (artist.getFavoritesTimestamp() != null) {
      model.setFavoritesTimestamp(new Date(Long.parseLong(artist.getFavoritesTimestamp())));
    }
    return model;
  }

  /**
   * Translates SongBo to SongsDto
   *
   * @param song SongBo
   * @return SongsDto
   */
  public static SongsByUserDto translate(SongBo song) {
    SongsByUserDto model = new SongsByUserDto();
    model.setArtistId(song.getArtistId());
    model.setArtistMbid(song.getArtistMbid());
    model.setArtistName(song.getArtistName());
    model.setAlbumId(song.getAlbumId());
    model.setAlbumName(song.getAlbumName());
    model.setAlbumYear(song.getYear());
    model.setSongDuration(song.getDuration());
    model.setSongId(song.getSongId());
    model.setSongName(song.getSongName());
    if (song.getFavoritesTimestamp() != null) {
      model.setFavoritesTimestamp(new Date(Long.parseLong(song.getFavoritesTimestamp())));
    }
    model.setContentType("Song");
    return model;
  }

}
