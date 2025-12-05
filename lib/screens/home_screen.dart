import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:droid_cigcounter/providers/cigarette_provider.dart';
import 'package:droid_cigcounter/utils/cost_calculator.dart';
import 'package:droid_cigcounter/utils/health_calculator.dart';
import 'package:droid_cigcounter/screens/analytics_screen.dart';
import 'package:droid_cigcounter/screens/history_screen.dart';
import 'package:droid_cigcounter/screens/settings_screen.dart';
import 'package:droid_cigcounter/screens/health_screen.dart';

class HomeScreen extends StatelessWidget {
  const HomeScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Tobacco Counter'),
        actions: [
          IconButton(
            icon: const Icon(Icons.analytics),
            onPressed: () => Navigator.push(context, MaterialPageRoute(builder: (_) => const AnalyticsScreen())),
          ),
          IconButton(
            icon: const Icon(Icons.history),
            onPressed: () => Navigator.push(context, MaterialPageRoute(builder: (_) => const HistoryScreen())),
          ),
          IconButton(
            icon: const Icon(Icons.health_and_safety),
            onPressed: () => Navigator.push(context, MaterialPageRoute(builder: (_) => const HealthScreen())),
          ),
          IconButton(
            icon: const Icon(Icons.settings),
            onPressed: () => Navigator.push(context, MaterialPageRoute(builder: (_) => const SettingsScreen())),
          ),
        ],
      ),
      body: Consumer<CigaretteProvider>(
        builder: (context, provider, child) {
          final todayCost = CostCalculator.calculateTotalCost(provider.todayCount, provider.costPerCigarette);
          final cancerRisk = HealthCalculator.calculateCancerRisk(provider.todayCount);

          return Center(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                const Text(
                  'Today\'s Count',
                  style: TextStyle(fontSize: 24, fontWeight: FontWeight.w300),
                ),
                const SizedBox(height: 20),
                GestureDetector(
                  onTap: () => provider.addCigarette(),
                  child: Container(
                    width: 200,
                    height: 200,
                    decoration: BoxDecoration(
                      shape: BoxShape.circle,
                      color: Colors.blueGrey.shade800,
                      boxShadow: [
                        BoxShadow(
                          color: Colors.black.withValues(alpha: 0.5),
                          blurRadius: 10,
                          offset: const Offset(0, 5),
                        ),
                      ],
                    ),
                    child: Center(
                      child: Text(
                        '${provider.todayCount}',
                        style: const TextStyle(fontSize: 80, fontWeight: FontWeight.bold, color: Colors.white),
                      ),
                    ),
                  ),
                ),
                const SizedBox(height: 40),
                _buildStatCard('Today\'s Cost', '¥${todayCost.toStringAsFixed(0)}'),
                _buildStatCard('Cancer Risk', '${cancerRisk.toStringAsFixed(2)}x'),
              ],
            ),
          );
        },
      ),
    );
  }

  Widget _buildStatCard(String title, String value) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 8.0, horizontal: 32.0),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Text(title, style: const TextStyle(fontSize: 18)),
          Text(value, style: const TextStyle(fontSize: 18, fontWeight: FontWeight.bold)),
        ],
      ),
    );
  }
}
