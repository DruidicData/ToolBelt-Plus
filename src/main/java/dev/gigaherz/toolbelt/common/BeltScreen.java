package dev.gigaherz.toolbelt.common;

import dev.gigaherz.toolbelt.ToolBelt;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class BeltScreen extends AbstractContainerScreen<BeltContainer>
{
    /**
     * The Identifier containing the chest GUI texture.
     */
    private static final Identifier GUI_TEXTURE = ToolBelt.location("textures/gui/belt.png");

    private static final int BASE_HEIGHT = 133;
    /** Y in the texture where the plain background strip behind the belt slots begins. */
    private static final int STRIP_TOP = 20;
    private static final int ROW_HEIGHT = 18;

    public BeltScreen(BeltContainer container, Inventory playerInventory, Component title)
    {
        super(container, playerInventory, title, 176, BASE_HEIGHT + container.extraHeight);
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks)
    {
        this.extractBackground(graphics, mouseX, mouseY, partialTicks);
        super.extractRenderState(graphics, mouseX, mouseY, partialTicks);
        this.extractTooltip(graphics, mouseX, mouseY);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks)
    {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        var menu = this.getMenu();
        int rows = menu.beltRows;

        // Top of the background, down to where the belt slots start
        graphics.blit(RenderPipelines.GUI_TEXTURED, GUI_TEXTURE, i, j, 0, 0, this.imageWidth, STRIP_TOP, 256, 256);
        // One plain background strip per belt row (the texture only has room for one)
        for (int r = 0; r < rows; r++)
        {
            graphics.blit(RenderPipelines.GUI_TEXTURED, GUI_TEXTURE, i, j + STRIP_TOP + r * ROW_HEIGHT, 0, STRIP_TOP, this.imageWidth, ROW_HEIGHT, 256, 256);
        }
        // The rest of the background (player inventory), shifted down by the extra rows
        int restTop = STRIP_TOP + ROW_HEIGHT;
        graphics.blit(RenderPipelines.GUI_TEXTURED, GUI_TEXTURE, i, j + restTop + menu.extraHeight, 0, restTop, this.imageWidth, BASE_HEIGHT - restTop, 256, 256);

        // Slot backgrounds for the belt
        for (int r = 0; r < rows; r++)
        {
            int slots = menu.slotsInRow(r);
            int width = slots * 18;
            int x = 7 + ((9 - slots) * 18) / 2;
            graphics.blit(RenderPipelines.GUI_TEXTURED, GUI_TEXTURE, i + x, j + 19 + r * ROW_HEIGHT, 0, BASE_HEIGHT, width, 18, 256, 256);
        }
    }
}