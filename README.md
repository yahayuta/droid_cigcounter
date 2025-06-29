# Tobacco Counter (Cigarette Counter)

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Android](https://img.shields.io/badge/Android-API%2029+-green.svg)](https://developer.android.com/about/versions/android-10)
[![Gradle](https://img.shields.io/badge/Gradle-8.9-blue.svg)](https://gradle.org/)

A comprehensive Android application for tracking cigarette consumption with detailed analytics, health insights, and a home screen widget. This app helps users monitor their smoking habits, calculate costs, and understand the health implications of their smoking behavior.

## 📱 Features

### Core Functionality
- **Cigarette Counting**: Track each cigarette smoked with timestamp
- **Cost Tracking**: Calculate total spending based on customizable cost per cigarette
- **Home Screen Widget**: Quick access to today's count and last smoke time
- **Detailed Analytics**: Comprehensive statistics and trends

### Analytics & Statistics
- **Daily/Monthly/Yearly Averages**: Track consumption patterns over time
- **Cost Analysis**: Total spending and average daily/monthly/yearly costs
- **Pack Calculations**: Convert cigarette counts to pack equivalents
- **Smoking Duration**: Track total days, months, and years of smoking

### Health Information
- **Cancer Risk Assessment**: Calculate relative cancer risk compared to non-smokers
- **Brinkman Index**: Health risk assessment based on daily consumption × years
- **Life Impact**: Estimate days of life potentially lost due to smoking

### Data Management
- **Smoke Log**: Detailed history of all smoking events
- **Chart Visualization**: Graphical representation of smoking patterns
- **Data Export**: Export smoking logs for backup or analysis
- **Reset Functionality**: Clear all data when needed

## 🚀 Getting Started

### Prerequisites
- Android API level 29 (Android 10) or higher
- Android Studio (for development)
- Gradle 8.9

### Installation

#### For Users
1. Download the latest APK from the [releases](https://github.com/yourusername/droid_cigcounter/releases) page
2. Enable "Install from Unknown Sources" in your Android settings
3. Install the APK file
4. Grant necessary permissions when prompted

#### For Developers
```bash
# Clone the repository
git clone https://github.com/yourusername/droid_cigcounter.git
cd droid_cigcounter

# Open in Android Studio
# Or build from command line
./gradlew build
```

### Building the Project
```bash
# Build debug version
./gradlew assembleDebug

# Build release version
./gradlew assembleRelease

# Install on connected device
./gradlew installDebug
```

## 📊 Usage

### Main Screen
The main screen displays comprehensive smoking statistics including:
- Total cigarette count
- Total cost spent
- Daily, monthly, and yearly averages
- Pack consumption statistics

### Home Screen Widget
1. Long press on your home screen
2. Select "Widgets"
3. Find "Tobacco Counter" widget
4. Add it to your home screen
5. Tap the widget to increment your daily count

### Menu Options
- **Reset**: Clear all smoking data
- **Edit Cost Per Count**: Change the cost per cigarette
- **Analyzer**: View detailed statistics
- **Smoke Log**: View detailed smoking history
- **Smoke Log Chart**: Visual representation of smoking patterns
- **Transport Smoke Log**: Export your data
- **Health Info**: View health risk assessments

## 🏗️ Architecture

### Project Structure
```
droid_cigcounter/
├── app/
│   ├── src/main/
│   │   ├── java/droid/cigcounter/
│   │   │   ├── CigCounterActivity.java      # Main activity
│   │   │   ├── CigCounterAppWidgetProvider.java  # Widget provider
│   │   │   ├── CigCounterContentProvider.java    # Data provider
│   │   │   ├── CigCounterDBHelper.java      # Database helper
│   │   │   └── CigCounterService.java       # Background service
│   │   ├── res/
│   │   │   ├── layout/                      # UI layouts
│   │   │   ├── values/                      # String resources
│   │   │   └── drawable/                    # App icons and images
│   │   └── AndroidManifest.xml
│   └── build.gradle
├── gradle/
└── build.gradle
```

### Key Components
- **CigCounterActivity**: Main application interface and analytics
- **CigCounterAppWidgetProvider**: Home screen widget functionality
- **CigCounterDBHelper**: SQLite database operations
- **CigCounterService**: Background service for widget interactions
- **CigCounterContentProvider**: Data access layer

## 📈 Health Calculations

### Cancer Risk Assessment
The app calculates relative cancer risk based on daily cigarette consumption:
- 1 cigarette/day: 2.18x risk
- 10 cigarettes/day: 3.59x risk
- 15 cigarettes/day: 4.70x risk
- 20 cigarettes/day: 5.87x risk
- 30 cigarettes/day: 5.95x risk
- 40 cigarettes/day: 7.17x risk
- 50+ cigarettes/day: 15.07x risk

### Brinkman Index
Health risk assessment based on the formula: `Daily cigarettes × Years of smoking`
- < 400: Not serious
- 400-499: Little danger
- 500-599: Low danger
- 600-999: Danger
- 1000-1199: High danger
- ≥ 1200: Extreme danger

## 🔧 Configuration

### Cost Settings
- Default cost per cigarette: ¥21 (Japanese Yen)
- Customizable through the app menu
- Supports any currency format

### Widget Configuration
- Displays today's cigarette count
- Shows last smoke timestamp
- Tap to increment counter
- Automatic daily reset

## 📱 Screenshots

*[Screenshots would be added here showing the main interface, widget, and analytics screens]*

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request. For major changes, please open an issue first to discuss what you would like to change.

### Development Setup
1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- Health risk calculations based on established medical research
- Brinkman Index methodology from clinical studies
- Android development community for best practices

## 📞 Support

If you encounter any issues or have questions:
- Open an issue on GitHub
- Check the existing issues for solutions
- Review the app's help documentation

## 🔄 Version History

- **v3.1.0** (Current)
  - Enhanced analytics and statistics
  - Improved widget functionality
  - Better health risk assessments
  - Bug fixes and performance improvements

- **v3.0.0**
  - Major UI redesign
  - Added chart visualization
  - Enhanced data export features

- **v2.0.0**
  - Added home screen widget
  - Implemented health calculations
  - Database improvements

---

**Note**: This app is designed to help users track and understand their smoking habits. It is not intended as medical advice. Please consult healthcare professionals for smoking cessation support. 