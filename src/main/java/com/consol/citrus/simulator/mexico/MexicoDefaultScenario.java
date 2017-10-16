package com.consol.citrus.simulator.mexico;

import com.consol.citrus.simulator.scenario.*;
import com.consol.citrus.http.message.HttpMessage;
import org.springframework.http.HttpStatus;

@Scenario("DEFAULT_SCENARIO")
public class MexicoDefaultScenario extends AbstractSimulatorScenario {

    @Override
    public void run(ScenarioDesigner designer) {
        designer.echo("Default scenario executed!");

        designer.send()
                .message(new HttpMessage("Welcome to the Citrus simulator")
                .status(HttpStatus.OK));
    }
}
