package org.version1.client;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/")
@RegisterRestClient(configKey="invoice-api")
public interface InvoiceClient {

    @GET
    String getInvoices();
}
