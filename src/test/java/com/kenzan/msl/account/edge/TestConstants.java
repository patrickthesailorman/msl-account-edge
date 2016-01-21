package com.kenzan.msl.account.edge;

import com.kenzan.msl.account.client.dao.AlbumsByUserDao;
import com.kenzan.msl.account.client.dao.ArtistsByUserDao;
import com.kenzan.msl.account.client.dao.SongsByUserDao;
import com.kenzan.msl.catalog.client.dao.AlbumArtistBySongDao;
import com.kenzan.msl.catalog.client.dao.SongsAlbumsByArtistDao;
import com.kenzan.msl.catalog.client.dao.SongsArtistByAlbumDao;
import com.kenzan.msl.common.bo.ArtistBo;
import com.kenzan.msl.ratings.client.dao.AverageRatingsDao;
import com.kenzan.msl.ratings.client.dao.UserRatingsDao;
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

    public List<ArtistsByUserDao> artistsByUserDaoArrayList = new ArrayList<>();
    public List<AlbumsByUserDao> albumsByUserDaoArrayList = new ArrayList<>();
    public List<SongsByUserDao> songsByUserDaoArrayList = new ArrayList<>();

    public ArtistsByUserDao ARTISTS_BY_USER_DAO;
    public AlbumsByUserDao ALBUMS_BY_USER_DAO;
    public SongsByUserDao SONGS_BY_USER_DAO;

    public AverageRatingsDao AVERAGE_RATINGS_DAO;
    public UserRatingsDao USER_RATINGS_DAO;

    public SongsAlbumsByArtistDao songsAlbumsByArtistDao;
    public SongsArtistByAlbumDao songsArtistByAlbumDao;
    public AlbumArtistBySongDao albumArtistBySongDao;

    public ArtistBo ARTIST_BO;

    private TestConstants() {

        ARTIST_BO = new ArtistBo();
        ARTIST_BO.setArtistId(ARTIST_UUID_2);

        ARTISTS_BY_USER_DAO = new ArtistsByUserDao();
        ARTISTS_BY_USER_DAO.setArtistName(ARTIST_NAME);
        ARTISTS_BY_USER_DAO.setArtistId(ARTIST_UUID);
        ARTISTS_BY_USER_DAO.setUserId(USER_ID);
        artistsByUserDaoArrayList.add(ARTISTS_BY_USER_DAO);

        ALBUMS_BY_USER_DAO = new AlbumsByUserDao();
        ALBUMS_BY_USER_DAO.setAlbumId(ALBUM_UUID);
        ALBUMS_BY_USER_DAO.setAlbumName(ALBUM_NAME);
        ALBUMS_BY_USER_DAO.setArtistId(ARTIST_UUID);
        ALBUMS_BY_USER_DAO.setArtistName(ARTIST_NAME);
        albumsByUserDaoArrayList.add(ALBUMS_BY_USER_DAO);

        SONGS_BY_USER_DAO = new SongsByUserDao();
        SONGS_BY_USER_DAO.setAlbumId(ALBUM_UUID);
        SONGS_BY_USER_DAO.setAlbumName(ALBUM_NAME);
        SONGS_BY_USER_DAO.setArtistId(ARTIST_UUID);
        SONGS_BY_USER_DAO.setArtistName(ARTIST_NAME);
        SONGS_BY_USER_DAO.setSongName(SONG_NAME);
        SONGS_BY_USER_DAO.setSongId(SONG_UUID);
        songsByUserDaoArrayList.add(SONGS_BY_USER_DAO);

        AVERAGE_RATINGS_DAO = new AverageRatingsDao();
        AVERAGE_RATINGS_DAO.setNumRating(new Long(20));
        AVERAGE_RATINGS_DAO.setSumRating(new Long(10));
        AVERAGE_RATINGS_DAO.setContentId(USER_ID);

        USER_RATINGS_DAO = new UserRatingsDao();
        USER_RATINGS_DAO.setRating(10);
        USER_RATINGS_DAO.setUserId(USER_ID);

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
        songsAlbumsByArtistDao = new SongsAlbumsByArtistDao();
        songsAlbumsByArtistDao.setSongName(SONG_NAME);
        songsAlbumsByArtistDao.setSongId(SONG_UUID);
        songsAlbumsByArtistDao.setSongDuration(SONG_DURATION);
        songsAlbumsByArtistDao.setArtistMbid(ARTIST_MBID.toString());
        songsAlbumsByArtistDao.setArtistName(ARTIST_NAME);
        songsAlbumsByArtistDao.setArtistId(ARTIST_UUID);
        songsAlbumsByArtistDao.setArtistGenres(genres);
        songsAlbumsByArtistDao.setSimilarArtists(null);

        songsArtistByAlbumDao = new SongsArtistByAlbumDao();
        songsArtistByAlbumDao.setAlbumId(ALBUM_UUID);
        songsArtistByAlbumDao.setAlbumName(ALBUM_NAME);
        songsArtistByAlbumDao.setArtistId(ARTIST_UUID);
        songsArtistByAlbumDao.setArtistName(ARTIST_NAME);
        songsArtistByAlbumDao.setImageLink(IMAGE_LINK);
        songsArtistByAlbumDao.setArtistGenres(genres);

        albumArtistBySongDao = new AlbumArtistBySongDao();
        albumArtistBySongDao.setSongId(SONG_UUID);
        albumArtistBySongDao.setSongName(SONG_NAME);
        albumArtistBySongDao.setAlbumId(ALBUM_UUID);
        albumArtistBySongDao.setAlbumName(ALBUM_NAME);
        albumArtistBySongDao.setArtistId(ARTIST_UUID);
        albumArtistBySongDao.setArtistName(ARTIST_NAME);
        albumArtistBySongDao.setSongDuration(SONG_DURATION);
        albumArtistBySongDao.setAlbumYear(ALBUM_YEAR);
        albumArtistBySongDao.setArtistGenres(genres);
    }

    public static TestConstants getInstance() {
        if ( instance == null ) {
            instance = new TestConstants();
        }
        return instance;
    }

}
