package portablejim.ud.config

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen
import net.minecraftforge.common.config.ConfigElement
import net.minecraftforge.common.config.Configuration
import net.minecraftforge.fml.client.IModGuiFactory
import net.minecraftforge.fml.client.config.GuiConfig
import net.minecraftforge.fml.client.config.IConfigElement
import portablejim.ud.MOD_ID
import portablejim.ud.UselessDeleterMod
import portablejim.ud.UselessDeleterMod.config

/**
 * Classes for config GUI
 */

class ConfigGui(parent: GuiScreen)
        : GuiConfig(parent, ConfigElement(config.getCategory(Configuration.CATEGORY_GENERAL)).childElements,
                MOD_ID, "main", false, false, getAbridgedConfigPath(config.toString())) {
}

class ConfigGuiFactory(): IModGuiFactory {
        override fun runtimeGuiCategories(): MutableSet<IModGuiFactory.RuntimeOptionCategoryElement>? {
                return null
        }

        override fun mainConfigGuiClass(): Class<out GuiScreen>? {
                return ConfigGui::class.java
        }

        override fun getHandlerFor(element: IModGuiFactory.RuntimeOptionCategoryElement?): IModGuiFactory.RuntimeOptionGuiHandler? {
                return null
        }

        override fun initialize(minecraftInstance: Minecraft?) {
        }

}
