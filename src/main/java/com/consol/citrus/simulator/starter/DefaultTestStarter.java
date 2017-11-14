package com.consol.citrus.simulator.starter;

import com.consol.citrus.http.client.HttpClient;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import com.consol.citrus.simulator.model.ScenarioParameter;
import com.consol.citrus.simulator.model.ScenarioParameterBuilder;
import com.consol.citrus.simulator.scenario.*;

import java.util.ArrayList;
import java.util.List;

@Starter("DefaultTestStarter")
public class DefaultTestStarter extends AbstractScenarioStarter {

    // @Autowired
    // private HttpClient hybrisClient;

    @Override
    public void run(ScenarioDesigner scenario) {

      scenario.echo("test action executed!");
        
      scenario.http()
        .client("http://localhost:8080")
        .send()
        .get("/services/rest");

      scenario.http()
        .client("http://localhost:8080")
        .receive()
        .response(HttpStatus.OK);
    }
}
