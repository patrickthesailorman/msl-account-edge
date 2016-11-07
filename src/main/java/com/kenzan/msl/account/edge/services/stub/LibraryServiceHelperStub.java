package com.kenzan.msl.account.edge.services.stub;

import com.google.common.base.Optional;
import com.kenzan.msl.account.edge.services.LibraryServiceHelper;
import com.kenzan.msl.common.bo.AlbumBo;
import com.kenzan.msl.common.bo.ArtistBo;
import com.kenzan.msl.common.bo.SongBo;

import java.util.UUID;

/**
 * @author Kenzan
 */
public class LibraryServiceHelperStub implements LibraryServiceHelper {
  public Optional<ArtistBo> getArtist(final UUID artistId) {
    return Optional.absent();
  }

  public Optional<AlbumBo> getAlbum(final UUID albumId) {
    return Optional.absent();
  }

  public Optional<SongBo> getSong(final UUID songId) {
    return Optional.absent();
  }
}
