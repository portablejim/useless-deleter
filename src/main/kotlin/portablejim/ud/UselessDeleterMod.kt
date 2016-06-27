package portablejim.ud

import net.minecraft.item.ItemStack
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityInject
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
import portablejim.ud.items.FilteredDeleter
import portablejim.ud.proxy.CommonProxy
import portablejim.ud.storage.GuiHandler

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

    lateinit var guiHandler: GuiHandler

    // Capabilities
    lateinit var INVENTORY_CAP: Capability<IItemHandler>

    var log: Logger = LogManager.getLogger(MODID)

    @Mod.EventHandler
    fun preinit(evt: FMLPreInitializationEvent) {
        //log = evt.modLog
        log.info("PRE")

        // Items
        filteredDeleter = FilteredDeleter("filtered_deleter")

        filteredDeleter.unlocalizedName = "${MODID}.filtered"

        GameRegistry.register(filteredDeleter)


        proxy.specialPreInit()
    }

    @Mod.EventHandler
    fun init(evt: FMLInitializationEvent) {
        // Hack because I don't have kotlin introspection.
        INVENTORY_CAP = UsedCapabilities.ITEM_HANDLER_CAPABILITY

        MinecraftForge.EVENT_BUS.register(filteredDeleter)

        GameRegistry.addRecipe(ShapedOreRecipe(ItemStack(filteredDeleter), "SSS", "SCS", "SSS", 'S', "cobblestone", 'C', "chestWood"))

        guiHandler = GuiHandler()
        NetworkRegistry.INSTANCE.registerGuiHandler(this, guiHandler)
    }

    @Mod.EventHandler
    fun postinit(evt: FMLPostInitializationEvent) {
    }

}