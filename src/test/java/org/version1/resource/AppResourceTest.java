package org.version1.resource;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.version1.model.Result;
import org.version1.service.AppService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class AppResourceTest {

    @InjectMock private AppService appService;
    @Inject private AppResource appResource;

    @Test
    void getMaxSpentCustomerDetails() {
        List<Result> expected = List.of(new Result("Tom", "Cruise", 33.73), new Result("Hannah", "Rob", 33.73));
        Mockito.when(appService.getMaxSpentCustomer()).thenReturn(expected);
        List<Result> actual = appResource.getMaxSpentCustomerDetails();
        assertEquals(2, actual.size());
        assertEquals("Tom", actual.get(0).name);
        assertEquals("Cruise", actual.get(0).surname);
        assertEquals(33.73, actual.get(0).amount);
        assertEquals("Hannah", actual.get(1).name);
        assertEquals("Rob", actual.get(1).surname);
        assertEquals(33.73, actual.get(1).amount);
    }
}