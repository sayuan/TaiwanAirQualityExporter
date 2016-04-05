package net.sayuan.apexporter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import io.prometheus.client.Gauge;
import io.prometheus.client.exporter.MetricsServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.joda.time.DateTime;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AirQualityExporter {

    public static final String API_URL = "http://opendata2.epa.gov.tw/AQX.json";

    private static final Logger logger = Logger.getLogger( AirQualityExporter.class.getName() );

    private static final Gauge co = Gauge.build()
            .name("airquality_co").help("CO")
            .labelNames("county", "site_name").register();
    private static final Gauge fpmi = Gauge.build()
            .name("airquality_fpmi").help("FPMI")
            .labelNames("county", "site_name").register();
    private static final Gauge no = Gauge.build()
            .name("airquality_no").help("NO")
            .labelNames("county", "site_name").register();
    private static final Gauge no2 = Gauge.build()
            .name("airquality_no2").help("NO2")
            .labelNames("county", "site_name").register();
    private static final Gauge nox = Gauge.build()
            .name("airquality_nox").help("NOx")
            .labelNames("county", "site_name").register();
    private static final Gauge o3 = Gauge.build()
            .name("airquality_o3").help("O3")
            .labelNames("county", "site_name").register();
    private static final Gauge pm10 = Gauge.build()
            .name("airquality_pm10").help("PM10")
            .labelNames("county", "site_name").register();
    private static final Gauge pm2_5 = Gauge.build()
            .name("airquality_pm2_5").help("PM2.5")
            .labelNames("county", "site_name").register();
    private static final Gauge psi = Gauge.build()
            .name("airquality_psi").help("PSI")
            .labelNames("county", "site_name").register();
    private static final Gauge so2 = Gauge.build()
            .name("airquality_so2").help("SO2")
            .labelNames("county", "site_name").register();
    private static final Gauge windDirec = Gauge.build()
            .name("airquality_wind_direction").help("Wind Direction")
            .labelNames("county", "site_name").register();
    private static final Gauge windSpeed = Gauge.build()
            .name("airquality_wind_speed").help("Wind Speed")
            .labelNames("county", "site_name").register();

    private ObjectMapper mapper;
    private DateTime lastSuccess;

    public AirQualityExporter() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());
    }

    private void updateData() {
        logger.log(Level.INFO, "Updating air quality data");

        ClientConfig clientConfig = new DefaultClientConfig();
        Client client = Client.create(clientConfig);

        // the `Content-Type` is `binary/octet-stream`
        WebResource resource = client.resource(API_URL);
        String result = resource.get(String.class);

        try {
            SiteAirQuality[] sites = mapper.readValue(result, SiteAirQuality[].class);
            for (SiteAirQuality e: sites) {
                co.labels(e.getCounty(), e.getSiteName()).set(e.getCO());
                fpmi.labels(e.getCounty(), e.getSiteName()).set(e.getFPMI());
                no.labels(e.getCounty(), e.getSiteName()).set(e.getNO());
                no2.labels(e.getCounty(), e.getSiteName()).set(e.getNO2());
                nox.labels(e.getCounty(), e.getSiteName()).set(e.getNOx());
                o3.labels(e.getCounty(), e.getSiteName()).set(e.getO3());
                pm10.labels(e.getCounty(), e.getSiteName()).set(e.getPM10());
                pm2_5.labels(e.getCounty(), e.getSiteName()).set(e.getPM2_5());
                psi.labels(e.getCounty(), e.getSiteName()).set(e.getPSI());
                so2.labels(e.getCounty(), e.getSiteName()).set(e.getSO2());
                windDirec.labels(e.getCounty(), e.getSiteName()).set(e.getWindDirec());
                windSpeed.labels(e.getCounty(), e.getSiteName()).set(e.getWindSpeed());
            }
            lastSuccess = DateTime.now();
        } catch (IOException e) {
            DateTime now = DateTime.now();
            if (now.minusHours(1).isAfter(lastSuccess)) {
                logger.log(Level.SEVERE, "Update air quality data failed for 1 hour. Reset all metrics", e);
                co.clear();
                fpmi.clear();
                no.clear();
                no2.clear();
                nox.clear();
                o3.clear();
                pm10.clear();
                pm2_5.clear();
                psi.clear();
                so2.clear();
                windDirec.clear();
                windSpeed.clear();
            } else {
                logger.log(Level.WARNING, "Update air quality data failed", e);
            }
        }
    }

    public void start() throws Exception {
        Server server = new Server(9991);
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");
        server.setHandler(context);
        context.addServlet(new ServletHolder(
                new MetricsServlet()), "/metrics");

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(()-> updateData(), 0, 5, TimeUnit.MINUTES);

        server.start();
        server.join();
    }

    public static void main(String[] args) throws Exception {
        new AirQualityExporter().start();
    }
}
