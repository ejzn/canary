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

public class MembershipValidationIT extends TestNGCitrusTestDesigner {

      @Autowired
      private HttpClient citrusSimulator;

      @Test(dataProvider = "mexicoDataProvider")
      @CitrusTest(name = "MembershipValidationTest.Mexico")
      @CitrusParameters( { "membershipNumber", "dateofBirth", "name" })
      public void Mexico(String membershipNumber, String dateofBirth, String name) {
            description("This is a test of the AS400 Membership Validation on QA3 Mexico.");
            author("Stephen Richter");

            variable("countryCode", "MX");

            http().client(citrusSimulator)
                  .send()
                  .post("/services/rest/membership/validation")
                  .payload(new ClassPathResource("test_data/membershipValidation_message.xml"))
                  .contentType("application/xml")
                  .accept("application/xml, */*");
      
            http().client(citrusSimulator)
                  .receive()
                  .response(HttpStatus.OK)
                  .contentType("application/xml");
      }

      @DataProvider(name = "mexicoDataProvider")
      public Object[][] mexicoDataProvider() {
          return new Object[][] {
              new Object[] { "900018501845", "19660210", "Francisco Javie Arce Anguiano", },
              new Object[] { "900014634968", "19660401", "Laura Vieyra Abdala", },
              new Object[] { "8510607055", "19630521", "Laura Andrade AcuÑa", },
              new Object[] { "900009422964", "19770810", "Claudia Arce", },
              new Object[] { "900021153935", "19400701", "Bailey Zeita", },
              new Object[] { "900000876013", "19330216", "Amada Barojas Ulloa", },
              new Object[] { "900000557999", "19801122", "Cecilia Martinez Oria", },
              new Object[] { "900007929704", "19731004", "Francisco Bernal Garcia", },
              new Object[] { "900000215318", "19580727", "Norma Angelica CarreÑo Gonzalez", },
              new Object[] { "900021623829", "19700428", "Guillermo H Gonzalez Gonzalez", }
          };
      }
}