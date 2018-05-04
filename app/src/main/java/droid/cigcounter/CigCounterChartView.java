package droid.cigcounter;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.afree.chart.AFreeChart;
import org.afree.chart.ChartFactory;
import org.afree.chart.axis.NumberAxis;
import org.afree.chart.plot.CategoryPlot;
import org.afree.chart.plot.PlotOrientation;
import org.afree.data.category.DefaultCategoryDataset;
import org.afree.graphics.geom.RectShape;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * 喫煙ロググラフビュー
 * @author yasupong
 */
@SuppressLint("DrawAllocation")
public class CigCounterChartView extends View {
	
	/** ログリスト */
	private List<String> logList = null;
	
	private String chart_name = null;
	private String chart_x_label = null;
	private String chart_y_label = null;
	private String chart_plot = null;
	
	/**
	 * コンストラクター
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public CigCounterChartView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * コンストラクター
	 * @param context
	 * @param attrs
	 */
	public CigCounterChartView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * コンストラクター
	 * @param context
	 */
	public CigCounterChartView(Context context) {
		super(context);
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		
		super.onDraw(canvas);
		
        RectShape chartArea = new RectShape(0,0,canvas.getWidth(),400);
        
        DefaultCategoryDataset data = new DefaultCategoryDataset();
    	
    	if (logList != null && logList.size() > 0) {
			
			Map<String, String> plotMap = new HashMap<String, String>();
			
			for (int i = 0; i < logList.size(); i++) {
	
				if (plotMap.size() > 10) {
					break;
				}
				
				String getDate = (String)logList.get(i);
				String date = getDate.substring(5, 10);
				
				if (plotMap.get(date) == null) {
					plotMap.put(date, "1");
				} else {
					int count = Integer.parseInt((String)plotMap.get(date));
					count++;
					plotMap.put(date, String.valueOf(count));
				}
			}
			
			Calendar cal = Calendar.getInstance();
			
			for (int i = 0; i < 10; i++) {
				
			    int month = cal.get(Calendar.MONTH) + 1;
			    int day = cal.get(Calendar.DATE);
			    String sMonth = "";
			    String sDay = "";
			    
			    if (10 > month) {
			    	sMonth = "0" + String.valueOf(month);
			    } else {
			    	sMonth = String.valueOf(month);
			    }
			    
			    if (10 > day) {
			    	sDay = "0" + String.valueOf(day);
			    } else {
			    	sDay = String.valueOf(day);
			    }
			    		
			    String date = sMonth + "/" + sDay;
				
			    boolean isExist = false;
			    
				for (Iterator<String> iterator = plotMap.keySet().iterator(); iterator.hasNext();) {
					String string = (String) iterator.next();
				    if (string.equals(date)) {
				    	data.addValue(Double.parseDouble((String)plotMap.get(string)), chart_plot, date);
				    	isExist = true;
				    	break;
				    }
				}
				
				if (!isExist) {
					data.addValue(0, chart_plot, date);
				}
			    
				cal.add(Calendar.DATE, -1);
			}
    	}
    	
        AFreeChart chart = ChartFactory.createBarChart(chart_name,chart_x_label,chart_y_label,data,PlotOrientation.VERTICAL,true,false,false);
        
        // 整数だけにする
        CategoryPlot plot = chart.getCategoryPlot();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        
        chart.draw(canvas, chartArea);
	}

	/**
	 * ログリスト取得
	 * @param logList
	 */
	public void setLogList(List<String> logList) {
		this.logList = logList;
	}

	/**
	 * @param chart_name the chart_name to set
	 */
	public void setChart_name(String chart_name) {
		this.chart_name = chart_name;
	}

	/**
	 * @param chart_x_label the chart_x_label to set
	 */
	public void setChart_x_label(String chart_x_label) {
		this.chart_x_label = chart_x_label;
	}

	/**
	 * @param chart_y_label the chart_y_label to set
	 */
	public void setChart_y_label(String chart_y_label) {
		this.chart_y_label = chart_y_label;
	}

	/**
	 * @param chart_plot the chart_plot to set
	 */
	public void setChart_plot(String chart_plot) {
		this.chart_plot = chart_plot;
	}
}
