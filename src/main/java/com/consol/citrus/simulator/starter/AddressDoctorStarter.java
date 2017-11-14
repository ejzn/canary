package com.consol.citrus.simulator.starter;

import com.consol.citrus.simulator.model.ScenarioParameter;
import com.consol.citrus.simulator.model.ScenarioParameterBuilder;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.simulator.scenario.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.*;


@Starter("AddressDoctorStarter")
public class AddressDoctorStarter extends AbstractScenarioStarter {

    @Autowired
    private HttpClient addressDoctorURL;
	
	@Override
    public void run(ScenarioDesigner scenario) {
        scenario
            .http()
            .client(addressDoctorURL)
            .send()
            .post()
            .contentType("application/json")
            .payload("{ " +
                    "login: \"165317\"," +
                "    password: \"%Gz@$5ua\", " +
                "    parameters: { " + 
                "       ProcessMode: \"${mode}\", " +
                "       ValidationParameters : { RangesToExpand: \"ALL\", MaxResultCount: \"30\"} " +
                "    }," +
                "    addresses: " +
                "       [{ " +
                "         FormattedAddress: [\"${street}\", \"${city}\", \"${postcode}\"], " +
                "         Country: [\"${country}\"] " +
                "      }] " +
                "}");

         scenario
            .http().client(addressDoctorURL)
            .receive()
            .response(HttpStatus.OK);
    }
    
    @Override
    public Collection<ScenarioParameter> getScenarioParameters() {
        List<ScenarioParameter> scenarioParameter = new ArrayList<>();

        //login=<userid>&password=<password>&street=15501 Weston
        //Pkwy&Locality=Cary&province=NC&postalcode=27513&country=usa
        // greeting (text area)
        scenarioParameter.add(new ScenarioParameterBuilder()
                .name("mode")
                .label("mode")
                .textbox()
                .build());
        scenarioParameter.add(new ScenarioParameterBuilder()
                .name("street")
                .label("street")
                .textbox()
                .build());
        scenarioParameter.add(new ScenarioParameterBuilder()
                .name("city")
                .label("city")
                .textbox()
                .build());
        scenarioParameter.add(new ScenarioParameterBuilder()
                .name("postcode")
                .label("postcode")
                .textbox()
                .build());
        scenarioParameter.add(new ScenarioParameterBuilder()
                .name("colonia")
                .label("colonia")
                .textbox()
                .build());
        scenarioParameter.add(new ScenarioParameterBuilder()
                .name("country")
                .label("country")
                .required()
                .textbox()
                .build());

        return scenarioParameter;
    }

}
