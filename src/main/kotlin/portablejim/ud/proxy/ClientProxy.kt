package portablejim.ud.proxy

import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraftforge.client.model.ModelLoader
import portablejim.ud.UselessDeleterMod

/**
 * Created by james on 25/06/16.
 */
class ClientProxy: CommonProxy() {
    override fun specialPreInit() {
        val filtered_deleter_model: ModelResourceLocation = ModelResourceLocation("${UselessDeleterMod.MODID}:filtered_deleter", "normal")
        ModelLoader.setCustomModelResourceLocation(UselessDeleterMod.filteredDeleter, 0, filtered_deleter_model)
    }
}