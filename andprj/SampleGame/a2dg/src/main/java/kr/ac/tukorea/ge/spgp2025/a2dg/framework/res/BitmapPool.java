package kr.ac.tukorea.ge.spgp2025.a2dg.framework.res;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.util.HashMap;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;



// Bitmap을 어느 것을 Load할 지는 각 class가 알고 잇으므로 책임지도록 한다.
// Fighter는 한 개만 만들어지므로 생성자에서 로드해도 괜찮지만 ball은 여러개 만들어지므로
// 로딩을 한번만 하려면 static member를 쓰면 되는데, 매번 객체가 몇개 만들어지는지 신경써서 로드해야하나?
//
 // Bitmap의 Load는 Bitmap Pool을 통해서 하도록 한다.
// int-Bitmap Dictionary에 Cache하므로 같은 ID로 여러 번 불려도 한 번만 로드된다.
public class BitmapPool {
    private static final String TAG = BitmapPool.class.getSimpleName();
    private static final HashMap<Integer, Bitmap> bitmaps = new HashMap<>();
    // 이 HashMap은 리소스 ID(mipmapResId)를 키로, Bitmap 객체를 값으로 저장합니다.
    //
    // 즉, 각 비트맵은 한 번 로드되면 bitmaps 맵에 캐시되어,
    // 이후 같은 비트맵을 요청할 때 다시 로드하지 않고 캐시된 값을 사용합니다.
    // 이렇게 함으로써 불필요한 리소스 로딩을 줄일 수 있습니다.
    //
     // 즉, 변수 자체는 단순히 데이터를 저장하는 역할을 하지만,
    // 그 사용 목적이 **"반복된 데이터 접근을 빠르게 처리하기 위해 저장해두는 것"**이라서 캐시라고 부를 수 있습니다.

    private static BitmapFactory.Options opts;
    // opts (BitmapFactory.Options)
    //
    // opts는 비트맵을 디코딩할 때 옵션을 설정하는 객체입니다.


    // 이 메서드는 주어진 mipmapResId에 해당하는 비트맵을 캐시에서 가져오거나,
    // 없으면 새로 로드하여 캐시에 저장하는 역할을 합니다.
    public static Bitmap get(int mipmapResId) {
        Bitmap bitmap = bitmaps.get(mipmapResId);

        if (bitmap == null) {

            if (opts == null) {
                opts = new BitmapFactory.Options();
                opts.inScaled = false;
                // 여기서 opts.inScaled = false;는 비트맵이 크기 조정 없이 원본 크기 그대로 로드되도록 설정하는 옵션입니다.
                //
                 // Bitmap을 로드하는 창구가 하나로 정해졌으므로, 모든 비트맵은 화면해상도에 관계없이 원본 상태 그대로
                // 로드하도록 하는 코드를 적용한다.
            }

            // 만약 캐시에서 해당 비트맵을 찾지 못하면,
            // BitmapFactory.decodeResource()를 사용하여 리소스에서 비트맵을 디코딩합니다.
            // 이 때, opts를 사용하여 비트맵의 스케일링을 비활성화합니다.

            Resources res = GameView.view.getResources();
            bitmap = BitmapFactory.decodeResource(res, mipmapResId, opts);
            Log.d(TAG, "Bitmap " + res.getResourceEntryName(mipmapResId) + "(" + mipmapResId + ") : " + bitmap.getWidth() + "x" + bitmap.getHeight());

            bitmaps.put(mipmapResId, bitmap);
            // bitmaps.put(mipmapResId, bitmap)을 통해 캐시에 저장됩니다.
        }

        return bitmap;
    }
}
