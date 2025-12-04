import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:droid_cigcounter/providers/cigarette_provider.dart';

class SettingsScreen extends StatefulWidget {
  const SettingsScreen({super.key});

  @override
  State<SettingsScreen> createState() => _SettingsScreenState();
}

class _SettingsScreenState extends State<SettingsScreen> {
  final TextEditingController _costController = TextEditingController();

  @override
  void initState() {
    super.initState();
    final provider = Provider.of<CigaretteProvider>(context, listen: false);
    _costController.text = provider.costPerCigarette.toString();
  }

  @override
  void dispose() {
    _costController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Settings'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          children: [
            TextField(
              controller: _costController,
              keyboardType: const TextInputType.numberWithOptions(decimal: true),
              decoration: const InputDecoration(
                labelText: 'Cost per Cigarette',
                border: OutlineInputBorder(),
              ),
              onSubmitted: (value) {
                _saveCost();
              },
            ),
            const SizedBox(height: 20),
            ElevatedButton(
              onPressed: _saveCost,
              child: const Text('Save Cost'),
            ),
            const Divider(height: 40),
            ElevatedButton(
              style: ElevatedButton.styleFrom(
                backgroundColor: Colors.red,
                foregroundColor: Colors.white,
              ),
              onPressed: () => _showResetDialog(context),
              child: const Text('Reset All Data'),
            ),
          ],
        ),
      ),
    );
  }

  void _saveCost() {
    final newCost = double.tryParse(_costController.text);
    if (newCost != null) {
      Provider.of<CigaretteProvider>(context, listen: false).updateCost(newCost);
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Cost updated')),
      );
    }
  }

  void _showResetDialog(BuildContext context) {
    showDialog(
      context: context,
      builder: (ctx) => AlertDialog(
        title: const Text('Reset Data'),
        content: const Text('Are you sure you want to delete all logs? This cannot be undone.'),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(ctx),
            child: const Text('Cancel'),
          ),
          TextButton(
            onPressed: () {
              Provider.of<CigaretteProvider>(context, listen: false).resetAllData();
              Navigator.pop(ctx);
              ScaffoldMessenger.of(context).showSnackBar(
                const SnackBar(content: Text('Data reset')),
              );
            },
            child: const Text('Reset', style: TextStyle(color: Colors.red)),
          ),
        ],
      ),
    );
  }
}
