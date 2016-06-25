package portablejim.ud

import net.minecraft.creativetab.CreativeTabs
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.SidedProxy
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.registry.GameRegistry
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import portablejim.ud.items.FilteredDeleter
import portablejim.ud.proxy.CommonProxy

/**
 * UselessDeleter Mod file.
 */
@Mod(modid = UselessDeleterMod.MODID, modLanguage = "kotlin", modLanguageAdapter = "io.drakon.forge.kotlin.KotlinAdapter")
object UselessDeleterMod {
    const val MODID: String = "uselessdeleter"

    @SidedProxy(
            clientSide = "portablejim.ud.proxy.ClientProxy",
            serverSide = "portablejim.ud.proxy.CommonProxy"
    )
    lateinit var proxy: CommonProxy

    // Items
    lateinit var filteredDeleter: FilteredDeleter

    var log: Logger = LogManager.getLogger(MODID)

    @Mod.EventHandler
    fun preinit(evt: FMLPreInitializationEvent) {
        //log = evt.modLog
        log.info("PRE")

        // Items
        filteredDeleter = FilteredDeleter("filtered_deleter")

        filteredDeleter.creativeTab = CreativeTabs.TOOLS
        filteredDeleter.unlocalizedName = "${MODID}.filtered"

        GameRegistry.register(filteredDeleter)


        proxy.specialPreInit()
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