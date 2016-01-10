package de.ultical.backend.api;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.apache.ibatis.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.ultical.backend.api.transferClasses.DfvMvName;
import de.ultical.backend.data.DataStore;
import de.ultical.backend.model.User;
import io.dropwizard.auth.Auth;

@Path("/dfvmvname")
public class DfvMvNameResource {

    private final static Logger LOGGER = LoggerFactory.getLogger(DfvMvNameResource.class);

    @Inject
    DataStore dataStore;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<DfvMvName> findDfvMvName(@QueryParam("search") String searchString, @Auth User user) {
        if (this.dataStore == null) {
            throw new WebApplicationException("Dependency Injectino for data store failed!",
                    Status.INTERNAL_SERVER_ERROR);
        }
        List<DfvMvName> result = null;
        try {
            result = this.dataStore.findDfvMvName("%" + searchString + "%");
        } catch (PersistenceException pe) {
            LOGGER.error("Database access failed!", pe);
            throw new WebApplicationException("Accessing the database failed", Status.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

}