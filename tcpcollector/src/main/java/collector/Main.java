package collector;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.DataLogger;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class Main {


    //This must be inside UCY's network
    private static final String HOST = "http://172.20.28.218";
    private static final String COMMAND = "/?command=dataquery";
    private static final String DATALOGGER = "&uri=dl:ucy2_DEM4BIPV_1min";
    private static final String FORMAT = "&format=json";
    private static final String MODE = "&mode=most-recent&p1=1";


    public static void main(String[] args) {

        String dlUrl = HOST+COMMAND+DATALOGGER+FORMAT+MODE;

        try {

            //Apache HTTP Client Stuff
            CloseableHttpClient client = HttpClients.createDefault();

            //Prepare the get request
            HttpGet httpGet = new HttpGet(dlUrl);

            //Execute Get request
            CloseableHttpResponse response = client.execute(httpGet);

            //Get response body as httpentty and convert to string
            HttpEntity entity = response.getEntity();
            String stResponse = EntityUtils.toString(entity);

            //Jackson stuff to deserialize the json string into java class Datalogger
            ObjectMapper mapper = new ObjectMapper();
            DataLogger dLogger = mapper.readValue(stResponse, DataLogger.class);

            //Do stuff...

            for(int i=0; i<dLogger.getHead().getFields().size(); i++) {

                System.out.println("(Metric, Value): ("+dLogger.getHead().getFields().get(i)+", "+ dLogger.getData().get(0).getVals().get(i)+")");
            }

            System.out.println(dLogger.getData().get(0).getVals().size());

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
