package canary;

import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.dsl.testng.TestNGCitrusTestDesigner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.testng.CitrusParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.core.io.ClassPathResource;

/**
 * This is a Java DSL Citrus Integrtion Test to test new Membership Registration on the AS400.
 *
 * @author Stephen Richter
 */
@Test
public class MembershipRegistrationIT extends TestNGCitrusTestDesigner {

      @Autowired
      private HttpClient citrusSimulator;

      @CitrusTest(name = "MembershipRegistrationTest.getMembershipNumber")
      public void getMembershipNumber() {
            description("Get the next new Membership Number available from the AS400.");
            author("Stephen Richter");

            // Define our temp variables
            variable("countryCode", "MX");
            variable("memberType", "GOLD");
            variable("memberCardType", "PRIMARY");

            http().client(citrusSimulator)
                  .send()
                  .post("/services/rest/membership/nextMembershipNumber")
                  .payload(new ClassPathResource("test_data/membershipNumber_message.xml"))
                  .contentType("application/xml")
                  .accept("application/xml, */*");
      
            http().client(citrusSimulator)
                  .receive()
                  .response(HttpStatus.OK)
                  .contentType("application/xml")
                  .payload("<Member>" +
                              "<responseCode>0</responseCode>" +
                              "<nextMembershipNumber>@ignore@</nextMembershipNumber>" +
                           "</Member>")
                  .extractFromPayload("/Member/nextMembershipNumber", "membershipNumber");
            
            echo("Save membershipNumber as CSV file: ${membershipNumber}");
      }
}