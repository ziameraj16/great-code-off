package org.version1.resource;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Test;
import org.version1.client.CustomerClient;
import org.version1.client.InvoiceClient;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;

@QuarkusTest
class AppResourceIT {

    @InjectMock @RestClient private CustomerClient customerClient;
    @InjectMock @RestClient private InvoiceClient invoiceClient;

    @Test
    public void testEndpoint() {
        when(invoiceClient.getInvoices()).thenReturn(INVOICE_RESULT);
        when(customerClient.getCustomers()).thenReturn(CUSTOMER_RESULT);
        given()
                .when().get("/greatcodeoff/max-spent")
                .then()
                .statusCode(200)
                .body(is("[{\"name\":\"Alice\",\"surname\":\"Klark\",\"amount\":235.78},{\"name\":\"David\",\"surname\":\"Nap\",\"amount\":235.78}]"));
    }

    @Test
    public void throwsError() {
        when(customerClient.getCustomers()).thenReturn("Invalid JSON");
        given()
                .when().get("/greatcodeoff/max-spent")
                .then()
                .statusCode(500)
                .body(containsString("Not able to parse response from external APIs"));
    }

    @Test
    public void testExternalEndpoint() {
        when(invoiceClient.getInvoices()).thenReturn(INVOICE_RESULT);
        when(customerClient.getCustomers()).thenReturn("{}");
        given()
                .when().get("/greatcodeoff/max-spent")
                .then()
                .statusCode(500)
                .body(containsString("No data found for customers"));
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