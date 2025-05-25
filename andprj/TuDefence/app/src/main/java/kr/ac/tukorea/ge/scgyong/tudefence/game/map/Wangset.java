package kr.ac.tukorea.ge.scgyong.tudefence.game.map;

import com.fasterxml.jackson.annotation.*;

public class Wangset {
    private Color[] colors;
    private String name;
    private long tile;
    private String type;
    private Wangtile[] wangtiles;

    @JsonProperty("colors")
    public Color[] getColors() { return colors; }
    @JsonProperty("colors")
    public void setColors(Color[] value) { this.colors = value; }

    @JsonProperty("name")
    public String getName() { return name; }
    @JsonProperty("name")
    public void setName(String value) { this.name = value; }

    @JsonProperty("tile")
    public long getTile() { return tile; }
    @JsonProperty("tile")
    public void setTile(long value) { this.tile = value; }

    @JsonProperty("type")
    public String getType() { return type; }
    @JsonProperty("type")
    public void setType(String value) { this.type = value; }

    @JsonProperty("wangtiles")
    public Wangtile[] getWangtiles() { return wangtiles; }
    @JsonProperty("wangtiles")
    public void setWangtiles(Wangtile[] value) { this.wangtiles = value; }
}
