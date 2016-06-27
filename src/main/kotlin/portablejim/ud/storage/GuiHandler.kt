package portablejim.ud.storage

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumHand
import net.minecraft.world.World
import net.minecraftforge.fml.common.network.IGuiHandler
import portablejim.ud.UselessDeleterMod
import portablejim.ud.items.FilteredDeleter

/**
 * Created by james on 26/06/16.
 */
class GuiHandler: IGuiHandler {
    override fun getClientGuiElement(ID: Int, player: EntityPlayer?, world: World?, hand: Int, y: Int, z: Int): Any? {
        val currentItem: ItemStack? = player?.getHeldItem(if(hand == 0) EnumHand.MAIN_HAND else EnumHand.OFF_HAND)

        if(currentItem != null && currentItem.item is FilteredDeleter && player!= null && player.inventory != null) {
            when (ID) {
                0 -> return FilterGui(player.inventory, FilterItemHandler(currentItem))
            }
            UselessDeleterMod.log.warn("Error loading Client GUI")
        }
        return null
    }

    override fun getServerGuiElement(ID: Int, player: EntityPlayer?, world: World?, hand: Int, y: Int, z: Int): Any? {
        val currentItem: ItemStack? = player?.getHeldItem(if(hand == 0) EnumHand.MAIN_HAND else EnumHand.OFF_HAND)

        if(currentItem != null && currentItem.item is FilteredDeleter && player!= null && player.inventory != null) {
            when (ID) {
                0 -> return FilterContainer(player.inventory, FilterItemHandler(currentItem))
            }
            UselessDeleterMod.log.warn("Error loading Server GUI: $ID")
        }
        return null
    }

}