package collector;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.DataLogger;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {


    //This must be inside UCY's network
    private static final String HOST = "http://172.20.28.218";
    private static final String COMMAND = "/?command=dataquery";
    private static final String DATALOGGER = "&uri=dl:ucy2_DEM4BIPV_1min";
    private static final String FORMAT = "&format=json";
    private static final String MODE = "&mode=most-recent&p1=1";

    //InfluxDB statics
    private static final String IFLUXDB = "http://localhost:8086";
    private static final String DATABASE = "mymetrics";

    public static void main(String[] args) {


        String dlUrl = HOST+COMMAND+DATALOGGER+FORMAT+MODE;

        //Apache HTTP Client Stuff
        CloseableHttpClient client = HttpClients.createDefault();

        //Prepare the get request
        HttpGet httpGet = new HttpGet(dlUrl);

        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

        service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

                try {


                    CloseableHttpResponse response = client.execute(httpGet);

                    //Get response body as httpentty and convert to string
                    HttpEntity entity = response.getEntity();
                    String stResponse = EntityUtils.toString(entity);

                    //Jackson stuff to deserialize the json string into java class Datalogger
                    ObjectMapper mapper = new ObjectMapper();
                    DataLogger dLogger = mapper.readValue(stResponse, DataLogger.class);


                    //Connect to influxdb and use the database
                    InfluxDB influxDB = InfluxDBFactory.connect(IFLUXDB);
                    influxDB.setDatabase(DATABASE);

                    Point point = Point.measurement("temperature")
                            .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                            .addField("PTemp_C_Avg", dLogger.getData().get(0).getVals().get(1))
                            .build();

                    System.out.println("PTemp_C_Avg: "+ dLogger.getData().get(0).getVals().get(1));
                    influxDB.write(point);

                    //Check influxdb connection
           /* Pong pong = influxDB.ping();
            System.out.println(pong.getVersion());
*/

                    //Do stuff...
                   /* for(int i=0; i<dLogger.getHead().getFields().size(); i++) {

                        System.out.println("(Metric, Value): ("+dLogger.getHead().getFields().get(i)+", "+ dLogger.getData().get(0).getVals().get(i)+")");
                    }

                    System.out.println(dLogger.getData().get(0).getVals().size());*/

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 3, TimeUnit.SECONDS);
    }



}
