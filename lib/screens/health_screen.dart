import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:droid_cigcounter/providers/cigarette_provider.dart';
import 'package:droid_cigcounter/utils/health_calculator.dart';

class HealthScreen extends StatelessWidget {
  const HealthScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Health Information'),
      ),
      body: Consumer<CigaretteProvider>(
        builder: (context, provider, child) {
          final dailyCount = provider.todayCount;
          // Assuming 1 year for Brinkman index demo if start date not set, or just use current count as daily average approximation
          // For accurate Brinkman, we need total smoking years. For now, we'll just show risk based on daily count.
          
          final cancerRisk = HealthCalculator.calculateCancerRisk(dailyCount);
          final lifeLost = HealthCalculator.calculateLifeLostMinutes(provider.logs.length); // Total logs

          return Padding(
            padding: const EdgeInsets.all(16.0),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                _buildInfoCard(
                  'Relative Cancer Risk',
                  '${cancerRisk.toStringAsFixed(2)}x',
                  'Compared to non-smokers, based on today\'s consumption.',
                  Colors.orange,
                ),
                const SizedBox(height: 16),
                _buildInfoCard(
                  'Life Lost (Estimated)',
                  '${(lifeLost / 60).toStringAsFixed(1)} hours',
                  'Based on total cigarettes tracked (11 mins/cig).',
                  Colors.red,
                ),
                const SizedBox(height: 20),
                const Text(
                  'Note: These are estimates based on general medical statistics and your tracked data. Consult a doctor for professional advice.',
                  style: TextStyle(fontStyle: FontStyle.italic, color: Colors.grey),
                ),
              ],
            ),
          );
        },
      ),
    );
  }

  Widget _buildInfoCard(String title, String value, String description, Color color) {
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(title, style: const TextStyle(fontSize: 18, fontWeight: FontWeight.bold)),
            const SizedBox(height: 8),
            Text(value, style: TextStyle(fontSize: 32, fontWeight: FontWeight.bold, color: color)),
            const SizedBox(height: 8),
            Text(description),
          ],
        ),
      ),
    );
  }
}
