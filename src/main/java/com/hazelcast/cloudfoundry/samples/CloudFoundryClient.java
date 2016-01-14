package com.hazelcast.cloudfoundry.samples;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.core.HazelcastInstance;
import org.json.JSONObject;

public class CloudFoundryClient {

    public CloudFoundryClient() {
        String sysEnv = System.getenv("VCAP_SERVICES");
        String url = parseAndGetURL(sysEnv);

        ClientConfig config = new ClientConfig();
        ClientNetworkConfig networkConfig = config.getNetworkConfig();
        networkConfig.addAddress(url).setSmartRouting(true);
        config.setNetworkConfig(networkConfig);
        HazelcastInstance client = HazelcastClient.newHazelcastClient(config);
    }

    private String parseAndGetURL(String envVariable) {
        StringBuilder url = new StringBuilder();
        JSONObject jsonObject = new JSONObject(envVariable);
        url.append(jsonObject.getString("host"));
        url.append(":");
        url.append(jsonObject.getString("port"));
        return url.toString();
    }

    public static void main(String args[]) {
        new CloudFoundryClient();
    }
}
