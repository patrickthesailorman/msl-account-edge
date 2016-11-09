package com.kenzan.msl.account.edge.services.stub;

import com.kenzan.msl.account.edge.services.RatingsService;
import io.swagger.model.AlbumInfo;
import io.swagger.model.ArtistInfo;
import io.swagger.model.SongInfo;

import java.util.List;
import java.util.UUID;

/**
 * @author Kenzan
 */
public class RatingsServiceStub implements RatingsService {

  public void processAlbumRatings(List<AlbumInfo> albumList, UUID userUuid) {}

  public void processArtistRatings(List<ArtistInfo> artistList, UUID userUuid) {}

  public void processSongRatings(List<SongInfo> songList, UUID userUuid) {}
}
