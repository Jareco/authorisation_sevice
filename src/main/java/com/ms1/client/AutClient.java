package com.ms1.client;


import com.ms1.models.User;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

public class AutClient {


    private String URI_SignUP="http://localhost:8080/todo/autservice/signUp/";
    private String URI_SignIn="http://localhost:8080/todo/autservice/signIn/";
    private String URI_Validate="http://localhost:8080/todo/autservice/validate/";
    private String URI_Test="http://localhost:8080/todo/autservice/test/";


    //Return null, wenn der Statuscode 406 (nicht akzeptiert) ist.
     public User signUp(User user){
         User ureturn=null;
         ClientConfig clientConfig = new DefaultClientConfig();
         clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
         Client client = Client.create(clientConfig);
         WebResource webResource = client.resource(URI_SignUP);

         ClientResponse response = webResource.accept("application/json")
                 .type("application/json").post(ClientResponse.class, user);

         //Return null, wenn Response Status 406 ist.
         if(response.getStatus()==406) return ureturn;

         ureturn=response.getEntity(User.class);

         return ureturn;
     }

    //Return null, wenn der Statuscode 404 (nicht gefunden) ist.
    public String signIn(User user){
        String token=null;
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        WebResource webResource = client.resource(URI_SignIn);

        ClientResponse response = webResource.accept("application/json")
                .type("application/json").post(ClientResponse.class, user);

        //Return null, wenn Response Status 404 ist.
        if(response.getStatus()==404) return token;

        token=response.getEntity(String.class);

        return token;
    }

    //Return false, wenn der Statuscode 404 oder 401 ist.
    public boolean validate(String token, String email){
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        WebResource webResource = client.resource(URI_Validate);


        ClientResponse response = webResource.accept("application/json")
                .type("application/json")
                .header("Authorization", token)
                .post(ClientResponse.class, email);

        if(response.getStatus()==404) return false;

        if(response.getStatus()==401) return false;

        if(response.getStatus()==401) return false;



        return true;
    }



}
