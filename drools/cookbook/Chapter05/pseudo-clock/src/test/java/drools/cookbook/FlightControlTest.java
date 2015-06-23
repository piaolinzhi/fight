package drools.cookbook;

import java.util.concurrent.TimeUnit;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseConfiguration;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.conf.EventProcessingOption;
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
import drools.cookbook.model.EmergencySignal;
import drools.cookbook.model.FlightControl;
import drools.cookbook.model.FlightStatus;

/**
 * 
 * @author Lucas Amador
 * 
 */
public class FlightControlTest {

    private static final int AIRPORT_AIR_SPACE_SCOPE = 50;
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
        KnowledgeBaseConfiguration config = KnowledgeBaseFactory.newKnowledgeBaseConfiguration();
        config.setOption(EventProcessingOption.STREAM);

        KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase(config);
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
    public void emergencySimulation() throws InterruptedException {

        FlightControl control = new FlightControl();
        control.setAirport("LAX");

        SessionPseudoClock clock = ksession.getSessionClock();

        ksession.setGlobal("control", control);

        FlighSimulation flightAA001 = new FlighSimulation("AA001", "San Francisco", "Los Angeles", 270);

        do {
            FlightStatus flightStatus = flightAA001.update();
            if (flightStatus.getDistance() <= AIRPORT_AIR_SPACE_SCOPE) {
                WorkingMemoryEntryPoint flightArrivalEntryPoint = ksession.getWorkingMemoryEntryPoint("flight-arrival");
                flightArrivalEntryPoint.insert(flightStatus);
                clock.advanceTime(7, TimeUnit.MINUTES);
                EmergencySignal emergencySignal = new EmergencySignal("AA001", 40);
                ksession.getWorkingMemoryEntryPoint("emergency-channel").insert(emergencySignal);
            } else {
                ksession.getWorkingMemoryEntryPoint("flight-control").insert(flightStatus);
                clock.advanceTime(5, TimeUnit.MINUTES);
            }
        } while (!flightAA001.isLanded());
    }

    @After
    public void stop() {
        ksession.halt();
        ksession.dispose();
    }

}
