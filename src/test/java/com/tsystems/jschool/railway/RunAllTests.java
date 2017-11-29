package com.tsystems.jschool.railway;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        BoardServiceImplTest.class,
        RouteServiceImplTest.class,
        StationServiceImplTest.class,
        TicketServiceImplTest.class,
        TrainServiceImplTest.class,
        WaypointServiceImplTest.class
})
public class RunAllTests {
}
