package com.gclue.TaikoSample001;

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
		// 描画クラスのインスタンスを生成
		MyView mView = new MyView( this );
		setContentView( mView );
	}
}

class MyView extends View {
	/** 太鼓画像データを保持する。 */
	private Bitmap taikoImage;
	/** 太鼓画像の原点（左上）のx座標を保持する。 */
	private int taikoX = 100;
	/** 太鼓画像の原点（左上）のy座標を保持する。 */
	private int taikoY = 50;
	/** サウンド再生データを保持する。 */
	private MediaPlayer mp;

	/**
	 * コンストラクタ。
	 * @param context コンテキスト
	 */
	public MyView( Context context ) {
		super( context );

		// イベント取得できるようにFocusを有効にする
		setFocusable( true );
		// Resourceインスタンスの生成
		Resources res = this.getContext().getResources();
		// 画像の読み込み（/res/drawable-hdpi/taiko.png）
		taikoImage = BitmapFactory.decodeResource( res, R.drawable.taiko );

		// サウンドデータを読み込む(/res/raw/pon.mp3)
		mp = MediaPlayer.create( context, R.raw.pon );
	}

	/**
	 * 描画処理。
	 */
	@Override
	protected void onDraw( Canvas canvas ) {
		// 背景色を設定する
		canvas.drawColor( Color.BLACK );

		// Bitmapイメージの描画
		Paint mPaint = new Paint();
		canvas.drawBitmap( taikoImage, taikoX, taikoY, mPaint );
	}

	/**
	 * タッチイベント
	 */
	@Override
	public boolean onTouchEvent( MotionEvent event ) {
		if ( event.getAction() == MotionEvent.ACTION_DOWN ) {
			// 指がタッチされたx,y座標の取得
			int touchX = (int) event.getX();
			int touchY = (int) event.getY();

			// 太鼓の中心座標を計算
			int centerX = taikoX + taikoImage.getWidth() / 2;
			int centerY = taikoY + taikoImage.getHeight() / 2;

			// 太鼓の中心座標と指の距離を計算
			double distance = Math.sqrt(
				Math.pow( (centerX - touchX), 2 )
					+ Math.pow( (centerY - touchY), 2 )
				);

			// 太鼓画像の半径
			int taikoR = taikoImage.getWidth() / 2;

			// あたり判定
			if ( distance < taikoR ) {
				// サウンド再生
				mp.start();
			}
		}
		return true;
	}
}