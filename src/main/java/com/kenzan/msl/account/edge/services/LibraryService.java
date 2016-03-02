/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.account.edge.services;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.mapping.Result;
import com.google.common.base.Optional;
import com.kenzan.msl.account.client.dto.AlbumsByUserDto;
import com.kenzan.msl.account.client.dto.ArtistsByUserDto;
import com.kenzan.msl.account.client.dto.SongsByUserDto;
import com.kenzan.msl.account.client.services.CassandraAccountService;
import com.kenzan.msl.account.edge.translate.Translators;
import com.kenzan.msl.common.bo.AbstractBo;
import com.kenzan.msl.common.bo.AlbumBo;
import com.kenzan.msl.common.bo.ArtistBo;
import com.kenzan.msl.common.bo.SongBo;
import io.swagger.model.AlbumInfo;
import io.swagger.model.ArtistInfo;
import io.swagger.model.MyLibrary;
import io.swagger.model.SongInfo;
import rx.Observable;

import javax.management.RuntimeErrorException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class LibraryService extends LibraryServiceHelper {

  /**
   * Retrieves the MyLibrary object with contained albums artist and songs
   *
   * @param cassandraAccountService com.kenzan.msl.server.services.CassandraAccountService
   * @param sessionToken uuid of authenticated user
   * @return MyLibrary
   */
  public MyLibrary get(final CassandraAccountService cassandraAccountService,
      final String sessionToken) {

    RatingsHelper ratingsHelper = RatingsHelper.getInstance();

    MyLibrary myLibrary = new MyLibrary();

    List<AlbumInfo> albumInfoList = getMyLibraryAlbums(cassandraAccountService, sessionToken);
    ratingsHelper.processAlbumRatings(albumInfoList, UUID.fromString(sessionToken));
    myLibrary.setAlbums(albumInfoList);

    List<ArtistInfo> artistInfoList = getMyLibraryArtists(cassandraAccountService, sessionToken);
    ratingsHelper.processArtistRatings(artistInfoList, UUID.fromString(sessionToken));
    myLibrary.setArtists(artistInfoList);

    List<SongInfo> songInfoList = getMyLibrarySongs(cassandraAccountService, sessionToken);
    ratingsHelper.processSongRatings(songInfoList, UUID.fromString(sessionToken));
    myLibrary.setSongs(songInfoList);

    return myLibrary;
  }

  /**
   * Adds data into a user library
   *
   * @param cassandraAccountService com.kenzan.msl.server.services.CassandraAccountService
   * @param id object uuid
   * @param sessionToken authenticated user uuid
   * @param contentType content type (artist|album|song)
   */
  public void add(final CassandraAccountService cassandraAccountService, final String id,
      final String sessionToken, final String contentType) {

    MyLibrary myLibrary = get(cassandraAccountService, sessionToken);

    switch (contentType) {
      case "Artist":
        Optional<ArtistBo> optArtistBo = getArtist(UUID.fromString(id));

        if (optArtistBo.isPresent() && !isInLibrary(optArtistBo.get(), myLibrary)) {
          try {
            ArtistsByUserDto optArtistDto = Translators.translate(optArtistBo.get());
            optArtistDto.setUserId(UUID.fromString(sessionToken));
            cassandraAccountService.addOrUpdateArtistsByUser(optArtistDto);
          } catch (Exception error) {
            throw error;
          }
        } else if (!optArtistBo.isPresent()) {
          throw new RuntimeErrorException(new Error("Unable to retrieve artist"));
        }
        break;
      case "Album":
        Optional<AlbumBo> optAlbumBo = getAlbum(UUID.fromString(id));

        if (optAlbumBo.isPresent() && !isInLibrary(optAlbumBo.get(), myLibrary)) {
          try {
            AlbumsByUserDto optAlbumDto = Translators.translate(optAlbumBo.get());
            optAlbumDto.setUserId(UUID.fromString(sessionToken));
            cassandraAccountService.addOrUpdateAlbumsByUser(optAlbumDto);
          } catch (Exception error) {
            throw error;
          }
        } else if (!optAlbumBo.isPresent()) {
          throw new RuntimeErrorException(new Error("Unable to retrieve album"));
        }
        break;
      case "Song":
        Optional<SongBo> optSongBo = getSong(UUID.fromString(id));

        if (optSongBo.isPresent() && !isInLibrary(optSongBo.get(), myLibrary)) {
          try {
            SongsByUserDto optSongDto = Translators.translate(optSongBo.get());
            optSongDto.setUserId(UUID.fromString(sessionToken));
            cassandraAccountService.addOrUpdateSongsByUser(optSongDto);
          } catch (Exception error) {
            throw error;
          }
        } else if (!optSongBo.isPresent()) {
          throw new RuntimeErrorException(new Error("Unable to retrieve song"));
        }
        break;
    }
  }

  /**
   * Deletes a song, an album or an artist from a user library
   *
   * @param cassandraAccountService com.kenzan.msl.server.services.CassandraAccountService
   * @param id song, album or artist Id
   * @param timestamp timestamp set on REST endpoint as String
   * @param sessionToken user UUID as String
   * @param contentType song, album or artist
   */
  public void remove(final CassandraAccountService cassandraAccountService, final String id,
      final String timestamp, final String sessionToken, final String contentType) {

    Date favoritesTimestamp = new Date(Long.parseLong(timestamp));
    switch (contentType) {
      case "Song":
        try {
          int initialSongsOnLibrary =
              getMyLibrarySongs(cassandraAccountService, sessionToken).size();
          cassandraAccountService.deleteSongsByUser(UUID.fromString(sessionToken),
              favoritesTimestamp, UUID.fromString(id));
          int postSongsOnLibrary = getMyLibrarySongs(cassandraAccountService, sessionToken).size();
          if (initialSongsOnLibrary <= postSongsOnLibrary) {
            throw new RuntimeErrorException(new Error("Unable to delete song"));
          }
        } catch (RuntimeErrorException err) {
          throw err;
        }
        break;
      case "Artist":
        try {
          int initialArtistsOnLibrary =
              getMyLibraryArtists(cassandraAccountService, sessionToken).size();
          cassandraAccountService.deleteArtistsByUser(UUID.fromString(sessionToken),
              favoritesTimestamp, UUID.fromString(id));
          int postArtistsOnLibrary =
              getMyLibraryArtists(cassandraAccountService, sessionToken).size();
          if (initialArtistsOnLibrary <= postArtistsOnLibrary) {
            throw new RuntimeErrorException(new Error("Unable to delete artist"));
          }
        } catch (RuntimeErrorException err) {
          throw err;
        }
        break;
      case "Album":
        try {
          int initialAlbumsOnLibrary =
              getMyLibraryAlbums(cassandraAccountService, sessionToken).size();
          cassandraAccountService.deleteAlbumsByUser(UUID.fromString(sessionToken),
              favoritesTimestamp, UUID.fromString(id));
          int postAlbumsOnLibrary =
              getMyLibraryAlbums(cassandraAccountService, sessionToken).size();
          if (initialAlbumsOnLibrary <= postAlbumsOnLibrary) {
            throw new RuntimeErrorException(new Error("Unable to delete album"));
          }
        } catch (RuntimeErrorException err) {
          throw err;
        }
        break;
    }
  }

  /**
   * Retrieves the album on a user library
   *
   * @param cassandraAccountService com.kenzan.msl.server.services.CassandraAccountService
   * @param uuid authenticated user uuid
   * @return List&lt;AlbumInfo&gt;
   */
  private List<AlbumInfo> getMyLibraryAlbums(final CassandraAccountService cassandraAccountService,
      final String uuid) {
    Observable<ResultSet> results =
        cassandraAccountService.getAlbumsByUser(UUID.fromString(uuid), Optional.absent(),
            Optional.absent());

    Result<AlbumsByUserDto> mappedResults =
        cassandraAccountService.mapAlbumsByUser(results).toBlocking().first();

    return Translators.translateAlbumsByUserDto(mappedResults);
  }

  /**
   * Retrieves the artist list from a respective user library
   *
   * @param cassandraAccountService com.kenzan.msl.server.services.CassandraAccountService
   * @param uuid uuid of authenticated user
   * @return List&lt;ArtistInfo&gt;
   */
  private List<ArtistInfo> getMyLibraryArtists(
      final CassandraAccountService cassandraAccountService, final String uuid) {

    Observable<ResultSet> results =
        cassandraAccountService.getArtistsByUser(UUID.fromString(uuid), Optional.absent(),
            Optional.absent());

    Result<ArtistsByUserDto> mappedResults =
        cassandraAccountService.mapArtistByUser(results).toBlocking().first();

    return Translators.translateArtistByUserDto(mappedResults);
  }

  /**
   * Retrieves the songs from a user library
   *
   * @param cassandraAccountService com.kenzan.msl.server.services.CassandraAccountService
   * @param uuid authenticated user uuid
   * @return List&lt;SongInfo&gt;
   */
  private List<SongInfo> getMyLibrarySongs(final CassandraAccountService cassandraAccountService,
      final String uuid) {

    Observable<ResultSet> results =
        cassandraAccountService.getSongsByUser(UUID.fromString(uuid), Optional.absent(),
            Optional.absent());

    Result<SongsByUserDto> mappedResults =
        cassandraAccountService.mapSongsByUser(results).toBlocking().first();

    return Translators.translateSongsByUserDto(mappedResults);
  }

  /**
   * Checks if an object is already on the library and if it is, it attach the timestamp on the
   * abstractBo object
   *
   * @param abstractBo artistInfo, albumInfo or songInfo
   * @param library MyLibrary
   * @return boolean
   */
  private boolean isInLibrary(AbstractBo abstractBo, MyLibrary library) {
    if (abstractBo instanceof AlbumBo) {
      AlbumBo album = (AlbumBo) abstractBo;
      for (AlbumInfo albumInfo : library.getAlbums()) {
        if (albumInfo.getAlbumId().equals(album.getAlbumId().toString())) {
          album.setFavoritesTimestamp(albumInfo.getFavoritesTimestamp());
          return true;
        }
      }
    } else if (abstractBo instanceof ArtistBo) {
      ArtistBo artist = (ArtistBo) abstractBo;
      for (ArtistInfo artistInfo : library.getArtists()) {
        if (artistInfo.getArtistId().equals(artist.getArtistId().toString())) {
          artist.setFavoritesTimestamp(artistInfo.getFavoritesTimestamp());
          return true;
        }
      }
    } else if (abstractBo instanceof SongBo) {
      SongBo song = (SongBo) abstractBo;
      for (SongInfo songInfo : library.getSongs()) {
        if (songInfo.getSongId().equals(song.getSongId().toString())) {
          song.setFavoritesTimestamp(songInfo.getFavoritesTimestamp());
          return true;
        }
      }
    }
    return false;
  }
}
