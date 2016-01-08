/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.account.edge.translate;

import com.kenzan.msl.account.client.translate.AccountClientTranslators;

import com.kenzan.msl.account.client.dao.*;
import com.kenzan.msl.common.bo.AlbumBo;
import com.kenzan.msl.common.bo.ArtistBo;
import com.kenzan.msl.common.bo.SongBo;

import java.util.Date;

public class Translators extends AccountClientTranslators {

    // ==========================================================================================================
    // ALBUMS
    // ==========================================================================================================

    public static AlbumsByUserDao translate(AlbumBo album) {
        AlbumsByUserDao model = new AlbumsByUserDao();
        model.setAlbumId(album.getAlbumId());
        model.setAlbumName(album.getAlbumName());
        model.setAlbumYear(album.getYear());
        model.setArtistId(album.getArtistId());
        model.setArtistMbid(album.getArtistMbid());
        model.setArtistName(album.getArtistName());
        model.setContentType("Album");
        if ( album.getFavoritesTimestamp() != null ) {
            model.setFavoritesTimestamp(new Date(Long.parseLong(album.getFavoritesTimestamp())));
        }
        return model;
    }

    // =========================================================================================================
    // ARTISTS
    // =========================================================================================================

    public static ArtistsByUserDao translate(ArtistBo artist) {
        ArtistsByUserDao model = new ArtistsByUserDao();
        model.setArtistName(artist.getArtistName());
        model.setArtistMbid(artist.getArtistMbid());
        model.setArtistId(artist.getArtistId());
        model.setContentType("Artist");
        if ( artist.getFavoritesTimestamp() != null ) {
            model.setFavoritesTimestamp(new Date(Long.parseLong(artist.getFavoritesTimestamp())));
        }
        return model;
    }

    // ===========================================================================================================
    // SONGS
    // ===========================================================================================================

    public static SongsByUserDao translate(SongBo song) {
        SongsByUserDao model = new SongsByUserDao();
        model.setArtistId(song.getArtistId());
        model.setArtistMbid(song.getArtistMbid());
        model.setArtistName(song.getArtistName());
        model.setAlbumId(song.getAlbumId());
        model.setAlbumName(song.getAlbumName());
        model.setAlbumYear(song.getYear());
        model.setSongDuration(song.getDuration());
        model.setSongId(song.getSongId());
        model.setSongName(song.getSongName());
        if ( song.getFavoritesTimestamp() != null ) {
            model.setFavoritesTimestamp(new Date(Long.parseLong(song.getFavoritesTimestamp())));
        }
        model.setContentType("Song");
        return model;
    }

}
