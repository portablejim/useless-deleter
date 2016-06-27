package portablejim.ud.storage

import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.client.resources.I18n
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import org.lwjgl.opengl.GL11

class FilterGui(internal var playerInventory:

                InventoryPlayer, internal var filterInventory: FilterItemHandler) : GuiContainer(FilterContainer(playerInventory, filterInventory)) {

    private val rows: Int

    init {

        val i = 222
        val j = i - 108
        this.rows = 1 //itemInv.getSizeInventory() / 9;

        this.ySize = j + this.rows * 18
    }

    override fun drawGuiContainerForegroundLayer(par1: Int, par2: Int) {
        //this.fontRendererObj.drawString(this.seedInventory.hasCustomInventoryName() ? this.seedInventory.getInventoryName() : I18n.format(this.seedInventory.getInventoryName()), 8, 6, 4210752);
        //this.fontRendererObj.drawString(this.playerInventory.hasCustomInventoryName() ? this.playerInventory.getInventoryName() : I18n.format(this.playerInventory.getInventoryName()), 8, this.ySize - 94, 4210752);
    }

    //@Override
    @SuppressWarnings("UnusedParameters")
    override fun drawGuiContainerBackgroundLayer(par1: Float, par2: Int, par3: Int) {
        // GUI Color
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f)

        this.mc.textureManager.bindTexture(background)

        val i = (this.width - this.xSize) / 2
        val j = (this.height - this.ySize) / 2
        drawTexturedModalRect(i, j, 0, 0, this.xSize, this.rows * 18 + 17)
        drawTexturedModalRect(i, j + this.rows * 18 + 17, 0, 126, this.xSize, 96)
    }

    companion object {
        private val background = ResourceLocation("textures/gui/container/generic_54.png")
    }
}
