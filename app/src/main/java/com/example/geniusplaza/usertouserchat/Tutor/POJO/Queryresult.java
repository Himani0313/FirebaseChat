package com.example.geniusplaza.usertouserchat.Tutor.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by geniusplaza on 7/17/17.
 */

public class Queryresult {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("numpods")
    @Expose
    private Integer numpods;
    @SerializedName("datatypes")
    @Expose
    private String datatypes;
    @SerializedName("timedout")
    @Expose
    private String timedout;
    @SerializedName("timedoutpods")
    @Expose
    private String timedoutpods;
    @SerializedName("timing")
    @Expose
    private Double timing;
    @SerializedName("parsetiming")
    @Expose
    private Double parsetiming;
    @SerializedName("parsetimedout")
    @Expose
    private Boolean parsetimedout;
    @SerializedName("recalculate")
    @Expose
    private String recalculate;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("host")
    @Expose
    private String host;
    @SerializedName("server")
    @Expose
    private String server;
    @SerializedName("related")
    @Expose
    private String related;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("pods")
    @Expose
    private List<Pod> pods = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public Integer getNumpods() {
        return numpods;
    }

    public void setNumpods(Integer numpods) {
        this.numpods = numpods;
    }

    public String getDatatypes() {
        return datatypes;
    }

    public void setDatatypes(String datatypes) {
        this.datatypes = datatypes;
    }

    public String getTimedout() {
        return timedout;
    }

    public void setTimedout(String timedout) {
        this.timedout = timedout;
    }

    public String getTimedoutpods() {
        return timedoutpods;
    }

    public void setTimedoutpods(String timedoutpods) {
        this.timedoutpods = timedoutpods;
    }

    public Double getTiming() {
        return timing;
    }

    public void setTiming(Double timing) {
        this.timing = timing;
    }

    public Double getParsetiming() {
        return parsetiming;
    }

    public void setParsetiming(Double parsetiming) {
        this.parsetiming = parsetiming;
    }

    public Boolean getParsetimedout() {
        return parsetimedout;
    }

    public void setParsetimedout(Boolean parsetimedout) {
        this.parsetimedout = parsetimedout;
    }

    public String getRecalculate() {
        return recalculate;
    }

    public void setRecalculate(String recalculate) {
        this.recalculate = recalculate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getRelated() {
        return related;
    }

    public void setRelated(String related) {
        this.related = related;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<Pod> getPods() {
        return pods;
    }

    public void setPods(List<Pod> pods) {
        this.pods = pods;
    }

}