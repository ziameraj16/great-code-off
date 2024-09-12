package org.version1.resource;

import io.quarkus.test.Mock;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.version1.service.AppService;


class AppResourceTest {

    @Mock private AppService appService;

    @Inject private AppResource appResource;

    @Test
    void getMaxSpentCustomerDetails() {

        appResource.getMaxSpentCustomerDetails();
    }
}