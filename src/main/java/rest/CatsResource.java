package rest;
import com.google.gson.Gson;
import utils.EMF_Creator;
import utils.HttpUtils;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;

@Path("cat")
public class CatsResource {

        private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
        Gson GSON = new Gson();
        @Context
        private UriInfo context;

        @Context
        SecurityContext securityContext;

        @GET
        @Produces(MediaType.APPLICATION_JSON)
        @Path("user")
        @RolesAllowed("user")
        public String getCatPicture() throws IOException {
         return GSON.toJson(HttpUtils.fetchData("https://thatcopy.pw/catapi/rest/"));
        }

        @GET
        @Produces(MediaType.APPLICATION_JSON)
        @Path("admin")
        @RolesAllowed("admin")
        public String getCatPictureForAdmin() throws IOException {
            //DOGS IN A CAT ENDPOINT??? WHAT KIND DARK MAGIC IS THIS??
            return GSON.toJson(HttpUtils.fetchData("https://dog-api.kinduff.com/api/facts"));
        }
    }

