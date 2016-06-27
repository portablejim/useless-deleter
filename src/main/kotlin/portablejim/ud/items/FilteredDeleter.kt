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
import net.minecraftforge.event.entity.player.EntityItemPickupEvent
import net.minecraftforge.fml.common.eventhandler.Event
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.items.IItemHandler
import portablejim.ud.UselessDeleterMod
import java.util.*

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

    override fun onItemRightClick(itemStackIn: ItemStack?, worldIn: World?, playerIn: EntityPlayer?, hand: EnumHand?): ActionResult<ItemStack?>? {
        // Has inventory capability or null
        val inventoryCap: IItemHandler? = if(playerIn?.hasCapability(UselessDeleterMod.INVENTORY_CAP, null) ?: false) {
            playerIn?.getCapability(UselessDeleterMod.INVENTORY_CAP, null) ?: null
        } else {
            null
        }

        if(inventoryCap != null && worldIn != null) {
            if (playerIn?.isSneaking ?: false) {
                if(!worldIn.isRemote) {
                    playerIn?.openGui(UselessDeleterMod, 0, worldIn, if(hand == EnumHand.MAIN_HAND) 0 else 1, 0, 0)
                    return ActionResult(EnumActionResult.SUCCESS, itemStackIn)
                }
            }
            else {
                toggleActive(itemStackIn)
            }
        }

        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand)
    }

    fun toggleActive(itemStackIn: ItemStack?) {
        val tagCompound: NBTTagCompound = itemStackIn?.tagCompound ?: NBTTagCompound()

        val activated: Boolean = tagCompound.getBoolean(ACTIVATE_KEY)

        tagCompound.setBoolean(ACTIVATE_KEY, !activated)

        itemStackIn?.tagCompound = tagCompound
    }

    override fun hasEffect(stack: ItemStack?): Boolean {
        return stack?.tagCompound?.getBoolean(ACTIVATE_KEY) ?: false
    }

    @SubscribeEvent
    fun pickupEvent(evt: EntityItemPickupEvent) {
        UselessDeleterMod.log.info("Pickup Event")

        // Has inventory capability or null
        val inventoryCap: IItemHandler? = evt.entityPlayer?.getCapability(UselessDeleterMod.INVENTORY_CAP, null) ?: null
        if(inventoryCap == null || evt.item == null || evt.item.entityItem == null) {
            return
        }

        var pickedUp: ItemStack = evt.item.entityItem

        var i: Int = 0


        var inventoryItems: MutableMap<Int, ItemStack> = HashMap()
        var deleters: MutableList<ItemStack> = ArrayList()

        for(invSlot in 0..inventoryCap.slots) {
            val invItem: ItemStack? = inventoryCap.getStackInSlot(invSlot)
            if(invItem != null) {
                inventoryItems.put(invSlot, invItem)
                if(invItem.item is FilteredDeleter) {
                    deleters.add(invItem)
                }
                if(pickedUp.stackSize > 0 && pickedUp.isItemEqual(invItem))
                {
                    pickedUp = inventoryCap.insertItem(invSlot, pickedUp, false) ?: ItemStack(pickedUp.item, 0)
                }
            }
        }

        for(deleter in deleters) {
            if(deleter.item is FilteredDeleter) {
                (deleter.item as FilteredDeleter).handleStack(deleter, pickedUp)
            }
        }

        if(pickedUp.stackSize > 0) {

        }
        else {
            //evt.item.isDead = true
            evt.item.setEntityItemStack(pickedUp)
            evt.result = Event.Result.ALLOW
        }


    }

    fun handleStack(deleter: ItemStack, targetStack: ItemStack) {
        val deleteList: Set<String> = setOf(
                "minecraft:stone",
                "minecraft:cobblestone",
                "minecraft:granite",
                "minecraft:diorite",
                "minecraft:andesite",
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

        if(deleter.hasEffect()) {
            if(deleteList.contains(Item.REGISTRY.getNameForObject(targetStack.item).toString())) {
                targetStack.stackSize = 0
            }
            UselessDeleterMod.log.info("Picked up: ${REGISTRY.getNameForObject(targetStack.item).toString()}")
        }
    }

}