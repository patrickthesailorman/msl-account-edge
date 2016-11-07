/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.account.edge.services.impl;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.mapping.Result;
import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.kenzan.msl.account.client.dto.AlbumsByUserDto;
import com.kenzan.msl.account.client.dto.ArtistsByUserDto;
import com.kenzan.msl.account.client.dto.SongsByUserDto;
import com.kenzan.msl.account.client.services.AccountDataClientService;
import com.kenzan.msl.account.edge.services.LibraryService;
import com.kenzan.msl.account.edge.services.LibraryServiceHelper;
import com.kenzan.msl.account.edge.services.RatingsService;
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

public class LibraryServiceImpl implements LibraryService {

  private final AccountDataClientService accountDataClientService;
  private final LibraryServiceHelper libraryServiceHelper;
  private final RatingsService ratingsService;

  /**
   * Constructor
   *
   * @param accountDataClientService com.kenzan.msl.account.client.services.AccountDataClientService
   * @param libraryServiceHelper com.kenzan.msl.catalog.client.services.LibraryServiceHelper
   * @param ratingsService com.kenzan.msl.account.edge.services.impl.RatingsServiceImpl
   */
  @Inject
  public LibraryServiceImpl(final AccountDataClientService accountDataClientService,
      final LibraryServiceHelper libraryServiceHelper, final RatingsService ratingsService) {
    this.accountDataClientService = accountDataClientService;
    this.libraryServiceHelper = libraryServiceHelper;
    this.ratingsService = ratingsService;
  }

  /**
   * Retrieves the MyLibrary object with contained albums artist and songs
   *
   * @param sessionToken uuid of authenticated user
   * @return MyLibrary
   */
  public MyLibrary get(final String sessionToken) {

    MyLibrary myLibrary = new MyLibrary();

    List<AlbumInfo> albumInfoList = getMyLibraryAlbums(sessionToken);
    ratingsService.processAlbumRatings(albumInfoList, UUID.fromString(sessionToken));
    myLibrary.setAlbums(albumInfoList);

    List<ArtistInfo> artistInfoList = getMyLibraryArtists(sessionToken);
    ratingsService.processArtistRatings(artistInfoList, UUID.fromString(sessionToken));
    myLibrary.setArtists(artistInfoList);

    List<SongInfo> songInfoList = getMyLibrarySongs(sessionToken);
    ratingsService.processSongRatings(songInfoList, UUID.fromString(sessionToken));
    myLibrary.setSongs(songInfoList);

    return myLibrary;
  }

  /**
   * Adds data into a user library
   *
   * @param id object uuid
   * @param sessionToken authenticated user uuid
   * @param contentType content type (artist|album|song)
   */
  public void add(final String id, final String sessionToken, final String contentType) {

    MyLibrary myLibrary = get(sessionToken);

    switch (contentType) {
      case "Artist":
        Optional<ArtistBo> optArtistBo = libraryServiceHelper.getArtist(UUID.fromString(id));

        if (optArtistBo.isPresent() && !isInLibrary(optArtistBo.get(), myLibrary)) {
          try {
            ArtistsByUserDto optArtistDto = Translators.translate(optArtistBo.get());
            optArtistDto.setUserId(UUID.fromString(sessionToken));
            accountDataClientService.addOrUpdateArtistsByUser(optArtistDto);
          } catch (Exception error) {
            throw error;
          }
        } else if (!optArtistBo.isPresent()) {
          throw new RuntimeErrorException(new Error("Unable to retrieve artist"));
        }
        break;
      case "Album":
        Optional<AlbumBo> optAlbumBo = libraryServiceHelper.getAlbum(UUID.fromString(id));

        if (optAlbumBo.isPresent() && !isInLibrary(optAlbumBo.get(), myLibrary)) {
          try {
            AlbumsByUserDto optAlbumDto = Translators.translate(optAlbumBo.get());
            optAlbumDto.setUserId(UUID.fromString(sessionToken));
            accountDataClientService.addOrUpdateAlbumsByUser(optAlbumDto);
          } catch (Exception error) {
            throw error;
          }
        } else if (!optAlbumBo.isPresent()) {
          throw new RuntimeErrorException(new Error("Unable to retrieve album"));
        }
        break;
      case "Song":
        Optional<SongBo> optSongBo = libraryServiceHelper.getSong(UUID.fromString(id));

        if (optSongBo.isPresent() && !isInLibrary(optSongBo.get(), myLibrary)) {
          try {
            SongsByUserDto optSongDto = Translators.translate(optSongBo.get());
            optSongDto.setUserId(UUID.fromString(sessionToken));
            accountDataClientService.addOrUpdateSongsByUser(optSongDto);
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
   * @param id song, album or artist Id
   * @param timestamp timestamp set on REST endpoint as String
   * @param sessionToken user UUID as String
   * @param contentType song, album or artist
   */
  public void remove(final String id, final String timestamp, final String sessionToken,
      final String contentType) {

    Date favoritesTimestamp = new Date(Long.parseLong(timestamp));
    switch (contentType) {
      case "Song":
        try {
          int initialSongsOnLibrary = getMyLibrarySongs(sessionToken).size();
          accountDataClientService.deleteSongsByUser(UUID.fromString(sessionToken),
              favoritesTimestamp, UUID.fromString(id));
          int postSongsOnLibrary = getMyLibrarySongs(sessionToken).size();
          if (initialSongsOnLibrary <= postSongsOnLibrary) {
            throw new RuntimeErrorException(new Error("Unable to delete song"));
          }
        } catch (RuntimeErrorException err) {
          throw err;
        }
        break;
      case "Artist":
        try {
          int initialArtistsOnLibrary = getMyLibraryArtists(sessionToken).size();
          accountDataClientService.deleteArtistsByUser(UUID.fromString(sessionToken),
              favoritesTimestamp, UUID.fromString(id));
          int postArtistsOnLibrary = getMyLibraryArtists(sessionToken).size();
          if (initialArtistsOnLibrary <= postArtistsOnLibrary) {
            throw new RuntimeErrorException(new Error("Unable to delete artist"));
          }
        } catch (RuntimeErrorException err) {
          throw err;
        }
        break;
      case "Album":
        try {
          int initialAlbumsOnLibrary = getMyLibraryAlbums(sessionToken).size();
          accountDataClientService.deleteAlbumsByUser(UUID.fromString(sessionToken),
              favoritesTimestamp, UUID.fromString(id));
          int postAlbumsOnLibrary = getMyLibraryAlbums(sessionToken).size();
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
   * @param uuid authenticated user uuid
   * @return List&lt;AlbumInfo&gt;
   */
  private List<AlbumInfo> getMyLibraryAlbums(final String uuid) {
    Observable<ResultSet> results =
        accountDataClientService.getAlbumsByUser(UUID.fromString(uuid), Optional.absent(),
            Optional.absent());

    Result<AlbumsByUserDto> mappedResults =
        accountDataClientService.mapAlbumsByUser(results).toBlocking().first();

    return Translators.translateAlbumsByUserDto(mappedResults);
  }

  /**
   * Retrieves the artist list from a respective user library
   *
   * @param uuid uuid of authenticated user
   * @return List&lt;ArtistInfo&gt;
   */
  private List<ArtistInfo> getMyLibraryArtists(final String uuid) {

    Observable<ResultSet> results =
        accountDataClientService.getArtistsByUser(UUID.fromString(uuid), Optional.absent(),
            Optional.absent());

    Result<ArtistsByUserDto> mappedResults =
        accountDataClientService.mapArtistByUser(results).toBlocking().first();

    return Translators.translateArtistByUserDto(mappedResults);
  }

  /**
   * Retrieves the songs from a user library
   *
   * @param uuid authenticated user uuid
   * @return List&lt;SongInfo&gt;
   */
  private List<SongInfo> getMyLibrarySongs(final String uuid) {

    Observable<ResultSet> results =
        accountDataClientService.getSongsByUser(UUID.fromString(uuid), Optional.absent(),
            Optional.absent());

    Result<SongsByUserDto> mappedResults =
        accountDataClientService.mapSongsByUser(results).toBlocking().first();

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
