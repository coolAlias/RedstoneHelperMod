package coolalias.redstonehelper.client;

import java.util.Arrays;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import coolalias.redstonehelper.ItemRedstoneHelper;
import coolalias.redstonehelper.RedstoneHelper;
import coolalias.redstonehelper.handlers.RHPacketHandler;
import coolalias.redstonehelper.lib.ModInfo;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiBaseSelector extends GuiContainer
{
	private static final ResourceLocation texturePath = new ResourceLocation(ModInfo.MOD_ID + ":textures/gui/gui_base_selector.png");
	private static InventoryBasic inventory = new InventoryBasic("tmp", true, 54);

	private static final CreativeTabs tab = CreativeTabs.tabBlock;

	private final EntityPlayer player;

	/** Used to display current base block of ItemRedstoneHelper */
	private EntityItem currentBlock;

	/** Amount scrolled in Creative mode inventory (0 = top, 1 = bottom) */
	private float currentScroll;

	/** True if the scrollbar is being dragged */
	private boolean isScrolling;

	/** True if the left mouse button was held down last time drawScreen was called. */
	private boolean wasClicking;

	public GuiBaseSelector(EntityPlayer player)
	{
		super(new ContainerBaseSelector(player));
		this.player = player;
		this.player.openContainer = this.inventorySlots;
		this.ySize = 154;
		this.xSize = 195;
	}

	// shift+click --> par4 = 1, regular click --> par4 = 0, click outside gui --> par4 = 4
	// par2 is the slot index
	// par3 is left click (0) or right click (1)
	protected void handleMouseClick(Slot par1Slot, int par2, int par3, int par4)
	{
		//System.out.println("Handling mouse click. par2: " + par2 + ", par3: " + par3 + ", par4: " + par4);
		par4 = par2 == -999 && par4 == 0 ? 4 : par4;

		if (par1Slot != null && par1Slot.inventory == inventory && par4 != 5)
		{
			ItemStack itemstack = par1Slot.getStack();

			if (itemstack != null && player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemRedstoneHelper)
			{
				currentBlock = new EntityItem(player.worldObj, 0, 0, 0, itemstack);
				player.addChatMessage("Setting base block to " + itemstack.getDisplayName());
				RHPacketHandler.sendPacketSetBaseBlock(itemstack.itemID, itemstack.getItemDamage());
			}
		}
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	public void initGui()
	{
		super.initGui();
		this.buttonList.clear();
		this.setCurrentCreativeTab();
		ItemStack held = player.getHeldItem();

		buttonList.add(new GuiButton(0, guiLeft + xSize - 50, guiTop + 2, 48, 20, RedstoneHelper.highlightIO ? "Disable" : "Enable"));

		if (held != null && held.getItem() instanceof ItemRedstoneHelper) {
			ItemStack blockStack = new ItemStack(ItemRedstoneHelper.getBaseBlockID(held),1,ItemRedstoneHelper.getBaseBlockMeta(held));
			currentBlock = new EntityItem(player.worldObj, 0, 0, 0, blockStack);
		}
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 */
	protected void drawGuiContainerForegroundLayer(int x, int y)
	{
		//tab.drawInForegroundOfTab();
		fontRenderer.drawString("Base Block", 8, 8, 4210752);
		fontRenderer.drawString("Select base block for circuits", 8, 24, 4210752);

		int buttonX = guiLeft + xSize - 50, buttonY = guiTop + 2;
		if (x > buttonX && x < buttonX + 48 && y > buttonY && y < buttonY + 20)
			drawHoveringText(Arrays.asList("Click to " + (RedstoneHelper.highlightIO ? "disable" : "enable") + " input/output highlighting"), x - guiLeft - 96, y - guiTop, fontRenderer);
	}

	/**
	 * returns (if you are not on the inventoryTab) and (the flag isn't set) and( you have more than 1 page of items)
	 */
	private boolean needsScrollBars() {
		return tab.shouldHidePlayerInventory() && ((ContainerBaseSelector) inventorySlots).hasMoreThan1PageOfItemsInList();
	}

	private void setCurrentCreativeTab()
	{
		if (tab == null) { return; }

		ContainerBaseSelector containercreative = (ContainerBaseSelector) inventorySlots;
		tab.displayAllReleventItems(containercreative.itemList);
		currentScroll = 0.0F;
		containercreative.scrollTo(0.0F);
	}

	/**
	 * Handles mouse input.
	 */
	public void handleMouseInput()
	{
		super.handleMouseInput();
		int i = Mouse.getEventDWheel();

		if (i != 0 && needsScrollBars())
		{
			double j = (double)(i > 0 ? 1 : -1) / (((ContainerBaseSelector) inventorySlots).itemList.size() / 9 - 5 + 1);

			currentScroll = (float)((double) currentScroll - j);

			if (currentScroll < 0.0F) { currentScroll = 0.0F; }

			if (currentScroll > 1.0F) { currentScroll = 1.0F; }

			((ContainerBaseSelector) inventorySlots).scrollTo(currentScroll);
		}
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	public void drawScreen(int par1, int par2, float par3)
	{
		boolean flag = Mouse.isButtonDown(0);
		int i1 = guiLeft + 175;
		int j1 = guiTop + 35;
		int k1 = i1 + 14;
		int l1 = j1 + 112;

		if (!wasClicking && flag && par1 >= i1 && par2 >= j1 && par1 < k1 && par2 < l1)
			isScrolling = needsScrollBars();

		if (!flag) { isScrolling = false; }

		wasClicking = flag;

		if (isScrolling)
		{
			currentScroll = ((float)(par2 - j1) - 7.5F) / ((float)(l1 - j1) - 15.0F);

			if (currentScroll < 0.0F) { currentScroll = 0.0F; }

			if (currentScroll > 1.0F) { currentScroll = 1.0F; }

			((ContainerBaseSelector) inventorySlots).scrollTo(currentScroll);
		}

		super.drawScreen(par1, par2, par3);
	}

	/**
	 * Draw the background layer for the GuiContainer (everything behind the items)
	 */
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderHelper.enableGUIStandardItemLighting();
		mc.getTextureManager().bindTexture(texturePath);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		drawTexturedModalRect(guiLeft + 175, guiTop + 35 + (int)((float) 91 * currentScroll), 232, 0, 12, 15);
		renderCurrentBlock();
	}

	/**
	 * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
	 */
	protected void actionPerformed(GuiButton button)
	{
		if (button.id == 0)
		{
			RedstoneHelper.highlightIO = !RedstoneHelper.highlightIO;
			button.displayString = RedstoneHelper.highlightIO ? "Disable" : "Enable";
		}
	}

	private float yaw;
	private float roll;
	private boolean rollDown;

	private void renderCurrentBlock()
	{
		GL11.glPushMatrix();
		GL11.glTranslatef(guiLeft + 75, guiTop + 18, 100);

		float scale = 50F;
		GL11.glScalef(-scale, scale, scale);

		RenderHelper.enableStandardItemLighting();

		GL11.glRotatef(180, 0, 0, 1);
		GL11.glRotatef(roll, 1, 0, 0);
		GL11.glRotatef(yaw, 0, 1, 0);

		RenderManager.instance.renderEntityWithPosYaw(currentBlock, 0, 0, 0, 0, 0);

		RenderHelper.disableStandardItemLighting();
		GL11.glPopMatrix();

		yaw += 0.5F;
		if (rollDown) {
			roll -= 0.05F;
			if (roll < -5) {
				rollDown = false;
				roll = -5;
			}
		} else {
			roll += 0.05F;
			if (roll > 25) {
				rollDown = true;
				roll = 25;
			}
		}
	}

	/**
	 * Returns the creative inventory
	 */
	static InventoryBasic getInventory() {
		return inventory;
	}
}
