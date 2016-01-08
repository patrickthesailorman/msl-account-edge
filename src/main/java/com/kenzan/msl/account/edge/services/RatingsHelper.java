package com.kenzan.msl.account.edge.services;

import com.kenzan.msl.ratings.client.dao.AverageRatingsDao;
import com.kenzan.msl.ratings.client.dao.UserRatingsDao;
import com.kenzan.msl.ratings.client.services.CassandraRatingsService;
import io.swagger.model.AlbumInfo;
import io.swagger.model.ArtistInfo;
import io.swagger.model.SongInfo;

import java.util.List;
import java.util.UUID;

public class RatingsHelper {

    private static RatingsHelper instance = null;

    private RatingsHelper() {
    }

    public static RatingsHelper getInstance() {
        if ( instance == null ) {
            instance = new RatingsHelper();
        }
        return instance;
    }

    /**
     * Adds average and user ratings to a list of AlbumInfo objects
     *
     * @param albumList List<AlbumInfo>
     * @param userUuid java.util.UUID
     */
    public void processAlbumRatings(List<AlbumInfo> albumList, UUID userUuid) {
        CassandraRatingsService cassandraRatingsService = CassandraRatingsService.getInstance();
        for ( AlbumInfo albumInfo : albumList ) {
            AverageRatingsDao averageRatingsDao = cassandraRatingsService
                .getAverageRating(UUID.fromString(albumInfo.getAlbumId()), "Album").toBlocking().first();

            if ( averageRatingsDao != null ) {
                long average = averageRatingsDao.getNumRating() / averageRatingsDao.getSumRating();
                albumInfo.setAverageRating((int) average);
            }

            UserRatingsDao userRatingsDao = cassandraRatingsService
                .getUserRating(userUuid, "Album", UUID.fromString(albumInfo.getAlbumId())).toBlocking().first();
            if ( userRatingsDao != null ) {
                albumInfo.setPersonalRating(userRatingsDao.getRating());
            }

        }
    }

    /**
     * Adds average and user ratings to a list of ArtistInfo objects
     *
     * @param artistList List<ArtistInfo>
     * @param userUuid java.util.UUID
     */
    public void processArtistRatings(List<ArtistInfo> artistList, UUID userUuid) {
        CassandraRatingsService cassandraRatingsService = CassandraRatingsService.getInstance();
        for ( ArtistInfo artistInfo : artistList ) {
            AverageRatingsDao averageRatingsDao = cassandraRatingsService
                .getAverageRating(UUID.fromString(artistInfo.getArtistId()), "Artist").toBlocking().first();

            if ( averageRatingsDao != null ) {
                long average = averageRatingsDao.getNumRating() / averageRatingsDao.getSumRating();
                artistInfo.setAverageRating((int) average);
            }

            UserRatingsDao userRatingsDao = cassandraRatingsService
                .getUserRating(userUuid, "Artist", UUID.fromString(artistInfo.getArtistId())).toBlocking().first();
            if ( userRatingsDao != null ) {
                artistInfo.setPersonalRating(userRatingsDao.getRating());
            }

        }
    }

    /**
     * Adds average and user rating data on a list of SongInfo objects
     *
     * @param songList List<SongInfo>
     * @param userUuid java.util.UUID
     */
    public void processSongRatings(List<SongInfo> songList, UUID userUuid) {
        CassandraRatingsService cassandraRatingsService = CassandraRatingsService.getInstance();
        for ( SongInfo songInfo : songList ) {
            AverageRatingsDao averageRatingsDao = cassandraRatingsService
                .getAverageRating(UUID.fromString(songInfo.getSongId()), "Song").toBlocking().first();

            if ( averageRatingsDao != null ) {
                long average = averageRatingsDao.getNumRating() / averageRatingsDao.getSumRating();
                songInfo.setAverageRating((int) average);
            }

            UserRatingsDao userRatingsDao = cassandraRatingsService
                .getUserRating(userUuid, "Song", UUID.fromString(songInfo.getSongId())).toBlocking().first();
            if ( userRatingsDao != null ) {
                songInfo.setPersonalRating(userRatingsDao.getRating());
            }
        }
    }

}
