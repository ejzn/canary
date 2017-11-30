package com.consol.citrus.simulator.as400;

import com.consol.citrus.simulator.scenario.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Scenario("MemberRenew")
@RequestMapping(value = "/services/rest/membership/renew", method = RequestMethod.POST)
public class MemberRenew extends AbstractSimulatorScenario {

    @Override
    public void run(ScenarioDesigner scenario) {

        scenario
            .http()
            .receive()
            .post()
            .payload("<membershipRenewalQuote>" +
                        "<membershipnumber>@ignore@</membershipnumber>" +
                        "<countryCode>@ignore@</countryCode>" +
                     "</membershipRenewalQuote>")
            .extractFromPayload("/membership/membershipnumber", "membershipNumber")
            .extractFromPayload("/membership/countryCode", "countryCode");

        scenario
            .http()
            .send()
            .response(HttpStatus.OK)
            .payload("<membershipRenewalQuoteResponse>" +
                        "<responseCode>0</responseCode>" +
                        "<priceQuote>100</priceQuote>" +
                        "<memberTypes>" +
                           "<memberType>" +
                            "    <memberCardType>PRIMARY</memberCardType>" +
                            "    <firstName>John</firstName>" +
                            "    <lastName>Doe</lastName>" +
                            "    <unit>1</unit>" +
                           "     <price>50</price>" +
                            "    <item>PrimaryItemNumber</item>" +
                           " </memberType>" +
                           " <memberType>" +
                            "    <memberCardType>ADDON</memberCardType>" +
                            "    <firstName>Marcus</firstName>" +
                            "    <lastName>Aerelius</lastName>" +
                            "   <unit>1</unit>" +
                            "    <price>25</price>" +
                            "    <item>AddonItemNumber</item>" +
                          " </memberType>" +
                           " <memberType>" +
                               " <memberCardType>ADDON</memberCardType>" +
                               " <firstName>Samantha</firstName>" +
                               " <lastName>Smith</lastName>" +
                               " <unit>1</unit>" +
                               " <price>25</price>" +
                               " <item>AddonItemNumber</item>" +
                           " </memberType>" +
                        "</memberTypes>" +
                     "</membershipRenewalQuoteResponse>");
    }
}