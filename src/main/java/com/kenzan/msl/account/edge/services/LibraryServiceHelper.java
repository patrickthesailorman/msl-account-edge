/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.account.edge.services;

import com.google.common.base.Optional;
import com.kenzan.msl.catalog.client.dao.AlbumArtistBySongDao;
import com.kenzan.msl.catalog.client.dao.SongsAlbumsByArtistDao;
import com.kenzan.msl.catalog.client.dao.SongsArtistByAlbumDao;
import com.kenzan.msl.catalog.client.services.CassandraCatalogService;
import com.kenzan.msl.common.bo.AlbumBo;
import com.kenzan.msl.common.bo.ArtistBo;
import com.kenzan.msl.common.bo.SongBo;

import java.util.UUID;

public class LibraryServiceHelper {

    private CassandraCatalogService cassandraCatalogService = CassandraCatalogService.getInstance();

    /**
     * Retrieves a specific artist from teh songs_albums_by_artist cassandra table
     *
     * @param artistId java.util.UUID
     * @return Optional<ArtistBo>
     */
    public Optional<ArtistBo> getArtist(final UUID artistId) {
        ArtistBo artistBo = new ArtistBo();

        SongsAlbumsByArtistDao songsAlbumsByArtistDao = cassandraCatalogService
            .mapSongsAlbumsByArtist(cassandraCatalogService.getSongsAlbumsByArtist(artistId, Optional.absent()))
            .toBlocking().first().one();

        if ( songsAlbumsByArtistDao == null ) {
            return Optional.absent();
        }
        else {
            artistBo.setArtistId(songsAlbumsByArtistDao.getArtistId());
            artistBo.setArtistName(songsAlbumsByArtistDao.getArtistName());

            if ( songsAlbumsByArtistDao.getArtistGenres() != null
                && songsAlbumsByArtistDao.getArtistGenres().size() > 0 ) {
                artistBo.setGenre(songsAlbumsByArtistDao.getArtistGenres().iterator().next());
            }
            if ( songsAlbumsByArtistDao.getSimilarArtists() != null ) {
                for ( UUID similarArtistUuid : songsAlbumsByArtistDao.getSimilarArtists().keySet() ) {
                    artistBo.getSimilarArtistsList().add(similarArtistUuid.toString());
                }
            }

            return Optional.of(artistBo);
        }
    }

    /**
     * Retrieves a specific album from the songs_artist_by_album cassandra table
     *
     * @param albumId java.util.UUID
     * @return Optional<AlbumBo>
     */
    public Optional<AlbumBo> getAlbum(final UUID albumId) {
        AlbumBo albumBo = new AlbumBo();

        SongsArtistByAlbumDao songsArtistByAlbumDao = cassandraCatalogService
            .mapSongsArtistByAlbum(cassandraCatalogService.getSongsArtistByAlbum(albumId, Optional.absent()))
            .toBlocking().first().one();

        if ( null == songsArtistByAlbumDao ) {
            return Optional.absent();
        }
        else {
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
    }

    /**
     * Retrieves a specific album from the songs_artist_by_album cassandra table
     *
     * @param songId java.util.UUID
     * @return Optional<SongBo>
     */
    public Optional<SongBo> getSong(final UUID songId) {
        SongBo songBo = new SongBo();

        AlbumArtistBySongDao albumArtistBySongDao = cassandraCatalogService
            .mapAlbumArtistBySong(cassandraCatalogService.getAlbumArtistBySong(songId, Optional.absent())).toBlocking()
            .first().one();

        if ( null == albumArtistBySongDao ) {
            return Optional.absent();
        }
        else {
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
}
