package com.consol.citrus.simulator.as400;

import com.consol.citrus.simulator.scenario.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Scenario("MEMBERSHIP_NUMBER")
@RequestMapping(value = "/services/rest/membershipnumber/korea", method = RequestMethod.POST)
public class MembershipNumber extends AbstractSimulatorScenario {

    @Override
    public void run(ScenarioDesigner scenario) {
        scenario.description("Membership Number Service on the AS400");

        scenario
            .http()
            .receive()
            .post()
            .payload("<nextMembershipNumber>" +
                        "<countryCode>KR</countryCode>" +
                        "<memberType>@ignore</memberType>" +
                        "<memberCardType>@ignore</memberCardType>" +
                        "<primaryMemberId>@ignore</primaryMemberId>" +
                        "<referenceId>@ignore</referenceId>" + 
                     "</nextMembershipNumber>");
        
        scenario
            .http()
            .send()
            .response(HttpStatus.OK)
            .contentType("application/xml")
            .payload("<Member>" +
                        "<responseCode>0</responseCode>" +
                        "<nextMembershipNumber>222222222</nextMembershipNumber>" +
                     "</Member>");
    }
}