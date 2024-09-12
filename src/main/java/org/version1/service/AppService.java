package org.version1.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.version1.client.CustomerClient;
import org.version1.client.InvoiceClient;

@ApplicationScoped
public class AppService {

    @RestClient CustomerClient customerClient;
    @RestClient InvoiceClient invoiceClient;

}
