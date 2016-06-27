package portablejim.ud.storage.util

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraftforge.items.IItemHandler
import net.minecraftforge.items.SlotItemHandler
import portablejim.ud.storage.FilterItemHandler

class SlotFilter(var filterHandler: FilterItemHandler, val index: Int, xPosition: Int, yPosition: Int) : SlotItemHandler(filterHandler, index, xPosition, yPosition) {
    override fun getSlotStackLimit(): Int {
        return 1
    }

    override fun onPickupFromSlot(par1EntityPlayer: EntityPlayer?, par2ItemStack: ItemStack?) {
    }

    override fun putStack(stack: ItemStack?) {
        filterHandler.insertItem(index, stack, false)
    }

    override fun decrStackSize(par1: Int): ItemStack? {
        return null
    }

    override fun isItemValid(par1ItemStack: ItemStack?): Boolean {
        return false
    }

    override fun canTakeStack(par1EntityPlayer: EntityPlayer?): Boolean {
        return false
    }

    override fun onSlotChange(p_75220_1_: ItemStack?, p_75220_2_: ItemStack?) {
        super.onSlotChange(p_75220_1_, p_75220_2_)
    }

    override fun onSlotChanged() {
        super.onSlotChanged()
        filterHandler.saveSlots()
    }
}
