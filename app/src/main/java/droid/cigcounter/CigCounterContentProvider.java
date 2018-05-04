package droid.cigcounter;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * たばこカウンターContentProvider
 * @author yasupong
 */
public class CigCounterContentProvider extends ContentProvider {

	/** DBコントロール */
    private CigCounterDBHelper dbHelper = null;

    public static final String AUTHORITY = "droid.cigcounter.CigCounter";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/");
    private static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + AUTHORITY;

	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		final SQLiteDatabase db = dbHelper.getReadableDatabase();
		db.delete(CigCounterDBHelper.DB_TABLE_CIGCOUNT, "", null);
		return 0;
	}

	@Override
	public String getType(Uri arg0) {
		return CONTENT_TYPE;
	}

	@Override
	public Uri insert(Uri arg0, ContentValues arg1) {
		final SQLiteDatabase db = dbHelper.getReadableDatabase();
		db.insert(CigCounterDBHelper.DB_TABLE_CIGCOUNT, "", arg1);
		return null;
	}

	@Override
	public boolean onCreate() {
		dbHelper = new CigCounterDBHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri arg0, String[] arg1, String arg2, String[] arg3, String arg4) {
		final SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor c = db.rawQuery("select * from " + CigCounterDBHelper.DB_TABLE_CIGCOUNT, null);
		if (c == null) return null;
		c.setNotificationUri(getContext().getContentResolver(), CONTENT_URI);
		return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		return 0;
	}
}
