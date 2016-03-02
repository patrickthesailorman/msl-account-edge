package io.swagger.api.impl;

import com.kenzan.msl.account.client.dto.UserDto;
import com.kenzan.msl.account.edge.services.AccountEdgeService;
import com.kenzan.msl.account.edge.services.LibraryService;
import io.swagger.api.*;

import io.swagger.model.MyLibrary;
import io.swagger.model.ErrorResponse;

import io.swagger.api.NotFoundException;

import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.UUID;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JaxRSServerCodegen", date = "2016-01-25T12:48:02.255-06:00")
public class AccountEdgeApiServiceImpl extends AccountEdgeApiService {

    private LibraryService libraryService = new LibraryService();
    private AccountEdgeService accountService = new AccountEdgeService(libraryService);

    @Override
    public Response getMyLibrary()
            throws NotFoundException {
        if (AccountEdgeSessionToken.getInstance().isValidToken()) {
            try {
                MyLibrary myLibrary = accountService.getMyLibrary(AccountEdgeSessionToken.getInstance().getTokenValue()).toBlocking().first();
                return Response.ok().entity(new AccountEdgeApiResponseMessage(AccountEdgeApiResponseMessage.OK, "success", myLibrary)).build();
            } catch (Exception e) {
                e.printStackTrace();

                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setMessage("Server error: " + e.getMessage());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity(new AccountEdgeApiResponseMessage(AccountEdgeApiResponseMessage.ERROR, "no valid sessionToken provided")).build();
    }

    @Override
    public Response addAlbum(String albumId)
            throws NotFoundException {
        // Validate required parameters
        if (StringUtils.isEmpty(albumId)) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new AccountEdgeApiResponseMessage(AccountEdgeApiResponseMessage.ERROR, "Required parameter 'albumId' is null or empty.")).build();
        }

        if (AccountEdgeSessionToken.getInstance().isValidToken()) {
            try {
                accountService.addToLibrary(albumId, AccountEdgeSessionToken.getInstance().getTokenValue(), "Album");
                return Response.ok().entity(new AccountEdgeApiResponseMessage(AccountEdgeApiResponseMessage.OK, "success")).build();
            } catch (Exception e) {
                e.printStackTrace();

                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setMessage("Server error: " + e.getMessage());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity(new AccountEdgeApiResponseMessage(AccountEdgeApiResponseMessage.ERROR, "no sessionToken provided")).build();
    }

    @Override
    public Response addArtist(String artistId)
            throws NotFoundException {
        // Validate required parameters
        if (StringUtils.isEmpty(artistId)) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new AccountEdgeApiResponseMessage(AccountEdgeApiResponseMessage.ERROR, "Required parameter 'artistId' is null or empty.")).build();
        }

        if (AccountEdgeSessionToken.getInstance().isValidToken()) {
            try {
                accountService.addToLibrary(artistId, AccountEdgeSessionToken.getInstance().getTokenValue(), "Artist");
                return Response.ok().entity(new AccountEdgeApiResponseMessage(AccountEdgeApiResponseMessage.OK, "success")).build();
            } catch (Exception e) {
                e.printStackTrace();

                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setMessage("Server error: " + e.getMessage());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity(new AccountEdgeApiResponseMessage(AccountEdgeApiResponseMessage.ERROR, "no sessionToken provided")).build();
    }

    @Override
    public Response addSong(String songId)
            throws NotFoundException {
        // Validate required parameters
        if (StringUtils.isEmpty(songId)) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new AccountEdgeApiResponseMessage(AccountEdgeApiResponseMessage.ERROR, "Required parameter 'songId' is null or empty.")).build();
        }
        if (AccountEdgeSessionToken.getInstance().isValidToken()) {
            try {
                accountService.addToLibrary(songId, AccountEdgeSessionToken.getInstance().getTokenValue(), "Song");
                return Response.ok().entity(new AccountEdgeApiResponseMessage(AccountEdgeApiResponseMessage.OK, "success")).build();
            } catch (Exception e) {
                e.printStackTrace();

                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setMessage("Server error: " + e.getMessage());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity(new AccountEdgeApiResponseMessage(AccountEdgeApiResponseMessage.ERROR, "no sessionToken provided")).build();
    }

    @Override
    public Response removeSong(String songId, String timestamp)
            throws NotFoundException {
        // Validate required parameters
        if (StringUtils.isEmpty(songId) || StringUtils.isEmpty(timestamp)) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new AccountEdgeApiResponseMessage(AccountEdgeApiResponseMessage.ERROR, "Required parameter 'songId' or 'timestamp' is null or empty.")).build();
        }
        if (AccountEdgeSessionToken.getInstance().isValidToken()) {
            try {
                accountService.removeFromLibrary(songId, timestamp, AccountEdgeSessionToken.getInstance().getTokenValue(), "Song");
                return Response.ok().entity(new AccountEdgeApiResponseMessage(AccountEdgeApiResponseMessage.OK, "success")).build();
            } catch (RuntimeException e) {
                e.printStackTrace();
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setMessage("Server error: " + e.getCause().getMessage());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity(new AccountEdgeApiResponseMessage(AccountEdgeApiResponseMessage.ERROR, "no sessionToken provided")).build();
    }

    @Override
    public Response removeArtist(String artistId, String timestamp)
            throws NotFoundException {
        // Validate required parameters
        if (StringUtils.isEmpty(artistId) || StringUtils.isEmpty(timestamp)) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new AccountEdgeApiResponseMessage(AccountEdgeApiResponseMessage.ERROR, "Required parameter 'artistId' is null or empty.")).build();
        }
        if (AccountEdgeSessionToken.getInstance().isValidToken()) {
            try {
                accountService.removeFromLibrary(artistId, timestamp, AccountEdgeSessionToken.getInstance().getTokenValue(), "Artist");
                return Response.ok().entity(new AccountEdgeApiResponseMessage(AccountEdgeApiResponseMessage.OK, "success")).build();
            } catch (RuntimeException e) {
                e.printStackTrace();
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setMessage("Server error: " + e.getCause().getMessage());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity(new AccountEdgeApiResponseMessage(AccountEdgeApiResponseMessage.ERROR, "no sessionToken provided")).build();
    }

    @Override
    public Response removeAlbum(String albumId, String timestamp)
            throws NotFoundException {
        // Validate required parameters
        if (StringUtils.isEmpty(albumId) || StringUtils.isEmpty(timestamp)) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new AccountEdgeApiResponseMessage(AccountEdgeApiResponseMessage.ERROR, "Required parameter 'albumId' is null or empty.")).build();
        }
        if (AccountEdgeSessionToken.getInstance().isValidToken()) {
            try {
                accountService.removeFromLibrary(albumId, timestamp, AccountEdgeSessionToken.getInstance().getTokenValue(), "Album");
                return Response.ok().entity(new AccountEdgeApiResponseMessage(AccountEdgeApiResponseMessage.OK, "success")).build();
            } catch (RuntimeException e) {
                e.printStackTrace();
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setMessage("Server error: " + e.getCause().getMessage());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity(new AccountEdgeApiResponseMessage(AccountEdgeApiResponseMessage.ERROR, "no sessionToken provided")).build();
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
        if (!password.equals(passwordConfirmation)) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage("Server error: password mismatch");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
        }
        try {
            UserDto newUser = new UserDto();
            newUser.setPassword(password);
            newUser.setUsername(email);
            newUser.setCreationTimestamp(new Date());
            newUser.setUserId(UUID.randomUUID());
            accountService.registerUser(newUser);
            return Response.ok().entity(new AccountEdgeApiResponseMessage(AccountEdgeApiResponseMessage.OK, "success")).build();
        }
        catch (RuntimeException e) {
            e.printStackTrace();
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage("Server error: " + e.getCause().getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
        }
    }
  
}
