import 'package:home_widget/home_widget.dart';

class WidgetService {
  static Future<void> updateWidget(int todayCount) async {
    await HomeWidget.saveWidgetData<int>('today_count', todayCount);
    await HomeWidget.updateWidget(
      androidName: 'WidgetProvider',
    );
  }
}
