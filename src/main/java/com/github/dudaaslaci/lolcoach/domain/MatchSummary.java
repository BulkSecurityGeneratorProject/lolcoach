package com.github.dudaaslaci.lolcoach.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A MatchSummary.
 */
@Entity
@Table(name = "MATCHSUMMARY")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MatchSummary implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "match_creation", nullable = false)
    private Long matchCreation;

    @NotNull
    @Column(name = "map_id", nullable = false)
    private Integer mapId;

    @NotNull
    @Column(name = "match_duration", nullable = false)
    private Long matchDuration;

    @NotNull
    @Column(name = "match_id", nullable = false)
    private Long matchId;

    @NotNull
    @Pattern(regexp = "(CLASSIC|ODIN|ARAM|TUTORIAL|ONEFORALL|ASCENSION|FIRSTBLOOD|KINGPORO)")
    @Column(name = "match_mode", nullable = false)
    private String matchMode;

    @NotNull
    @Pattern(regexp = "(CUSTOM_GAME|MATCHED_GAME|TUTORIAL_GAME)")
    @Column(name = "match_type", nullable = false)
    private String matchType;

    @NotNull
    @Column(name = "match_version", nullable = false)
    private String matchVersion;

    @NotNull
    @Column(name = "platform_id", nullable = false)
    private String platformId;

    @NotNull
    @Column(name = "region", nullable = false)
    private String region;

    @NotNull
    @Column(name = "season", nullable = false)
    private String season;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMatchCreation() {
        return matchCreation;
    }

    public void setMatchCreation(Long matchCreation) {
        this.matchCreation = matchCreation;
    }

    public Integer getMapId() {
        return mapId;
    }

    public void setMapId(Integer mapId) {
        this.mapId = mapId;
    }

    public Long getMatchDuration() {
        return matchDuration;
    }

    public void setMatchDuration(Long matchDuration) {
        this.matchDuration = matchDuration;
    }

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    public String getMatchMode() {
        return matchMode;
    }

    public void setMatchMode(String matchMode) {
        this.matchMode = matchMode;
    }

    public String getMatchType() {
        return matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

    public String getMatchVersion() {
        return matchVersion;
    }

    public void setMatchVersion(String matchVersion) {
        this.matchVersion = matchVersion;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MatchSummary matchSummary = (MatchSummary) o;

        if ( ! Objects.equals(id, matchSummary.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MatchSummary{" +
                "id=" + id +
                ", matchCreation='" + matchCreation + "'" +
                ", mapId='" + mapId + "'" +
                ", matchDuration='" + matchDuration + "'" +
                ", matchId='" + matchId + "'" +
                ", matchMode='" + matchMode + "'" +
                ", matchType='" + matchType + "'" +
                ", matchVersion='" + matchVersion + "'" +
                ", platformId='" + platformId + "'" +
                ", region='" + region + "'" +
                ", season='" + season + "'" +
                '}';
    }
}
