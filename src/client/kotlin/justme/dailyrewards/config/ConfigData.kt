package justme.dailyrewards.config

data class ConfigData(
    // Core Settings
    var modEnabled: Boolean = true,
    var dailyReminder: Boolean = true,
    
    // Animation Settings
    var flipAnimation: Boolean = true,
    var flipSpeed: Float = 0.5f,
    
    // Overlay Settings
    var showOverlay: Boolean = true,
    
    // Daily Claim Tracking
    var lastClaimDate: String = "",
    var lastClaimTimestamp: Long = 0L, 
    var currentStreakDays: Int = 0,
    var totalClaimsCount: Int = 0,
)
