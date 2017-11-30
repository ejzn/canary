package com.consol.citrus.simulator.as400;

import com.consol.citrus.simulator.scenario.*;
import com.consol.citrus.http.message.HttpMessage;
import org.springframework.http.HttpStatus;

@Scenario("DEFAULT_SCENARIO")
public class DefaultScenario extends AbstractSimulatorScenario {

    @Override
    public void run(ScenarioDesigner designer) {
        designer.echo("Default scenario executed!");

        designer.send()
                .message(new HttpMessage("There is no service defined on this port")
                .status(HttpStatus.OK));
    }
}