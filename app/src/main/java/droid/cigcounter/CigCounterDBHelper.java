package droid.cigcounter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * データベース接続
 * @author yasupong
 */
public class CigCounterDBHelper extends SQLiteOpenHelper {

	public static String DB_NAME = "CigCounter";
	public static int DB_VERSON = 1;
	public static String DB_TABLE_CIGCOUNT = "cigcount";
	public static String DB_TABLE_CIGPRICE = "cigprice";

	public CigCounterDBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSON);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		arg0.execSQL( "create table if not exists " + DB_TABLE_CIGCOUNT + "(smokedate text primary key)" );
		arg0.execSQL( "create table if not exists " + DB_TABLE_CIGPRICE + "(price text primary key)" );
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		arg0.execSQL( "drop table if exists " + DB_TABLE_CIGCOUNT );
		arg0.execSQL( "drop table if exists " + DB_TABLE_CIGPRICE );
		onCreate(arg0);
	}
}
