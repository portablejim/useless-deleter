package portablejim.ud.items

import net.minecraft.item.Item
import portablejim.ud.UselessDeleterMod

/**
 * Created by james on 25/06/16.
 */
class FilteredDeleter(registryName: String): Item() {
    init {
        setRegistryName(registryName)
    }

}