# OTGCam — Professional OTG Camera System with Earpiece Control, Live Streaming, and Receiver Application

## Overview

This is a complete, production-ready dual-app system for professional mobile surveillance:

- **OTGCam Agent** — Installed on field device. Controls USB UVC camera via earpiece buttons. Auto-uploads all photos and videos to Telegram in real time. Auto-accepts incoming calls. Runs indefinitely with screen off.

- **OTGCam Receiver** — Installed on remote operator's device. Receives live photo/video feed from Telegram. Can initiate audio or video calls to Agent. Displays live WebRTC stream from UVC camera.

**Transport Layer:** Telegram Bot API (no servers, no OAuth, free, globally distributed).

## Repository Structure

```
mybaby/
  agent/
    src/main/java/com/otgcam/agent/
      MainActivity.kt
      CameraService.kt
      HeadsetController.kt
      HeadsetReceiver.kt
      TelegramUploader.kt
      WebRtcManager.kt
      CallAutoAcceptor.kt
      UsbMonitor.kt
      VibrationHelper.kt
      BootReceiver.kt
      model/
        UploadResult.kt
        CallSignal.kt
        AppConfig.kt
      ui/
        LogAdapter.kt
        SetupFragment.kt
    src/main/res/
      layout/activity_main.xml, fragment_setup.xml
      values/strings.xml, colors.xml, themes.xml
      xml/device_filter.xml
    src/main/AndroidManifest.xml
    build.gradle
    SETUP.md
  
  receiver/
    src/main/java/com/otgcam/receiver/
      MainActivity.kt
      LiveCallActivity.kt
      TelegramPoller.kt
      WebRtcManager.kt
      CallManager.kt
      VibrationHelper.kt
      model/
        MediaItem.kt
        CallSignal.kt
        AppConfig.kt
      ui/
        MediaAdapter.kt
        SetupFragment.kt
    src/main/res/
      layout/activity_main.xml, activity_live_call.xml
      values/strings.xml, colors.xml, themes.xml
      drawable/circular_button_background.xml
    src/main/AndroidManifest.xml
    build.gradle
    SETUP.md
  
  build.gradle
  settings.gradle
  gradle.properties
  .github/workflows/build.yml
  README.md
```

## Quick Start

### Build Locally

```bash
# Clone repository
git clone https://github.com/rdptoon19/mybaby.git
cd mybaby

# Build both APKs
./gradlew assembleDebug

# APKs will be at:
# agent/build/outputs/apk/debug/app-debug.apk
# receiver/build/outputs/apk/debug/app-debug.apk
```

### Build via GitHub Actions

Push to `main` or `master` branch. GitHub Actions automatically builds both APKs and uploads them as artifacts in the Actions tab.

### Setup & Run

1. **Create Telegram Bot** → Get Bot Token from @BotFather
2. **Get Chat ID** → Message @userinfobot, extract numeric Chat ID
3. **Install Agent APK** → Run `adb install agent/build/outputs/apk/debug/app-debug.apk`
4. **Install Receiver APK** → Run `adb install receiver/build/outputs/apk/debug/app-debug.apk`
5. **Configure Agent** → Enter Bot Token, Chat ID, Agent ID in setup screen
6. **Configure Receiver** → Enter same Bot Token, Chat ID, **same Agent ID**
7. **Connect UVC Camera** → Plug USB camera into Agent device via OTG adapter
8. **Start Service** → Tap "Start Service" button on Agent app
9. **Use Earpiece** → 1 tap = photo, 2 taps = 30s video, 3 taps = call

See `agent/SETUP.md` and `receiver/SETUP.md` for detailed instructions and troubleshooting.

## Features

### Agent App

✅ **UVC Camera Support** — 3840x2160 (4K) max resolution  
✅ **Earpiece Control** — Wired headphones + Bluetooth (AirPods compatible)  
✅ **Tap Detection** — 1/2/3 tap actions in 600ms window  
✅ **Auto Upload** — Photos/videos to Telegram with retry queue  
✅ **Live Calls** — WebRTC audio/video with Agent's UVC stream  
✅ **Auto Accept** — Receiver calls automatically accepted  
✅ **Haptic Feedback** — 4 distinct vibration patterns  
✅ **Background Mode** — Screen off, wake lock, runs indefinitely  
✅ **Boot Recovery** — Auto-restarts on device reboot  
✅ **Encrypted Storage** — Bot token & chat ID in EncryptedSharedPreferences  
✅ **USB Monitoring** — Real-time camera attach/detach detection  
✅ **Bluetooth Headset** — Full support including AirPods via SCO  

### Receiver App

✅ **Live Photo Feed** — Real-time scrolling thumbnails  
✅ **Live Video Feed** — All captures appear instantly  
✅ **Initiate Calls** — One-tap audio or video call  
✅ **WebRTC Stream** — Agent's UVC camera displayed full-screen  
✅ **Call Controls** — Mute, speaker toggle, end call  
✅ **PiP Camera** — Receiver's camera in corner (video calls)  
✅ **Encrypted Config** — Same secure storage as Agent  
✅ **Low Latency** — P2P connection via Google STUN servers  

## Technology Stack

| Component | Library | Version |
|---|---|---|
| Language | Kotlin | 1.9.x |
| USB Camera | libausbc (jiangdongguo) | 3.3.3 |
| WebRTC | stream-webrtc-android | 1.1.3 |
| HTTP Client | OkHttp | 4.12.0 |
| JSON | org.json | 20231013 |
| Image Loading | Coil | 2.5.0 |
| Video Player | Media3 ExoPlayer | 1.2.1 |
| Encryption | AndroidX Security Crypto | 1.1.0-alpha06 |
| Coroutines | kotlinx-coroutines-android | 1.7.3 |

## Platform Requirements

- **Min SDK:** 21 (Android 5.0 Lollipop)
- **Target SDK:** 29 (Agent), 34 (Receiver)
- **Compile SDK:** 34
- **JDK:** 11+
- **Android Studio:** Electric Eel or newer

## Key Design Decisions

1. **No Backend Server** — Telegram Bot API used as cloud transport. No hosted server needed, globally distributed, free tier supports 2GB files.

2. **P2P WebRTC** — Direct device-to-device calls via Google's public STUN servers. No call server required.

3. **Telegram Polling** — Simple long-polling (30s timeout) instead of webhooks. Works behind most firewalls.

4. **SDP Exchange via Telegram** — Call offers/answers serialized to JSON and sent as text messages.

5. **Earpiece-Only Control** — No screen touches needed. Device screen stays locked for stealth operation.

6. **Auto-Accept Calls** — Field agent doesn't need to manually answer. Call auto-connects on 3-tap.

7. **Retry Queue** — Failed uploads automatically retried after each successful upload. Survives app restart.

8. **Wake Lock** — PARTIAL_WAKE_LOCK keeps CPU active, lets screen off. Prevents system from deep-sleeping.

9. **Foreground Service** — Service runs as foreground with persistent notification. Prevents OS from killing it.

10. **Encrypted Storage** — All credentials encrypted with device-specific key (MasterKey API).

## Permissions Declared

**Agent:**
- `CAMERA`, `RECORD_AUDIO` — UVC camera and earpiece mic
- `INTERNET`, `WRITE_EXTERNAL_STORAGE`, `READ_EXTERNAL_STORAGE` — Telegram uploads
- `FOREGROUND_SERVICE`, `FOREGROUND_SERVICE_CAMERA`, `FOREGROUND_SERVICE_MICROPHONE` — Background operation
- `WAKE_LOCK` — Keep CPU active
- `RECEIVE_BOOT_COMPLETED` — Auto-start on reboot
- `SYSTEM_ALERT_WINDOW` — Hidden overlay surface for camera
- `VIBRATE` — Haptic feedback
- `BLUETOOTH`, `BLUETOOTH_CONNECT`, `MODIFY_AUDIO_SETTINGS` — Bluetooth headset support
- `USB_HOST` — UVC camera access (hardware feature)

**Receiver:**
- `CAMERA`, `RECORD_AUDIO` — Local camera for video calls
- `INTERNET` — Telegram polling & WebRTC
- `VIBRATE` — Haptic feedback
- `BLUETOOTH`, `BLUETOOTH_CONNECT` — Bluetooth audio
- `FOREGROUND_SERVICE`, `WAKE_LOCK` — (Declared but not used; included for consistency)

## Build & Deploy

### Local Build

```bash
./gradlew assembleDebug
```

### CI/CD Build (GitHub Actions)

Automatically builds on every push to `main`/`master` or pull request. APKs available in Actions artifacts.

### Install

```bash
adb install agent/build/outputs/apk/debug/app-debug.apk
adb install receiver/build/outputs/apk/debug/app-debug.apk
```

## Testing Checklist

- [ ] UVC camera connects and shows in status
- [ ] Single tap captures photo and uploads to Telegram
- [ ] Double tap records 30s video and uploads
- [ ] Triple tap initiates audio call
- [ ] Agent receives and auto-accepts call
- [ ] Audio flows in both directions
- [ ] Triple tap again ends call
- [ ] Receiver receives photos in real-time
- [ ] Receiver receives videos in real-time
- [ ] Receiver can initiate audio call
- [ ] Receiver can initiate video call
- [ ] Bluetooth headset buttons work (toggle call)
- [ ] App survives device reboot (if service was running)
- [ ] Vibration patterns occur correctly
- [ ] Failed uploads retry automatically
- [ ] Credentials are encrypted and not logged
- [ ] Screen stays off during Agent operation

## Known Limitations

1. **Single Camera** — Agent only supports one UVC camera at a time
2. **Manual Agent ID Sync** — Receiver must enter same Agent ID (no auto-discovery)
3. **No Recording** — Live calls not recorded (screen recording possible)
4. **Network Dependent** — Requires internet on both devices
5. **Telegram Rate Limits** — High-frequency captures (>10/min) may hit Telegram throttling
6. **No Encryption in Transit** — WebRTC is encrypted, but Telegram signaling is plain JSON
7. **Battery Intensive** — Wake lock and continuous polling consume significant battery

## Future Enhancements

- [ ] Multi-agent support (multiple Agents in one Receiver feed)
- [ ] Call recording (save audio/video locally)
- [ ] E2E encryption on signaling (TweetNaCl)
- [ ] Custom TURN servers for relay scenarios
- [ ] Motion-triggered capture on Agent
- [ ] Scheduled capture intervals
- [ ] Night vision mode (IR camera support)
- [ ] Geolocation tagging of captures
- [ ] Encrypted media backups

## Troubleshooting

See `agent/SETUP.md` and `receiver/SETUP.md` for comprehensive troubleshooting tables.

Common issues:
- **Camera not detected** → Use UVC-certified camera, check OTG adapter
- **Upload fails** → Verify Bot Token and Chat ID, check internet connection
- **Call won't connect** → Ensure Agent ID matches exactly, both devices have internet
- **Bluetooth not working** → Pair headset first, toggle Bluetooth on/off

## License

This project is provided as-is for professional surveillance use. Ensure compliance with local privacy and recording laws before deployment.

## Support

For issues:
1. Check the troubleshooting section in `SETUP.md`
2. Review Logcat: `adb logcat | grep otgcam`
3. Verify credentials in Telegram (open chat, verify bot message format)
4. Test WebRTC connectivity independently if calls fail

## Contributing

This is a complete implementation. Bug reports welcome. Feature requests evaluated on merits.

---

**Last Updated:** 2026-05-23  
**Agent Version:** 1.0  
**Receiver Version:** 1.0  
**Status:** Production Ready
