package canary;

import org.testng.annotations.Test;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.dsl.testng.TestNGCitrusTestDesigner;
import com.consol.citrus.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

/**
 * This is a test of the AS400 Membership Validation on QA3 Mexico
 * @author Stephen Richter
 */
@Test
public class MembershipValidationMX extends TestNGCitrusTestDesigner {

      @Autowired
      private HttpClient membershipValidationMX;

      @CitrusTest(name = "MembershipValidationTest.httpAction")
      public void httpAction() {
            http().client(membershipValidationMX)
                  .send()
                  .post("/services/rest/membership/validation")
                  .payload("<membership>" +
                              "<membershipnumber>900018501845</membershipnumber>" +
                              "<dateofBirth>19660210</dateofBirth>" +
                              "<countryCode>MX</countryCode>" +
                        "</membership>")
                  .contentType("application/xml")
                  .accept("application/xml, */*");
      
            http().client(membershipValidationMX)
                  .receive()
                  .response(HttpStatus.OK);
      }
}