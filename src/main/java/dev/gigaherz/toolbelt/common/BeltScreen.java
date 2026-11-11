package dev.gigaherz.toolbelt.common;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.gigaherz.toolbelt.ToolBelt;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class BeltScreen extends AbstractContainerScreen<BeltContainer>
{
    /**
     * The ResourceLocation containing the chest GUI texture.
     */
    private static final ResourceLocation GUI_TEXTURE = ToolBelt.location("textures/gui/belt.png");

    private static final int BASE_HEIGHT = 133;
    /** Y in the texture where the plain background strip behind the belt slots begins. */
    private static final int STRIP_TOP = 20;
    private static final int ROW_HEIGHT = 18;

    public BeltScreen(BeltContainer container, Inventory playerInventory, Component title)
    {
        super(container, playerInventory, title);
        this.imageWidth = 176;
        this.imageHeight = BASE_HEIGHT + container.extraHeight;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(graphics, mouseX, mouseY, partialTicks);
        super.render(graphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTicks, int mouseX, int mouseY)
    {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        var menu = this.getMenu();
        int rows = menu.beltRows;

        // Top of the background, down to where the belt slots start
        graphics.blit(GUI_TEXTURE, i, j, 0, 0, this.imageWidth, STRIP_TOP);
        // One plain background strip per belt row (the texture only has room for one)
        for (int r = 0; r < rows; r++)
        {
            graphics.blit(GUI_TEXTURE, i, j + STRIP_TOP + r * ROW_HEIGHT, 0, STRIP_TOP, this.imageWidth, ROW_HEIGHT);
        }
        // The rest of the background (player inventory), shifted down by the extra rows
        int restTop = STRIP_TOP + ROW_HEIGHT;
        graphics.blit(GUI_TEXTURE, i, j + restTop + menu.extraHeight, 0, restTop, this.imageWidth, BASE_HEIGHT - restTop);

        // Slot backgrounds for the belt
        for (int r = 0; r < rows; r++)
        {
            int slots = menu.slotsInRow(r);
            int width = slots * 18;
            int x = 7 + ((9 - slots) * 18) / 2;
            graphics.blit(GUI_TEXTURE, i + x, j + 19 + r * ROW_HEIGHT, 0, BASE_HEIGHT, width, 18);
        }
    }
}