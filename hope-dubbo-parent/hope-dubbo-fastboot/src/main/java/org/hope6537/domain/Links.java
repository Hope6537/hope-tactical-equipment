package org.hope6537.domain;

import java.util.HashMap;
import java.util.Map;

public class Links {

    private String self;
    private String alternate;
    private String cast;
    private String reviews;
    private String similar;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The self
     */
    public String getSelf() {
        return self;
    }

    /**
     * @param self The self
     */
    public void setSelf(String self) {
        this.self = self;
    }

    /**
     * @return The alternate
     */
    public String getAlternate() {
        return alternate;
    }

    /**
     * @param alternate The alternate
     */
    public void setAlternate(String alternate) {
        this.alternate = alternate;
    }

    /**
     * @return The cast
     */
    public String getCast() {
        return cast;
    }

    /**
     * @param cast The cast
     */
    public void setCast(String cast) {
        this.cast = cast;
    }

    /**
     * @return The reviews
     */
    public String getReviews() {
        return reviews;
    }

    /**
     * @param reviews The reviews
     */
    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    /**
     * @return The similar
     */
    public String getSimilar() {
        return similar;
    }

    /**
     * @param similar The similar
     */
    public void setSimilar(String similar) {
        this.similar = similar;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
