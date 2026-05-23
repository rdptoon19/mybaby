package com.otgcam.agent.model

sealed class UploadResult {
    data class Success(val fileId: String, val fileName: String) : UploadResult()
    data class Failure(val reason: String, val file: java.io.File) : UploadResult()
}
