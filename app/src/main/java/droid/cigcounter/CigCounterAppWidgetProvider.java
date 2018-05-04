package droid.cigcounter;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

/**
 * たばこカウンターAppWidgetProvider
 * @author yasupong
 */
public class CigCounterAppWidgetProvider extends AppWidgetProvider {
	// 更新時に呼ばれる
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		Intent intent = new Intent(context, CigCounterService.class);
		context.startService(intent);
	}
}
