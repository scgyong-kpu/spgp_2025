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


// 쿠키런 게임의 스테이지 	텍스트로 된 맵 파일맵 데이터를 읽고,
// 게임 오브젝트(장애물, 젤리, 발판 등)를 실시간으로 생성하는 역할을 합니다.
//즉, 스크롤되는 맵을 자동으로 구성해주는 핵심 컨트롤러
public class MapLoader implements IGameObject {
    private final MainScene scene;
    private final Random random = new Random();
    private float x;
    // x: 지금까지 생성된 맵의 오른쪽 끝 위치

    private int index;
    // index: 지금까지 몇 번째 열(column)을 생성했는지

    public MapLoader(MainScene mainScene, int stage) {
        this.scene = mainScene;
        loadStage(GameView.view.getContext(), stage);
    }
    private int stage_width, page_width;
    // page_width: 한 페이지의 가로 길이 (| 구분자로 판단)
    // stage_width: 전체 맵의 총 가로 길이

    private ArrayList<String> lines = new ArrayList<>();
    // lines: 텍스트 맵을 한 줄씩 읽어 저장한 리스트

    private static final int STAGE_HEIGHT = 9;

    private void loadStage(Context context, int stage) {
        AssetManager assets = context.getAssets();
        try {
            // stage_01.txt, stage_02.txt 등 텍스트 맵을 읽어서 lines 리스트에 저장
            // 각 줄의 문자열은 'X', 'Y', '0', 'F' 등 맵 타일 문자들

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
        // 배경이 왼쪽으로 흐르기 때문에 x가 작아지며,
        // 현재 화면 폭(Metrics.width)을 넘지 않으면 계속 createColumn() 호출

        // x 는 화면의 어디에까지 만들었나를 기억한다. SPEED 가 음수이므로 시간에 따라 감소한다
        x += MapObject.SPEED * GameView.frameTime;
        while (x < Metrics.width) {
            createColumn();
            index++;
            x += 100f;
        }
    }

    // 한 열(column)에 해당하는 오브젝트 생성
    private void createColumn() {
        for (int row = 0; row < STAGE_HEIGHT; row++) {
            char tile = getAt(index, row); // 알아내서 // 타일 문자
            float y = 100 * row;
            createObject(tile, x, y); // 생성한다 // 해당 타일 문자에 맞는 오브젝트 생성
        }
    }
    protected interface MapObjectCreator {
        MapObject get(char tile, float left, float top);
    }
    protected static MapObjectCreator[] mapCreators = {
            JellyItem::get, Floor::get, ObstacleFactory::get,
    };

    // mapCreators 배열은 각각:
    //
    //JellyItem.get()
    //Floor.get()
    //ObstacleFactory.get()
    private void createObject(char tile, float left, float top) {
        for (MapObjectCreator creator: mapCreators) {
            MapObject mapObject = creator.get(tile, left, top);
            if (mapObject != null) {
                scene.add(mapObject);
                return;
            }
        }
    }


    // getAt(col, row): 해당 위치의 문자 가져오기
    // 맵은 여러 “페이지”로 나뉘며 | 기호로 구분됨
    // 각 페이지는 STAGE_HEIGHT줄로 구성되며, 열과 행 번호에 따라 정확한 문자를 추출함
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

    // 스테이지 진행률을 보여주는 진행 게이지 UI를 그림
    Gauge gauge = new Gauge(0.025f, R.color.mapGaugeFg, R.color.mapGaugeBg);
    @Override
    public void draw(Canvas canvas) {
        gauge.draw(canvas, 200, 100, 1200, (float)index / stage_width);
    }
}
