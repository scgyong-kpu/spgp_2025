package io.quicktype;

import com.fasterxml.jackson.annotation.*;

public class Wangtile {
    private long tileid;
    private long[] wangid;

    @JsonProperty("tileid")
    public long getTileid() { return tileid; }
    @JsonProperty("tileid")
    public void setTileid(long value) { this.tileid = value; }

    @JsonProperty("wangid")
    public long[] getWangid() { return wangid; }
    @JsonProperty("wangid")
    public void setWangid(long[] value) { this.wangid = value; }
}
