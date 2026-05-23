package com.otgcam.agent.model

/**
 * Represents a WebRTC signaling message exchanged via Telegram between Agent and Receiver.
 * Serialized to JSON and sent as text messages through the Telegram Bot API.
 *
 * Event types:
 * - "call_request" → Receiver initiates a call to Agent (Receiver's offer in sdp)
 * - "call_answer" → Agent responds to call request (Agent's answer in sdp)
 * - "call_end" → Either side ends the call
 * - "live_start" → Agent initiates live stream (Agent's offer in sdp)
 * - "live_end" → Agent ends live stream
 * - "ice_candidate" → ICE candidate for NAT traversal (candidate field populated)
 */
data class CallSignal(
    val event: String,              // "call_request", "call_answer", "call_end", etc.
    val sdp: String? = null,        // Session Description Protocol offer/answer
    val candidate: String? = null,  // ICE candidate JSON string
    val videoEnabled: Boolean = false,  // Whether call includes video
    val agentId: String = ""        // Target agent ID (must match receiver's configured value)
)
