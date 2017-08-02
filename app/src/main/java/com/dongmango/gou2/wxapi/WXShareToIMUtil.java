package com.dongmango.gou2.wxapi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.dongmango.gou2.R;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by Administrator on 2017/3/9.
 */

public class WXShareToIMUtil {
    //req.scene = SendMessageToWX.Req.WXSceneTimeline;// 表示发送场景为朋友圈，这个代表分享到朋友圈
    //req.scene = SendMessageToWX.Req.WXSceneSession;//表示发送场景为好友对话，这个代表分享给好友
    //req.scene = SendMessageToWX.Req.WXSceneFavorite;// 表示发送场景为收藏，这个代表添加到微信收藏
    public final static int WX_PYQ = SendMessageToWX.Req.WXSceneTimeline;
    public final static int WX_PY = SendMessageToWX.Req.WXSceneSession;
    public final static int WX_SC = SendMessageToWX.Req.WXSceneFavorite;

    /**
     * 分享文本、图片和链接
     *
     * @param context
     * @param scene
     * @param link
     * @param title
     * @param description
     * @param bitmap
     */
    public static void shareTextToWX(Context context, int scene, String title, String description, String link, Bitmap bitmap) {
        WXWebpageObject webpageObject = new WXWebpageObject();
        webpageObject.webpageUrl = link;
        // 用WXTextObject对象初始化一个WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = webpageObject;
        // 发送文本类型的消息时，title字段不起作用
        msg.title = title;
        msg.description = description;

        if (bitmap != null) {
            try {
                msg.thumbData = getBitmapBytes(bitmap, false);
            } catch (Exception e) {
                Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
                ByteArrayOutputStream output = new ByteArrayOutputStream();//初始化一个流对象
                bmp.compress(Bitmap.CompressFormat.PNG, 100, output);//把bitmap100%高质量压缩 到 output对象里
                bmp.recycle();//自由选择是否进行回收
                byte[] result = output.toByteArray();//转换成功了
                try {
                    output.close();
                } catch (Exception e2) {
                    e.printStackTrace();
                }
                msg.thumbData = result;
            }
        } else {
            Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
            ByteArrayOutputStream output = new ByteArrayOutputStream();//初始化一个流对象
            bmp.compress(Bitmap.CompressFormat.PNG, 100, output);//把bitmap100%高质量压缩 到 output对象里
            bmp.recycle();//自由选择是否进行回收
            byte[] result = output.toByteArray();//转换成功了
            try {
                output.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            msg.thumbData = result;
        }

        // 构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text"); // transaction字段用于唯一标识一个请求
        req.message = msg;
        req.scene = scene;
        // 调用api接口发送数据到微信
        IWXAPI api = WXAPIFactory.createWXAPI(context, WXConfig.APP_ID, false);
        api.registerApp(WXConfig.APP_ID);
        api.sendReq(req);
    }


    /**
     * 分享文本和图片
     *
     * @param context
     * @param scene
     * @param title
     * @param description
     * @param bitmap
     */
    public static void shareTextToWX(Context context, int scene, String title, String description, Bitmap bitmap) {
        WXImageObject wxImageObject = new WXImageObject(bitmap);

        // 用WXTextObject对象初始化一个WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = wxImageObject;
        // 发送文本类型的消息时，title字段不起作用
        msg.title = title;
        msg.description = description;

        if (bitmap != null) {
            try {
                msg.thumbData = getBitmapBytes(bitmap, false);
            } catch (Exception e) {
                Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
                ByteArrayOutputStream output = new ByteArrayOutputStream();//初始化一个流对象
                bmp.compress(Bitmap.CompressFormat.PNG, 100, output);//把bitmap100%高质量压缩 到 output对象里
                bmp.recycle();//自由选择是否进行回收
                byte[] result = output.toByteArray();//转换成功了
                try {
                    output.close();
                } catch (Exception e2) {
                    e.printStackTrace();
                }
                msg.thumbData = result;
            }
        } else {
            Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
            ByteArrayOutputStream output = new ByteArrayOutputStream();//初始化一个流对象
            bmp.compress(Bitmap.CompressFormat.PNG, 100, output);//把bitmap100%高质量压缩 到 output对象里
            bmp.recycle();//自由选择是否进行回收
            byte[] result = output.toByteArray();//转换成功了
            try {
                output.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            msg.thumbData = result;
        }

        // 构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text"); // transaction字段用于唯一标识一个请求
        req.message = msg;
        req.scene = scene;
        // 调用api接口发送数据到微信
        IWXAPI api = WXAPIFactory.createWXAPI(context, WXConfig.APP_ID, false);
        api.registerApp(WXConfig.APP_ID);
        api.sendReq(req);
    }

    /**
     * 构造一个用于请求的唯一标识
     *
     * @param type 分享的内容类型
     * @return
     */
    private static String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis())
                : type + System.currentTimeMillis();
    }

    // 需要对图片进行处理，否则微信会在log中输出thumbData检查错误
    private static byte[] getBitmapBytes(Bitmap bitmap, boolean paramBoolean) {
        Bitmap localBitmap = Bitmap.createBitmap(80, 80, Bitmap.Config.RGB_565);
        Canvas localCanvas = new Canvas(localBitmap);
        int i;
        int j;
        if (bitmap.getHeight() > bitmap.getWidth()) {
            i = bitmap.getWidth();
            j = bitmap.getWidth();
        } else {
            i = bitmap.getHeight();
            j = bitmap.getHeight();
        }
        while (true) {
            localCanvas.drawBitmap(bitmap, new Rect(0, 0, i, j), new Rect(0, 0, 80
                    , 80), null);
            if (paramBoolean)
                bitmap.recycle();
            ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
            localBitmap.compress(Bitmap.CompressFormat.JPEG, 100,
                    localByteArrayOutputStream);
            localBitmap.recycle();
            byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
            try {
                localByteArrayOutputStream.close();
                return arrayOfByte;
            } catch (Exception e) {

            }
            i = bitmap.getHeight();
            j = bitmap.getHeight();
        }
    }
}
