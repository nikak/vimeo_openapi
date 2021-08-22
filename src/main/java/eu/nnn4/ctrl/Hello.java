package eu.nnn4.ctrl;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.Operation;

import eu.nnn4.config.MyEnvValues;
import eu.nnn4.pojo.MyListOfValues;
import eu.nnn4.pojo.MyToken;
import eu.nnn4.pojo.MyValue;

@RequestScoped
@Path("/hello")
public class Hello {

    @Inject
    @ConfigProperty(name="username", defaultValue="admin")
    private String username;

    // Example injection of the default configuration singleton instance which provides lower level programmatic API to retireve configuration values
    @Inject
    Config config;

    // Example showing reading an optional config property dynamically using Provider
    @Inject
    @ConfigProperty(name = "application.dynamicOptionalProperty")
    Provider<Optional<String>> dynamicOptionalProperty;

    // Example showing reading a config property dynamically using Provider
    @Inject
    @ConfigProperty(name = "application.dynamicProperty", defaultValue = "Default Value")
    Provider<String> dynamicProperty;

    // Example injection of a request scoped bean that contains injected values. Configuration is updated for each request
    @Inject
    MyEnvValues bean;

    @Inject
    @ConfigProperty(name = "pojo.value")
    MyValue pojo;
    
    @GET
    @Operation(operationId = "hello world")
    @Produces(MediaType.TEXT_PLAIN)
    public String helloWorld() {
        return "Hello World!";
    }

    @Path("/s0")
    @GET
    public Response hello() {
        Map<String, Object> configProperties = new HashMap<>();

        configProperties.put("username", username);
        configProperties.put("password", config.getValue("password", String.class));
        configProperties.put("microprofile-apis", config.getValue("microprofile.apis", String[].class));
        
        return Response.ok(configProperties).build();
    }

    @Produces(MediaType.APPLICATION_JSON)
    @Path("/s1")
    @GET
    public Response getPropertiesRetrievedProgrammatically() throws MalformedURLException, UnknownHostException {
        Map<String, Object> configProperties = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd");

        configProperties.put("which.properties",
                "PropertiesRetrievedProgrammatically - Properties retrieved from an injected Config instance programmatically. These will change immediately if you override them in Payara during runtime.");

        configProperties.put("default.property",
                config.getOptionalValue("default.property", String.class).orElse("Default value"));

        configProperties.put("file.property", config.getValue("file.property", String.class));

        configProperties.put("date.property", config.getValue("date.property", LocalDate.class).format(formatter));

        configProperties.put("HOME",
                config.getOptionalValue("HOME", String.class).orElse("HOME environment variable not set"));

        configProperties.put("java.home",
                config.getOptionalValue("java.home", String.class).orElse("java.home environment variable not set"));

        configProperties.put("application.property", config.getValue("application.property", String.class));

        configProperties.put("application.url",
                config.getOptionalValue("application.url", URL.class).orElse(new URL("http://localhost")));

        configProperties.put("application.address", config.getOptionalValue("application.address", InetAddress.class)
                .orElse(InetAddress.getByAddress(new byte[] { 10, 0, 0, 1 })).getHostAddress());

        configProperties.put("application.numberOfWorkers",
                config.getOptionalValue("application.numberOfWorkers", Integer.class).orElse(10));

        configProperties.put("echo.test", config.getValue("echo.test", String.class));

        configProperties.put("echowhatever", config.getValue("echowhatever", String.class));

        return Response.ok(configProperties).build();
    }

    @Inject
    @ConfigProperty(name = "my.app.token")
    private MyToken token;

    @Produces(MediaType.APPLICATION_JSON)
    @Path("/token")
    @GET
    public Response getMyToken() {
        Map<String, Object> configProperties = new HashMap<>();

        configProperties.put("which.properties",
                "Token in config - Injected token using a custom converter.");

        return Response.ok(token).build();
    }
    

    /* An example of injecting a parameterized type using a custom converter 
     * (ConfigListConverter.java registered via a service locator file)
     */
    @Inject
    @ConfigProperty(name = "myList")
    List<String> myList;

    @Produces(MediaType.APPLICATION_JSON)
    @Path("/s2")
    @GET
    public Response getListOfProperties() {
        Map<String, Object> configProperties = new HashMap<>();

        configProperties.put("which.properties",
                "ListProperties in config - Injected?? list using a custom converter.");
        int i=0;
        for (String string : myList) {
            configProperties.put("count-"+i, string);
            i++;
        }
        return Response.ok(configProperties).build();
    }

    @Inject
    @ConfigProperty(name = "myList2")
    MyListOfValues values;

    @Produces(MediaType.APPLICATION_JSON)
    @Path("/s2-2")
    @GET
    public Response getListOfValues() {
        Map<String, Object> configProperties = new HashMap<>();

        configProperties.put("which.properties",
                "ListProperties in config - Injected?? list using a custom converter.");
        int i=0;
        for (String string : values.getValues()) {
            configProperties.put("count-"+i, string);
            i++;
        }
        return Response.ok(configProperties).build();
    }

    @Produces(MediaType.APPLICATION_JSON)
    @Path("/s3")
    @GET
    public Response printPayaraProperties() {
        Map<String, Object> configProperties = new HashMap<>();

        configProperties.put("which.properties",
                "PayaraProperties - Standard Payara config properties. These will be provided automatically by the server via the config API.");

        configProperties.put("payara.instance.http.port", config.getValue("payara.instance.http.port", Integer.class));
        configProperties.put("payara.instance.http.address", config.getValue("payara.instance.http.address", InetAddress.class));
        configProperties.put("payara.instance.https.port", config.getValue("payara.instance.https.port", Integer.class));
        configProperties.put("payara.instance.https.address", config.getValue("payara.instance.https.address", InetAddress.class));
        configProperties.put("payara.instance.root", config.getValue("payara.instance.root", String.class));
        configProperties.put("payara.instance.type", config.getValue("payara.instance.type", String.class));
        configProperties.put("payara.instance.name", config.getValue("payara.instance.name", String.class));
        configProperties.put("payara.domain.name", config.getValue("payara.domain.name", String.class));
        configProperties.put("payara.domain.installroot", config.getValue("payara.domain.installroot", String.class));
        configProperties.put("payara.config.dir", config.getValue("payara.config.dir", String.class));
        configProperties.put("payara.instance.config.name", config.getValue("payara.instance.config.name", String.class));
        configProperties.put("payara.instance.admin.host", config.getValue("payara.instance.admin.host", String.class));
        configProperties.put("payara.instance.admin.port", config.getValue("payara.instance.admin.port", Integer.class));
        configProperties.put("payara.instance.starttime", config.getValue("payara.instance.starttime", Long.class));

        return Response.ok(configProperties).build();
    }

    @Produces(MediaType.APPLICATION_JSON)
    @Path("/s4")
    @GET
    public Response getRequestScopedInjectedProperties() {
        Map<String, Object> configProperties = new HashMap<>();
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy MM dd");

        configProperties.put("which.properties", "RequestScopedInjectedProperties - Properties injected into a Request Scoped Bean.");
        configProperties.put("default.property",bean.getDefaultProperty());
        configProperties.put("file.property",bean.getFileProperty());
        configProperties.put("date.property", bean.getDate().format(formatter));
        configProperties.put("HOME", bean.getHome());
        configProperties.put("java.home",bean.getJavaHome());
        configProperties.put("application.property",bean.getApplicationProperty());
        configProperties.put("Test Pojo", pojo);
        
        return Response.ok(configProperties).build();
    }

    @Produces(MediaType.APPLICATION_JSON)
    @Path("/s5")
    @GET
    public Response getDynamicInjectedProperties() {
        Map<String, Object> configProperties = new HashMap<>();

        configProperties.put("which.properties", "DynamicInjectedProperties - These will change immediately if you override them in Payara during runtime");
        configProperties.put("application.dynamicProperty", dynamicProperty.get());
        configProperties.put("application.dynamicOptionalProperty", dynamicOptionalProperty.get().orElse("Not defined"));
        
        return Response.ok(configProperties).build();
    }

}
