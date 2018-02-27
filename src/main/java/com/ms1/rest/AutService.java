package com.ms1.rest;

import com.ms1.management.Manager;
import com.ms1.models.User;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.spi.resource.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/autservice")
@Produces( {"application/json"})
@Singleton
public class AutService {
	private Manager m= new Manager();
	private String URI_MS4="http://10.103.102.13:9991/todolist/notify-service/add-user";
	private String URI_MS2="http://10.103.102.9:9090/path/restapi/todolist/createList";
	private String jwt=null;


	@POST
	@Path("/signIn/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response signIn(User user) {
		User usersignIn=m.getUserByEmailAndPassword(user.getEmail(), user.getPassword());
		if(usersignIn==null)
			return Response.status(404).entity("NotFound").build();

		usersignIn.setLastSession(System.currentTimeMillis());
		m.updateUser(usersignIn);
		String token=m.createJWT(user.getEmail());
		return Response.status(200).entity(token).build();
	}

	@POST
	@Path("/signUp")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response signUp(User user) {

		User userToAdd=new User(user.getFirstname(),user.getLastname(),user.getEmail(), user.getId(),user.getPassword());
		User userAdded=m.addUser(userToAdd);
		if(userAdded==null) return Response.status(406).entity("AlreadyExists").build();

		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		Client client = Client.create(clientConfig);
		WebResource webResource_MS4 = client.resource(URI_MS4);
		WebResource webResource_MS2 = client.resource(URI_MS2);

		//Sending User to MS4
		ClientResponse response_MS4 = webResource_MS4.accept("application/json")
				.type("application/json").post(ClientResponse.class, userAdded);
		System.out.println("Statuscode_MS4: " + response_MS4.getStatus());

		//Sending User to MS2
		ClientResponse response_MS2 = webResource_MS2.accept("application/json")
				.type("application/json")
				.header("email", userAdded.getEmail())
				.post(ClientResponse.class);



		return Response.status(200).entity(userAdded).build();
	}

	@POST
	@Path("/validate")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN })
	public Response validate(@HeaderParam("Authorization") String token,String email) {
		System.out.println("Token: " + token);
		System.out.println("Email: " + email);
		User userValidate=m.getUserByEmail(email);
		if(userValidate==null) return Response.status(404).entity("NotFound").build();

		boolean validateJWT=m.validateJWT(token,userValidate);
		if(validateJWT==false) return Response.status(401).entity("InvalidToken").build();

		long userLastSession=userValidate.getLastSession();
		long currentTime=System.currentTimeMillis();

		long differenceBetweenSessions=currentTime-userLastSession;

		//Session 15 Minuten
		if(differenceBetweenSessions>900000) return Response.status(401).entity("SessionIsFinished").build();

		//m.deleteUser(userValidate);
		userValidate.setLastSession(System.currentTimeMillis());
		m.updateUser(userValidate);

		return Response.status(200).entity("Ok").build();
	}


	//Code below for TESTING!


	@POST
	@Path("/createUser/")
	@Consumes(MediaType.APPLICATION_JSON)
	public void createUser(User user) {
		System.out.println ("MS4: Username: " + user.getFirstname() + " . " + user.getEmail());
	}

	@POST
	@Path("/createList/")
	@Consumes(MediaType.APPLICATION_JSON)
	public void createList(String email) {
		System.out.println ("MS2: Email: " + email);
	}


}
