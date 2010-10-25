package com.redhat.gss.strata.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.providers.jaxb.json.Mapped;
import org.jboss.resteasy.annotations.providers.jaxb.json.XmlNsMap;

import com.redhat.gss.strata.model.Values;

/**
 * List values service.  This service provides access to commonly-used dynamic name-value pairs.
 * 
 * @author Tim Walsh
 * @author Chris Bredesen
 */
@Path("/values")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Mapped(namespaceMap = {@XmlNsMap(namespace = "http://www.redhat.com/gss/strata", jsonName = "strata")})
public interface ValuesResource {

	/**
	 * Case types
	 * 
	 * <p><b>XML Example</b><pre><code>
	 * curl -v -u rhn-user:pwd -X GET -H 'Accept: application/xml' http://localhost:8080/Strata/values/case/types
	 * </code></pre></p>
	 */
	@GET
	@Path("/case/types")
	public Values listCaseTypes();

	/**
	 * Case severity
	 * 
	 * <p><b>XML Example</b><pre><code>
	 * curl -v -u rhn-user:pwd -X GET -H 'Accept: application/xml' http://localhost:8080/Strata/values/case/severity
	 * </code></pre></p>
	 */
	@GET
	@Path("/case/severity")
	public Values listCaseSeverity();
	
	/**
	 * Case status
	 * 
	 * <p><b>XML Example</b><pre><code>
	 * curl -v -u rhn-user:pwd -X GET -H 'Accept: application/xml' http://localhost:8080/Strata/values/case/status
	 * </code></pre></p>
	 */
	@GET
	@Path("/case/status")
	public Values listCaseStatus();

}
