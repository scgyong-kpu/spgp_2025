package kr.ac.tukorea.ge.spgp2025.a2dg.framework.map;

import com.fasterxml.jackson.annotation.*;

public class TiledMap {
    private long compressionlevel;
    private long height;
    private boolean infinite;
    private MapLayer[] layers;
    private long nextlayerid;
    private long nextobjectid;
    private String orientation;
    private String renderorder;
    private String tiledversion;
    private long tileheight;
    private Tileset[] tilesets;
    private long tilewidth;
    private String type;
    private String version;
    private long width;

    @JsonProperty("compressionlevel")
    public long getCompressionlevel() { return compressionlevel; }
    @JsonProperty("compressionlevel")
    public void setCompressionlevel(long value) { this.compressionlevel = value; }

    @JsonProperty("height")
    public long getHeight() { return height; }
    @JsonProperty("height")
    public void setHeight(long value) { this.height = value; }

    @JsonProperty("infinite")
    public boolean getInfinite() { return infinite; }
    @JsonProperty("infinite")
    public void setInfinite(boolean value) { this.infinite = value; }

    @JsonProperty("layers")
    public MapLayer[] getLayers() { return layers; }
    @JsonProperty("layers")
    public void setLayers(MapLayer[] value) { this.layers = value; }

    @JsonProperty("nextlayerid")
    public long getNextlayerid() { return nextlayerid; }
    @JsonProperty("nextlayerid")
    public void setNextlayerid(long value) { this.nextlayerid = value; }

    @JsonProperty("nextobjectid")
    public long getNextobjectid() { return nextobjectid; }
    @JsonProperty("nextobjectid")
    public void setNextobjectid(long value) { this.nextobjectid = value; }

    @JsonProperty("orientation")
    public String getOrientation() { return orientation; }
    @JsonProperty("orientation")
    public void setOrientation(String value) { this.orientation = value; }

    @JsonProperty("renderorder")
    public String getRenderorder() { return renderorder; }
    @JsonProperty("renderorder")
    public void setRenderorder(String value) { this.renderorder = value; }

    @JsonProperty("tiledversion")
    public String getTiledversion() { return tiledversion; }
    @JsonProperty("tiledversion")
    public void setTiledversion(String value) { this.tiledversion = value; }

    @JsonProperty("tileheight")
    public long getTileheight() { return tileheight; }
    @JsonProperty("tileheight")
    public void setTileheight(long value) { this.tileheight = value; }

    @JsonProperty("tilesets")
    public Tileset[] getTilesets() { return tilesets; }
    @JsonProperty("tilesets")
    public void setTilesets(Tileset[] value) { this.tilesets = value; }

    @JsonProperty("tilewidth")
    public long getTilewidth() { return tilewidth; }
    @JsonProperty("tilewidth")
    public void setTilewidth(long value) { this.tilewidth = value; }

    @JsonProperty("type")
    public String getType() { return type; }
    @JsonProperty("type")
    public void setType(String value) { this.type = value; }

    @JsonProperty("version")
    public String getVersion() { return version; }
    @JsonProperty("version")
    public void setVersion(String value) { this.version = value; }

    @JsonProperty("width")
    public long getWidth() { return width; }
    @JsonProperty("width")
    public void setWidth(long value) { this.width = value; }
}
