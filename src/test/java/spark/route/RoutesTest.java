package spark.route;

import org.junit.Test;

import java.util.List;

public class RoutesTest {

    @Test(expected = UnsupportedOperationException.class)
    public void exposesUnmodifiableRoutes() {
        Routes simpleRouteMatcher = Routes.create();
        List<RouteEntry> routes = simpleRouteMatcher.routes();

        routes.add(new RouteEntry(HttpMethod.get, "/hello", "*/*", new Object()));
    }
}
