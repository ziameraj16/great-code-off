package org.version1.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.version1.client.CustomerClient;
import org.version1.client.InvoiceClient;
import org.version1.exception.ThirdPartyException;
import org.version1.model.Customer;
import org.version1.model.Customers;
import org.version1.model.Invoices;
import org.version1.model.Result;

import java.util.Comparator;
import java.util.List;

@ApplicationScoped
public class AppService {

    @RestClient CustomerClient customerClient;
    @RestClient InvoiceClient invoiceClient;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Result> getMaxSpentCustomer() {
        final Customers customers;
        final Invoices invoices;
        try {
            customers = objectMapper.readValue(customerClient.getCustomers(), Customers.class);
            invoices = objectMapper.readValue(invoiceClient.getInvoices(), Invoices.class);
        } catch (JsonProcessingException e) {
            throw new ThirdPartyException("Not able to parse response from external APIs", e);
        }
        if (customers.customers == null || customers.customers.isEmpty()) {
            throw new ThirdPartyException("No data found for customers");
        }
        if (invoices.invoices == null || invoices.invoices.isEmpty()) {
            throw new ThirdPartyException("No data found for invoices");
        }

        final double maxAmount = getMaxAmountFromInvoices(invoices);
        final List<Integer> customerIds = getCustomerIdsWithMaxAmount(invoices, maxAmount);
        final List<Customer> maxAmtCustomers = getCustomerDetailsFromIds(customers, customerIds);
        if (maxAmtCustomers.isEmpty()) {
            throw new ThirdPartyException(String.format("No customer found for IDS: %s", customerIds));
        }
        return maxAmtCustomers.stream().map(customer -> new Result(customer.name, customer.surname, maxAmount)).toList();
    }

    private static List<Customer> getCustomerDetailsFromIds(Customers customers, List<Integer> customerIds) {
        final List<Customer> maxAmtCustomers = customers.customers.stream().filter(c -> customerIds.contains(c.ID)).toList();
        return maxAmtCustomers;
    }

    private static List<Integer> getCustomerIdsWithMaxAmount(Invoices invoices, double maxAmount) {
        final List<Integer> customerIds = invoices.invoices.stream().filter(invoice -> invoice.amount == maxAmount).map(invoice -> invoice.customerId).toList();
        return customerIds;
    }

    private static double getMaxAmountFromInvoices(Invoices invoices) {
        return invoices.invoices.stream().max(Comparator.comparing(v -> v.amount)).get().amount;
    }
}
