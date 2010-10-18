package com.redhat.gss.strata.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.providers.jaxb.json.Mapped;
import org.jboss.resteasy.annotations.providers.jaxb.json.XmlNsMap;

import com.redhat.gss.strata.model.Values;

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
	 * List products
	 *
	 * <p><b>XML Example</b><pre><code>
	 * curl -v -u rhn-user:pwd -X GET -H 'Accept: application/xml' http://localhost:8080/Strata/values/products
	 * </code></pre></p>
	 */
	@GET
	public Values listProducts();
	
	/**
	 * List product versions
	 *
	 * <p><b>XML Example</b><pre><code>
	 * curl -v -u rhn-user:pwd -X GET -H 'Accept: application/xml' http://localhost:8080/Strata/values/products/versions/JBoss%20AS
	 * </code></pre></p>
	 */
	@GET
	@Path("/versions/{product}")
	public Values listProductVersions(@PathParam("product") String product);
	

}
