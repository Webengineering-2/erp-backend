package de.dhbw.erpbackend.devserver;

import org.apache.tomee.embedded.Configuration;
import org.apache.tomee.embedded.Container;

import java.io.File;

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

        Configuration cfg = new Configuration()
                .http(port)
                .property("erpDataSource", "new://Resource?type=DataSource")
                .property("erpDataSource.JdbcDriver", "org.h2.Driver")
                .property("erpDataSource.JdbcUrl", "jdbc:h2:file:./data/erp;AUTO_SERVER=TRUE;DB_CLOSE_DELAY=-1")
                .property("erpDataSource.UserName", "sa")
                .property("erpDataSource.Password", "")
                .property("erpDataSource.JtaManaged", "true");

        Container container = new Container();
        container.setup(cfg);
        container.start();
        container.deploy("", war, true);

        System.out.println("ERP backend running at http://localhost:" + port + "/");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                container.stop();
            } catch (Exception ignored) {
            }
        }));
        container.await();
    }
}
