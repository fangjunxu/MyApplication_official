
/**
 * Created by fangjun.xu on 9/26/16.
 * just for test view functions
 *
 */
package com.myapplication_test.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;



public class DrawView extends View {
      final String TAGS= "DrawView";
      public   float current_x = 0;
      public   float current_y = 0;

      public  DrawView(Context context) {
          super(context);

      }
      @Override
      public void onDraw(Canvas canvas) {
          super.onDraw(canvas);
          //create paint
          Paint mpaint =  new Paint();
          //set color
          mpaint.setColor(Color.GREEN);
          //draw circle
          canvas.drawCircle(current_x,current_x,15, mpaint);
      }
}
