package com.consol.citrus.simulator.starter;

import com.consol.citrus.simulator.model.ScenarioParameter;
import com.consol.citrus.simulator.model.ScenarioParameterBuilder;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.simulator.scenario.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.*;

@Starter("MemberValidationStarter")
public class MemberValidationStarter extends AbstractScenarioStarter {

    @Autowired
    private HttpClient hybrisClient;

    @Override
    public void run(ScenarioDesigner scenario) {
        
        scenario.http()
                .client(hybrisClient)
                .send()
                .post("/services/rest/membership/validation")
                .contentType("application/xml")
                .payload("<membership>" +
                            "<membershipnumber>${membershipnumber}</membershipnumber>" +
                            "<dateofBirth>${dateofBirth}</dateofBirth>" +
                            "<countryCode>${countryCode}</countryCode>" +
                        "</membership>");
    }

    @Override
    public List<ScenarioParameter> getScenarioParameters() {
        List<ScenarioParameter> scenarioParameter = new ArrayList<>();

        // Mandatory Fields
        scenarioParameter.add(new ScenarioParameterBuilder()
            .name("membershipnumber")
            .label("Membership Number")
            .required()
            .textbox()
            .build());
        scenarioParameter.add(new ScenarioParameterBuilder()
            .name("dateofBirth")
            .label("Date of Birth")
            .required()
            .textbox()
            .build());
        scenarioParameter.add(new ScenarioParameterBuilder()
            .name("countryCode")
            .label("Country Code")
            .required()
            .textbox()
            .build());

        return scenarioParameter;
    }
}
