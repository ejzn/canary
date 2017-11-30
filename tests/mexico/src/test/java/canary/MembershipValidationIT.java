package canary;

import org.testng.annotations.Test;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.dsl.testng.TestNGCitrusTestDesigner;
import com.consol.citrus.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

/**
 * This is a test of the AS400 Membership Validation
 *
 * @author Stephen
 */
@Test
public class MembershipValidationIT extends TestNGCitrusTestDesigner {

      @Autowired
      private HttpClient hybrisClient;

      @CitrusTest(name = "MembershipValidationTest.httpAction")
      public void httpAction() {
            http().client(hybrisClient)
                  .send()
                  .post("/services/rest/membership/validation")
                  .payload("<membership>" +
                              "<membershipnumber>123456789</membershipnumber>" +
                              "<dateofBirth>19910415</dateofBirth>" +
                              "<countryCode>MX</countryCode>" +
                        "</membership>")
                  .contentType("application/xml")
                  .accept("application/xml, */*");
      
            http().client(hybrisClient)
                  .receive()
                  .response(HttpStatus.OK);
      }
}