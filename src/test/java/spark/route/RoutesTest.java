package spark.route;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RoutesTest {

    @Test
    public void testParseValidateAddRoute_whenHttpMethodIsValid_thenAddRoute() {
        //given
        String route = "get'/hello'";
        String acceptType = "*/*";
        Object target = new Object();

        RouteEntry expectedRouteEntry = new RouteEntry(HttpMethod.get, "/hello", acceptType, target);
        List<RouteEntry> expectedRoutes = new ArrayList<>();
        expectedRoutes.add(expectedRouteEntry);

        Routes simpleRouteMatcher = Routes.create();
        simpleRouteMatcher.add(route, acceptType, target);

        //then
        List<RouteEntry> routes = simpleRouteMatcher.routes();
        assertTrue("Should return true because http method is valid and the route should be added to the list",
                   Util.equals(routes, expectedRoutes));

    }

    @Test
    public void testParseValidateAddRoute_whenHttpMethodIsInvalid_thenDoNotAddRoute() {
        //given
        String route = "test'/hello'";
        String acceptType = "*/*";
        Object target = new Object();

        Routes simpleRouteMatcher = Routes.create();
        simpleRouteMatcher.add(route, acceptType, target);

        //then
        List<RouteEntry> routes = simpleRouteMatcher.routes();
        assertEquals("Should return 0 because test is not a valid http method, so the route is not added to the list",
                     routes.size(), 0);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void exposesUnmodifiableRoutes() throws Exception {
        Routes simpleRouteMatcher = Routes.create();
        List<RouteEntry> routes = simpleRouteMatcher.routes();

        routes.add(new RouteEntry(HttpMethod.get, "/hello", "*/*", new Object()));
    }
}
