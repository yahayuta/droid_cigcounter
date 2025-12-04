class CostCalculator {
  static double calculateTotalCost(int totalCount, double costPerCigarette) {
    return totalCount * costPerCigarette;
  }

  static double calculateCostPerPack(double costPerCigarette, int packSize) {
    return costPerCigarette * packSize;
  }
}
