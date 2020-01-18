package com.kaithavalappil.getmybus_admin.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.kaithavalappil.getmybus_admin.R;

public class Timeline extends View {
    int Width, heitht,height_pix,state;
    Bitmap bitmap;
    Bitmap checked;
    public Timeline(Context context, int Width, int heitht) {
        super(context);
        this.Width = Width;
        this.heitht = heitht;
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.map_pin);
        this.state = 1;

    }

    public Timeline(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Timeline(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public Timeline(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {


//        heitht = canvas.getHeight()/2;
        super.onDraw(canvas);
        Width = canvas.getWidth();
        heitht = height_pix*canvas.getHeight()/100;
//        RectF rect = new RectF(10, 10, Width, heitht);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.rgb(0,102,255));
        paint.setAntiAlias(true);
        Paint paint_fill = new Paint();
        paint_fill.setStyle(Paint.Style.STROKE);
        paint_fill.setColor(Color.BLACK);
        paint_fill.setStrokeWidth(2);
//        paint_fill.setColor(Color.WHITE);
        paint_fill.setAntiAlias(true);
        Rect rect = new Rect(0,0,Width,Width);
//        canvas.drawRect(rect, paint);
        switch (state){
            case 0:{
//                Path shape = RoundedRect(0,0,Width,heitht,Width/2,Width/2,true,true,false,false);
//                canvas.drawPath(shape,paint);
                canvas.drawRect(0,Width/2,Width, heitht,paint);
                canvas.drawCircle(Width/2,Width/2,Width/2,paint);
                canvas.drawCircle(Width/2,heitht,Width/2,paint);
                canvas.drawCircle(Width/2,Width/2-1,(Width/2)-2,paint_fill);
                canvas.drawBitmap(checked,null,rect,paint);
                break;
            }
            case 1:{
                canvas.drawRect(0,0,Width, heitht,paint);
                canvas.drawCircle(Width/2,Width/2-1,(Width/2)-2,paint_fill);
                canvas.drawBitmap(checked,null,rect,paint);
                break;
            }
            case  2:{
                Path shape = RoundedRect(0,0,Width,heitht,Width/2,Width/2,false,false,true,true);
                canvas.drawPath(shape,paint);
                canvas.drawCircle(Width/2,Width/2-1,(Width/2)-2,paint_fill);
                canvas.drawBitmap(checked,null,rect,paint);
                break;
            }
            case  3:{
//                Path shape = RoundedRect(0,0,Width,heitht,Width/2,Width/2,false,false,true,true);
//                canvas.drawPath(shape,paint);
                canvas.drawRect(0,0,Width, heitht,paint);
                canvas.drawCircle(Width/2,heitht,Width/2,paint);
                canvas.drawCircle(Width/2,Width/2-1,(Width/2)-2,paint_fill);
                break;
            }
            case 4:{
                canvas.drawCircle(Width/2,Width/2-1,(Width/2)-2,paint_fill);
            }
        }
//        if (height_pix==100){
//            canvas.drawRect(0,0,Width, heitht,paint);
//            canvas.drawCircle(Width/2,heitht/2,(Width/2)-2,paint_fill);
//            Bitmap checked = BitmapFactory.decodeResource(getResources(), R.drawable.checked);
//            canvas.drawBitmap(checked,null,rect,paint);
//        }
//        else {
//            Path shape = RoundedRect(0,0,Width,heitht,Width/2,Width/2,false,false,true,true);
//            canvas.drawPath(shape,paint);
//            canvas.drawBitmap(bitmap,null,rect,paint);
//        }

//        canvas.drawCircle(Width/2,heitht/2,(Width/2)-2,paint_fill);
//        canvas.drawRoundRect(rect,5,10, paint );
    }
    public void  setState(int state){
        this.state = state;
        checked = BitmapFactory.decodeResource(getResources(), R.drawable.checked);
    }
    public  void setHight(int heitht){
        this.height_pix = heitht;
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.map_pin);
    }
    public void setResource(int resource){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resource);
    }
    public static Path RoundedRect(float left, float top, float right, float bottom, float rx, float ry, boolean tl, boolean tr, boolean br, boolean bl){
        Path path = new Path();
        if (rx < 0) rx = 0;
        if (ry < 0) ry = 0;
        float width = right - left;
        float height = bottom - top;
        if (rx > width / 2) rx = width / 2;
        if (ry > height / 2) ry = height / 2;
        float widthMinusCorners = (width - (2 * rx));
        float heightMinusCorners = (height - (2 * ry));

        path.moveTo(right, top + ry);
        if (tr)
            path.rQuadTo(0, -ry, -rx, -ry);//top-right corner
        else{
            path.rLineTo(0, -ry);
            path.rLineTo(-rx,0);
        }
        path.rLineTo(-widthMinusCorners, 0);
        if (tl)
            path.rQuadTo(-rx, 0, -rx, ry); //top-left corner
        else{
            path.rLineTo(-rx, 0);
            path.rLineTo(0,ry);
        }
        path.rLineTo(0, heightMinusCorners);

        if (bl)
            path.rQuadTo(0, ry, rx, ry);//bottom-left corner
        else{
            path.rLineTo(0, ry);
            path.rLineTo(rx,0);
        }

        path.rLineTo(widthMinusCorners, 0);
        if (br)
            path.rQuadTo(rx, 0, rx, -ry); //bottom-right corner
        else{
            path.rLineTo(rx,0);
            path.rLineTo(0, -ry);
        }

        path.rLineTo(0, -heightMinusCorners);

        path.close();//Given close, last lineto can be removed.

        return path;
    }
}
