package com.otgcam.agent.model

/**
 * Immutable configuration for the OTGCam Agent application.
 * Loaded from EncryptedSharedPreferences on app startup.
 * Contains all credentials and runtime settings.
 */
data class AppConfig(
    val botToken: String,           // Telegram Bot API token from @BotFather
    val chatId: String,             // Telegram chat ID (numeric or -numeric for groups)
    val agentId: String,            // Unique identifier assigned by operator (e.g., "cam-001")
    val stunServerUrl: String = "stun:stun.l.google.com:19302"  // Google's public STUN server
)
