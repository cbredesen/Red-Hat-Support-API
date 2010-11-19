package com.redhat.gss.strata.resource;


import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.providers.jaxb.json.Mapped;
import org.jboss.resteasy.annotations.providers.jaxb.json.XmlNsMap;

import com.redhat.gss.strata.model.Group;
import com.redhat.gss.strata.model.Groups;

@Path("/account")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
public interface AccountResource {

	@GET
	@Path("/{accountNumber}/folders/{folderNumber}")
	@Mapped(namespaceMap = {@XmlNsMap(namespace = "http://www.redhat.com/gss/strata", jsonName = "strata")})
	public Group getGroup(final @PathParam("accountNumber") String accountNumber,
			final @PathParam("folderNumber") String folderNumber);
	
	/**
	 * Create Folder
	 * 
	 * @param accountNumber
	 * @param folder
	 * 
	 * @return
	 */
	@POST
	@Path("/{accountNumber}/folders")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
	public void createGroup(final @PathParam("accountNumber") String accountNumber,
			@Mapped(namespaceMap = {@XmlNsMap(namespace = "http://www.redhat.com/gss/strata", jsonName = "strata")}) Group group);
	
	/**
	 * List folders
	 * 
	 * @param security
	 * @param accountNumber
	 * @return
	 */
	@GET
	@Path("/{accountNumber}/folders")
	@Mapped(namespaceMap = {@XmlNsMap(namespace = "http://www.redhat.com/gss/strata", jsonName = "strata")})
	public Groups listGroups(final @PathParam("accountNumber") String accountNumber);
	/**
	 * Return default account for logged in user. 
	 * 
	 * @param accountNumber if specified indicates if access to account is permitted
	 * 
	 * @return
	 */
	@GET
	@Path("/")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML, MediaType.TEXT_HTML})
	public String defaultAccount(final @QueryParam("accountNumber") String accountNumber);
	
	/**
	 * Delete folder 
	 * 
	 * @param accountNumber
	 * @param folderNumber
	 * 
	 * @return
	 */
	@DELETE
	@Path("/{accountNumber}/folders/{folderNumber}")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML, MediaType.TEXT_HTML})
	public void deleteGroup(	
			final @PathParam("accountNumber") String accountNumber,
			final @PathParam("folderNumber") String groupNumber);
	
	/**
	 * Update a folder
	 * 
	 * @param accountNumber
	 * @param folderNumber needed for uri resolution
	 * @param folder
	 * @return
	 */
	@PUT
	@Path("/{accountNumber}/folders/{folderNumber}")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
	public void updateGroup(final @PathParam("accountNumber") String accountNumber,
			final @PathParam("folderNumber") String folderNumber,    // needed for path resolution even tho folder number is in Folder.
			@Mapped(namespaceMap = {@XmlNsMap(namespace = "http://www.redhat.com/gss/strata", jsonName = "strata")}) Group group);
}