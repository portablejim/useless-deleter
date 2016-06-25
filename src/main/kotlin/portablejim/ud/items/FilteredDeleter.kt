package portablejim.ud.items

import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumHand
import net.minecraft.world.World
import net.minecraftforge.items.IItemHandler
import portablejim.ud.UselessDeleterMod

/**
 * Created by james on 25/06/16.
 */

class FilteredDeleter(regName: String): Item() {
    init {
        setRegistryName(regName)
        creativeTab = CreativeTabs.TOOLS
        maxStackSize = 1
        hasSubtypes = false
        canRepair = false
    }

    val ACTIVATE_KEY: String = "UD_Activate"

    override fun onItemRightClick(itemStackIn: ItemStack?, worldIn: World?, playerIn: EntityPlayer?, hand: EnumHand?): ActionResult<ItemStack>? {
        // Has inventory capability or null
        val inventoryCap: IItemHandler? = if(playerIn?.hasCapability(UselessDeleterMod.INVENTORY_CAP, null) ?: false) {
            playerIn?.getCapability(UselessDeleterMod.INVENTORY_CAP, null) ?: null
        } else {
            null
        }

        if(inventoryCap != null && playerIn?.isSneaking ?: false) {
            val tagCompound: NBTTagCompound = itemStackIn?.tagCompound ?: NBTTagCompound()

            val activated: Boolean = tagCompound.getBoolean(ACTIVATE_KEY)

            tagCompound.setBoolean(ACTIVATE_KEY, !activated)

            itemStackIn?.tagCompound = tagCompound
        }

        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand)
    }

    override fun hasEffect(stack: ItemStack?): Boolean {
        return stack?.tagCompound?.getBoolean(ACTIVATE_KEY) ?: false
    }

}