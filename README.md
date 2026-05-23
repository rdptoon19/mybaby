# OTGCam — Professional OTG Camera System

A complete, production-ready dual-app Android system for professional mobile surveillance with UVC camera support, earpiece control, live streaming, and WebRTC calling.

## Apps

- **Agent** — Installed on field device with UVC camera. Controls via earpiece buttons, auto-uploads to Telegram.
- **Receiver** — Installed on remote operator's device. Views live feed and initiates calls.

## Quick Start

```bash
# Build both APKs
./gradlew assembleDebug

# APKs at:
# agent/build/outputs/apk/debug/app-debug.apk
# receiver/build/outputs/apk/debug/app-debug.apk
```

## Technology Stack

- Kotlin 1.9.10
- Android SDK 21-34
- WebRTC (stream-webrtc-android 1.1.3)
- Telegram Bot API
- OkHttp 4.12.0

## Status

🔨 **Under Construction** — Building step by step.

See `agent/SETUP.md` and `receiver/SETUP.md` for detailed setup instructions (coming soon).
