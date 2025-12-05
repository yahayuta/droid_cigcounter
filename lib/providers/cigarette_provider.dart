import 'package:flutter/foundation.dart';
import 'package:droid_cigcounter/database/database_helper.dart';
import 'package:droid_cigcounter/models/cigarette_log.dart';
import 'package:droid_cigcounter/services/settings_service.dart';
import 'package:droid_cigcounter/services/widget_service.dart';

class CigaretteProvider with ChangeNotifier {
  final DatabaseHelper _dbHelper = DatabaseHelper.instance;
  final SettingsService _settingsService = SettingsService();

  int _todayCount = 0;
  double _costPerCigarette = 21.0;
  List<CigaretteLog> _logs = [];

  int get todayCount => _todayCount;
  double get costPerCigarette => _costPerCigarette;
  List<CigaretteLog> get logs => _logs;

  CigaretteProvider() {
    _loadData();
  }

  Future<void> _loadData() async {
    await _loadSettings();
    await _loadTodayCount();
    await _loadLogs();
  }

  Future<void> _loadSettings() async {
    _costPerCigarette = await _settingsService.getCostPerCigarette();
    notifyListeners();
  }

  Future<void> _loadTodayCount() async {
    final now = DateTime.now();
    final startOfDay = DateTime(now.year, now.month, now.day).millisecondsSinceEpoch;
    final endOfDay = DateTime(now.year, now.month, now.day, 23, 59, 59).millisecondsSinceEpoch;
    
    _todayCount = await _dbHelper.countLogsByDateRange(startOfDay, endOfDay);
    await WidgetService.updateWidget(_todayCount);
    notifyListeners();
  }

  Future<void> _loadLogs() async {
    _logs = await _dbHelper.readAllLogs();
    notifyListeners();
  }

  Future<void> addCigarette() async {
    final log = CigaretteLog(timestamp: DateTime.now().millisecondsSinceEpoch);
    await _dbHelper.create(log);
    await _loadTodayCount();
    await _loadLogs();
  }

  Future<void> updateCost(double newCost) async {
    await _settingsService.setCostPerCigarette(newCost);
    _costPerCigarette = newCost;
    notifyListeners();
  }
  
  Future<void> resetAllData() async {
    await _dbHelper.deleteAll();
    await _loadTodayCount();
    await _loadLogs();
  }
}
