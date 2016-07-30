package portablejim.ud.proxy

import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraftforge.client.model.ModelLoader
import portablejim.ud.MOD_ID
import portablejim.ud.UselessDeleterMod

/**
 * Created by james on 25/06/16.
 */
class ClientProxy: CommonProxy() {
    override fun specialPreInit() {
        val filtered_deleter_model: ModelResourceLocation = ModelResourceLocation("${MOD_ID}:filtered_deleter", "normal")
        val strict_filtered_deleter_model: ModelResourceLocation = ModelResourceLocation("${MOD_ID}:filtered_deleter_strict", "normal")
        val open_filtered_deleter_model: ModelResourceLocation = ModelResourceLocation("${MOD_ID}:filtered_deleter_open", "normal")
        ModelLoader.setCustomModelResourceLocation(UselessDeleterMod.filteredDeleter, 0, filtered_deleter_model)
        ModelLoader.setCustomModelResourceLocation(UselessDeleterMod.strictFilteredDeleter, 0, strict_filtered_deleter_model)
        ModelLoader.setCustomModelResourceLocation(UselessDeleterMod.openFilteredDeleter, 0, open_filtered_deleter_model)
    }
}