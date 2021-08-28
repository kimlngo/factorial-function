package faas.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.commons.io.FileUtils;

public class FunctionHostId {
    public static String getHostId() throws IOException {
        String path = "/tmp/host.txt";
        
        File hostId = new File(path);
        
        if(hostId == null || !hostId.exists()) {
            System.out.println("FaaS instance is freshly initialized");
            
            synchronized (FunctionHostId.class) {

                if (hostId == null || !hostId.exists()) {
                    try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "UTF-8"))) {
                        String instanceId = String.format("%06d", (int) (Math.random() * 1000000));
                        System.out.println("HostId generated: " + instanceId);

                        writer.write(instanceId);
                        System.out.println("HostId writes to file ok");
                        return instanceId;
                    }
                } else {
                    return FileUtils.readFileToString(new File(path), "UTF-8");
                }
            }
            
        } else if (hostId.isFile()) {
            try {
                String instanceId = FileUtils.readFileToString(hostId, "UTF-8");
                System.out.println("Loaded HostId: " + instanceId);
                return instanceId;
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Exception while reading hostId, message: " + e.getMessage());
                throw e;
            }
        } else {
            return "HostIdNotFound";
        }
    }
}
