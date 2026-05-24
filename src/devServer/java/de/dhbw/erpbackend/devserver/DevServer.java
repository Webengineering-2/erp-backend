package de.dhbw.erpbackend.devserver;

import org.apache.catalina.startup.Tomcat;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class DevServer {

    public static void main(String[] args) throws Exception {
        int port = Integer.parseInt(System.getProperty("server.port", "8080"));
        String warPath = System.getProperty("war.path");
        if (warPath == null) {
            throw new IllegalStateException("System property 'war.path' not set");
        }
        File war = new File(warPath);
        if (!war.isFile()) {
            throw new IllegalStateException("war file not found: " + war.getAbsolutePath());
        }

        Path baseDir = Files.createTempDirectory("erp-backend-tomcat-");
        Files.createDirectories(baseDir.resolve("webapps"));

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(port);
        tomcat.setBaseDir(baseDir.toString());
        tomcat.getConnector();
        tomcat.addWebapp("", war.getAbsolutePath());

        tomcat.start();
        System.out.println("ERP backend running at http://localhost:" + port + "/");
        tomcat.getServer().await();
    }
}
