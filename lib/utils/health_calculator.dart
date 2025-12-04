class HealthCalculator {
  
  static double calculateCancerRisk(int dailyCount) {
    if (dailyCount <= 0) return 1.0;
    if (dailyCount < 10) return 2.18;
    if (dailyCount < 15) return 3.59;
    if (dailyCount < 20) return 4.70;
    if (dailyCount < 30) return 5.87;
    if (dailyCount < 40) return 5.95;
    if (dailyCount < 50) return 7.17;
    return 15.07;
  }

  static int calculateBrinkmanIndex(int dailyCount, double yearsSmoked) {
    return (dailyCount * yearsSmoked).round();
  }

  static String getBrinkmanRiskLevel(int index) {
    if (index < 400) return 'Not Serious';
    if (index < 500) return 'Little Danger';
    if (index < 600) return 'Low Danger';
    if (index < 1000) return 'Danger';
    if (index < 1200) return 'High Danger';
    return 'Extreme Danger';
  }
  
  static double calculateLifeLostMinutes(int totalCigarettes) {
    // Estimate: 1 cigarette = 11 minutes of life lost
    return totalCigarettes * 11.0;
  }
}
