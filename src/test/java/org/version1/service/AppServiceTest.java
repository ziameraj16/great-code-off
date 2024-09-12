package org.version1.service;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Test;
import org.version1.client.CustomerClient;
import org.version1.client.InvoiceClient;
import org.version1.model.Result;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@QuarkusTest
class AppServiceTest {

    @InjectMock @RestClient private CustomerClient customerClient;
    @InjectMock @RestClient private InvoiceClient invoiceClient;
    @Inject private AppService appService;

    @Test void getMaxSpentCustomer() {
        when(invoiceClient.getInvoices()).thenReturn(INVOICE_RESULT);
        when(customerClient.getCustomers()).thenReturn(CUSTOMER_RESULT);
        final List<Result> actual = appService.getMaxSpentCustomer();
        assertEquals(2, actual.size());
        assertEquals("Alice", actual.get(0).name);
        assertEquals("Klark", actual.get(0).surname);
        assertEquals(235.78, actual.get(0).amount);
        assertEquals("David", actual.get(1).name);
        assertEquals("Nap", actual.get(1).surname);
        assertEquals(235.78, actual.get(1).amount);
    }

    private static final String INVOICE_RESULT = """
            {
              "invoices": [
                {
                  "ID": 0,
                  "customerId": 0,
                  "amount": 12
                },
                {
                  "ID": 1,
                  "customerId": 0,
                  "amount": 235.78
                },
                {
                  "ID": 2,
                  "customerId": 1,
                  "amount": 5.06
                },
                {
                  "ID": 3,
                  "customerId": 2,
                  "amount": 12.6
                },
                {
                  "ID": 4,
                  "customerId": 3,
                  "amount": 235.78
                }
              ]
            }
            """;
    private static final String CUSTOMER_RESULT = """
            {
              "customers": [
                {
                  "ID": 0,
                  "name": "Alice",
                  "surname": "Klark"
                },
                {
                  "ID": 1,
                  "name": "Bob",
                  "surname": "McAdoo"
                },
                {
                  "ID": 2,
                  "name": "Cindy",
                  "surname": "Law"
                },
                {
                  "ID": 3,
                  "name": "David",
                  "surname": "Nap"
                },
                {
                  "ID": 4,
                  "name": "Elvis",
                  "surname": "Blue"
                }
              ]
            }
            """;


}