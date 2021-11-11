package rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import facades.UserFacade;
import facades.ValutaFacade;
import utils.EMF_Creator;
import utils.HttpUtils;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.lang.reflect.ReflectPermission;
import java.util.concurrent.ExecutionException;

@Path("valuta")
public class ValutaResource {

        private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
        public static final ValutaFacade VALUTA_FACADE = ValutaFacade.getValutaFacade(EMF);
        Gson GSON = new Gson();

        @Context
        private UriInfo context;

        @Context
        SecurityContext securityContext;

        @GET
        @Produces(MediaType.APPLICATION_JSON)
        @Path("latest/{base}")
        //@RolesAllowed("user")
        public Response getAllLatestValuta(@PathParam("base") String base) throws IOException {
                //HttpUtils.fetchData("https://api.exchangerate.host/latest?base=DKK");
            return Response.ok(GSON.toJson(HttpUtils.getLatestRatesFromBase(base))).build();
        }

        @GET
        @Produces(MediaType.APPLICATION_JSON)
        @Path("update/symbols")
        //@RolesAllowed("user")
        public Response updateValutaSymbols() throws IOException {
                VALUTA_FACADE.updateValutaSymbols();
                return Response.ok().build();
        }

        @GET
        @Produces(MediaType.APPLICATION_JSON)
        @Path("{code}")
        public Response getSingleValuta(@PathParam("code")String code) throws IOException {
                return Response.ok(GSON.toJson(HttpUtils.getSingleValutaValue(code))).build();
        }

        @GET
        @Produces(MediaType.APPLICATION_JSON)
        @Path("symbols")
        public Response getAllValutaSumbols() throws Exception {
        return Response.ok(VALUTA_FACADE.getAllSymbolsFromDB()).build();
        }

        @GET
        @Produces(MediaType.APPLICATION_JSON)
        @Path("{from}/{to}/{valuta1}/{valuta2}")
        //@RolesAllowed("admin")
        public Response getParralelleData(
                @PathParam("from") String from,
                @PathParam("to") String to,
                @PathParam("valuta1") String valuta1,
                @PathParam("valuta2") String valuta2) throws IOException, ExecutionException, InterruptedException {
            return Response.ok(GSON.toJson(HttpUtils.parralellFetch(valuta1,valuta2,from,to))).build();
        }
    }



