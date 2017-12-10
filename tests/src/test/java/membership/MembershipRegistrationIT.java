package canary;

import org.testng.annotations.*;
import com.consol.citrus.annotations.*;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.actions.AbstractTestAction;
import com.consol.citrus.dsl.testng.TestNGCitrusTestDesigner;
import com.consol.citrus.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.core.io.ClassPathResource;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import canary.CSVUtil;

import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.io.IOException;

/**
 * This is a Java DSL Citrus Integrtion Test to test new Membership Registration on the AS400.
 *
 * @author Stephen Richter
 */
@Test
public class MembershipRegistrationIT extends TestNGCitrusTestDesigner {

      @Autowired
      private HttpClient citrusSimulator;

      @Parameters("context")
      @CitrusTest(name = "MembershipRegistrationTest.getMembershipNumber")
      public void getMembershipNumber(@Optional @CitrusResource TestContext context) {
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
                              "<nextMembershipNumber>@variable('membershipNumber')@</nextMembershipNumber>" +
                           "</Member>");

            action(new AbstractTestAction() {
                public void doExecute(TestContext context) {
                  exportCSV(context.getVariable("membershipNumber"));
                }
            });
            
      }

      private static final DateFormat sdf = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");

      // Export returned values to CSV to send to the AS400
      private void exportCSV(String membershipNumber) {

        try {
          Date date = new Date();
          
          String csvFile = System.getProperty("user.dir")+File.separator+MembershipRegistrationIT.class.getPackage().getName()+File.separator+"outgoing"+File.separator+ membershipNumber + "-" + sdf.format(date) + ".csv";
          FileWriter writer = new FileWriter(csvFile);
  
          CSVUtil.writeLine(writer, Arrays.asList("XCMBRN", "B", "C", "D"));
  
          // Write response to outgoing CSV file
          CSVUtil.writeLine(writer, Arrays.asList(membershipNumber, "test", "test", "test"));
  
          writer.flush();
          writer.close();
        } catch (IOException ioe) {
          echo("Exception thrown from MembershipRegistrationIT!");
          echo("Error: " + ioe); // TODO: Learn Exceptions and throw a proper Citrus error
        }
        
      }
}