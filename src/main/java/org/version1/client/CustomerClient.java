package org.version1.client;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/")
@RegisterRestClient(configKey="customer-api")
public interface CustomerClient {

    @GET
    String getCustomers();
}
