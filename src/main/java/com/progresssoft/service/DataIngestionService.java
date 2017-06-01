package com.progresssoft.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.progresssoft.dto.FileDto;
import com.progresssoft.manager.DataIngestionManager;

/**
 * DataSource Service
 */
@Path("dataimport")
public class DataIngestionService {
	private static final Logger LOG = LogManager.getLogger();

	@Autowired
	private DataIngestionManager dataIngestionManager;


	/** {@inheritDoc} */
	@POST
	@Path("/upload")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response upload(FileDto file) {
		if (file.getContentType().equals("text/csv") || file.getContentType().equals("application/vnd.ms-excel")) {
			dataIngestionManager.importData(file);
		} else {
			return createBadRequestError("File format should be CSV");
		}
		return Response.ok().build();

	}

	private Response createBadRequestError(String message) {
		return Response
				.status(Status.BAD_REQUEST)
				.entity("{\"error\" : \"" + message + "\"}")
				.build();
	}

}
