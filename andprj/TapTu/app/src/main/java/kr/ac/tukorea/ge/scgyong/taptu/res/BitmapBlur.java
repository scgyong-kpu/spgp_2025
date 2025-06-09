package kr.ac.tukorea.ge.scgyong.taptu.res;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RenderEffect;
import android.graphics.RenderNode;
import android.graphics.Shader;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;

public class BitmapBlur {
    private static final float BLUR_RADIUS = 5f;
    private static final String TAG = BitmapBlur.class.getSimpleName();
    public static Bitmap blurBitmap(Context context, Bitmap bitmap) {
        Bitmap blurredBitmap = null;
        try {
            blurredBitmap = blurBitmapWithRenderEffect(bitmap);
            if (blurredBitmap != null) {
                return blurredBitmap;
            }
        } catch (Exception e) {
            Log.w(TAG, "RenderEffect Exception: " + e);
        }

        try {
            blurredBitmap = blurBitmapWithRenderScript(context, bitmap);
        } catch (Exception e) {
            Log.w(TAG, "RenderScript Exception: " + e);
        }

        return blurredBitmap;
    }

    public static Bitmap blurBitmapWithRenderEffect(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            return null;
        }
        Bitmap blurredBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());

        // Create a RenderNode and apply the RenderEffect
        RenderNode renderNode = new RenderNode("BlurNode");
        renderNode.setRenderEffect(RenderEffect.createBlurEffect(BLUR_RADIUS, BLUR_RADIUS, Shader.TileMode.CLAMP));

        // Use a Canvas to draw the original bitmap onto the RenderNode
        Canvas canvas = renderNode.beginRecording(bitmap.getWidth(), bitmap.getHeight());
        canvas.drawBitmap(bitmap, 0, 0, null);
        renderNode.endRecording();

        // Draw the RenderNode onto the blurred bitmap
        Canvas outputCanvas = new Canvas(blurredBitmap);
        outputCanvas.drawRenderNode(renderNode);

        return blurredBitmap;
    }
    public static Bitmap blurBitmapWithRenderScript(Context context, Bitmap bitmap) {
        Bitmap.Config config = bitmap.getConfig() != null ? bitmap.getConfig() : Bitmap.Config.ARGB_8888;
        Bitmap blurredBitmap = bitmap.copy(config, true);
        RenderScript rs = RenderScript.create(context);
        Allocation input = Allocation.createFromBitmap(rs, bitmap);
        Allocation output = Allocation.createTyped(rs, input.getType());
        ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        script.setRadius(BLUR_RADIUS);
        script.setInput(input);
        script.forEach(output);
        output.copyTo(blurredBitmap);
        rs.destroy();
        return blurredBitmap;
    }
}
