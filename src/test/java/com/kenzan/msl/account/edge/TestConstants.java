package com.kenzan.msl.account.edge;

import com.kenzan.msl.account.client.dto.AlbumsByUserDto;
import com.kenzan.msl.account.client.dto.ArtistsByUserDto;
import com.kenzan.msl.account.client.dto.SongsByUserDto;
import com.kenzan.msl.catalog.client.dto.AlbumArtistBySongDto;
import com.kenzan.msl.catalog.client.dto.SongsAlbumsByArtistDto;
import com.kenzan.msl.catalog.client.dto.SongsArtistByAlbumDto;
import com.kenzan.msl.common.bo.ArtistBo;
import com.kenzan.msl.ratings.client.dto.AverageRatingsDto;
import com.kenzan.msl.ratings.client.dto.UserRatingsDto;
import io.swagger.model.AlbumInfo;
import io.swagger.model.ArtistInfo;
import io.swagger.model.SongInfo;

import java.util.*;

public class TestConstants {

    private static TestConstants instance = null;

    public final UUID ALBUM_UUID = UUID.fromString("00000000-0000-0000-0000-000000001");
    public final UUID ALBUM_UUID_2 = UUID.fromString("00000000-0000-0000-0002-000000011");
    public final String ALBUM_NAME = "AlbumName1";
    public final Integer ALBUM_YEAR = 1971;

    public final UUID ARTIST_UUID = UUID.fromString("00000000-0000-0000-0001-000000001");
    public final UUID ARTIST_UUID_2 = UUID.fromString("00000000-0000-0000-0002-000000022");
    public final String ARTIST_NAME = "ArtistName1";
    public final UUID ARTIST_MBID = UUID.fromString("00000000-0000-0000-0004-00000001");

    public final UUID SONG_UUID = UUID.fromString("00000000-0000-0000-0002-000000001");
    public final UUID SONG_UUID_2 = UUID.fromString("00000000-0000-0000-0002-000000032");
    public final String SONG_NAME = "SongName1";
    public final Integer SONG_DURATION = 101;
    public final Integer SONG_YEAR = 1961;

    public final UUID USER_ID = UUID.fromString("00000000-0000-0000-0001-000000001");
    public final String IMAGE_LINK = "some link";
    public final Date FAVORITES_TIMESTAMP = new Date();
    public final String GENRE = "someGenre";

    public List<AlbumInfo> albumList = new ArrayList<>();
    public List<ArtistInfo> artistList = new ArrayList<>();
    public List<SongInfo> songList = new ArrayList<>();

    public List<ArtistsByUserDto> artistsByUserDtoArrayList = new ArrayList<>();
    public List<AlbumsByUserDto> albumsByUserDtoArrayList = new ArrayList<>();
    public List<SongsByUserDto> songsByUserDtoArrayList = new ArrayList<>();

    public ArtistsByUserDto ARTISTS_BY_USER_DTO;
    public AlbumsByUserDto ALBUMS_BY_USER_DTO;
    public SongsByUserDto SONGS_BY_USER_DTO;

    public AverageRatingsDto AVERAGE_RATINGS_DTO;
    public UserRatingsDto USER_RATINGS_DTO;

    public SongsAlbumsByArtistDto songsAlbumsByArtistDto;
    public SongsArtistByAlbumDto songsArtistByAlbumDto;
    public AlbumArtistBySongDto albumArtistBySongDto;

    public ArtistBo ARTIST_BO;

    private TestConstants() {

        ARTIST_BO = new ArtistBo();
        ARTIST_BO.setArtistId(ARTIST_UUID_2);

        ARTISTS_BY_USER_DTO = new ArtistsByUserDto();
        ARTISTS_BY_USER_DTO.setArtistName(ARTIST_NAME);
        ARTISTS_BY_USER_DTO.setArtistId(ARTIST_UUID);
        ARTISTS_BY_USER_DTO.setUserId(USER_ID);
        artistsByUserDtoArrayList.add(ARTISTS_BY_USER_DTO);

        ALBUMS_BY_USER_DTO = new AlbumsByUserDto();
        ALBUMS_BY_USER_DTO.setAlbumId(ALBUM_UUID);
        ALBUMS_BY_USER_DTO.setAlbumName(ALBUM_NAME);
        ALBUMS_BY_USER_DTO.setArtistId(ARTIST_UUID);
        ALBUMS_BY_USER_DTO.setArtistName(ARTIST_NAME);
        albumsByUserDtoArrayList.add(ALBUMS_BY_USER_DTO);

        SONGS_BY_USER_DTO = new SongsByUserDto();
        SONGS_BY_USER_DTO.setAlbumId(ALBUM_UUID);
        SONGS_BY_USER_DTO.setAlbumName(ALBUM_NAME);
        SONGS_BY_USER_DTO.setArtistId(ARTIST_UUID);
        SONGS_BY_USER_DTO.setArtistName(ARTIST_NAME);
        SONGS_BY_USER_DTO.setSongName(SONG_NAME);
        SONGS_BY_USER_DTO.setSongId(SONG_UUID);
        songsByUserDtoArrayList.add(SONGS_BY_USER_DTO);

        AVERAGE_RATINGS_DTO = new AverageRatingsDto();
        AVERAGE_RATINGS_DTO.setNumRating(new Long(20));
        AVERAGE_RATINGS_DTO.setSumRating(new Long(10));
        AVERAGE_RATINGS_DTO.setContentId(USER_ID);

        USER_RATINGS_DTO = new UserRatingsDto();
        USER_RATINGS_DTO.setRating(10);
        USER_RATINGS_DTO.setUserId(USER_ID);

        AlbumInfo albumInfo = new AlbumInfo();
        albumInfo.setAlbumId(ALBUM_UUID.toString());
        albumInfo.setAlbumName(ALBUM_NAME);
        albumInfo.setArtistName(ARTIST_NAME);
        albumInfo.setArtistId(ARTIST_UUID.toString());
        albumInfo.setArtistMbid(ARTIST_MBID.toString());
        albumInfo.setAverageRating(10);
        albumInfo.setFavoritesTimestamp(FAVORITES_TIMESTAMP.toString());
        albumInfo.setGenre(GENRE);
        albumInfo.setYear(ALBUM_YEAR);
        albumList.add(albumInfo);

        ArtistInfo artistInfo = new ArtistInfo();
        artistInfo.setArtistName(ARTIST_NAME);
        artistInfo.setArtistMbid(ARTIST_MBID.toString());
        artistInfo.setArtistId(ARTIST_UUID.toString());
        artistList.add(artistInfo);

        SongInfo songInfo = new SongInfo();
        songInfo.setArtistId(ARTIST_UUID.toString());
        songInfo.setArtistName(ARTIST_NAME);
        songInfo.setSongName(SONG_NAME);
        songInfo.setSongId(SONG_UUID.toString());
        songList.add(songInfo);

        Set<String> genres = new HashSet<String>();
        genres.add(GENRE);
        songsAlbumsByArtistDto = new SongsAlbumsByArtistDto();
        songsAlbumsByArtistDto.setSongName(SONG_NAME);
        songsAlbumsByArtistDto.setSongId(SONG_UUID);
        songsAlbumsByArtistDto.setSongDuration(SONG_DURATION);
        songsAlbumsByArtistDto.setArtistMbid(ARTIST_MBID.toString());
        songsAlbumsByArtistDto.setArtistName(ARTIST_NAME);
        songsAlbumsByArtistDto.setArtistId(ARTIST_UUID);
        songsAlbumsByArtistDto.setArtistGenres(genres);
        songsAlbumsByArtistDto.setSimilarArtists(null);

        songsArtistByAlbumDto = new SongsArtistByAlbumDto();
        songsArtistByAlbumDto.setAlbumId(ALBUM_UUID);
        songsArtistByAlbumDto.setAlbumName(ALBUM_NAME);
        songsArtistByAlbumDto.setArtistId(ARTIST_UUID);
        songsArtistByAlbumDto.setArtistName(ARTIST_NAME);
        songsArtistByAlbumDto.setImageLink(IMAGE_LINK);
        songsArtistByAlbumDto.setArtistGenres(genres);

        albumArtistBySongDto = new AlbumArtistBySongDto();
        albumArtistBySongDto.setSongId(SONG_UUID);
        albumArtistBySongDto.setSongName(SONG_NAME);
        albumArtistBySongDto.setAlbumId(ALBUM_UUID);
        albumArtistBySongDto.setAlbumName(ALBUM_NAME);
        albumArtistBySongDto.setArtistId(ARTIST_UUID);
        albumArtistBySongDto.setArtistName(ARTIST_NAME);
        albumArtistBySongDto.setSongDuration(SONG_DURATION);
        albumArtistBySongDto.setAlbumYear(ALBUM_YEAR);
        albumArtistBySongDto.setArtistGenres(genres);
    }

    public static TestConstants getInstance() {
        if ( instance == null ) {
            instance = new TestConstants();
        }
        return instance;
    }

}
