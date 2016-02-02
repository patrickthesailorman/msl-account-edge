/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.account.edge.services;

import com.kenzan.msl.account.edge.TestConstants;
import com.kenzan.msl.ratings.client.services.CassandraRatingsService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;

import org.powermock.modules.junit4.PowerMockRunner;
import rx.Observable;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(PowerMockRunner.class)
@PrepareForTest(CassandraRatingsService.class)
public class RatingsHelperTest {

    private TestConstants tc = TestConstants.getInstance();
    private RatingsHelper rh = RatingsHelper.getInstance();
    private CassandraRatingsService cassandraRatingsService;

    private Integer average = (int) (tc.AVERAGE_RATINGS_DTO.getNumRating() / tc.AVERAGE_RATINGS_DTO.getSumRating());

    @Before
    public void init()
        throws Exception {
        PowerMock.mockStatic(CassandraRatingsService.class);
        cassandraRatingsService = createMock(CassandraRatingsService.class);
        PowerMock.expectNew(CassandraRatingsService.class).andReturn(cassandraRatingsService);
        expect(CassandraRatingsService.getInstance()).andReturn(cassandraRatingsService).anyTimes();
    }

    @Test
    public void testProcessAlbumRatings() {
        expect(cassandraRatingsService.getAverageRating(tc.ALBUM_UUID, "Album"))
            .andReturn(Observable.just(tc.AVERAGE_RATINGS_DTO));

        expect(cassandraRatingsService.getUserRating(tc.USER_ID, "Album", tc.ALBUM_UUID))
            .andReturn(Observable.just(tc.USER_RATINGS_DTO));

        replay(cassandraRatingsService);
        PowerMock.replayAll();

        /* **************************** */

        rh.processAlbumRatings(tc.albumList, tc.USER_ID);
        Integer average = (int) (tc.AVERAGE_RATINGS_DTO.getNumRating() / tc.AVERAGE_RATINGS_DTO.getSumRating());
        assertNotNull(tc.albumList.get(0).getAverageRating());
        assertEquals(tc.albumList.get(0).getAverageRating(), average);
        assertNotNull(tc.albumList.get(0).getPersonalRating());
        assertEquals(tc.albumList.get(0).getPersonalRating(), tc.USER_RATINGS_DTO.getRating());
    }

    @Test
    public void testProcessArtistRatings()
        throws Exception {
        expect(cassandraRatingsService.getAverageRating(tc.ARTIST_UUID, "Artist"))
            .andReturn(Observable.just(tc.AVERAGE_RATINGS_DTO));

        expect(cassandraRatingsService.getUserRating(tc.USER_ID, "Artist", tc.ARTIST_UUID))
            .andReturn(Observable.just(tc.USER_RATINGS_DTO));

        replay(cassandraRatingsService);
        PowerMock.replayAll();

        /* **************************** */

        rh.processArtistRatings(tc.artistList, tc.USER_ID);

        assertNotNull(tc.artistList.get(0).getAverageRating());
        assertEquals(tc.artistList.get(0).getAverageRating(), average);
        assertNotNull(tc.artistList.get(0).getPersonalRating());
        assertEquals(tc.artistList.get(0).getPersonalRating(), tc.USER_RATINGS_DTO.getRating());
    }

    @Test
    public void testProcessSongRatings()
        throws Exception {
        expect(cassandraRatingsService.getAverageRating(tc.SONG_UUID, "Song"))
            .andReturn(Observable.just(tc.AVERAGE_RATINGS_DTO));

        expect(cassandraRatingsService.getUserRating(tc.USER_ID, "Song", tc.SONG_UUID))
            .andReturn(Observable.just(tc.USER_RATINGS_DTO));

        replay(cassandraRatingsService);
        PowerMock.replayAll();

        /* **************************** */

        rh.processSongRatings(tc.songList, tc.USER_ID);

        assertNotNull(tc.songList.get(0).getAverageRating());
        assertEquals(tc.songList.get(0).getAverageRating(), average);
        assertNotNull(tc.songList.get(0).getPersonalRating());
        assertEquals(tc.songList.get(0).getPersonalRating(), tc.USER_RATINGS_DTO.getRating());

    }
}
