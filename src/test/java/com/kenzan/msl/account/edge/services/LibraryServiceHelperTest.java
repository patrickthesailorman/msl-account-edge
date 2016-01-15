/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.account.edge.services;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.mapping.Result;
import com.google.common.base.Optional;
import com.kenzan.msl.account.edge.TestConstants;
import com.kenzan.msl.catalog.client.dao.AlbumArtistBySongDao;
import com.kenzan.msl.catalog.client.dao.SongsAlbumsByArtistDao;
import com.kenzan.msl.catalog.client.dao.SongsArtistByAlbumDao;
import com.kenzan.msl.catalog.client.services.CassandraCatalogService;
import com.kenzan.msl.common.bo.AlbumBo;
import com.kenzan.msl.common.bo.ArtistBo;
import com.kenzan.msl.common.bo.SongBo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import rx.Observable;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(PowerMockRunner.class)
@PrepareForTest(CassandraCatalogService.class)
public class LibraryServiceHelperTest {

    private TestConstants tc = TestConstants.getInstance();
    private LibraryServiceHelper lsh = new LibraryServiceHelper();
    private CassandraCatalogService cassandraCatalogService;

    private Observable<ResultSet> observableResultSet;

    @Before
    public void init()
        throws Exception {
        ResultSet resultSet = createMock(ResultSet.class);
        observableResultSet = Observable.just(resultSet);

        PowerMock.mockStatic(CassandraCatalogService.class);
        cassandraCatalogService = createMock(CassandraCatalogService.class);
        PowerMock.expectNew(CassandraCatalogService.class).andReturn(cassandraCatalogService);

        expect(CassandraCatalogService.getInstance()).andReturn(cassandraCatalogService).anyTimes();
    }

    @Test
    public void testGetArtistWhenNull() {
        expect(cassandraCatalogService.getSongsAlbumsByArtist(tc.ARTIST_UUID, Optional.absent()))
            .andReturn(observableResultSet);

        expect(cassandraCatalogService.mapSongsAlbumsByArtist(observableResultSet)).andReturn(Observable.just(null));

        replay(cassandraCatalogService);
        PowerMock.replayAll();

        /* *************************** */

        Optional<ArtistBo> result = lsh.getArtist(tc.ARTIST_UUID);
        assertEquals(result, Optional.absent());
    }

    @Test
    public void testGetArtist() {
        Result<SongsAlbumsByArtistDao> songsAlbumsByArtistDaoResult = PowerMockito.mock(Result.class);
        PowerMockito.when(songsAlbumsByArtistDaoResult.one()).thenReturn(tc.songsAlbumsByArtistDao);

        expect(cassandraCatalogService.getSongsAlbumsByArtist(tc.ARTIST_UUID, Optional.absent()))
            .andReturn(observableResultSet);

        expect(cassandraCatalogService.mapSongsAlbumsByArtist(observableResultSet))
            .andReturn(Observable.just(songsAlbumsByArtistDaoResult));

        replay(cassandraCatalogService);
        PowerMock.replayAll();

        /* *************************** */

        Optional<ArtistBo> result = lsh.getArtist(tc.ARTIST_UUID);
        assertNotNull(result.get());
        assertEquals(result.get().getArtistId(), tc.songsAlbumsByArtistDao.getArtistId());
        assertEquals(result.get().getArtistName(), tc.songsAlbumsByArtistDao.getArtistName());
        assertEquals(result.get().getGenre(), tc.GENRE);
    }

    @Test
    public void testGetAlbumWhenNull() {
        expect(cassandraCatalogService.getSongsArtistByAlbum(tc.ALBUM_UUID, Optional.absent()))
            .andReturn(observableResultSet);

        expect(cassandraCatalogService.mapSongsArtistByAlbum(observableResultSet)).andReturn(Observable.just(null));

        replay(cassandraCatalogService);
        PowerMock.replayAll();

        /* *************************** */

        Optional<AlbumBo> result = lsh.getAlbum(tc.ALBUM_UUID);
        assertEquals(result, Optional.absent());
    }

    @Test
    public void testGetAlbum() {
        Result<SongsArtistByAlbumDao> songsArtistByAlbumDaoResult = PowerMockito.mock(Result.class);
        PowerMockito.when(songsArtistByAlbumDaoResult.one()).thenReturn(tc.songsArtistByAlbumDao);

        expect(cassandraCatalogService.getSongsArtistByAlbum(tc.ALBUM_UUID, Optional.absent()))
            .andReturn(observableResultSet);

        expect(cassandraCatalogService.mapSongsArtistByAlbum(observableResultSet))
            .andReturn(Observable.just(songsArtistByAlbumDaoResult));

        replay(cassandraCatalogService);
        PowerMock.replayAll();

        /* *************************** */

        Optional<AlbumBo> result = lsh.getAlbum(tc.ALBUM_UUID);
        assertNotNull(result.get());
        assertEquals(result.get().getAlbumId(), tc.songsArtistByAlbumDao.getAlbumId());
        assertEquals(result.get().getAlbumName(), tc.songsArtistByAlbumDao.getAlbumName());
        assertEquals(result.get().getArtistId(), tc.songsArtistByAlbumDao.getArtistId());
        assertEquals(result.get().getArtistName(), tc.songsArtistByAlbumDao.getArtistName());
        assertEquals(result.get().getImageLink(), tc.songsArtistByAlbumDao.getImageLink());
        assertEquals(result.get().getGenre(), tc.GENRE);
    }

    @Test
    public void testGetSongWhenNull() {
        expect(cassandraCatalogService.getAlbumArtistBySong(tc.SONG_UUID, Optional.absent()))
            .andReturn(observableResultSet);

        expect(cassandraCatalogService.mapAlbumArtistBySong(observableResultSet)).andReturn(Observable.just(null));

        replay(cassandraCatalogService);
        PowerMock.replayAll();

        /* *************************** */

        Optional<SongBo> result = lsh.getSong(tc.SONG_UUID);
        assertEquals(result, Optional.absent());
    }

    @Test
    public void testGetSong() {
        Result<AlbumArtistBySongDao> albumArtistBySongDaoResult = PowerMockito.mock(Result.class);
        PowerMockito.when(albumArtistBySongDaoResult.one()).thenReturn(tc.albumArtistBySongDao);

        expect(cassandraCatalogService.getAlbumArtistBySong(tc.SONG_UUID, Optional.absent()))
            .andReturn(observableResultSet);

        expect(cassandraCatalogService.mapAlbumArtistBySong(observableResultSet))
            .andReturn(Observable.just(albumArtistBySongDaoResult));

        replay(cassandraCatalogService);
        PowerMock.replayAll();

        /* *************************** */

        Optional<SongBo> result = lsh.getSong(tc.SONG_UUID);
        assertNotNull(result.get());

        assertEquals(result.get().getSongId(), tc.albumArtistBySongDao.getSongId());
        assertEquals(result.get().getSongName(), tc.albumArtistBySongDao.getSongName());
        assertEquals(result.get().getAlbumId(), tc.albumArtistBySongDao.getAlbumId());
        assertEquals(result.get().getAlbumName(), tc.albumArtistBySongDao.getAlbumName());
        assertEquals(result.get().getArtistId(), tc.albumArtistBySongDao.getArtistId());
        assertEquals(result.get().getArtistName(), tc.albumArtistBySongDao.getArtistName());
        assertEquals(result.get().getDuration(), tc.albumArtistBySongDao.getSongDuration());
        assertEquals(result.get().getGenre(), tc.GENRE);
    }
}
