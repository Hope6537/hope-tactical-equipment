package org.hope6537.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Comic {

    private String id;
    private String title;
    private Integer year;
    private String mpaaRating;
    private Integer runtime;
    private String criticsConsensus;
    private ReleaseDates releaseDates;
    private Ratings ratings;
    private String synopsis;
    private Posters posters;
    private List<AbridgedCast> abridgedCast = new ArrayList<AbridgedCast>();
    private AlternateIds alternateIds;
    private Links links;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return The year
     */
    public Integer getYear() {
        return year;
    }

    /**
     * @param year The year
     */
    public void setYear(Integer year) {
        this.year = year;
    }

    /**
     * @return The mpaaRating
     */
    public String getMpaaRating() {
        return mpaaRating;
    }

    /**
     * @param mpaaRating The mpaa_rating
     */
    public void setMpaaRating(String mpaaRating) {
        this.mpaaRating = mpaaRating;
    }

    /**
     * @return The runtime
     */
    public Integer getRuntime() {
        return runtime;
    }

    /**
     * @param runtime The runtime
     */
    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    /**
     * @return The criticsConsensus
     */
    public String getCriticsConsensus() {
        return criticsConsensus;
    }

    /**
     * @param criticsConsensus The critics_consensus
     */
    public void setCriticsConsensus(String criticsConsensus) {
        this.criticsConsensus = criticsConsensus;
    }

    /**
     * @return The releaseDates
     */
    public ReleaseDates getReleaseDates() {
        return releaseDates;
    }

    /**
     * @param releaseDates The release_dates
     */
    public void setReleaseDates(ReleaseDates releaseDates) {
        this.releaseDates = releaseDates;
    }

    /**
     * @return The ratings
     */
    public Ratings getRatings() {
        return ratings;
    }

    /**
     * @param ratings The ratings
     */
    public void setRatings(Ratings ratings) {
        this.ratings = ratings;
    }

    /**
     * @return The synopsis
     */
    public String getSynopsis() {
        return synopsis;
    }

    /**
     * @param synopsis The synopsis
     */
    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    /**
     * @return The posters
     */
    public Posters getPosters() {
        return posters;
    }

    /**
     * @param posters The posters
     */
    public void setPosters(Posters posters) {
        this.posters = posters;
    }

    /**
     * @return The abridgedCast
     */
    public List<AbridgedCast> getAbridgedCast() {
        return abridgedCast;
    }

    /**
     * @param abridgedCast The abridged_cast
     */
    public void setAbridgedCast(List<AbridgedCast> abridgedCast) {
        this.abridgedCast = abridgedCast;
    }

    /**
     * @return The alternateIds
     */
    public AlternateIds getAlternateIds() {
        return alternateIds;
    }

    /**
     * @param alternateIds The alternate_ids
     */
    public void setAlternateIds(AlternateIds alternateIds) {
        this.alternateIds = alternateIds;
    }

    /**
     * @return The links
     */
    public Links getLinks() {
        return links;
    }

    /**
     * @param links The links
     */
    public void setLinks(Links links) {
        this.links = links;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
