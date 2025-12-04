import 'package:shared_preferences/shared_preferences.dart';

class SettingsService {
  static const String _keyCostPerCigarette = 'cost_per_cigarette';
  static const String _keyPackSize = 'pack_size';
  static const String _keySmokingStartDate = 'smoking_start_date';

  Future<void> setCostPerCigarette(double cost) async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.setDouble(_keyCostPerCigarette, cost);
  }

  Future<double> getCostPerCigarette() async {
    final prefs = await SharedPreferences.getInstance();
    return prefs.getDouble(_keyCostPerCigarette) ?? 21.0; // Default 21 Yen
  }

  Future<void> setPackSize(int size) async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.setInt(_keyPackSize, size);
  }

  Future<int> getPackSize() async {
    final prefs = await SharedPreferences.getInstance();
    return prefs.getInt(_keyPackSize) ?? 20;
  }
  
  Future<void> setSmokingStartDate(int timestamp) async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.setInt(_keySmokingStartDate, timestamp);
  }
  
  Future<int?> getSmokingStartDate() async {
    final prefs = await SharedPreferences.getInstance();
    return prefs.getInt(_keySmokingStartDate);
  }
}
