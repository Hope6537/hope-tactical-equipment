package org.hope6537.domain;

import java.util.HashMap;
import java.util.Map;

public class AlternateIds {

    private String imdb;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The imdb
     */
    public String getImdb() {
        return imdb;
    }

    /**
     * @param imdb The imdb
     */
    public void setImdb(String imdb) {
        this.imdb = imdb;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
