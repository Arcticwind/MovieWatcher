package com.example.moviewatcher.webservices.omdb;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OmdbSearchResponse {

    @SerializedName("Search")
    @Expose
    private List<OmdbSearch> OmdbSearches = null;
    @SerializedName("totalResults")
    @Expose
    private String totalResults;
    @SerializedName("Response")
    @Expose
    private String response;

    public List<OmdbSearch> getOmdbSearches() {
        return OmdbSearches;
    }

    public void setOmdbSearches(List<OmdbSearch> OmdbSearches) {
        this.OmdbSearches = OmdbSearches;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

}