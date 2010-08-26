package com.redhat.gss.strata.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.providers.jaxb.json.Mapped;
import org.jboss.resteasy.annotations.providers.jaxb.json.XmlNsMap;
import org.jboss.resteasy.util.HttpHeaderNames;

import com.redhat.gss.strata.model.Case;
import com.redhat.gss.strata.model.Cases;
import com.redhat.gss.strata.model.Comment;
import com.redhat.gss.strata.model.Comments;


/**
 * Support case services. This interface defines the service contracts for all
 * support case operations in RESTful terms. Though it is expected that much of
 * the integration will be with this Java interface, we are tightly coupled with
 * HTTP and so many protocol-specific idioms are reflected here.
 * 
 * @author Tim Walsh
 * @author Chris Bredesen
 */
@Path("/cases")
@Produces( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN })
public interface CaseService {

	/**
	 * Create a case, possibly inclusive of nested comments and tags.  Accepts XML and JSON.
	 * 
	 * @param supportCase Case to create.
	 * 
	 * @return Response including URL of new case resource in the Location header.
	 */
	@POST
	@Consumes( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response createCase(
			@Mapped(namespaceMap = { @XmlNsMap(namespace = "http://www.redhat.com/gss/strata", jsonName = "strata") }) Case supportCase);

	/**
	 * Update a case, including nested comments and tags.  Accepts XML and JSON.
	 * 
	 * @return TODO wtf does this return?
	 */
	@PUT
	@Path("/{caseNumber}")
	@Consumes( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response updateCase(
			final @PathParam("caseNumber") String caseNumber, 
			@Mapped(namespaceMap = { @XmlNsMap(namespace = "http://www.redhat.com/gss/strata", jsonName = "strata") }) Case supportCase);

	/**
	 * List cases.  Supports listing of full case information as well as resource URLs only.
	 * 
	 * @param detail Set to true if detail of comments to be retrieved, if false
	 *            then only references will be returned.
	 * 
	 * @return list of cases
	 * 
	 * @see SupportCaseList.
	 */
	@GET
	@Mapped(namespaceMap = { @XmlNsMap(namespace = "http://www.redhat.com/gss/strata", jsonName = "strata") })
	public Cases listCases(
			final @QueryParam("detail") @DefaultValue("false") boolean detail);

	/**
	 * Retrieve case. The default behavior is to emit xml, although an Accept:
	 * header can specifiy application/xml, this is not needed. Specify Accept:
	 * application/json to retrieve list as json.
	 * 
	 * @param caseNumber Case number.  Cannot be <code>null</code>.
	 */
	@GET
	@Path("/{caseNumber}")
	@Mapped(namespaceMap = { @XmlNsMap(namespace = "http://www.redhat.com/gss/strata", jsonName = "strata") })
	public Case getCase(final @PathParam("caseNumber") String caseNumber);

	/**
	 * Upload a file to a case using the POST method. Note that this method is
	 * non-resumable and only suitable for small files. Larger files should use
	 * the resumable upload methods. Multiple files can be sent in this request
	 * as a multi-part form request is expected and supported.
	 * 
	 * @param caseNumber
	 *            the case number
	 * @return response indicating status of operation, a list of created file
	 *         uri's will be returned as multiple Location headers.
	 */
	@POST
	@Path("/{caseNumber}/attachments")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response addAttachment(
			final @PathParam("caseNumber") String caseNumber);

	/**
	 * List files attached to a support case.
	 * 
	 * @param caseNumber
	 *            the case number
	 * @param detail
	 *            set to true if detail of comments to be retrieved, if false
	 *            then only references will be returned.
	 * @return list of files as XML or JSON
	 */
	@GET
	@Path("/{caseNumber}/attachments")
	@Mapped(namespaceMap = { @XmlNsMap(namespace = "http://www.redhat.com/gss/strata", jsonName = "strata") })
	public Response listAttachments(
			final @PathParam("caseNumber") String caseNumber,
			final @QueryParam("detail") @DefaultValue("false") boolean detail);

	/**
	 * Download a file using the GET method. Note that this method is
	 * non-resumable. This method returns a header
	 * "Content-Disposition: attachment; filename=mylinks.rtf" . This will
	 * invoke the SaveAs dialog in a browser with the actual file name. eg
	 * myfile.txt rather than the file id which is specified in the url. Partial
	 * download request can be made by specifying a Range header
	 * "Range: bytes 493283-593283".
	 * 
	 * @param caseNumber
	 *            the case number
	 * @param uuid
	 *            the id of the file
	 * @param range
	 *            the optional range parameter from the header in format Range:
	 *            bytes <start>-<end>
	 * 
	 * @return response streaming of file to client.
	 */
	@GET
	@Path("/{caseNumber}/attachments/{uuid}")
	@Produces( { MediaType.APPLICATION_OCTET_STREAM, MediaType.TEXT_HTML,
			MediaType.TEXT_XML, MediaType.TEXT_PLAIN })
	public Response getAttachment(
			final @PathParam("caseNumber") String caseNumber,
			final @PathParam("uuid") String uuid,
			final @HeaderParam("Range") String range);

	/**
	 * Append a file using the PUT method. This method allows for the addition
	 * to an existing file. The file (or part) must have been initially uploaded
	 * using the addFile method. This method can be called multiple times on any
	 * file the user has permission to write to.
	 * 
	 * @param caseNumber
	 *            the case number
	 * @param uuid
	 *            the id of the file
	 * @param contentType
	 *            the mime content type
	 * @param match
	 *            supplied the If-Match with a ETag from previous append/add
	 * 
	 * @return response indicating status of operation.
	 */
	@PUT
	@Path("/{caseNumber}/attachments/{uuid}")
	@Consumes( { MediaType.APPLICATION_OCTET_STREAM })
	public Response appendAttachment(
			final @PathParam("caseNumber") String caseNumber,
			final @PathParam("uuid") String uuid,
			final @HeaderParam(HttpHeaderNames.CONTENT_TYPE) String contentType);

	/**
	 * Add a comment and / or tags to supportCase using json/xml
	 * 
	 * @param caseNumber the case number
	 * @param Comment the comment to add
	 * 
	 * @return HTTP Response indicating created of error
	 */
	@POST
	@Path("/{caseNumber}/comments")
	@Consumes( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response addComment(
			final @PathParam("caseNumber") String caseNumber,
			@Mapped(namespaceMap = { @XmlNsMap(namespace = "http://www.redhat.com/gss/strata", jsonName = "strata") }) Comment comment);

	/**
	 * Update a comment and / or tags to supportCase using json/xml
	 * 
	 * @param caseNumber
	 *            the case number
	 * @param id
	 *            the comment id
	 * @param comment
	 *            the comment to update
	 * 
	 * @return HTTP Response indicating created of error
	 */
	@PUT
	@Path("/{caseNumber}/comments/{id}")
	@Consumes( { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response updateComment(
			final @PathParam("caseNumber") String caseNumber,
			final @PathParam("id") String uuid,
			@Mapped(namespaceMap = { @XmlNsMap(namespace = "http://www.redhat.com/gss/strata", jsonName = "strata") }) Comment comment);

	/**
	 * Retrieve list of comment uri's attached to supportCase. The default
	 * behavior is to emit xml, although an Accept: header can specifiy
	 * application/xml, this is not needed. Specify Accept: application/json to
	 * retrieve list as json.
	 * 
	 * @param caseNumber
	 *            the case number
	 * @param detail
	 *            set to true if detail of comments to be retrieved, if false
	 *            then only references will be returned.
	 * 
	 * @return comments as XML or JSON
	 * 
	 * @see com.redhat.gss.Values.strataapi.resource.Comments
	 */
	@GET
	@Path("/{caseNumber}/comments")
	@Mapped(namespaceMap = { @XmlNsMap(namespace = "http://www.redhat.com/gss/strata", jsonName = "strata") })
	public Comments listComments(
			final @PathParam("caseNumber") String caseNumber,
			final @QueryParam("detail") @DefaultValue("false") boolean detail);

	/**
	 * Retrieve case comment. The default behavior is to emit xml, although an
	 * Accept: header can specifiy application/xml, this is not needed. Specify
	 * Accept: application/json to retrieve list as json.
	 * 
	 * @param caseNumber
	 *            the case number
	 * @param id
	 *            the id of comment id to retrieve
	 * 
	 * @return case meta data
	 */
	@GET
	@Path("/{caseNumber}/comments/{id}")
	@Mapped(namespaceMap = { @XmlNsMap(namespace = "http://www.redhat.com/gss/strata", jsonName = "strata") })
	public Comment getComment(
			final @PathParam("caseNumber") String caseNumber,
			final @PathParam("id") String uuid);

	/**
	 * Set comment status to public or private.
	 * 
	 * @param caseNumber
	 *            the case number
	 * @param uuid
	 *            the comment uuid to set
	 * @param publik
	 *            the privacy flag
	 * 
	 * @return case meta data
	 * 
	 * @todo actually with uuid the caseNumber is redundant, exists here only in
	 *       interim as there is no index lookup yet to map uuid to specific
	 *       comment entries. When a db is employed this can be very quick and
	 *       refactored to remove caseNumber.
	 */
	@PUT
	@Path("/{caseNumber}/comments/{id}/status")
	@Mapped(namespaceMap = { @XmlNsMap(namespace = "http://www.redhat.com/gss/strata", jsonName = "strata") })
	public Response setCommentPublic(
			final @PathParam("caseNumber") String caseNumber,
			final @PathParam("id") String id,
			final @QueryParam("public") @DefaultValue("false") boolean publik);

}
