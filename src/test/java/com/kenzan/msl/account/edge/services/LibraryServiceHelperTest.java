/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.account.edge.services;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.mapping.Result;
import com.google.common.base.Optional;
import com.kenzan.msl.account.edge.TestConstants;
import com.kenzan.msl.catalog.client.dto.AlbumArtistBySongDto;
import com.kenzan.msl.catalog.client.dto.SongsAlbumsByArtistDto;
import com.kenzan.msl.catalog.client.dto.SongsArtistByAlbumDto;
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
        Result<SongsAlbumsByArtistDto> songsAlbumsByArtistDtoResult = PowerMockito.mock(Result.class);
        PowerMockito.when(songsAlbumsByArtistDtoResult.one()).thenReturn(tc.songsAlbumsByArtistDto);

        expect(cassandraCatalogService.getSongsAlbumsByArtist(tc.ARTIST_UUID, Optional.absent()))
            .andReturn(observableResultSet);

        expect(cassandraCatalogService.mapSongsAlbumsByArtist(observableResultSet))
            .andReturn(Observable.just(songsAlbumsByArtistDtoResult));

        replay(cassandraCatalogService);
        PowerMock.replayAll();

        /* *************************** */

        Optional<ArtistBo> result = lsh.getArtist(tc.ARTIST_UUID);
        assertNotNull(result.get());
        assertEquals(result.get().getArtistId(), tc.songsAlbumsByArtistDto.getArtistId());
        assertEquals(result.get().getArtistName(), tc.songsAlbumsByArtistDto.getArtistName());
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
        Result<SongsArtistByAlbumDto> songsArtistByAlbumDtoResult = PowerMockito.mock(Result.class);
        PowerMockito.when(songsArtistByAlbumDtoResult.one()).thenReturn(tc.songsArtistByAlbumDto);

        expect(cassandraCatalogService.getSongsArtistByAlbum(tc.ALBUM_UUID, Optional.absent()))
            .andReturn(observableResultSet);

        expect(cassandraCatalogService.mapSongsArtistByAlbum(observableResultSet))
            .andReturn(Observable.just(songsArtistByAlbumDtoResult));

        replay(cassandraCatalogService);
        PowerMock.replayAll();

        /* *************************** */

        Optional<AlbumBo> result = lsh.getAlbum(tc.ALBUM_UUID);
        assertNotNull(result.get());
        assertEquals(result.get().getAlbumId(), tc.songsArtistByAlbumDto.getAlbumId());
        assertEquals(result.get().getAlbumName(), tc.songsArtistByAlbumDto.getAlbumName());
        assertEquals(result.get().getArtistId(), tc.songsArtistByAlbumDto.getArtistId());
        assertEquals(result.get().getArtistName(), tc.songsArtistByAlbumDto.getArtistName());
        assertEquals(result.get().getImageLink(), tc.songsArtistByAlbumDto.getImageLink());
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
        Result<AlbumArtistBySongDto> albumArtistBySongDtoResult = PowerMockito.mock(Result.class);
        PowerMockito.when(albumArtistBySongDtoResult.one()).thenReturn(tc.albumArtistBySongDto);

        expect(cassandraCatalogService.getAlbumArtistBySong(tc.SONG_UUID, Optional.absent()))
            .andReturn(observableResultSet);

        expect(cassandraCatalogService.mapAlbumArtistBySong(observableResultSet))
            .andReturn(Observable.just(albumArtistBySongDtoResult));

        replay(cassandraCatalogService);
        PowerMock.replayAll();

        /* *************************** */

        Optional<SongBo> result = lsh.getSong(tc.SONG_UUID);
        assertNotNull(result.get());

        assertEquals(result.get().getSongId(), tc.albumArtistBySongDto.getSongId());
        assertEquals(result.get().getSongName(), tc.albumArtistBySongDto.getSongName());
        assertEquals(result.get().getAlbumId(), tc.albumArtistBySongDto.getAlbumId());
        assertEquals(result.get().getAlbumName(), tc.albumArtistBySongDto.getAlbumName());
        assertEquals(result.get().getArtistId(), tc.albumArtistBySongDto.getArtistId());
        assertEquals(result.get().getArtistName(), tc.albumArtistBySongDto.getArtistName());
        assertEquals(result.get().getDuration(), tc.albumArtistBySongDto.getSongDuration());
        assertEquals(result.get().getGenre(), tc.GENRE);
    }
}
