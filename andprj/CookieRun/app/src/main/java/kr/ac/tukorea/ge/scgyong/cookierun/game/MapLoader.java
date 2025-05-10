package kr.ac.tukorea.ge.scgyong.cookierun.game;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.util.JsonReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import kr.ac.tukorea.ge.scgyong.cookierun.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.util.Gauge;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class MapLoader implements IGameObject {
    private final MainScene scene;
    private final Random random = new Random();
    private float x;
    private int index;
    public MapLoader(MainScene mainScene, int stage) {
        this.scene = mainScene;
        loadStage(GameView.view.getContext(), stage);
    }
    private int stage_width, page_width;
    private ArrayList<String> lines = new ArrayList<>();
    private static final int STAGE_HEIGHT = 9;

    private void loadStage(Context context, int stage) {
        AssetManager assets = context.getAssets();
        try {
            String file = String.format("stage_%02d.txt", stage);
            InputStream is = assets.open(file);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);

            lines.clear();
            page_width = 0;
            while (true) {
                String line = reader.readLine();
                if (line == null) break;
                if (page_width == 0) {
                    page_width = line.indexOf('|');
                }
                lines.add(line);
            }

            int pages = lines.size() / STAGE_HEIGHT;
            int lastCol = lines.get(lines.size() - 1).length();
            stage_width = (pages - 1) * page_width + lastCol;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void update() {
        // x 는 화면의 어디에까지 만들었나를 기억한다. SPEED 가 음수이므로 시간에 따라 감소한다
        x += MapObject.SPEED * GameView.frameTime;
        while (x < Metrics.width) {
            createColumn();
            index++;
            x += 100f;
        }
    }
    private void createColumn() {
        for (int row = 0; row < STAGE_HEIGHT; row++) {
            char tile = getAt(index, row); // 알아내서
            float y = 100 * row;
            createObject(tile, x, y); // 생성한다
        }
    }
    protected interface MapObjectCreator {
        MapObject get(char tile, float left, float top);
    }
    protected static MapObjectCreator[] mapCreators = {
            JellyItem::get, Floor::get, ObstacleFactory::get,
    };
    private void createObject(char tile, float left, float top) {
        for (MapObjectCreator creator: mapCreators) {
            MapObject mapObject = creator.get(tile, left, top);
            if (mapObject != null) {
                scene.add(mapObject);
                return;
            }
        }
    }

    private char getAt(int col, int row) {
        if (col >= stage_width) return 0; // Stage Ends
//        int idx = row * STAGE_WIDTH + col;
//        if (idx >= STAGES[0].length) return 0;
//        return STAGES[0][idx]; // STAGES[0] 이 1번째 스테이지 이다
        try {
            int lineIndex = col / page_width * STAGE_HEIGHT + row; // 텍스트파일의 몇번째 라인에서 가져와야 하나
            String line = lines.get(lineIndex);
            return line.charAt(col % page_width); // 고른 문자열에서 몇번째 글자인가
        } catch (Exception e) {
            return 0; // 계산이 잘못된 경우에는 아무것도 없다고 리턴한다
        }
    }
    Gauge gauge = new Gauge(0.025f, R.color.mapGaugeFg, R.color.mapGaugeBg);
    @Override
    public void draw(Canvas canvas) {
        gauge.draw(canvas, 200, 100, 1200, (float)index / stage_width);
    }
}
