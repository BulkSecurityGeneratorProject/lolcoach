package com.github.dudaaslaci.lolcoach.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Summoner.
 */
@Entity
@Table(name = "SUMMONER")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Summoner implements Serializable {

    @Id
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "region", nullable = false)
    private String region;

//    private List<MatchSummary> matchHistory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Summoner summoner = (Summoner) o;

        if ( ! Objects.equals(id, summoner.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Summoner{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", region='" + region + "'" +
                '}';
    }

//    public List<MatchSummary> getMatchHistory() {
//        return matchHistory;
//    }
//
//    public void setMatchHistory(List<MatchSummary> matchHistory) {
//        this.matchHistory = matchHistory;
//    }
}
