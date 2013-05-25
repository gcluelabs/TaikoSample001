package com.example.taikosample001;

import com.gclue.TaikoSample001.R;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class TaikoSample001 extends Activity {

	@Override
	public void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		MyView mView = new MyView( this );
		setContentView( mView );
	}
}

class MyView extends View {
	private Bitmap taikoImage;
	private int taikoX = 100;
	private int taikoY = 50;
	private MediaPlayer mp;

	public MyView( Context context ) {
		super( context );

		setFocusable( true );
		Resources res = this.getContext().getResources();
		taikoImage = BitmapFactory.decodeResource( res, R.drawable.taiko );

		mp = MediaPlayer.create( context, R.raw.pon );
	}

	@Override
	protected void onDraw( Canvas canvas ) {
		canvas.drawColor( Color.BLACK );

		Paint mPaint = new Paint();
		canvas.drawBitmap( taikoImage, taikoX, taikoY, mPaint );
	}

	@Override
	public boolean onTouchEvent( MotionEvent event ) {
		if ( event.getAction() == MotionEvent.ACTION_DOWN ) {
			int touchX = (int) event.getX();
			int touchY = (int) event.getY();

			int centerX = taikoX + taikoImage.getWidth() / 2;
			int centerY = taikoY + taikoImage.getHeight() / 2;

			double distance = Math.sqrt(
				Math.pow( (centerX - touchX), 2 )
					+ Math.pow( (centerY - touchY), 2 )
				);

			int taikoR = taikoImage.getWidth() / 2;

			if ( distance < taikoR ) {
				mp.start();
			}
		}
		return true;
	}
}