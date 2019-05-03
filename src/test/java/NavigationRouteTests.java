import com.datoramahw.pages.NavigationRoute;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class NavigationRouteTests {
    @Test
    public void durationDistanceTest () {
        NavigationRoute navigationRoute = new NavigationRoute("34 min", "47.3 km", "");
        NavigationRoute navigationRoute2 = new NavigationRoute("3 hours", "96 meters", "");
        assertEquals("", navigationRoute.getDistance(), 47.3, 0.3);
        assertEquals("", navigationRoute.getDuration(), 34);
        assertEquals("", navigationRoute2.getDistance(), 0.096, 0.3);
        assertEquals("", navigationRoute2.getDuration(), 180);
    }
    @Test
    public void sortTest () {
        NavigationRoute navigationRoute = new NavigationRoute("34 min", "47.3 km", "");
        NavigationRoute navigationRoute2 = new NavigationRoute("3 hours", "96 meters", "");
        List<NavigationRoute> routes = new ArrayList<>();
        routes.add(navigationRoute2);
        routes.add(navigationRoute);
        assertEquals(routes.get(0).getDuration(), 180);
        routes.sort(null);
        assertEquals(routes.get(0).getDuration(), 34);
    }
}
