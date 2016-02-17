/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package io.swagger.client;

import io.swagger.api.impl.AccountEdgeApiResponseMessage;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

public class LibraryClient {

    private ResteasyClient client;

    public LibraryClient() {
        client = new ResteasyClientBuilder().build();
    }

    /**
     * Retrieve the current user's library
     *
     * @param sessionToken String
     * @return AccountEdgeApiResponseMessage
     */
    public AccountEdgeApiResponseMessage getLibrary(String sessionToken) {

        ResteasyWebTarget target = client.target(String.format("%s/account-edge/users/mylibrary",
                                                               ClientConstants.getInstance().BASE_URL));
        Response response = target.request().cookie(new NewCookie("sessionToken", sessionToken)).get();

        if ( response.getStatus() != 200 ) {
            throw new RuntimeException(String.format("Failed : HTTP error code : %s", response.getStatus()));
        }

        return response.readEntity(AccountEdgeApiResponseMessage.class);
    }

    /**
     * Attach a song to current user's library
     *
     * @param songId String
     * @param sessionToken String
     * @return AccountEdgeApiResponseMessage
     */
    public AccountEdgeApiResponseMessage addSongToLibrary(String songId, String sessionToken) {
        ResteasyWebTarget target = client.target(String.format("%s/account-edge/users/mylibrary/addsong/%s",
                                                               ClientConstants.getInstance().BASE_URL, songId));

        Response response = target.request().cookie(new NewCookie("sessionToken", sessionToken))
            .put(Entity.entity(songId, MediaType.APPLICATION_JSON));

        if ( response.getStatus() != 200 ) {
            throw new RuntimeException(String.format("Failed : HTTP error code : %s", response.getStatus()));
        }
        return response.readEntity(AccountEdgeApiResponseMessage.class);
    }

    /**
     * Remove song from current user's library
     *
     * @param songId String
     * @param timestamp String
     * @param sessionToken String
     * @return AccountEdgeApiResponseMessage
     */
    public AccountEdgeApiResponseMessage removeSongFromLibrary(String songId, String timestamp, String sessionToken) {
        ResteasyWebTarget target = client.target(String.format("%s/account-edge/users/mylibrary/removesong/%s/%s",
                                                               ClientConstants.getInstance().BASE_URL, songId,
                                                               timestamp));

        Response response = target.request().cookie(new NewCookie("sessionToken", sessionToken)).delete();

        if ( response.getStatus() != 200 ) {
            throw new RuntimeException(String.format("Failed : HTTP error code : %s", response.getStatus()));
        }
        return response.readEntity(AccountEdgeApiResponseMessage.class);
    }

    /**
     * Attach an album to current user's library
     *
     * @param albumId String
     * @param sessionToken String
     * @return AccountEdgeApiResponseMessage
     */
    public AccountEdgeApiResponseMessage addAlbumToLibrary(String albumId, String sessionToken) {
        ResteasyWebTarget target = client.target(String.format("%s/account-edge/users/mylibrary/addalbum/%s",
                                                               ClientConstants.getInstance().BASE_URL, albumId));

        Response response = target.request().cookie(new NewCookie("sessionToken", sessionToken))
            .put(Entity.entity(albumId, MediaType.APPLICATION_JSON));

        if ( response.getStatus() != 200 ) {
            throw new RuntimeException(String.format("Failed : HTTP error code : %s", response.getStatus()));
        }
        return response.readEntity(AccountEdgeApiResponseMessage.class);
    }

    /**
     * Remove album from current user's library
     *
     * @param albumId String
     * @param timestamp String
     * @param sessionToken String
     * @return AccountEdgeApiResponseMessage
     */
    public AccountEdgeApiResponseMessage removeAlbumFromLibrary(String albumId, String timestamp, String sessionToken) {
        ResteasyWebTarget target = client.target(String.format("%s/account-edge/users/mylibrary/removealbum/%s/%s",
                                                               ClientConstants.getInstance().BASE_URL, albumId,
                                                               timestamp));

        Response response = target.request().cookie(new NewCookie("sessionToken", sessionToken)).delete();

        if ( response.getStatus() != 200 ) {
            throw new RuntimeException(String.format("Failed : HTTP error code : %s", response.getStatus()));
        }
        return response.readEntity(AccountEdgeApiResponseMessage.class);
    }

    /**
     * Attach an artist to current user's library
     *
     * @param artistId String
     * @param sessionToken String
     * @return AccountEdgeApiResponseMessage
     */
    public AccountEdgeApiResponseMessage addArtistToLibrary(String artistId, String sessionToken) {
        ResteasyWebTarget target = client.target(String.format("%s/account-edge/users/mylibrary/addartist/%s",
                                                               ClientConstants.getInstance().BASE_URL, artistId));

        Response response = target.request().cookie(new NewCookie("sessionToken", sessionToken))
            .put(Entity.entity(artistId, MediaType.APPLICATION_JSON));

        if ( response.getStatus() != 200 ) {
            throw new RuntimeException(String.format("Failed : HTTP error code : %s", response.getStatus()));
        }
        return response.readEntity(AccountEdgeApiResponseMessage.class);
    }

    /**
     * Remove artist from current user's library
     *
     * @param artistId String
     * @param timestamp String
     * @param sessionToken String
     * @return AccountEdgeApiResponseMessage
     */
    public AccountEdgeApiResponseMessage removeArtistFromLibrary(String artistId, String timestamp, String sessionToken) {
        ResteasyWebTarget target = client.target(String.format("%s/account-edge/users/mylibrary/removeartist/%s/%s",
                                                               ClientConstants.getInstance().BASE_URL, artistId,
                                                               timestamp));

        Response response = target.request().cookie(new NewCookie("sessionToken", sessionToken)).delete();

        if ( response.getStatus() != 200 ) {
            throw new RuntimeException(String.format("Failed : HTTP error code : %s", response.getStatus()));
        }
        return response.readEntity(AccountEdgeApiResponseMessage.class);
    }
}
