package com.kenzan.msl.account.edge.services;

import io.swagger.model.AlbumInfo;
import io.swagger.model.ArtistInfo;
import io.swagger.model.SongInfo;

import java.util.List;
import java.util.UUID;

public interface RatingsService {

  void processAlbumRatings(List<AlbumInfo> albumList, UUID userUuid);

  void processArtistRatings(List<ArtistInfo> artistList, UUID userUuid);

  void processSongRatings(List<SongInfo> songList, UUID userUuid);
}
