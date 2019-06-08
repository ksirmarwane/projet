package com.pluginjira.oauth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;



public class PropertiesClient {
	
	public static final String CONSUMER_KEY = "consumer_key";
    public static final String PRIVATE_KEY = "private_key";
    public static final String REQUEST_TOKEN = "request_token";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String SECRET = "secret";
    public static final String JIRA_HOME = "jira_home";
  
    
    public final String fileUrl;
    public final String propFileName = "config.properties";
    
    //constructeur
    public PropertiesClient() throws Exception {
        fileUrl = "jcms/plugins/Jiraplugin/" + propFileName;
    }

    //create Map of default properties values
    public final static Map<String, String> DEFAULT_PROPERTY_VALUES = ImmutableMap.<String, String>builder()
            .put(JIRA_HOME, "http://devtools.spectrumgroupe.fr:10009")
            .put(CONSUMER_KEY, "OauthKey")
            .put(PRIVATE_KEY, "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAPWnUF4DxuhbxOrs4vaDrNgF1woUDHY/iJfLnKIE4/Y9yH3idMyVS7Q/VV7nqQAdFupnMQudDRYLONBwnErM2W+TbUbiK4Nr3Qplxy1/PjEcgy3EV4w7UStu2TdZqVo5dOE/1D2mASU35UKghIopDzTdjqEPboM5Bep97BEW9dOdAgMBAAECgYEAmtIQPLB0tAziVo1lnafvT1ae6KMqF3yDQef5l8MtSQw3R99pbWbKh5dC3sVyJbgvsRvINWGN1c7Mx9GxLxNcFVHggWc3meYi/UjfyZ9xeIgL/6dNdYMn2ne9+gYTzEDozoQu+bePhTZCXM2v/MnvgEZvAebEJtYaO+22w+mi2AECQQD8/MMrQ4DKEuHy2syD0AFVrFAKcyk+me7tpPona6Iom4u2VsjSEtx+xKpThosGc9j4gZNuLyGUb+ai3b3+H5zdAkEA+JQxwSnzE6che7fBU+r9F8f+LtP+huvoqVB9oTj+frHegWxpoYvUwsx7cVtY3pnoR1SAR+j4291z0NM1/S1FwQJBAMM+BxmShQhuvjYAqiloFD+cNkF6UvBfOEfrNKvLo2AXeyGDpISLzeN/v7uqcAzQxzgyOCem9BFssuAqpYBDwXkCQBRiIopSZEpSRB6e/M1i0YonriIs5AwwQA3SOdsEj2nYMASw3SCQFMw8dErhULJAMJnYxgQfiHiO+jY/e4fdNIECQAOYrNwfbAZDS8X5Uj9yIbAXQUBzihnbkLKnWJljVgwj+ybUPngS+Rd2JVklztFT+U4UPiDA0icF7RL+zqwtb68=")
            .put(REQUEST_TOKEN, "GIwkKJ5UDBGBELAbrVgtHk4R1cWp0xPw")
            .put(SECRET,"AM6Nxr")
            .put(ACCESS_TOKEN, "HRn5pP1Kq6t4fbjRnNjcJoDMIFOlbf00")
            .build();


    // lire properties from File
    public Properties tryGetProperties() throws IOException {
        InputStream inputStream = new FileInputStream(new File(fileUrl));
        System.out.println(fileUrl);
        Properties prop = new Properties();
       
        prop.load(inputStream);
        System.out.println(prop);
        return prop;
    }
    
    
    
    //Lire properties from file si nn return default properties
    public Map<String, String> getPropertiesOrDefaults() {
        try {
        	System.out.println("reading properties");
            Map<String, String> map = toMap(tryGetProperties());
            map.putAll(Maps.difference(map, DEFAULT_PROPERTY_VALUES).entriesOnlyOnRight());
            return map;
        } catch (FileNotFoundException e) {
        	System.out.println("reading properties failed !!!");
            tryCreateDefaultFile();
            return new HashMap<>(DEFAULT_PROPERTY_VALUES);
        } catch (IOException e) {
            return new HashMap<>(DEFAULT_PROPERTY_VALUES);
        }
    }

    
    //Propertiers To Map 
    public Map<String, String> toMap(Properties properties) {
        return properties.entrySet().stream()
                .filter(entry -> entry.getValue() != null)
                .collect(Collectors.toMap(o -> o.getKey().toString(), t -> t.getValue().toString()));
    }
    //Map To Propertiers
    public Properties toProperties(Map<String, String> propertiesMap) {
        Properties properties = new Properties();
        propertiesMap.entrySet()
                .stream()
                .forEach(entry -> properties.put(entry.getKey(), entry.getValue()));
        return properties;
    }

    // sauvegarder properties to config.prop
    public void savePropertiesToFile(Map<String, String> properties) {
        OutputStream outputStream = null;
        File file = new File(fileUrl);

        try {
            outputStream = new FileOutputStream(file);
            Properties p = toProperties(properties);
            p.store(outputStream, null);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            closeQuietly(outputStream);
        }
    }

    public void tryCreateDefaultFile() {
        System.out.println("Creating default properties file: " + propFileName);
        tryCreateFile().ifPresent(file -> savePropertiesToFile(DEFAULT_PROPERTY_VALUES));
    }

    public Optional<File> tryCreateFile() {
        try {
            File file = new File(fileUrl);
            file.createNewFile();
            return Optional.of(file);
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    public void closeQuietly(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException e) {
            // ignored
        }
    }
    
}
