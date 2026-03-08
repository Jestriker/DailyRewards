package justme.dailyrewards

import com.terraformersmc.modmenu.api.ModMenuApi
import com.terraformersmc.modmenu.api.ConfigScreenFactory
import net.minecraft.client.gui.screen.Screen
import justme.dailyrewards.ui.DailyRewardsConfigScreen
 
class DailyRewardsModMenu : ModMenuApi {
    override fun getModConfigScreenFactory(): ConfigScreenFactory<Screen> {
        return ConfigScreenFactory { parent: Screen? ->
            DailyRewardsConfigScreen.create(parent)
        }
    }
}
