package portablejim.ud.storage

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.ClickType
import net.minecraft.inventory.Container
import net.minecraft.inventory.Slot
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraftforge.items.ItemStackHandler
import net.minecraftforge.items.SlotItemHandler
import portablejim.ud.items.FilteredDeleter
import portablejim.ud.storage.util.SlotDisabled
import portablejim.ud.storage.util.SlotFilter

class FilterContainer(playerInventory: InventoryPlayer, val filterHandler: FilterItemHandler) : Container() {
    init {

        val rows = 1
        val invHeightPx = (rows - 4) * 18

        run {
            val invRow = 0
            for (invColumn in 0..8) {
                addSlotToContainer(SlotFilter(filterHandler, invColumn + invRow * 9, 8 + invColumn * 18, 18 + invRow * 18))
            }
        }

        for(invRow in 0..2) {
            for(invColumn in 0..8) {
            this.addSlotToContainer(Slot(playerInventory, invColumn + invRow * 9 + 9, 8 + invColumn * 18, 102 + invRow * 18 + invHeightPx + 1));
        }
        }

        for(invColumn in 0..8) {
            if(invColumn == playerInventory.currentItem) {
                this.addSlotToContainer(SlotDisabled(playerInventory, invColumn, 8 + invColumn * 18, 161 + invHeightPx));
            }
            else {
                this.addSlotToContainer(Slot(playerInventory, invColumn, 8 + invColumn * 18, 161 + invHeightPx));

            }
        }

    }


    override fun canInteractWith(player: EntityPlayer): Boolean {
        return true
    }

    override fun onContainerClosed(playerIn: EntityPlayer?) {

        if(playerIn != null) {
            filterHandler.saveSlots()
        }
        super.onContainerClosed(playerIn)
    }

    override fun detectAndSendChanges() {
        super.detectAndSendChanges()
    }

    override fun slotClick(slotId: Int, clickedButton: Int, mode: ClickType?, playerIn: EntityPlayer): ItemStack? {
        if (slotId >= 0 && slotId < 9) {
            if (mode == ClickType.PICKUP || mode == ClickType.PICKUP_ALL ||
                    mode == ClickType.SWAP)
            // 1 is shift-click
            {
                val slot = this.inventorySlots[slotId]

                val dropping = playerIn.inventory.itemStack

                if (dropping != null) {
                    val copy = dropping.copy()
                    copy.stackSize = 1
                    slot.putStack(copy)
                } else if (slot.stack != null) {
                    slot.putStack(null)
                }

                detectAndSendChanges()

                return null
            }

            return null
        }

        return super.slotClick(slotId, clickedButton, mode, playerIn)
    }

    override fun transferStackInSlot(player: EntityPlayer?, slotIndex: Int): ItemStack? {
        if (slotIndex < 8) {
            return null
        }

        val slot = this.inventorySlots[slotIndex]
        if (slot == null || !slot.hasStack) {
            return null
        }

        val stack = slot.stack
        val stackCopy = stack!!.copy()

        val startIndex: Int
        val endIndex: Int

        if (slotIndex < 9) {
            return null
        } else if (slotIndex < 18) {
            startIndex = 18
            endIndex = 18 + 27 + 9
        } else {
            startIndex = 9
            endIndex = 18
        }

        if (!this.mergeItemStack(stack, startIndex, endIndex, false)) {
            return null
        }

        if (stack.stackSize == 0) {
            slot.putStack(null)
        } else {
            slot.onSlotChanged()
        }

        if (stack.stackSize == stackCopy.stackSize) {
            return null
        }

        slot.onPickupFromSlot(player, stack)
        return stackCopy
    }
}
