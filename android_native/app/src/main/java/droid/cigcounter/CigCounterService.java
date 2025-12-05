package droid.cigcounter;

import java.text.SimpleDateFormat;
import java.util.Date;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * たばこカウンターサービス
 * @author yasupong
 */
public class CigCounterService extends Service {

	/** イベント識別子 */
	private static final String ACTION_BTNCLICK = "droid.cigcounter.CigCounterService.ACTION_BTNCLICK";
	/**  日付フォーマット */
	private static final String DATE_FMT = "yyyy/MM/dd";
	/**  日付フォーマット */
	private static final String DATE_FMT_DB = "yyyy/MM/dd HH:mm:ss";
	/** ブランク */
	private static final String STR_BLANK = " ";
	
	/** 前回喫煙時刻 */
	private String _lastSmokeDate = "NO DATA";
	
	@SuppressLint("SimpleDateFormat")
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// リモートビューの取得
		RemoteViews view = new RemoteViews(getPackageName(), droid.cigcounter.R.layout.widgetview);
				
		// ボタンイベントを拾う
		if (intent != null && ACTION_BTNCLICK.equals(intent.getAction())) {
			btnClicked(view, intent);
		}
		
		// 最新喫煙時刻を取得する
		getLastSmokeDate(intent);
		
		// 最新喫煙日付を設定
		view.setTextViewText(droid.cigcounter.R.id.textDate, _lastSmokeDate);

		// btnImageSmakeとボタンクリックイベントの関連付け
		Intent newintent = new Intent();
		newintent.setAction(ACTION_BTNCLICK);
		PendingIntent pending = PendingIntent.getService(this, 0, newintent, 0);
		view.setOnClickPendingIntent(droid.cigcounter.R.id.btnImageSmake, pending);

		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FMT);
		
		// 日付取得
		String strToday = sdf.format(new Date(System.currentTimeMillis()));
		
		// 本数を画面表示
		view.setTextViewText(droid.cigcounter.R.id.textCount, getString(R.string.wid_disp_count) + getCountByDay(intent, strToday));

		// ホームウィジェットの更新
		ComponentName widget = new ComponentName(this, CigCounterAppWidgetProvider.class);
		AppWidgetManager manager = AppWidgetManager.getInstance(this);
		manager.updateAppWidget(widget, view);
		
	    return START_STICKY_COMPATIBILITY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	/**
	 * ボタンクリック時に呼ばれる
	 * @param view
	 * @param intent
	 */
	@SuppressLint("SimpleDateFormat")
	private void btnClicked(RemoteViews view, Intent intent) {
		
		// 最新喫煙時刻を取得する
		getLastSmokeDate(intent);
		
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FMT);
		SimpleDateFormat sdfDB = new SimpleDateFormat(DATE_FMT_DB);
		
		// 日付取得
		String strToday = sdf.format(new Date(System.currentTimeMillis()));
		// DB挿入処理
		String recDate = sdfDB.format(new Date(System.currentTimeMillis()));
		
		ContentValues values = new ContentValues();
		values.put("smokedate", recDate);

		intent.setData(CigCounterContentProvider.CONTENT_URI);
		getContentResolver().insert(intent.getData(), values);

		// 本日の本数
		int dateCount = getCountByDay(intent, strToday);
		
		// 本数を画面表示
		view.setTextViewText(droid.cigcounter.R.id.textCount, getString(R.string.wid_disp_count) + dateCount);
		
		String viewtext = getString(R.string.wid_now_date)  + STR_BLANK + recDate + "\n"+ 
				getString(R.string.wid_last_date) + STR_BLANK + _lastSmokeDate;
		
		// すった本数から箱数を割り出す
		if (dateCount > 19) {
			int boxCount = dateCount / 20;
			viewtext = viewtext + "\n" + getString(R.string.wid_smoke_pack) + STR_BLANK + boxCount;
		}
		
		Toast.makeText(this, viewtext, Toast.LENGTH_LONG).show();
		
		// 前回喫煙時刻を更新
		_lastSmokeDate = recDate;
	}

	/*
	 * 当日のカウントを取得する
	 */
	private int getCountByDay(Intent intent, String strToday) {

		if (intent == null) {
			return 0;
		}
		
		intent.setData(CigCounterContentProvider.CONTENT_URI);
		// 再検索実行
        Cursor c = getContentResolver().query(intent.getData(), null, null, null, "order by smokedate");

        int dateCount = 0;

        // カウントをとる
        while (c.moveToNext()){
			if (strToday.equals(c.getString(0).substring(0, 10))) {
				dateCount++;
			}
        }

        c.close();

		return dateCount;
	}
	
	/**
	 * 最新喫煙時刻を取得する
	 * @param intent
	 */
	private void getLastSmokeDate(Intent intent) {
		// 最新喫煙時刻を取得する
		if (intent != null && "NO DATA".equals(_lastSmokeDate) ) {
			intent.setData(CigCounterContentProvider.CONTENT_URI);
			// 再検索実行
			Cursor c = getContentResolver().query(intent.getData(), null, null, null, "order by smokedate desc");
			if (c.getCount() > 0) {
				 c.moveToLast();
				_lastSmokeDate = c.getString(0);
			}
				
			c.close();
		 }
	}
}
