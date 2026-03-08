package justme.dailyrewards.commands.debug

import justme.dailyrewards.utils.manager.DailyClaimManager
import justme.dailyrewards.utils.MessageUtils
import justme.dailyrewards.RewardCard
import justme.dailyrewards.RewardOffer
import justme.dailyrewards.DailyRewardsClient
import justme.dailyrewards.RewardFetcher
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.IntegerArgumentType
import net.minecraft.client.MinecraftClient
import org.slf4j.LoggerFactory
import kotlin.random.Random

object DebugCommand {
    private val logger = LoggerFactory.getLogger("[DailyRewards-Debug]")
    
    fun register() {
        ClientCommandRegistrationCallback.EVENT.register { dispatcher, registryAccess ->
            registerDebugCommands(dispatcher)
        }
    }
    
    private fun registerDebugCommands(dispatcher: CommandDispatcher<FabricClientCommandSource>) {
        dispatcher.register(
            ClientCommandManager.literal("dailyrewards-debug")
                .then(ClientCommandManager.literal("timezone")
                    .executes { context ->
                        showTimezoneDebug()
                        1
                    }
                )
                .then(ClientCommandManager.literal("times")
                    .executes { context ->
                        showDisplayTimes()
                        1
                    }
                )
                .then(ClientCommandManager.literal("full")
                    .executes { context ->
                        showFullDebugInfo()
                        1
                    }
                )
        )
        
        dispatcher.register(
            ClientCommandManager.literal("debugcards")
                .executes {
                    generateDebugCards()
                }
                .then(ClientCommandManager.literal("streak")
                    .then(ClientCommandManager.argument("value", IntegerArgumentType.integer(0))
                        .executes { context ->
                            val streakValue = IntegerArgumentType.getInteger(context, "value")
                            generateDebugCardsWithStreak(streakValue)
                        }
                    )
                )
        )
    }
    
    private fun showTimezoneDebug() {
        logger.info("=== TIMEZONE DEBUG ===")
        
        val hypixelTime = DailyClaimManager.getCurrentHypixelTime()
        val localTime = DailyClaimManager.getCurrentLocalTime()
        
        MessageUtils.sendInfo("🌍 Timezone Debug:")
        MessageUtils.sendMessage("§7Hypixel (EST): §f$hypixelTime")
        MessageUtils.sendMessage("§7Your timezone: §f$localTime")
    }
    
    private fun showDisplayTimes() {
        logger.info("=== DISPLAY TIMES DEBUG ===")
        
        val displayTimes = DailyClaimManager.getDisplayTimes()
        MessageUtils.sendInfo("🕐 Current Times:")
        displayTimes.lines().forEach { line ->
            if (line.isNotBlank()) {
                MessageUtils.sendMessage("§7$line")
            }
        }
    }
    
    private fun showFullDebugInfo() {
        logger.info("=== FULL DEBUG INFO ===")
        
        val debugInfo = DailyClaimManager.getDebugInfo()
        MessageUtils.sendInfo("🔍 Full Debug Info:")
        debugInfo.lines().forEach { line ->
            if (line.isNotBlank()) {
                if (line.startsWith("===")) {
                    MessageUtils.sendMessage("§e$line")
                } else if (line.contains("🕐") || line.contains("📅") || line.contains("🔄") || line.contains("📊") || line.contains("🌍")) {
                    MessageUtils.sendMessage("§6$line")
                } else {
                    MessageUtils.sendMessage("§7$line")
                }
            }
        }
    }

    private fun generateDebugCards(): Int {
        return generateDebugCardsWithStreak(null)
    }

    private fun generateDebugCardsWithStreak(customStreak: Int?): Int {
        val originalStreak = RewardFetcher.currentStreak
        val originalHighest = RewardFetcher.highestStreak
        
        if (customStreak != null) {
            RewardFetcher.currentStreak = customStreak
            RewardFetcher.currentBarStep = when {
                customStreak <= 8 -> customStreak
                else -> 8
            }
            if (customStreak > RewardFetcher.highestStreak) {
                RewardFetcher.highestStreak = customStreak
            }
            logger.info("Debug cards with custom streak: $customStreak (was $originalStreak), score=${RewardFetcher.currentBarStep}")
        } else {
            logger.info("Generating debug cards with current streak: $originalStreak")
        }
        
        val commonOptions = listOf(
            Pair("Coins", "coins") to 1000..10000,
            Pair("Mystery Dust", "dust") to 1..20,
            Pair("Coins", "coins") to 250..5000
        )

        val rareOptions = listOf(
            Pair("Souls", "souls") to 2..10,
            Pair("BedWars XP", "experience") to 100..1000,
            Pair("SkyWars Tokens", "coins") to 1..5
        )

        val epicOptions = listOf(
            Pair("Hypixel XP", "experience") to 1000..5000,
            Pair("Debug Card", "chest_open") to 1..2
        )
        
        val legendaryOptions = listOf(
            Pair("Reward Token", "adsense_token") to 1..1,
            Pair("Mystery Box", "mystery_box") to 1..1
        )
        
        fun <T> pick(list: List<T>) = list[Random.nextInt(list.size)]
        
        val cards = listOf(
            generateCard(pick(commonOptions), "common"),
            generateCard(pick(commonOptions), "common"),
            generateCard(pick(rareOptions), "rare"),
            generateCard(pick(epicOptions), "epic"),
            generateCard(pick(legendaryOptions), "legendary")
        )
        
        val offer = RewardOffer("debug", cards)
        
        DailyRewardsClient.setPendingOffer(offer)
        
        val streakInfo = if (customStreak != null) {
            " (Streak: $customStreak)"
        } else {
            " (Current streak: ${RewardFetcher.currentStreak})"
        }
        
        MessageUtils.sendSuccess("🎁 Debug reward cards generated$streakInfo! Screen will open shortly.")
        logger.info("Debug cards generated successfully: ${cards.size} cards with streak: ${RewardFetcher.currentStreak}")
        
        return 1
    }
    
    private fun generateCard(optionWithRange: Pair<Pair<String, String>, IntRange>, rarity: String): RewardCard {
        val (info, range) = optionWithRange
        val (name, icon) = info
        val amount = range.random()
        
        return RewardCard(
            name = name,
            amount = amount.toString(),
            description = "Debug $name (Generated)",
            rarity = rarity,
            iconUrl = icon
        )
    }
}
