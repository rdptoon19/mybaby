package com.otgcam.agent.model

/**
 * Represents the result of a photo or video upload operation.
 * Either contains the Telegram file ID and filename on success,
 * or contains the failure reason and reference to the failed file for retry.
 */
sealed class UploadResult {
    /**
     * Upload succeeded. File is now stored on Telegram's servers.
     * @param fileId Telegram's unique identifier for this file
     * @param fileName Original filename (e.g., "photo_20260523_104512_123.jpg")
     */
    data class Success(val fileId: String, val fileName: String) : UploadResult()

    /**
     * Upload failed. File reference is saved for automatic retry.
     * @param reason Human-readable error message (e.g., "Network timeout", "401 Unauthorized")
     * @param file Reference to the local file for retry attempts
     */
    data class Failure(val reason: String, val file: java.io.File) : UploadResult()
}
