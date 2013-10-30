package coolalias.redstonehelper.client;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import coolalias.redstonehelper.RedstoneHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ContainerBaseSelector extends Container
{
	/** the list of items in this container */
	public List itemList = new ArrayList();
	
	private static final int ROWS = 6, COLUMNS = 9;

	public ContainerBaseSelector(EntityPlayer player)
	{
		for (int i = 0; i < ROWS; ++i) {
			for (int j = 0; j < COLUMNS; ++j) {
				addSlotToContainer(new Slot(GuiBaseSelector.getInventory(), i * COLUMNS + j, COLUMNS + j * 18, 35 + i * 18));
			}
		}
		
		scrollTo(0.0F);
	}

	public boolean canInteractWith(EntityPlayer player) {
		return player.getHeldItem() != null && player.getHeldItem().itemID == RedstoneHelper.logicHelper.itemID;
	}

	/**
	 * Updates the gui slots ItemStack's based on scroll position.
	 */
	public void scrollTo(float par1)
	{
		int i = itemList.size() / COLUMNS - ROWS + 1;
		int j = (int)((double)(par1 * (float) i) + 0.5D);

		if (j < 0) { j = 0; }

		for (int k = 0; k < ROWS; ++k)
		{
			for (int l = 0; l < COLUMNS; ++l)
			{
				int i1 = l + (k + j) * COLUMNS;

				if (i1 >= 0 && i1 < itemList.size()) {
					GuiBaseSelector.getInventory().setInventorySlotContents(l + k * COLUMNS, (ItemStack) itemList.get(i1));
				}
				else {
					GuiBaseSelector.getInventory().setInventorySlotContents(l + k * COLUMNS, (ItemStack) null);
				}
			}
		}
	}

	/**
	 * theCreativeContainer seems to be hard coded to 9x5 items
	 */
	public boolean hasMoreThan1PageOfItemsInList() {
		return itemList.size() > ROWS * COLUMNS;
	}

	/**
	 * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
	 */
	public ItemStack transferStackInSlot(EntityPlayer player, int par2) {
		return null;
	}
	
	/**
	 * Returns true if the player can "drag-split" items into this slot,. returns true by default. Called to check if
	 * the slot can be added to a list of Slots to split the held ItemStack across.
	 */
	public boolean canDragIntoSlot(Slot par1Slot) {
		return false;
	}
}
