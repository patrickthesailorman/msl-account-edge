package com.kenzan.msl.account.edge.services;

import com.kenzan.msl.ratings.client.dto.AverageRatingsDto;
import com.kenzan.msl.ratings.client.dto.UserRatingsDto;
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
            AverageRatingsDto averageRatingsDto = cassandraRatingsService
                .getAverageRating(UUID.fromString(albumInfo.getAlbumId()), "Album").toBlocking().first();

            if ( averageRatingsDto != null ) {
                long average = averageRatingsDto.getNumRating() / averageRatingsDto.getSumRating();
                albumInfo.setAverageRating((int) average);
            }

            UserRatingsDto userRatingsDto = cassandraRatingsService
                .getUserRating(userUuid, "Album", UUID.fromString(albumInfo.getAlbumId())).toBlocking().first();
            if ( userRatingsDto != null ) {
                albumInfo.setPersonalRating(userRatingsDto.getRating());
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
            AverageRatingsDto averageRatingsDto = cassandraRatingsService
                .getAverageRating(UUID.fromString(artistInfo.getArtistId()), "Artist").toBlocking().first();

            if ( averageRatingsDto != null ) {
                long average = averageRatingsDto.getNumRating() / averageRatingsDto.getSumRating();
                artistInfo.setAverageRating((int) average);
            }

            UserRatingsDto userRatingsDto = cassandraRatingsService
                .getUserRating(userUuid, "Artist", UUID.fromString(artistInfo.getArtistId())).toBlocking().first();
            if ( userRatingsDto != null ) {
                artistInfo.setPersonalRating(userRatingsDto.getRating());
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
            AverageRatingsDto averageRatingsDto = cassandraRatingsService
                .getAverageRating(UUID.fromString(songInfo.getSongId()), "Song").toBlocking().first();

            if ( averageRatingsDto != null ) {
                long average = averageRatingsDto.getNumRating() / averageRatingsDto.getSumRating();
                songInfo.setAverageRating((int) average);
            }

            UserRatingsDto userRatingsDto = cassandraRatingsService
                .getUserRating(userUuid, "Song", UUID.fromString(songInfo.getSongId())).toBlocking().first();
            if ( userRatingsDto != null ) {
                songInfo.setPersonalRating(userRatingsDto.getRating());
            }
        }
    }

}
