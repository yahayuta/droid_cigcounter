# Tobacco Counter (Cigarette Counter)

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Flutter](https://img.shields.io/badge/Flutter-3.0+-blue.svg)](https://flutter.dev/)

A cross-platform Flutter application for tracking cigarette consumption with detailed analytics, health insights, and a home screen widget. Migrated from the original native Android app to support multiple platforms.

## 📱 Features

### Core Functionality
- **Cigarette Counting**: Track each cigarette smoked with timestamp
- **Cost Tracking**: Calculate total spending based on customizable cost per cigarette
- **Home Screen Widget**: Quick access to today's count (Android)
- **Detailed Analytics**: Comprehensive statistics and trends with charts

### Analytics & Statistics
- **Weekly Charts**: Visual representation of smoking patterns
- **Daily/Monthly/Yearly Tracking**: Monitor consumption over time
- **Cost Analysis**: Total spending calculations
- **Smoking History**: Detailed log of all smoking events

### Health Information
- **Cancer Risk Assessment**: Calculate relative cancer risk compared to non-smokers
- **Brinkman Index**: Health risk assessment based on daily consumption × years
- **Life Impact**: Estimate time potentially lost due to smoking (11 min/cigarette)

### Data Management
- **Persistent Storage**: SQLite database for reliable data storage
- **Settings**: Customizable cost per cigarette
- **Reset Functionality**: Clear all data when needed

## 📖 How to Use

### 1. App Overview
The Tobacco Counter app helps you track your daily cigarette consumption. It provides an immediate count for the current day, estimated costs, and an associated health risk factor. You can also view historical data, analytics, and manage settings.

### 2. Home Screen
*   **Access:** This is the main screen you see when you open the app.
*   **Core Functionality (Count Up):** To record a cigarette, simply **tap the large circular number in the center of the screen.** The count will increase by one, and the estimated cost and health risk will update immediately.
*   **Information Displayed:**
    *   **Today's Count:** Shows the total number of cigarettes you have recorded for the current day.
    *   **Today's Cost:** Displays the estimated financial cost incurred from today's smoking, based on your recorded cigarettes and the "Cost Per Cigarette" set in settings.
    *   **Cancer Risk:** Provides an estimated cancer risk factor based on your daily consumption.
*   **Navigation:** The App Bar at the top of the screen provides quick access to other sections of the app:
    *   **Analytics:** (Graph Icon) - For detailed statistics and trends.
    *   **History:** (Clock Icon) - To view a log of all recorded cigarettes.
    *   **Health:** (Heart Icon) - For more specific health-related information.
    *   **Settings:** (Gear Icon) - To customize app parameters.

### 3. Analytics Screen
*   **Access:** Tap the **graph icon** in the Home Screen's App Bar.
*   **Purpose:** This screen offers various charts and graphs to visualize your smoking patterns over time, helping you understand your habits better.

### 4. History Screen
*   **Access:** Tap the **clock icon** in the Home Screen's App Bar.
*   **Purpose:** Here, you will find a chronological list of every cigarette you have recorded, complete with timestamps. This allows you to review your past consumption.

### 5. Health Screen
*   **Access:** Tap the **heart icon** in the Home Screen's App Bar.
*   **Purpose:** This section is designed to provide you with more in-depth information about the health impacts of smoking and potentially tips for reduction.

### 6. Settings Screen
*   **Access:** Tap the **gear icon** in the Home Screen's App Bar.
*   **Purpose:** This screen allows you to personalize the app's behavior.
    *   **Cost Per Cigarette:** You can adjust this value to accurately reflect the actual cost of your cigarettes. This setting directly influences the "Today's Cost" calculation on the Home Screen.
    *   **Reset All Data:** There is an option here to clear all your recorded cigarette data, effectively starting fresh. Use with caution as this action is usually irreversible.

## 🚀 Getting Started

### Prerequisites
- [Flutter SDK](https://flutter.dev/docs/get-started/install) (3.0 or higher)
- Android Studio / VS Code with Flutter extensions
- For Android: Android SDK
- For iOS: Xcode (macOS only)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yahayuta/droid_cigcounter.git
   cd droid_cigcounter
   ```

2. **Install dependencies**
   ```bash
   flutter pub get
   ```

3. **Run the app**
   ```bash
   # Run on connected device/emulator
   flutter run
   
   # Run on specific device
   flutter devices
   flutter run -d <device-id>
   ```

### Building

```bash
# Build APK (Android)
flutter build apk --release

# Build App Bundle (Android)
flutter build appbundle --release

# Build iOS (macOS only)
flutter build ios --release
```

## 📂 Project Structure

```
droid_cigcounter/
├── lib/
│   ├── main.dart                      # App entry point
│   ├── models/
│   │   └── cigarette_log.dart         # Data model
│   ├── database/
│   │   └── database_helper.dart       # SQLite operations
│   ├── providers/
│   │   └── cigarette_provider.dart    # State management
│   ├── services/
│   │   ├── settings_service.dart      # SharedPreferences
│   │   └── widget_service.dart        # Widget integration
│   ├── utils/
│   │   ├── health_calculator.dart     # Health calculations
│   │   └── cost_calculator.dart       # Cost calculations
│   └── screens/
│       ├── home_screen.dart           # Main counter UI
│       ├── analytics_screen.dart      # Charts & stats
│       ├── history_screen.dart        # Log history
│       ├── settings_screen.dart       # Configuration
│       └── health_screen.dart         # Health info
├── android/                           # Android platform code
├── ios/                               # iOS platform code
└── android_native/                    # Legacy Android app (archived)
```

## 🏗️ Architecture

### State Management
- **Provider**: Reactive state management for UI updates
- **ChangeNotifier**: For cigarette count and settings

### Data Layer
- **SQLite**: Local database via `sqflite` package
- **SharedPreferences**: For app settings
- **Models**: Data classes with serialization

### Dependencies
```yaml
sqflite: ^2.0.0+4           # SQLite database
path_provider: ^2.0.11      # File system access
shared_preferences: ^2.0.15 # Settings storage
intl: ^0.18.0               # Date formatting
fl_chart: ^0.55.0           # Chart visualization
home_widget: ^0.3.0         # Android widget support
provider: ^6.0.5            # State management
```

## 📊 Health Calculations

### Cancer Risk Assessment
Relative risk compared to non-smokers based on daily consumption:
- 1-9 cigarettes/day: 2.18x risk
- 10-14 cigarettes/day: 3.59x risk
- 15-19 cigarettes/day: 4.70x risk
- 20-29 cigarettes/day: 5.87x risk
- 30-39 cigarettes/day: 5.95x risk
- 40-49 cigarettes/day: 7.17x risk
- 50+ cigarettes/day: 15.07x risk

### Brinkman Index
Health risk formula: `Daily cigarettes × Years of smoking`
- < 400: Not serious
- 400-499: Little danger
- 500-599: Low danger
- 600-999: Danger
- 1000-1199: High danger
- ≥ 1200: Extreme danger

## 🔧 Configuration

### Cost Settings
- Default: ¥21 (Japanese Yen) per cigarette
- Customizable through Settings screen
- Supports any currency format

### Home Screen Widget (Android)
1. Long press on home screen
2. Select "Widgets"
3. Find "Tobacco Counter" widget
4. Add to home screen
5. Widget displays today's count and updates automatically

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🔄 Migration from Android

This Flutter version is a complete rewrite of the original native Android application. The legacy Android code is preserved in the `android_native/` directory for reference.

**Migration highlights:**
- Cross-platform support (Android, iOS, Web, Desktop)
- Modern Flutter UI with Material Design 3
- Improved state management with Provider
- Enhanced chart visualization with fl_chart
- Maintained all original features and calculations

### Android Native Version

The original Android native app in `android_native/` has been updated to the latest Android development tools:
- **Android SDK**: 35 (Android 15)
- **Android Gradle Plugin**: 8.7.3
- **Gradle**: 8.11.1
- **Java**: VERSION_21

For the original Android documentation, see [android_native/README.md](android_native/README.md).

## 📞 Support

If you encounter any issues or have questions:
- Open an issue on GitHub
- Check existing issues for solutions

## ⚠️ Disclaimer

This app is designed to help users track and understand their smoking habits. It is not intended as medical advice. Please consult healthcare professionals for smoking cessation support.

---

**Note**: Health risk calculations are based on established medical research and are provided for informational purposes only.
