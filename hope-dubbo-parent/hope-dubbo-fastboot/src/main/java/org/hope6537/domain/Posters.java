package org.hope6537.domain;

import java.util.HashMap;
import java.util.Map;

public class Posters {

    private String thumbnail;
    private String profile;
    private String detailed;
    private String original;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The thumbnail
     */
    public String getThumbnail() {
        return thumbnail;
    }

    /**
     * @param thumbnail The thumbnail
     */
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    /**
     * @return The profile
     */
    public String getProfile() {
        return profile;
    }

    /**
     * @param profile The profile
     */
    public void setProfile(String profile) {
        this.profile = profile;
    }

    /**
     * @return The detailed
     */
    public String getDetailed() {
        return detailed;
    }

    /**
     * @param detailed The detailed
     */
    public void setDetailed(String detailed) {
        this.detailed = detailed;
    }

    /**
     * @return The original
     */
    public String getOriginal() {
        return original;
    }

    /**
     * @param original The original
     */
    public void setOriginal(String original) {
        this.original = original;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
