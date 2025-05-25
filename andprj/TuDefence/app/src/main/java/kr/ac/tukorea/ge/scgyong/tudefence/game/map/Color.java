package kr.ac.tukorea.ge.scgyong.tudefence.game.map;

import com.fasterxml.jackson.annotation.*;

public class Color {
    private String color;
    private String name;
    private long probability;
    private long tile;

    @JsonProperty("color")
    public String getColor() { return color; }
    @JsonProperty("color")
    public void setColor(String value) { this.color = value; }

    @JsonProperty("name")
    public String getName() { return name; }
    @JsonProperty("name")
    public void setName(String value) { this.name = value; }

    @JsonProperty("probability")
    public long getProbability() { return probability; }
    @JsonProperty("probability")
    public void setProbability(long value) { this.probability = value; }

    @JsonProperty("tile")
    public long getTile() { return tile; }
    @JsonProperty("tile")
    public void setTile(long value) { this.tile = value; }
}
