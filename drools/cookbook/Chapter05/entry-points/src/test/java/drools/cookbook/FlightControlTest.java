package drools.cookbook;

import java.util.concurrent.TimeUnit;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.impl.ClassPathResource;
import org.drools.runtime.KnowledgeSessionConfiguration;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.conf.ClockTypeOption;
import org.drools.runtime.rule.WorkingMemoryEntryPoint;
import org.drools.time.SessionPseudoClock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import drools.cookbook.helper.FlighSimulation;
import drools.cookbook.model.FlightControl;
import drools.cookbook.model.FlightStatus;

/**
 * 
 * @author Lucas Amador
 * 
 */
public class FlightControlTest {

    private StatefulKnowledgeSession ksession;

    @Before
    public void start() {
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add(new ClassPathResource("rules.drl", getClass()), ResourceType.DRL);

        if (kbuilder.hasErrors()) {
            if (kbuilder.getErrors().size() > 0) {
                for (KnowledgeBuilderError kerror : kbuilder.getErrors()) {
                    System.err.println(kerror);
                }
            }
        }

        KnowledgeBase kbase = kbuilder.newKnowledgeBase();
        kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());

        KnowledgeSessionConfiguration conf = KnowledgeBaseFactory.newKnowledgeSessionConfiguration();
        conf.setOption(ClockTypeOption.get("pseudo"));

        ksession = kbase.newStatefulKnowledgeSession(conf, null);

        new Thread() {
            @Override
            public void run() {
                ksession.fireUntilHalt();
            }
        }.start();
    }

    @Test
    public void flightArrival() throws InterruptedException {

        FlightControl control = new FlightControl();
        control.setAirport("LAX");

        SessionPseudoClock clock = ksession.getSessionClock();

        ksession.setGlobal("control", control);

        final FlighSimulation flightAA001 = new FlighSimulation("AA001", "San Francisco", "Los Angeles", 270);

        while (true) {
            FlightStatus flightStatus = flightAA001.update();
            if (flightStatus.getDistance() <= 50) {
                WorkingMemoryEntryPoint flightArrivalEntryPoint = ksession.getWorkingMemoryEntryPoint("flight-arrival");
                flightArrivalEntryPoint.insert(flightStatus);
                break;
            } else {
                ksession.getWorkingMemoryEntryPoint("flight-control").insert(flightStatus);
            }
            clock.advanceTime(5, TimeUnit.MINUTES);
            Thread.sleep(1000);
        }
    }

    @After
    public void stop() {
        ksession.halt();
        ksession.dispose();
    }

}
