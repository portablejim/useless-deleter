package portablejim.ud

import io.drakon.forge.kotlin.KotlinAdapter
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import org.apache.logging.log4j.LogManager

/**
 * Created by james on 25/06/16.
 */
@Mod(modid = "uselessdeleter", modLanguage = "kotlin", modLanguageAdapter = "io.drakon.forge.kotlin.KotlinAdapter")
object UselessDeleterMod {

    @Mod.EventHandler
    fun preinit(evt: FMLPreInitializationEvent) {
        LogManager.getLogger().info("PRE")
    }

    @Mod.EventHandler
    fun init(evt: FMLInitializationEvent) {
        LogManager.getLogger().info("INIT")
    }

    @Mod.EventHandler
    fun postinit(evt: FMLPostInitializationEvent) {
        LogManager.getLogger().info("POST")
    }

}