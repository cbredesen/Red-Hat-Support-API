package com.redhat.gss.strata.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.providers.jaxb.json.Mapped;
import org.jboss.resteasy.annotations.providers.jaxb.json.XmlNsMap;

import com.redhat.gss.strata.model.Products;
import com.redhat.gss.strata.model.Versions;

/**
 * Provides access to product names, versions and other related information about products. 
 * 
 * @author Chris Bredesen
 */
@Path("/products")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Mapped(namespaceMap = {@XmlNsMap(namespace = "http://www.redhat.com/gss/strata", jsonName = "strata")})
public interface ProductsResource {

	/**
	 * List products available to the calling user.
	 */
	@GET
	public Products listProducts();

	/**
	 * List all versions for <code>product</code>.
	 * 
	 * @param product Product name (TODO: use code here).
	 * 
	 * @return All product versions.
	 */
	@GET
	@Path("{product}/versions")
	public Versions listProductVersions(@PathParam("product") String product);

}
