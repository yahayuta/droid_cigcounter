import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:intl/intl.dart';
import 'package:droid_cigcounter/providers/cigarette_provider.dart';

class HistoryScreen extends StatelessWidget {
  const HistoryScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Smoke Log'),
      ),
      body: Consumer<CigaretteProvider>(
        builder: (context, provider, child) {
          if (provider.logs.isEmpty) {
            return const Center(child: Text('No logs yet.'));
          }
          return ListView.builder(
            itemCount: provider.logs.length,
            itemBuilder: (context, index) {
              final log = provider.logs[index];
              final date = DateTime.fromMillisecondsSinceEpoch(log.timestamp);
              return ListTile(
                leading: const Icon(Icons.smoking_rooms),
                title: Text(DateFormat('yyyy-MM-dd HH:mm').format(date)),
                subtitle: Text('Log ID: ${log.id}'),
              );
            },
          );
        },
      ),
    );
  }
}
