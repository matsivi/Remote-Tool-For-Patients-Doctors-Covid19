# Remote Assistance Tool for Patients and Doctors

## Overview
This project provides a remote assistance tool for patients and doctors, featuring both an Android application and Python backend utilities.

## What the App Does

### Android App
- Designed for remote assistance between patients and doctors.
- Features:
  - Manage patient appointments and priorities.
  - Display information and updates about COVID-19.
  - Text-to-speech notifications for patients and health updates.
  - QR code scanning and world map visualization.
  - Fragment-based UI for switching between modules (appointments, actions, vaccination info, etc.).
  - Communicates with a backend server for real-time updates (e.g., "next patient" notifications).

### Python Tool
- Acts as a backend utility for managing patient appointments using MongoDB.
- Provides a graphical interface (Tkinter) for:
  - Adding new appointments (name, surname, date, time).
  - Viewing and refreshing the list of appointments.
  - Sending appointment data to a device over the network.
  - Notifying the system when the next patient is ready.
- Connects to a MongoDB database and communicates with the Android app/server via sockets.

## Structure
- **Android/Covid19New/**: Android app source code and configuration.
- **Python/**: Python backend utilities and configuration.

## Setup

### Android App
1. Open `Android/Covid19New/` in Android Studio.
2. Build and run the app on an emulator or device.

### Python Tool
1. Ensure Python 3.x is installed.
2. Install dependencies:
   ```
   pip install pymongo
   ```
3. Configure `app.conf` as needed.
4. Run the tool:
   ```
   python pymongo-tool.py
   ```

## Dependencies
- Android: Gradle, Android SDK
- Python: pymongo

## Author
- Marios Tsividis


