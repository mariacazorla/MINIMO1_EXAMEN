package edu.upc.dsa;

import io.swagger.jaxrs.config.BeanConfig;
import org.apache.log4j.Logger;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

public class Main {
    // Logger para mostrar información en consola
    final static Logger logger = Logger.getLogger(Main.class);

    // URI base donde se desplegará la aplicación REST
    public static final String BASE_URI = "http://localhost:8080/dsaApp/";

    // Método que configura e inicia el servidor HTTP Grizzly
    public static HttpServer startServer() {
        // Configuración del paquete donde están los servicios REST
        final ResourceConfig rc = new ResourceConfig().packages("edu.upc.dsa.services");

        // Registro de clases de Swagger para la documentación
        rc.register(io.swagger.jaxrs.listing.ApiListingResource.class);
        rc.register(io.swagger.jaxrs.listing.SwaggerSerializers.class);

        // Configuración de Swagger para la API REST
        BeanConfig config = new BeanConfig();
        config.setTitle("API Gestió de Vols");
        config.setVersion("1.0.0");
        config.setBasePath("/dsaApp");
        config.setResourcePackage("edu.upc.dsa.services");
        config.setScan(true);

        // Crear e iniciar el servidor con la configuración especificada
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    // Método main que arranca el servidor y espera entrada del usuario para detenerlo
    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();

        // Servir archivos estáticos desde la carpeta public/
        StaticHttpHandler staticHandler = new StaticHttpHandler("./public/");
        server.getServerConfiguration().addHttpHandler(staticHandler, "/");

        // Mostrar en consola la URL del backend con Swagger
        logger.info("Servidor iniciat a: " + BASE_URI);
        logger.info("Swagger UI a: " + BASE_URI + "swagger");

        // Espera hasta que se pulse ENTER para detener el servidor
        System.in.read();
        server.shutdownNow();
    }
}