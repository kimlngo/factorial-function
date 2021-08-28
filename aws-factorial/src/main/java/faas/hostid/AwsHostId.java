package faas.hostid;

import com.amazonaws.services.lambda.runtime.Context;

public class AwsHostId {
    public static String getHostId(Context context) {
        String logStreamName = context.getLogStreamName();
        return logStreamName.split("]")[1];
    }
}
