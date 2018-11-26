package io.ticketcoin.util;

import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import io.ticketcoin.dashboard.dto.TicketDTO;
import io.ticketcoin.oauth.ClientTokenDTO;
import io.ticketcoin.rest.response.TicketDTOResponseWrapper;

public class RSClient {

	private String username;
	private String password;
	ClientTokenDTO token;
	
	static Client client = Client.create();
    
	
	public RSClient(String username, String password)
	{
		this.username=username;
		this.password=password;
	}
	
	
	//set the appropriate URL
	static String consumeCardUrl = "http://localhost/services/secure/merchant/consumeCard";
	static String authUrl = "http://localhost/services/oauth/token";

	
       public static void main(String[] args){
    	   RSClient client = new RSClient(args[0], args[1]);
    	   
    	   client.authenticate();
    	   TicketDTOResponseWrapper response = client.consumeCard("123");
    	   if(response.isSuccess())
    	   {
    		   System.out.println("Entra");
    	   }
    	   else 
    	   {
    		   System.out.println("Esci");
    	   }
       }        

       
       public TicketDTOResponseWrapper consumeCard(String cardID){
    	   return consumeCard(cardID, true);
       }
       
       private TicketDTOResponseWrapper consumeCard(String cardID, boolean authetnticateIfNecessary){
		WebResource webResource = client.resource(consumeCardUrl);

	   	String inputData ="{" +
	   			"\"cardID\":\"" + cardID + "\"" +
	   			"}" 
	   			;
	   	
	   	
	   	ClientResponse response = webResource
	   			.type(MediaType.APPLICATION_JSON)
	   			.accept(MediaType.APPLICATION_JSON)
	   			.header("Authorization","Bearer " + token.getAccess_token())
	   			.post(ClientResponse.class,inputData);

	   	if(response.getStatus()==200){
	   		return response.getEntity(TicketDTOResponseWrapper.class);
   		}
	   	else if(response.getStatus()==401 && authetnticateIfNecessary){
	   		authenticate();
	   		return consumeCard(cardID, false);
   		}
	   	else {
   			throw new RuntimeException("HTTP Error: "+ response.getStatus());
   		}
	   		
		

	}
       

       


   	public void authenticate(){
   	  WebResource webResource = client.resource(authUrl);
   	String inputData = 
   			"username=" + username + "&" +
   			"password=" + password + "&" +
   			"grant_type=password&" +
   			"client_id=admin&" +
   			"client_secret=admin" 
   			;
   	
   	
   	  ClientResponse response = webResource.type(MediaType.APPLICATION_FORM_URLENCODED).accept(MediaType.APPLICATION_JSON).post(ClientResponse.class,inputData);
   		if(response.getStatus()!=200){
   			throw new RuntimeException("HTTP Error: "+ response.getStatus());
   		}
   		
   		token = response.getEntity(ClientTokenDTO.class);

   	}

        
         
}