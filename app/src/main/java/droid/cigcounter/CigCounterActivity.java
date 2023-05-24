package droid.cigcounter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * たばこカウンター集計画面
 * @author yasupong
 */
@SuppressLint("SimpleDateFormat")
public class CigCounterActivity extends Activity {

	/** メニューアイテムID0 */
	private static final int MENU_ITEM0 = 0;
	/** メニューアイテムID1 */
	private static final int MENU_ITEM1 = 1;
	/** メニューアイテムID2 */
	private static final int MENU_ITEM2 = 2;
	/** メニューアイテムID3 */
	private static final int MENU_ITEM3 = 3;
	/** メニューアイテムID4 */
	private static final int MENU_ITEM4 = 4;
	/** メニューアイテムID5 */
	private static final int MENU_ITEM5 = 5;
	/** メニューアイテムID6 */
	private static final int MENU_ITEM6 = 6;
	/** 数値長さ */
	private static final int MAX_LENGTH = 6;
	/** ブランク */
	private static final String STR_BLANK = " ";
	/** 最大リスト出力 */
	private static final int MAX_LIST = 500;
	/** 1本につき失う寿命*/
	private static final int LOST_MIN = 5;
	
	/** がん発生率本数 */
	private final static List<String> COUNT_RATE = new ArrayList<String>();
	static {
		COUNT_RATE.add("1");
		COUNT_RATE.add("10");
		COUNT_RATE.add("15");
		COUNT_RATE.add("20");
		COUNT_RATE.add("30");
		COUNT_RATE.add("40");
		COUNT_RATE.add("50");
	}
	
	/** ガン発生率 */
	private final static List<String> CANCER_RATE = new ArrayList<String>();
	static {
		CANCER_RATE.add("2.18");
		CANCER_RATE.add("3.59");
		CANCER_RATE.add("4.70");
		CANCER_RATE.add("5.87");
		CANCER_RATE.add("5.95");
		CANCER_RATE.add("7.17");
		CANCER_RATE.add("15.07");
	}
	
	/** BIランク */
	private final static List<String> BI_RANK = new ArrayList<String>();
	static {
		BI_RANK.add("400");
		BI_RANK.add("500");
		BI_RANK.add("600");
		BI_RANK.add("1000");
		BI_RANK.add("1200");
	}
	
	/** 一本あたりのコスト */
	private double costPerCount = 21;
	/** BIメッセージ */
	private Map<String, String> BI_MSG = new HashMap<String,String>();
	/** 一日平均本数 */
	private double daily_ave = 0;
	/** 喫煙年数 */
	private double smokeYears = 0;
	/** 総本数 */
	private double totalSmokes = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        // リストビューを構築する（喫煙統計）
		makeSmokeStatListView();
		
		BI_MSG.put(BI_RANK.get(0),getString(R.string.health_bi_400));
		BI_MSG.put(BI_RANK.get(1),getString(R.string.health_bi_500));
		BI_MSG.put(BI_RANK.get(2),getString(R.string.health_bi_600));
		BI_MSG.put(BI_RANK.get(3),getString(R.string.health_bi_1000));
		BI_MSG.put(BI_RANK.get(4),getString(R.string.health_bi_1200));
	}

	/**
	 * リストビューを構築する（喫煙統計）
	 */
	private void makeSmokeStatListView() {
		
        List<String> viewList = new ArrayList<String>();
        List<String> resultList = getSmokeHistoryList();
        
        if (resultList == null || resultList.size() == 0) {
        	Toast.makeText(getBaseContext(), getString(R.string.wid_nodata), Toast.LENGTH_SHORT).show();
        	return;
        }
        
        totalSmokes = resultList.size();
        
        viewList.add(getString(R.string.act_total_count) + STR_BLANK + String.valueOf(resultList.size()) );
        viewList.add(getString(R.string.act_total_cost) + STR_BLANK + String.valueOf(resultList.size() * costPerCount) + STR_BLANK + getString(R.string.act_currency));
        viewList.add(getString(R.string.act_cost_per_price) + STR_BLANK + costPerCount + STR_BLANK + getString(R.string.act_currency));

        // 日単位
		int dateCount = 0;
        // 月単位
		int monthCount = 0;
        // 年単位
		int yearCount = 0;

		String strStartDate = resultList.get(resultList.size()-1).substring(0, 10);
		String strLastDate = resultList.get(0).substring(0, 10);
		
		try {
			DateFormat fmt =new SimpleDateFormat("yyyy/MM/dd"); 
		    Date startDate = fmt.parse(strStartDate);
		    Date lastDate = fmt.parse(strLastDate);
		    
		    // 日数
		    Calendar startCal1 = Calendar.getInstance();
		    startCal1.setTime(startDate);
		    Calendar lastCal1 = Calendar.getInstance(); 
		    lastCal1.setTime(lastDate);
		    
	        while (!lastCal1.before(startCal1)) {
	        	lastCal1.add(Calendar.DATE, -1);
	        	dateCount++;
	        }
	        
	        // 月数
		    Calendar startCal2 = Calendar.getInstance();
		    startCal2.setTime(startDate);
		    Calendar lastCal2 = Calendar.getInstance(); 
		    lastCal2.setTime(lastDate);
		    
	        while (!lastCal2.before(startCal2)) {
	        	lastCal2.add(Calendar.MONTH, -1);
	            monthCount++;
	        }
	        
	        // 月数
		    Calendar startCal3 = Calendar.getInstance();
		    startCal3.setTime(startDate);
		    Calendar lastCal3 = Calendar.getInstance(); 
		    lastCal3.setTime(lastDate);
		    
	        while (!lastCal3.before(startCal3)) {
	        	lastCal3.add(Calendar.YEAR, -1);
	        	yearCount++;
	        }
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
        // 日単位
        double averageCountPerDay = totalSmokes / dateCount;
        daily_ave = averageCountPerDay;
        viewList.add(getString(R.string.act_total_days) + STR_BLANK + dateCount + STR_BLANK + getString(R.string.act_days));
        viewList.add(getString(R.string.act_day_cost_average) + STR_BLANK + subStr(String.valueOf(averageCountPerDay * costPerCount)) + STR_BLANK + getString(R.string.act_currency));
        viewList.add(getString(R.string.act_day_count_average) + STR_BLANK + subStr(String.valueOf(averageCountPerDay)));
        viewList.add(getString(R.string.act_day_packs_average) + STR_BLANK + subStr(String.valueOf(averageCountPerDay / 20)) + STR_BLANK + getString(R.string.act_packs));
        
        // 月単位
        double averageCountPerMonth = totalSmokes / monthCount;
        viewList.add(getString(R.string.act_total_months) + STR_BLANK + monthCount + STR_BLANK + getString(R.string.act_months));
        viewList.add(getString(R.string.act_month_cost_average) + STR_BLANK + subStr(String.valueOf(averageCountPerMonth * costPerCount)) + STR_BLANK + getString(R.string.act_currency));
        viewList.add(getString(R.string.act_month_count_average) + STR_BLANK + subStr(String.valueOf(averageCountPerMonth)));
        viewList.add(getString(R.string.act_month_packs_average) + STR_BLANK + subStr(String.valueOf(averageCountPerMonth / 20)) + STR_BLANK +  getString(R.string.act_packs));

        // 年単位
        double averageCountPerYear = totalSmokes / yearCount;
        smokeYears = yearCount;
        viewList.add(getString(R.string.act_total_years) + STR_BLANK + yearCount + STR_BLANK + getString(R.string.act_years));
        viewList.add(getString(R.string.act_year_cost_average) + STR_BLANK + subStr(String.valueOf((averageCountPerYear * costPerCount))) + STR_BLANK + getString(R.string.act_currency));
        viewList.add(getString(R.string.act_year_count_average) + STR_BLANK + subStr(String.valueOf(averageCountPerYear)));
        viewList.add(getString(R.string.act_year_packs_average) + STR_BLANK + subStr(String.valueOf(averageCountPerYear / 20)) + STR_BLANK + getString(R.string.act_packs));

        // 統計表示リストを画面に表示
        ListView lv = new ListView(this);
		ArrayAdapter<Object> adapter = new ArrayAdapter<Object>(this, android.R.layout.simple_list_item_1, viewList.toArray(new String[0]));
        lv.setAdapter(adapter);
        
        LinearLayout layout = (LinearLayout)findViewById(R.id.linearLayout1);
        layout.removeAllViews();

        layout.addView(lv);
	}

	/**
	 * リストビューを構築する（喫煙ログ）
	 */
	private void makeSmokeLogListView() {
		
        List<String> viewList = new ArrayList<String>();
        List<String> resultList = getSmokeHistoryList();
        
        // ログリスト作成
		for (int i = 0; i < resultList.size(); i++) {
			if (i >= MAX_LIST) break;
			viewList.add(resultList.get(i));
		}
		
        // 統計表示リストを画面に表示
        ListView lv = new ListView(this);
        ArrayAdapter<Object> adapter = new ArrayAdapter<Object>(this, android.R.layout.simple_list_item_1, viewList.toArray(new String[0]));
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	// リストのデータ削除
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        		final String value = ((TextView)view).getText().toString();
        		DialogInterface.OnClickListener listner = new DialogInterface.OnClickListener(){
        			public void onClick(DialogInterface dialog, int which) {
        				if (DialogInterface.BUTTON_POSITIVE == which) {
        					deleteLog(value);
        					makeSmokeLogListView();
        				}
        			}
        		};
        	    	
           		AlertDialog.Builder diag = new AlertDialog.Builder(CigCounterActivity.this);
        		diag.setTitle(R.string.menu_msg_delete);
        		diag.setMessage(value);
        		diag.setPositiveButton("Yes", listner);
        		diag.setNegativeButton("No", listner);
	            diag.create();
	            diag.show();    
        	}
        });
        
        LinearLayout layout = (LinearLayout)findViewById(R.id.linearLayout1);
        layout.removeAllViews();

        layout.addView(lv);
	}
	
	/**
	 * リストビューを構築する（健康情報）
	 */
	private void makeHealthInfoListView() {
		
        List<String> viewList = new ArrayList<String>();
		// 総本数
		viewList.add(getString(R.string.act_total_count) + STR_BLANK + totalSmokes );
        // 一日の平均本数
        viewList.add(getString(R.string.act_day_count_average) + STR_BLANK + subStr(String.valueOf(daily_ave)));
        // 喫煙年数
        viewList.add(getString(R.string.act_total_years) + STR_BLANK + smokeYears + STR_BLANK + getString(R.string.act_years));
        
        String rateMsg = getString(R.string.health_msg) + " 1 " + getString(R.string.health_times);
        
        for (int i = 0; i < COUNT_RATE.size(); i++) {
			String countRate = COUNT_RATE.get(i);
			if (Double.parseDouble(countRate) <= daily_ave) {
		        // ガン発生率     
				rateMsg = getString(R.string.health_msg) + " " + CANCER_RATE.get(i) + " " + getString(R.string.health_times);
			}
		}
        
        // ガン発生率
        viewList.add(rateMsg);
        // ブリンクマン指数
        double bi = daily_ave*smokeYears;
		viewList.add(getString(R.string.health_bri) + " " + subStr(String.valueOf(bi)));
		
        String biMsg = getString(R.string.health_bri_rate) + getString(R.string.health_bi_none);
        
		for (int i = 0; i < BI_RANK.size(); i++) {
			String biRank = BI_RANK.get(i);
			if (Double.parseDouble(biRank) <= bi) {
		        // 危険度     
				biMsg = getString(R.string.health_bri_rate) + BI_MSG.get(BI_RANK.get(i));
			}
		}
		
		viewList.add(biMsg);
		
		// 失った寿命
		double days = ((totalSmokes * LOST_MIN) / 60) / 24;
		viewList.add(getString(R.string.health_lost_time) + STR_BLANK + subStr(String.valueOf(days)));
		
        // 健康情報リストを画面に表示
        ListView lv = new ListView(this);
        ArrayAdapter<Object> adapter = new ArrayAdapter<Object>(this, android.R.layout.simple_list_item_1, viewList.toArray(new String[0]));
        lv.setAdapter(adapter);
        
        LinearLayout layout = (LinearLayout)findViewById(R.id.linearLayout1);
        layout.removeAllViews();

        layout.addView(lv);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		// リセットメニュー追加
		MenuItem actionItem0 = menu.add(0, MENU_ITEM0, 0, getString(R.string.menu_reset));
		actionItem0.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
     
		// 値段変更メニュー追加
		MenuItem actionItem1 = menu.add(0, MENU_ITEM1, 0, getString(R.string.menu_change_cpc));
		actionItem1.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		
		// 喫煙統計メニュー追加
		MenuItem actionItem2 = menu.add(0, MENU_ITEM2, 0, getString(R.string.menu_analyzer)); 
		actionItem2.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		
		// 喫煙ログメニュー追加
		MenuItem actionItem3 = menu.add(0, MENU_ITEM3, 0, getString(R.string.menu_logs));
		actionItem3.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		
		// 喫煙ロググラフ
		MenuItem actionItem4 = menu.add(0, MENU_ITEM4, 0, getString(R.string.menu_logs_chart));
		actionItem4.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		
		// 喫煙ログ保存メニュー追加
		MenuItem actionItem5 = menu.add(0, MENU_ITEM5, 0, getString(R.string.menu_trans));
		actionItem5.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		
		// 健康情報メニュー追加
		MenuItem actionItem6 = menu.add(0, MENU_ITEM6, 0, getString(R.string.menu_health));
		actionItem6.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case MENU_ITEM0: // リセット
        		DialogInterface.OnClickListener listnerDel = new DialogInterface.OnClickListener(){
        			public void onClick(DialogInterface dialog, int which) {
        				if (DialogInterface.BUTTON_POSITIVE == which) {
        					deleteAll();
        					makeSmokeStatListView();
        				}
        			}
        		};
				AlertDialog.Builder dlgDel = new AlertDialog.Builder(this);
				dlgDel.setMessage(getString(R.string.menu_msg_reset));
        		dlgDel.setPositiveButton("Yes", listnerDel);
        		dlgDel.setNegativeButton("No", listnerDel);
        		dlgDel.show();
				return true;
			case MENU_ITEM1: // 値段変更
				final EditText editView = new EditText(CigCounterActivity.this);
        		DialogInterface.OnClickListener listnerChg = new DialogInterface.OnClickListener(){
        			public void onClick(DialogInterface dialog, int which) {
						try {
							costPerCount = Double.valueOf(editView.getText().toString());
							CigCounterDBHelper dbHelper = new CigCounterDBHelper(getBaseContext());
							SQLiteDatabase db = dbHelper.getWritableDatabase();
							ContentValues values = new ContentValues();
							values.put("price", costPerCount);
							int colNum = db.update(CigCounterDBHelper.DB_TABLE_CIGPRICE, values, null, null);
							if (colNum == 0) db.insert(CigCounterDBHelper.DB_TABLE_CIGPRICE, null, values);
							db.close();
						}
						catch (Throwable th) {
							th.printStackTrace();
							Toast.makeText(getBaseContext(), getString(R.string.dlog_msg_invalid), Toast.LENGTH_SHORT).show();
							return;
						}
		 				makeSmokeStatListView();
        			}
        		};
				AlertDialog.Builder dlgChange = new AlertDialog.Builder(this);
				dlgChange.setMessage(getString(R.string.dlog_cost_per_count));
        		dlgChange.setPositiveButton("OK", listnerChg);
        		editView.setText(String.valueOf(costPerCount));
        		dlgChange.setView(editView);
        		dlgChange.show();
				return true;
			case MENU_ITEM2: // 喫煙統計リストビュー作成
				makeSmokeStatListView();
				return true;
			case MENU_ITEM3: // 喫煙ログリストビュー作成
				makeSmokeLogListView();
				return true;
			case MENU_ITEM4: // 喫煙ロググラフ
				loadChart();
				return true;
			case MENU_ITEM5: // 喫煙ログ保存メニュー作成
				saveSmokeLog();
				return true;
			case MENU_ITEM6: // 健康情報
				makeHealthInfoListView();
				return true;
		}
		return true;
	}
	
	/**
	 * DB全件検索
	 * @return
	 */
	private List<String> getSmokeHistoryList() {
		CigCounterDBHelper dbHelper = new CigCounterDBHelper(this);
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		Cursor cPrice = db.query(CigCounterDBHelper.DB_TABLE_CIGPRICE, new String[]{ "price" }, null, null, null, null, null);
		if (cPrice.getCount() > 0) {
			cPrice.moveToFirst();
			costPerCount = Double.parseDouble(cPrice.getString(0));
		}
		cPrice.close();
        db.close();
        
        // 全検索実行      
		SQLiteDatabase db2 = dbHelper.getWritableDatabase();

		Cursor cAll = db2.query(CigCounterDBHelper.DB_TABLE_CIGCOUNT, new String[]{ "smokedate" }, null, null, null, null, "smokedate desc");
		
        List<String> resultList = new ArrayList<String>();
        
        // リストに落とし込む
        while (cAll.moveToNext()){
        	resultList.add(cAll.getString(0));
        }
        
        cAll.close();
        db2.close();
        
        return resultList;
	}
	
	/**
	 * 喫煙ログ保存
	 */
	private void saveSmokeLog() {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		
		String text = "";
        List<String> resultList = getSmokeHistoryList();
        
        // ログリスト作成
		for (Iterator<String> iterator = resultList.iterator(); iterator.hasNext();) {
			String getDate = iterator.next();
			text = text + getDate + "\n";
		}
		
		intent.putExtra(Intent.EXTRA_TEXT, text);  
		startActivity(Intent.createChooser(intent, getString(R.string.menu_msg_save_log)));
	}
	
	/**
	 * 喫煙ロググラフ作成
	 */
	private void loadChart() {
        LinearLayout layout = (LinearLayout)findViewById(R.id.linearLayout1);
        layout.removeAllViews();

        // ビューの作成
        CigCounterChartView cView = new CigCounterChartView(this);
        // ログビリスト取得
        cView.setLogList(getSmokeHistoryList());
        cView.setChart_name(getString(R.string.chart_name));
        cView.setChart_x_label(getString(R.string.chart_x_label));
        cView.setChart_y_label(getString(R.string.chart_y_label));
        cView.setChart_plot(getString(R.string.chart_plot));
            
        layout.addView(cView);
	}
	
	/**
	 * サブストリングする
	 * @param in
	 * @return
	 */
	public static String subStr(String in) {
		if (in.length() > MAX_LENGTH) {
			in = in.substring(0,MAX_LENGTH);
		}
		return in;
	}
	
	/**
	 * 全データ削除
	 */
	public void deleteAll() {
		CigCounterDBHelper dbHelper = new CigCounterDBHelper(this);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		try {
			db.delete(CigCounterDBHelper.DB_TABLE_CIGCOUNT, null, null);
		}
		finally {
			db.close();
			dbHelper.close();
		}
	}
	
	/**
	 * 指定したレコードを削除する
	 * @param date 日付
	 */
	public void deleteLog(String date) {
		CigCounterDBHelper dbHelper = new CigCounterDBHelper(this);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		try {
			db.delete(CigCounterDBHelper.DB_TABLE_CIGCOUNT, "smokedate='" + date + "'", null);
		}
		finally {
			db.close();
			dbHelper.close();
		}
	}
}
