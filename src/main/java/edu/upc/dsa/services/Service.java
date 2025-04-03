package edu.upc.dsa.services;

import edu.upc.dsa.Manager;
import edu.upc.dsa.ManagerImpl;
import edu.upc.dsa.models.Avio;
import edu.upc.dsa.models.Maleta;
import edu.upc.dsa.models.Vol;
import io.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/vols", description = "Endpoint per la gestió de vols")
@Path("/vols")
public class Service {

    private final Manager manager;

    public Service() {
        this.manager = ManagerImpl.getInstance();
    }

    //Afegir avió
    @POST
    @Path("/avio/{id}/{model}/{companyia}")
    @ApiOperation(value = "Afegir o modificar un avió")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Avió afeigit corectament"),
            @ApiResponse(code = 500, message = "Error")

    })
    public Response addOrUpdateAvio(@PathParam("id") String id,
                                    @PathParam("model") String model,
                                    @PathParam("companyia") String companyia) {
        Avio avio = manager.afegirAvio(id, model, companyia);
        return Response.status(201).entity(avio).build();
    }

    // Afegir  vol
    @POST
    @Path("/vol/{id}/{horaSortida}/{horaArribada}/{origen}/{desti}/{idAvio}")
    @ApiOperation(value = "Afegir o modificar un vol")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Vol afeigit correctament"),
            @ApiResponse(code = 500, message = "Error")
    })
    public Response addOrUpdateVol(@PathParam("id") String id,
                                   @PathParam("horaSortida") String horaSortida,
                                   @PathParam("horaArribada") String horaArribada,
                                   @PathParam("origen") String origen,
                                   @PathParam("desti") String desti,
                                   @PathParam("idAvio") String idAvio) {
        Vol vol = manager.afegirVol(id, horaSortida, horaArribada, origen, desti, idAvio);
        if (vol == null) return Response.status(404).entity("Avió no trobat").build();
        return Response.status(201).entity(vol).build();
    }

    //Facturar una maleta
    @POST
    @Path("/facturar/{idUsuari}/{idVol}")
    @ApiOperation(value = "Facturar una maleta")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = " Maleta facturada correctament"),
            @ApiResponse(code = 500, message = "Error")
    })
    public Response facturarMaleta(@PathParam("idUsuari") String idUsuari,
                                   @PathParam("idVol") String idVol) {
        Maleta maleta = manager.facturarMaleta(idUsuari, idVol);
        if (maleta == null) return Response.status(404).entity("Vol no trobat").build();
        return Response.status(201).entity(maleta).build();
    }

    // Obtenir les maletes d’un vol en ordre de descarrega
    @GET
    @Path("/maletes/{idVol}")
    @ApiOperation(value = "Obtenir les maletes d’un vol en ordre de descarrega")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Llista mostrada amb éxit"),
            @ApiResponse(code = 404, message = "Llista no trabada")
    })
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMaletesDescarga(@PathParam("idVol") String idVol) {
        List<Maleta> maletes = manager.getMaletesDescarga(idVol);
        GenericEntity<List<Maleta>> entity = new GenericEntity<List<Maleta>>(maletes) {};
        return Response.status(200).entity(entity).build();
    }

}
