package kr.ac.tukorea.ge.spgp2025.a2dg.framework.map;

import com.fasterxml.jackson.annotation.*;

public class MapLayer {
    private long[] data;
    private long height;
    private long id;
    private String name;
    private long opacity;
    private String type;
    private boolean visible;
    private long width;
    private long x;
    private long y;

    @JsonProperty("data")
    public long[] getData() { return data; }
    @JsonProperty("data")
    public void setData(long[] value) { this.data = value; }

    @JsonProperty("height")
    public long getHeight() { return height; }
    @JsonProperty("height")
    public void setHeight(long value) { this.height = value; }

    @JsonProperty("id")
    public long getID() { return id; }
    @JsonProperty("id")
    public void setID(long value) { this.id = value; }

    @JsonProperty("name")
    public String getName() { return name; }
    @JsonProperty("name")
    public void setName(String value) { this.name = value; }

    @JsonProperty("opacity")
    public long getOpacity() { return opacity; }
    @JsonProperty("opacity")
    public void setOpacity(long value) { this.opacity = value; }

    @JsonProperty("type")
    public String getType() { return type; }
    @JsonProperty("type")
    public void setType(String value) { this.type = value; }

    @JsonProperty("visible")
    public boolean getVisible() { return visible; }
    @JsonProperty("visible")
    public void setVisible(boolean value) { this.visible = value; }

    @JsonProperty("width")
    public long getWidth() { return width; }
    @JsonProperty("width")
    public void setWidth(long value) { this.width = value; }

    @JsonProperty("x")
    public long getX() { return x; }
    @JsonProperty("x")
    public void setX(long value) { this.x = value; }

    @JsonProperty("y")
    public long getY() { return y; }
    @JsonProperty("y")
    public void setY(long value) { this.y = value; }

    public int tileAt(int x, int y) {
        if (x >= width) return -1;
        if (y >= height) return -1;
        return (int) data[(int) (y * width + x)];
    }
}
