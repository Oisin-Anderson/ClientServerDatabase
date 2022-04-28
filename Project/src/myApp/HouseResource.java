package myApp;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("houses")
public class HouseResource {
	   
    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<House> getHouses() {
        System.out.println("GetHouses");
        return HouseDAO.instance.getHouses();
    }
    
    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("{houseID}")
    public House getHouse(@PathParam("houseID") String houseID) {
        System.out.println("GetHouse");
        return HouseDAO.instance.getHouse(houseID);
    }

    
    @POST
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes(MediaType.APPLICATION_JSON)
    public House postPlayer(
            @FormParam("hname") String hname,
            @FormParam("seat") String seat,
            @FormParam("words") String words,
            @Context HttpServletResponse servletResponse) throws IOException {
        
        House house = new House();

        int id = HouseDAO.instance.getNextId();
        house.setId(id);
        house.sethName(hname);
        house.setSeat(seat);
        house.setWords(words);
        HouseDAO.instance.create(house);
        
        return house;
    }
    
    
    @DELETE
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response deleteAllPlayers(
            @Context HttpServletResponse servletResponse) throws IOException {

        HouseDAO.instance.deleteAll();
        return Response.status(200).entity("Houses have all been Deleted.").build();
    }

    
    @PUT
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("{houseID}")
    public Response putPlayer(
            @FormParam("id") String id,
            @FormParam("hname") String hname,
            @FormParam("seat") String seat,
            @FormParam("words") String words,
            @Context HttpServletResponse servletResponse) throws IOException {
        
        House check = HouseDAO.instance.getHouse(id);
        
        if(check != null){
            House house = new House();
            house.setId(Integer.parseInt(id));
            house.sethName(hname);
            house.setSeat(seat);
            house.setWords(words);
            HouseDAO.instance.update(house);
            return Response.status(200).build();
        }
        
        return Response.status(304).build();
    }

    
    @DELETE
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("{houseID}")
    public Response deletePlayer(@PathParam("houseID") String id,
            @Context HttpServletResponse servletResponse) throws IOException {

        HouseDAO.instance.delete(Integer.parseInt(id));
        return Response.status(200).entity("Selected House has been Deleted.").build();
    }
    
}
