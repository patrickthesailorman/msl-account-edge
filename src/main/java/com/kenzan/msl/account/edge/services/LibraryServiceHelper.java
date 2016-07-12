package com.kenzan.msl.account.edge.services;

import com.google.common.base.Optional;
import com.kenzan.msl.common.bo.AlbumBo;
import com.kenzan.msl.common.bo.ArtistBo;
import com.kenzan.msl.common.bo.SongBo;

import java.util.UUID;

public interface LibraryServiceHelper {

  Optional<ArtistBo> getArtist(final UUID artistId);

  Optional<AlbumBo> getAlbum(final UUID albumId);

  Optional<SongBo> getSong(final UUID songId);
}
