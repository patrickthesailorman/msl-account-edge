/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.account.edge.services;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.mapping.Result;
import com.google.common.base.Optional;
import com.kenzan.msl.catalog.client.dao.AlbumArtistBySongDao;
import com.kenzan.msl.catalog.client.dao.SongsAlbumsByArtistDao;
import com.kenzan.msl.catalog.client.dao.SongsArtistByAlbumDao;
import com.kenzan.msl.catalog.client.services.CassandraCatalogService;
import com.kenzan.msl.common.bo.AlbumBo;
import com.kenzan.msl.common.bo.ArtistBo;
import com.kenzan.msl.common.bo.SongBo;
import rx.Observable;

import java.util.UUID;

public class LibraryServiceHelper {

    /**
     * Retrieves a specific artist from teh songs_albums_by_artist cassandra table
     *
     * @param artistId java.util.UUID
     * @return Optional<ArtistBo>
     */
    public Optional<ArtistBo> getArtist(final UUID artistId) {
        CassandraCatalogService cassandraCatalogService = CassandraCatalogService.getInstance();

        Observable<ResultSet> observableArtist = cassandraCatalogService.getSongsAlbumsByArtist(artistId,
                                                                                                Optional.absent());

        Result<SongsAlbumsByArtistDao> mappingResult = cassandraCatalogService.mapSongsAlbumsByArtist(observableArtist)
            .toBlocking().first();

        if ( mappingResult == null ) {
            return Optional.absent();
        }

        ArtistBo artistBo = new ArtistBo();
        SongsAlbumsByArtistDao songsAlbumsByArtistDao = mappingResult.one();

        artistBo.setArtistId(songsAlbumsByArtistDao.getArtistId());
        artistBo.setArtistName(songsAlbumsByArtistDao.getArtistName());

        if ( songsAlbumsByArtistDao.getArtistGenres() != null && songsAlbumsByArtistDao.getArtistGenres().size() > 0 ) {
            artistBo.setGenre(songsAlbumsByArtistDao.getArtistGenres().iterator().next());
        }
        if ( songsAlbumsByArtistDao.getSimilarArtists() != null ) {
            for ( UUID similarArtistUuid : songsAlbumsByArtistDao.getSimilarArtists().keySet() ) {
                artistBo.getSimilarArtistsList().add(similarArtistUuid.toString());
            }
        }

        return Optional.of(artistBo);
    }

    /**
     * Retrieves a specific album from the songs_artist_by_album cassandra table
     *
     * @param albumId java.util.UUID
     * @return Optional<AlbumBo>
     */
    public Optional<AlbumBo> getAlbum(final UUID albumId) {
        CassandraCatalogService cassandraCatalogService = CassandraCatalogService.getInstance();

        Observable<ResultSet> observableAlbum = cassandraCatalogService.getSongsArtistByAlbum(albumId,
                                                                                              Optional.absent());

        Result<SongsArtistByAlbumDao> mapResults = cassandraCatalogService.mapSongsArtistByAlbum(observableAlbum)
            .toBlocking().first();

        if ( null == mapResults ) {
            return Optional.absent();
        }

        AlbumBo albumBo = new AlbumBo();
        SongsArtistByAlbumDao songsArtistByAlbumDao = mapResults.one();

        albumBo.setAlbumId(songsArtistByAlbumDao.getAlbumId());
        albumBo.setAlbumName(songsArtistByAlbumDao.getAlbumName());
        albumBo.setArtistId(songsArtistByAlbumDao.getArtistId());
        albumBo.setArtistName(songsArtistByAlbumDao.getArtistName());
        albumBo.setImageLink(songsArtistByAlbumDao.getImageLink());

        if ( songsArtistByAlbumDao.getArtistGenres() != null && songsArtistByAlbumDao.getArtistGenres().size() > 0 ) {
            albumBo.setGenre(songsArtistByAlbumDao.getArtistGenres().iterator().next());
        }

        return Optional.of(albumBo);
    }

    /**
     * Retrieves a specific album from the songs_artist_by_album cassandra table
     *
     * @param songId java.util.UUID
     * @return Optional<SongBo>
     */
    public Optional<SongBo> getSong(final UUID songId) {
        CassandraCatalogService cassandraCatalogService = CassandraCatalogService.getInstance();

        Observable<ResultSet> observableSong = cassandraCatalogService.getAlbumArtistBySong(songId, Optional.absent());

        Result<AlbumArtistBySongDao> mapResults = cassandraCatalogService.mapAlbumArtistBySong(observableSong)
            .toBlocking().first();

        if ( null == mapResults ) {
            return Optional.absent();
        }

        SongBo songBo = new SongBo();
        AlbumArtistBySongDao albumArtistBySongDao = mapResults.one();

        songBo.setSongId(albumArtistBySongDao.getSongId());
        songBo.setSongName(albumArtistBySongDao.getSongName());
        songBo.setAlbumId(albumArtistBySongDao.getAlbumId());
        songBo.setAlbumName(albumArtistBySongDao.getAlbumName());
        songBo.setArtistId(albumArtistBySongDao.getArtistId());
        songBo.setArtistName(albumArtistBySongDao.getArtistName());
        songBo.setDuration(albumArtistBySongDao.getSongDuration());
        songBo.setYear(albumArtistBySongDao.getAlbumYear());

        if ( albumArtistBySongDao.getArtistGenres() != null && albumArtistBySongDao.getArtistGenres().size() > 0 ) {
            songBo.setGenre(albumArtistBySongDao.getArtistGenres().iterator().next());
        }

        return Optional.of(songBo);
    }
}
