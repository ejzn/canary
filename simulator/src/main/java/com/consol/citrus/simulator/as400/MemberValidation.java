package com.consol.citrus.simulator.as400;

import com.consol.citrus.simulator.scenario.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Scenario("MEMBER_VALIDATION")
@RequestMapping(value = "/services/rest/membership/validation", method = RequestMethod.POST)
public class MemberValidation extends AbstractSimulatorScenario {

    @Override
    public void run(ScenarioDesigner scenario) {
        scenario.description("Membership Validation on the AS400");

        scenario
            .http()
            .receive()
            .post()
            .payload("<membership>" +
                        "<membershipnumber>@assertThat(anyOf(equalTo(123456789), equalTo(999999999), equalTo(888888888), equalTo(777777777), equalTo(666666666), equalTo(555555555), equalTo(900021153935)))@</membershipnumber>" +
                        "<dateofBirth>@ignore@</dateofBirth>" +
                        "<name>@ignore@</name>" +
                        "<countryCode>@ignore@</countryCode>" +
                     "</membership>")
            .extractFromPayload("/membership/membershipnumber", "membershipNumber")
            .extractFromPayload("/membership/dateofBirth", "dateofBirth");
        
        scenario
            .http()
            .send()
            .response(HttpStatus.OK)
            .payload("<Members>" +
            			"<member>" +
                        	"<MembershipNumber>${membershipNumber}</MembershipNumber>" +
                        	"<ValidationStatus>true</ValidationStatus>" +
                        	"<MembershipActivationStatus>ACTIVE</MembershipActivationStatus>" +
                        	"<MemberType>GOLD</MemberType>" +
                        	"<MemberCardType>PRIMARY</MemberCardType>" +
                        	"<ExpiryDate>20190109</ExpiryDate>" +
                        	"<DateOfBirth>${dateofBirth}</DateOfBirth>" +
                        	"<Gender>MALE</Gender>" +
                        "</member>" +
                     "</Members>")
            .contentType("application/xml");
    }
}