package test;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.ResponseSpecBuilder;

import com.jayway.restassured.config.RestAssuredConfig;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.ResponseSpecification;
import org.apache.commons.collections.map.HashedMap;
import org.apache.tika.io.IOUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import com.jayway.restassured.config.SSLConfig;
import org.junit.rules.ExpectedException;

import static com.jayway.restassured.http.ContentType.HTML;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.config.RestAssuredConfig.config;
import static com.jayway.restassured.config.SSLConfig.sslConfig;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.matchers.JUnitMatchers.containsString;


/**
 * Created by teixeirg on 30/07/2015.
 */
public class restTest {

    private static final String TOKEN = "1Vhpb%2BI8EP4rKPsR5XAukqiw4igsWyiUs%2BXLK8d2IJQctc3VX78OFLbQY7v7qtIuipR4bM888" +
            "3g8M%2BLi6yZa5FaEsjCJixJQNClHYpTgMJ4WpeGgLjvS19IFg9FC98qMEcrFwpzYFDNvJy1KSxp7CWQh82IYEeZx5PXL7ZanK5" +
            "oHD1ukXLNWlP7DmmsA1yi4gFiubduO72rAxibQTEAKWkCwhl1iuNBwdFAIAgtbxPcBAMhBvuE6Qg1jS9KMGYcxL0q6BixZK8iG" +
            "NgCOB2zPcpSC6U6k3Ojgk575tAe8YUVpxnnqqep6vVbWhpLQqaprGlBv260%2BmpEISgdvd4Zorp7QCPL3vcwkIZaD3VKPx" +
            "DzkW6mUWWLClKUrlgJsUwGG6zmmaaiMJTIljF%2Boz02VLjDz%2BuE0hnxJyRNk%2FBZkTdVcVazBLJx%2BkY57CW7GQbIbV" +
            "mGcxCGCi%2FARZkfQJnyW4Fx5MU1oyGfRm1wALVMskw2SETDjL5J6Cu2Dik4QUgZlNoPgSVePBISKQCO5Ya9ZlL78%2F8DYqR" +
            "1QGLPsGNjp8PewknhFFklKsMwOLj%2FB%2FrjCV1gsXRDkNWO0WLJwRa6zKEohIuzppAn6uKpcl5Ig3LRCJiJzwzJ06nN4Z8M9" +
            "G7VwKmLuTw7v2cHtlYzgYklKndWN8zAA41ZbX11Sq8lcztmkvF1qSXEH4PnineB47PvhWcAeA2y%2FIxzdtQKU9x%2Fm3x2%2" +
            "FMb82r90E5r%2F7htlpVWwnarm2eTOaONUG6NQNtzJ%2BuLpqGcFtvvV4vyobjz0wrj%2FUr%2BNCyPpotLpebyoRalGztr1R" +
            "J1b9Rs%2FPq93FLLBpd8xwp76cdi%2FdwXI22PpaoxEPVvcUpvH6cUzLCb9y72q9Gpm283Q6nD0s3Oh2Ux2M2fxmxtYJa0%2" +
            "BiKr3rjysG3rIe1frU3k4229vN3TC9nMNKcLUYVdT27LtZqG8myaY1LHc7NcfqN1vjVRRNQC9K1Gi%2BvR41a%2BayEV2xBR2" +
            "19UHaNkn%2BMp%2BSzePkxtHS%2Bchp3Lm1eFwNex3U2NClthp3k3hM7tC3yvC%2B1UnvV42OVb65RA%2F52nAKrbSLGvq3yx" +
            "rR0YpY02LxSP0zrk9lhxTYX%2FpzgvhhmIVss%2FYHGTHNcjHjIjFKJYgtjRAby7YGDdm0HVd2A1eTTQhtQ7ehGZjWIS%2FuD" +
            "Z6hqSZxEGZqs0K0j%2Bb3saDI8wmkhErvaKpBDnPXCe%2FEHVoOOKHPags4ry09gsI0JFkBOiT5RSIS7SxhXMUJWkZibn8r3z" +
            "R4PnfAJtbgMFvAMjgVIigkL%2Bqc9RPLBzEfa%2FgSh9kF7ImLSUO0R3I6dSxcokwpYczJlO4QK7MUoVSJCX9RwI5bzwUnZtR" +
            "zD39a5rO4zyEnGXG53fDXJf5kt1DKyYa%2FJqsuRBciMk%2Fp3UYFeShbJ8Rd8VonFHdpwsXBELzLo2lCn7n7ivbXJk%2BFRw" +
            "%2BPKLkgx19y8vZMLrsD78d3SpMgXAihAJ%2B1PId3E0u73R%2B5r1DYy6SHG7ukofQCyy5VHFup8Ne9lBzuDhER0YCx0OPbl" +
            "GTlystiIp5KJROYruEUALAcn5iGbmrEtEzd9XXfxjqAAbQwsAs2CAzg%2B6SgBwXf1vygoAHdD3R8ZPcEYemF%2BF1WQ%2BwB" +
            "xVBsBSiiRVOAeMRb2z%2Fgb6fwd9Lpp9Bk%2FBM0adkPOJasm9CWDR8D2dcNXcaildRFeBm%2BDT87mnSg%2FxM0aTaSLQ2KG" +
            "DLFl4MwlB1MICYa0onpfz5N5t9O07B%2F2ftUGkxBw99%2FqX4jsahvFzz17M%2BF0g8%3D";


    private Map REQUESTHEADER =  new HashMap();


    @Rule
    public ExpectedException exception = ExpectedException.none();

    public static ResponseSpecification crsSpectation() {
        return new ResponseSpecBuilder().
                expectBody(containsString("An error occurred while processing your request.")).
                expectContentType(HTML).
                expectStatusCode(500).build();
    }
    @Before
    public void setUp() {
        // set default port for REST-assured
        RestAssured.port = 8443;
        SSLConfig ssl = new SSLConfig();
        ssl.allowAllHostnames();
        //RestAssured.config = RestAssured.config().sslConfig(sslConfig().allowAllHostnames());
        // set default URI for REST-assured.
        // In integration tests, this would most likely point to localhost.
        RestAssured.baseURI = "https://127.0.0.1/documents/srv/ccp/cms/api/web/v2";
        REQUESTHEADER.put("x-auth-token",TOKEN);
        REQUESTHEADER.put("Accept", "application/json");
        REQUESTHEADER.put("Version", "1");
        REQUESTHEADER.put("Content-type", "application/json");
        REQUESTHEADER.put("Referer", "http://localhost/ccpservices");
    }
    //Helper Methods



    @Test
    public void testAuthenticationWorking() {
        //accept HTTPS
        RestAssuredConfig conf = config().sslConfig(sslConfig().relaxedHTTPSValidation());

        Response  resp;
        String responseString;


        // "401 Unauthorized"
        resp = given()
                .header( "Accept", "application/json" )
                .config(conf)
                .expect()
                .statusCode(401)
                .when()
                .get("/folders/root/documents");
        responseString = resp.asString();
        System.out.println(responseString);

        resp.then()
                .body(containsString("This request requires HTTP authentication"));


        // Authorized
        Map parameters = new HashedMap();
        parameters.put("j_password","author1.2K14");
        parameters.put("j_username","author1");
        parameters.put("tenant","00000185-24a6-3bd1-b232-d7ff21f33b6a");
        resp =given()
                .parameters(parameters)
                .headers(REQUESTHEADER)
                .config(conf)
                .expect()
                .statusCode(200)
                .when()
                .get("/users/current");
        responseString = resp.asString();
        System.out.println( responseString );
        resp.then()
                .statusCode(200)
                .body(containsString("userId"));
    }

    @Test
    public void testGetDocumentsOnRootFolder() throws Exception{
        //accept HTTPS
        RestAssuredConfig conf = config().sslConfig( sslConfig().relaxedHTTPSValidation() );
        //Make a Map for request
        Response resp;
        //Get response String
        String responseString;
        try{
            resp = given()
                .headers( REQUESTHEADER )
                .config( conf )
           .when()
                .get( "/folders/root/documents" );
            responseString= resp.asString();
            resp.then()
                    .statusCode( 200 )
                    .contentType( ContentType.JSON )
                    .body(containsString( "ownerId" ));
            System.out.println( responseString );
        } finally {
            RestAssured.reset();
        }
    }


    @Test
    public void testFileUpload() throws IOException {
        //accept HTTPS
        RestAssuredConfig conf = config().sslConfig( sslConfig().relaxedHTTPSValidation());
        //Make a Map for request
        Response resp;
        //String to receive Response
        String responseString;

        Map parameters = new HashMap<String, String>();
        parameters.put("metadata", "{\"sps_product_t\" : \"ring\", \"sps_pages_m_t\": [\"10\", \"20\", \"30\", \"40\"], \"sps_color_t\" : \"white\"}");
        parameters.put( "name", "text.txt" );
        parameters.put("referer", "http://localhost/ccpservices");
        parameters.put( "x-auth-token", TOKEN );

        try{

            final byte[] bytes = IOUtils.toByteArray(getClass().getResourceAsStream("test.txt"));


            final File file = new File(getClass().getClassLoader()
                        .getResource("").getFile());

            assertNotNull(file);
            assertTrue(file.canRead());
            resp= given()
                    .formParams(parameters)
                    .multiPart("file", "file" , bytes )
                    .header("X-Auth-Token",TOKEN)
                    .config(conf)
                    .when()
                    .post("/documents/root");
            responseString= resp.asString();
            System.out.println( responseString );
            resp.then()
                    .statusCode( 201 )
                    .contentType( ContentType.JSON )
                    .body(containsString("modifiedBy"));


        } finally {
            RestAssured.reset();
        }
    }

    @Test
    public void testGetFile(){
        //accept HTTPS
        RestAssuredConfig conf = config().sslConfig( sslConfig().relaxedHTTPSValidation());

        final String DOCUMENTID = "48a47655-7bf3-4045-9c50-8b1232d0f018";
        try{
             given()
                .headers( REQUESTHEADER )
                .config( conf )
            .when()
                .get( "/documents/" + DOCUMENTID + "/content" )
            .then()
                .statusCode( 200 )
                .contentType( "application/octet-stream" );
        }finally {
            RestAssured.reset();
        }
    }

}
