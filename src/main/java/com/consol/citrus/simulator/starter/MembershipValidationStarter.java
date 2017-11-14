package com.consol.citrus.simulator.starter;

import com.consol.citrus.simulator.model.ScenarioParameter;
import com.consol.citrus.simulator.model.ScenarioParameterBuilder;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.simulator.scenario.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.*;


@Starter("MembershipValidationStarter")
public class MembershipValidationStarter extends AbstractScenarioStarter {

    @Autowired
    private HttpClient membershipURL;
	
	@Override
    public void run(ScenarioDesigner scenario) {
        scenario
            .http()
            .client(membershipURL)
            .send()
            .post()
            .contentType("application/xml")
            .payload("${xmlrequest}");

         scenario
            .http().client(membershipURL)
            .receive()
            .response(HttpStatus.OK);
    }
    
    @Override
    public Collection<ScenarioParameter> getScenarioParameters() {
        List<ScenarioParameter> scenarioParameter = new ArrayList<>();

        scenarioParameter.add(new ScenarioParameterBuilder()
                .name("xmlrequest")
                .label("Request")
                .required()
                .textarea()
                .value("<membership><membershipnumber>900014634968</membershipnumber><dateofBirth>19660401</dateofBirth><countryCode>MX</countryCode>")
                .build());
        

        return scenarioParameter;
    }

}
