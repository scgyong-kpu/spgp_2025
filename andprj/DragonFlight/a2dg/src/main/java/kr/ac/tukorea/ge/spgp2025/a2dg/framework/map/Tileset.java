package kr.ac.tukorea.ge.spgp2025.a2dg.framework.map;

import android.graphics.Rect;

import com.fasterxml.jackson.annotation.*;

public class Tileset {
    private long columns;
    private long firstgid;
    private String image;
    private long imageheight;
    private long imagewidth;
    private long margin;
    private String name;
    private long spacing;
    private long tilecount;
    private long tileheight;
    private long tilewidth;

    @JsonProperty("columns")
    public long getColumns() { return columns; }
    @JsonProperty("columns")
    public void setColumns(long value) { this.columns = value; }

    @JsonProperty("firstgid")
    public long getFirstgid() { return firstgid; }
    @JsonProperty("firstgid")
    public void setFirstgid(long value) { this.firstgid = value; }

    @JsonProperty("image")
    public String getImage() { return image; }
    @JsonProperty("image")
    public void setImage(String value) { this.image = value; }

    @JsonProperty("imageheight")
    public long getImageheight() { return imageheight; }
    @JsonProperty("imageheight")
    public void setImageheight(long value) { this.imageheight = value; }

    @JsonProperty("imagewidth")
    public long getImagewidth() { return imagewidth; }
    @JsonProperty("imagewidth")
    public void setImagewidth(long value) { this.imagewidth = value; }

    @JsonProperty("margin")
    public long getMargin() { return margin; }
    @JsonProperty("margin")
    public void setMargin(long value) { this.margin = value; }

    @JsonProperty("name")
    public String getName() { return name; }
    @JsonProperty("name")
    public void setName(String value) { this.name = value; }

    @JsonProperty("spacing")
    public long getSpacing() { return spacing; }
    @JsonProperty("spacing")
    public void setSpacing(long value) { this.spacing = value; }

    @JsonProperty("tilecount")
    public long getTilecount() { return tilecount; }
    @JsonProperty("tilecount")
    public void setTilecount(long value) { this.tilecount = value; }

    @JsonProperty("tileheight")
    public long getTileheight() { return tileheight; }
    @JsonProperty("tileheight")
    public void setTileheight(long value) { this.tileheight = value; }

    @JsonProperty("tilewidth")
    public long getTilewidth() { return tilewidth; }
    @JsonProperty("tilewidth")
    public void setTilewidth(long value) { this.tilewidth = value; }

    public void getRect(Rect rect, int tileNo) {
        int x = (int) ((tileNo - 1) % columns);
        int y = (int) ((tileNo - 1) / columns);
        rect.left = (int) (x * (tilewidth + spacing) + margin);
        rect.top = (int) (y * (tileheight + spacing) + margin);
        rect.right = (int) (rect.left + tilewidth);
        rect.bottom = (int) (rect.top + tileheight);
    }
}
