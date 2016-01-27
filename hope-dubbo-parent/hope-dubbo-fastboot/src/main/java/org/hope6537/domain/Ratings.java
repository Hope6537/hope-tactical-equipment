package org.hope6537.domain;

import java.util.HashMap;
import java.util.Map;

public class Ratings {

    private String criticsRating;
    private Integer criticsScore;
    private String audienceRating;
    private Integer audienceScore;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The criticsRating
     */
    public String getCriticsRating() {
        return criticsRating;
    }

    /**
     * @param criticsRating The critics_rating
     */
    public void setCriticsRating(String criticsRating) {
        this.criticsRating = criticsRating;
    }

    /**
     * @return The criticsScore
     */
    public Integer getCriticsScore() {
        return criticsScore;
    }

    /**
     * @param criticsScore The critics_score
     */
    public void setCriticsScore(Integer criticsScore) {
        this.criticsScore = criticsScore;
    }

    /**
     * @return The audienceRating
     */
    public String getAudienceRating() {
        return audienceRating;
    }

    /**
     * @param audienceRating The audience_rating
     */
    public void setAudienceRating(String audienceRating) {
        this.audienceRating = audienceRating;
    }

    /**
     * @return The audienceScore
     */
    public Integer getAudienceScore() {
        return audienceScore;
    }

    /**
     * @param audienceScore The audience_score
     */
    public void setAudienceScore(Integer audienceScore) {
        this.audienceScore = audienceScore;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
