package collector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class IPMICollector {

    //Pre-request ipmitool must be install in the box

    // IPMI Unix Command  that gets the content from all sensors  -- (REMOTE)
    private static final String REMOTE_IPMI = "sudo ipmitool -I lanplus -H 10.16.27.53 -U MAAS_LINC -P 217=R[tVprQ] -L OPERATOR  sdr list full";
    // IPMI Unix Command  that gets the content from all sensors  -- (LOCAL)
    private static final String LOCAL_IPMI = "sudo ipmitool sdr list full";

    public static void main(String[] args) {
        String cmdIPMI = REMOTE_IPMI;

        String k = null, v = null;
        String[] tokens = new String[3];

        Map<String, String> ipmiOut = new HashMap<String, String>();
        Process proc = null;


        try {
            proc = Runtime.getRuntime().exec(cmdIPMI);
            BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));

            String cmdOut = null;


            // Do something with the output of the command execution
            while ((cmdOut = br.readLine())!=null){
                tokens = cmdOut.split("\\|");
                k = tokens[0];
                v = tokens[1].split("\\s+")[1];
                ipmiOut.put(k,v);
                System.out.println(ipmiOut);


            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
