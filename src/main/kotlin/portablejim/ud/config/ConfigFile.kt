package portablejim.ud.config

import net.minecraftforge.common.config.Configuration
import net.minecraftforge.oredict.OreDictionary
import java.util.*

/**
 * Created by james on 27/06/16.
 */

val DELETE_BLOCKS_DEFAULT: List<String> = listOf(
        "minecraft:stone",
        "minecraft:cobblestone",
        "minecraft:coarse_dirt",
        "minecraft:clay",
        "minecraft:clay_ball",
        "minecraft:hardened_clay",
        "minecraft:stained_hardened_clay",
        "minecraft:netherrack",
        "minecraft:sand",
        "minecraft:sandstone",
        "minecraft:soul_sand",
        "minecraft:poisonous_potato",
        "minecraft:snowball",
        "minecraft:packed_ice"
)
val DELETE_BLOCKS_DESCRIPTION = "Blocks that are seen to be useless. Used as the blocks able to be put in the Filtered Deleter"

val DELETE_OREDICT_DEFAULT: List<String> = listOf(
        "cobblestone",
        "stone"
)
val DELETE_OREDICT_DESCRIPTION = "Also include the blocks in these oredict entries to the ones in useless_blocks"

object Config {

    var whitelist: MutableList<String> = mutableListOf()
    var whitelist_oredict: MutableList<String> = mutableListOf()

    fun syncConfig(configFile: Configuration) {

        whitelist = configFile.getStringList("useless_blocks", Configuration.CATEGORY_GENERAL.toString(), DELETE_BLOCKS_DEFAULT.toTypedArray(), DELETE_BLOCKS_DESCRIPTION).toMutableList() ?: mutableListOf<String>()
        whitelist_oredict = configFile.getStringList("useless_oredictionary", Configuration.CATEGORY_GENERAL.toString(), DELETE_OREDICT_DEFAULT.toTypedArray(), DELETE_OREDICT_DESCRIPTION).toMutableList() ?: mutableListOf<String>()

        built_whitelist = buildWhitelist(whitelist, whitelist_oredict)

        if(configFile.hasChanged()) {
            configFile.save();
        }
    }

    var built_whitelist = whitelist.toSet()

    fun buildWhitelist(initialEntries: List<String>, entriesToAdd: List<String>): Set<String> {

        val oredictEntries = entriesToAdd.map { OreDictionary.getOres(it) }.flatten()
        val oredictStringsAny = oredictEntries.map { it.item?.registryName }.plus(initialEntries).filterNotNull()
        val oredictStrings: List<String> = oredictStringsAny.filterIsInstance<String>()

        return oredictStrings.toSet()
    }

}