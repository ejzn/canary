package com.consol.citrus.simulator.starter;

import com.consol.citrus.simulator.model.ScenarioParameter;
import com.consol.citrus.simulator.model.ScenarioParameterBuilder;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.simulator.scenario.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.*;

@Starter("DefaultTestStarter")
public class DefaultTestStarter extends AbstractScenarioStarter {

    @Autowired
    private HttpClient hybrisClient;

    @Override
    public void run(ScenarioDesigner scenario) {

      scenario.echo("Excuting a basic GET request");
        
      scenario.http()
        .client(hybrisClient)
        .send()
        .get("/services/rest");

      scenario.http()
        .client(hybrisClient)
        .receive()
        .response(HttpStatus.OK);
    }
}
