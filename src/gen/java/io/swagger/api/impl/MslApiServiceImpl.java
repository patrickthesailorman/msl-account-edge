package io.swagger.api.impl;

import com.kenzan.msl.server.services.CassandraAccountService;
import com.kenzan.msl.server.services.AccountService;
import io.swagger.api.ApiResponseMessage;
import io.swagger.api.MslApiService;
import io.swagger.api.NotFoundException;
import io.swagger.model.MyLibrary;
import io.swagger.model.ErrorResponse;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.core.Response;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JaxRSServerCodegen", date = "2015-12-26T11:26:56.588-06:00")
public class MslApiServiceImpl extends MslApiService {

    private AccountService accountService = new CassandraAccountService();

    @Override
    public Response getMyLibrary()
            throws NotFoundException {
        if (MslSessionToken.getInstance().isValidToken()) {

            MyLibrary myLibrary;
            try {
                myLibrary = accountService.getMyLibrary(MslSessionToken.getInstance().getTokenValue()).toBlocking().first();
                return Response.ok().entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "success", myLibrary)).build();
            } catch (Exception e) {
                e.printStackTrace();

                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setMessage("Server error: " + e.getMessage());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "no valid sessionToken provided")).build();
    }

    @Override
    public Response addAlbum(String albumId)
            throws NotFoundException {
        // Validate required parameters
        if (StringUtils.isEmpty(albumId)) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "Required parameter 'albumId' is null or empty.")).build();
        }

        if (MslSessionToken.getInstance().isValidToken()) {
            try {
                accountService.addToLibrary(albumId, MslSessionToken.getInstance().getTokenValue(), "Album");
                return Response.ok().entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "success")).build();
            } catch (Exception e) {
                e.printStackTrace();

                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setMessage("Server error: " + e.getMessage());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "no sessionToken provided")).build();
    }

    @Override
    public Response addArtist(String artistId)
            throws NotFoundException {
        // Validate required parameters
        if (StringUtils.isEmpty(artistId)) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "Required parameter 'artistId' is null or empty.")).build();
        }

        if (MslSessionToken.getInstance().isValidToken()) {
            try {
                accountService.addToLibrary(artistId, MslSessionToken.getInstance().getTokenValue(), "Artist");
                return Response.ok().entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "success")).build();
            } catch (Exception e) {
                e.printStackTrace();

                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setMessage("Server error: " + e.getMessage());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "no sessionToken provided")).build();
    }

    @Override
    public Response addSong(String songId)
            throws NotFoundException {
        // Validate required parameters
        if (StringUtils.isEmpty(songId)) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "Required parameter 'songId' is null or empty.")).build();
        }
        if (MslSessionToken.getInstance().isValidToken()) {
            try {
                accountService.addToLibrary(songId, MslSessionToken.getInstance().getTokenValue(), "Song");
                return Response.ok().entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "success")).build();
            } catch (Exception e) {
                e.printStackTrace();

                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setMessage("Server error: " + e.getMessage());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "no sessionToken provided")).build();
    }

    @Override
    public Response removeSong(String songId, String timestamp)
            throws NotFoundException {
        // Validate required parameters
        if (StringUtils.isEmpty(songId) || StringUtils.isEmpty(timestamp)) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "Required parameter 'songId' or 'timestamp' is null or empty.")).build();
        }
        if (MslSessionToken.getInstance().isValidToken()) {
            try {
                accountService.removeFromLibrary(songId, timestamp, MslSessionToken.getInstance().getTokenValue(), "Song");
                return Response.ok().entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "success")).build();
            } catch (RuntimeException e) {
                e.printStackTrace();
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setMessage("Server error: " + e.getCause().getMessage());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "no sessionToken provided")).build();
    }

    @Override
    public Response removeArtist(String artistId, String timestamp)
            throws NotFoundException {
        // Validate required parameters
        if (StringUtils.isEmpty(artistId) || StringUtils.isEmpty(timestamp)) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "Required parameter 'artistId' is null or empty.")).build();
        }
        if (MslSessionToken.getInstance().isValidToken()) {
            try {
                accountService.removeFromLibrary(artistId, timestamp, MslSessionToken.getInstance().getTokenValue(), "Artist");
                return Response.ok().entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "success")).build();
            } catch (RuntimeException e) {
                e.printStackTrace();
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setMessage("Server error: " + e.getCause().getMessage());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "no sessionToken provided")).build();
    }

    @Override
    public Response removeAlbum(String albumId, String timestamp)
            throws NotFoundException {
        // Validate required parameters
        if (StringUtils.isEmpty(albumId) || StringUtils.isEmpty(timestamp)) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "Required parameter 'albumId' is null or empty.")).build();
        }
        if (MslSessionToken.getInstance().isValidToken()) {
            try {
                accountService.removeFromLibrary(albumId, timestamp, MslSessionToken.getInstance().getTokenValue(), "Album");
                return Response.ok().entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "success")).build();
            } catch (RuntimeException e) {
                e.printStackTrace();
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setMessage("Server error: " + e.getCause().getMessage());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "no sessionToken provided")).build();
    }

    @Override
    public Response getUserInfo()
            throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }

    @Override
    public Response getRecentSongs()
            throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }

    @Override
    public Response registration(String email, String password, String passwordConfirmation)
            throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }

}
