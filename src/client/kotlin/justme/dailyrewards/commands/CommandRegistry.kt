package justme.dailyrewards.commands

import justme.dailyrewards.commands.debug.DebugCommand
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import org.slf4j.LoggerFactory

object CommandRegistry {
    private val logger = LoggerFactory.getLogger("[DailyRewards-Commands]")

    fun registerAll() {
        logger.info("Registering DailyRewards commands...")
        
        registerMainCommands()
        registerDebugCommands()
        
        logger.info("All DailyRewards commands registered successfully")
    }
    
    private fun registerMainCommands() {
        MainCommands.register()
    }
    
    private fun registerDebugCommands() {
        DebugCommand.register()
    }
}
