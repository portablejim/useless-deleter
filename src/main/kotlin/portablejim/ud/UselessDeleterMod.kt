package portablejim.ud

import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityInject
import net.minecraftforge.common.config.Configuration
import net.minecraftforge.common.network.ForgeNetworkHandler
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.SidedProxy
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.network.NetworkRegistry
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.items.IItemHandler
import net.minecraftforge.oredict.ShapedOreRecipe
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import portablejim.ud.capabilities.UsedCapabilities
import portablejim.ud.config.Config
import portablejim.ud.items.FilteredDeleter
import portablejim.ud.proxy.CommonProxy
import portablejim.ud.storage.GuiHandler

const val MOD_ID: String = "uselessdeleter"

/**
 * UselessDeleter Mod file.
 */
@Mod(modid = MOD_ID,
        modLanguage = "kotlin", modLanguageAdapter = "portablejim.ud.repack.adapter.KotlinAdapter",
        //modLanguage = "kotlin", modLanguageAdapter = "io.drakon.forge.kotlin.KotlinAdapter",
        guiFactory = "portablejim.ud.config.ConfigGuiFactory")
object UselessDeleterMod {
    @SidedProxy(
            clientSide = "portablejim.ud.proxy.ClientProxy",
            serverSide = "portablejim.ud.proxy.CommonProxy"
    )
    lateinit var proxy: CommonProxy

    // Items
    lateinit var filteredDeleter: FilteredDeleter
    lateinit var strictFilteredDeleter: FilteredDeleter
    lateinit var openFilteredDeleter: FilteredDeleter

    lateinit var guiHandler: GuiHandler
    lateinit var config: Configuration

    // Capabilities
    lateinit var INVENTORY_CAP: Capability<IItemHandler>

    var log: Logger = LogManager.getLogger(MOD_ID)

    @Mod.EventHandler
    fun preinit(evt: FMLPreInitializationEvent) {
        //log = evt.modLog
        log.info("PRE")

        config = Configuration(evt.suggestedConfigurationFile)
        Config.syncConfig(config)

        // Items
        filteredDeleter = FilteredDeleter("filtered_deleter", true, true)
        strictFilteredDeleter = FilteredDeleter("filtered_deleter_strict", true, false)
        openFilteredDeleter = FilteredDeleter("filtered_deleter_open", false, false)

        filteredDeleter.unlocalizedName = "${MOD_ID}.filtered"
        strictFilteredDeleter.unlocalizedName = "${MOD_ID}.filteredstrict"
        openFilteredDeleter.unlocalizedName = "${MOD_ID}.filteredopen"

        GameRegistry.register(filteredDeleter)
        GameRegistry.register(strictFilteredDeleter)
        GameRegistry.register(openFilteredDeleter)


        proxy.specialPreInit()
    }

    @Mod.EventHandler
    fun init(evt: FMLInitializationEvent) {
        // Hack because I don't have kotlin introspection.
        INVENTORY_CAP = UsedCapabilities.ITEM_HANDLER_CAPABILITY

        MinecraftForge.EVENT_BUS.register(filteredDeleter)

        GameRegistry.addRecipe(ShapedOreRecipe(ItemStack(filteredDeleter), "SPS", "PHP", "RPR", 'S', "string", 'P', "paper", 'H', Blocks.HOPPER, 'R', "dustRedstone"))
        GameRegistry.addRecipe(ShapedOreRecipe(ItemStack(strictFilteredDeleter), "OPO", "PHP", "OPO", 'O', "obsidian", 'P', "paper", 'H', Blocks.HOPPER))

        guiHandler = GuiHandler()
        NetworkRegistry.INSTANCE.registerGuiHandler(this, guiHandler)
    }

    @Mod.EventHandler
    fun postinit(evt: FMLPostInitializationEvent) {
    }

}