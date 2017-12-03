package canary;

import org.testng.annotations.Test;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.dsl.testng.TestNGCitrusTestDesigner;
import com.consol.citrus.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.core.io.ClassPathResource;

@Test
public class MembershipValidationMX extends TestNGCitrusTestDesigner {

      @Autowired
      private HttpClient citrusSimulator;

      @CitrusTest(name = "MembershipValidationTest.httpAction")
      public void httpAction() {
            description("This is a test of the AS400 Membership Validation on QA3 Mexico.");
            author("Stephen Richter");

            http().client(citrusSimulator)
                  .send()
                  .post("/services/rest/membership/validation")
                  .payload(new ClassPathResource("test_data/bailey_zeita.xml"))
                  .contentType("application/xml")
                  .accept("application/xml, */*");
      
            http().client(citrusSimulator)
                  .receive()
                  .response(HttpStatus.OK)
                  .contentType("application/xml");
      }
}