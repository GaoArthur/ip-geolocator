package geolocator;

import java.net.URL;

import java.io.IOException;

import com.google.gson.Gson;

import com.google.common.net.UrlEscapers;

import org.apache.commons.io.IOUtils;

/**
 * Class of obtaining geolocation information about an IP address or host name.
 * The class uses the <a href="https://ip-api.com/">IP-API.com</a>web service.
 */

public class GeoLocator {

    /**
     * URI of the geolocation service.
     */

    public static final String GEOLOCATOR_SERVICE_URI = "http://ip-api.com/json/";

    private static Gson GSON = new Gson();

    public GeoLocator() {}

    /**
     * Returns geolocation information about the computer running the JVM.
     *
     * @return an object wrapping the geolocation information returned
     * @throws IOException if any I/O error occurs
     */

    public GeoLocation getGeoLocation() throws IOException {
        return getGeoLocation(null);
    }

    /**
     * Return geolocation information about the IP address or host name specified.
     * If the parameter is {@code null}, the method returns geolocation information
     * about the computer running the JVM.
     *
     * @param ipAddrOrHost the IP address or host name, may be {@code null}
     * @return an object wrapping the geolocation information returned
     * @throws IOException if any I/O error occurs
     */

    public GeoLocation getGeoLocation(String ipAddrOrHost) throws IOException {
        URL url;
        if (ipAddrOrHost != null) {
            ipAddrOrHost = UrlEscapers.urlPathSegmentEscaper().escape(ipAddrOrHost);
            url = new URL(GEOLOCATOR_SERVICE_URI + ipAddrOrHost);
        } else {
            url = new URL(GEOLOCATOR_SERVICE_URI);
        }
        String s = IOUtils.toString(url, "UTF-8");
        return GSON.fromJson(s, GeoLocation.class);
    }

    public static void main(String[] args) throws IOException {
        try {
            String arg = args.length > 0 ? args[0] : null;
            System.out.println(new GeoLocator().getGeoLocation(arg));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
