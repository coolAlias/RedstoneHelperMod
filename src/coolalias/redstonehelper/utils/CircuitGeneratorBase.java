/**
    Copyright (C) <2013> <coolAlias>

    This file is part of coolAlias' Redstone Helper Mod; as such,
    you can redistribute it and/or modify it under the terms of the GNU
    General Public License as published by the Free Software Foundation,
    either version 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package coolalias.redstonehelper.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.FakePlayerFactory;
import coolalias.redstonehelper.ItemRedstoneHelper;
import coolalias.redstonehelper.RedstoneHelper;
import coolalias.redstonehelper.lib.LogHelper;
import coolalias.redstonehelper.lib.circuits.CircuitBase;
import coolalias.structuregen.api.util.Structure;
import coolalias.structuregen.api.util.StructureGeneratorBase;

public abstract class CircuitGeneratorBase extends StructureGeneratorBase
{
	protected EntityPlayer player;
	
	public CircuitGeneratorBase() {}
	
	public CircuitGeneratorBase(EntityPlayer player) {
		this.player = player;
	}

	public CircuitGeneratorBase(Entity entity, int[][][][] blocks) {
		super(entity, blocks);
	}

	public CircuitGeneratorBase(Entity entity, int[][][][] blocks, int structureFacing) {
		super(entity, blocks, structureFacing);
	}

	public CircuitGeneratorBase(Entity entity, int[][][][] blocks, int structureFacing, int offX, int offY, int offZ) {
		super(entity, blocks, structureFacing, offX, offY, offZ);
	}

	@Override
	public int getRealBlockID(int fakeID, int customData1)
	{
		int id, baseID = RedstoneHelper.getBaseBlockID();
		ItemStack helper = (player != null ? player.getHeldItem() : null);
		
		
		if (helper != null && helper.getItem() instanceof ItemRedstoneHelper)
			baseID = ItemRedstoneHelper.getBaseBlockID(helper);
		
		if (fakeID == CircuitBase.BASE)
			id = baseID;
		else if (fakeID == CircuitBase.INOUT)
			id = (highlightIO(helper) ? Block.cloth.blockID : baseID);
		else {
			LogHelper.log(Level.WARNING, "Unhandled block id " + fakeID + " in getRealBlockID, returning 0 (air block).");
			id = 0;
		}
		
		if (id > 0 && (Block.blocksList[id] == null || !Block.blocksList[id].isOpaqueCube())) {
			player.addChatMessage("Block id " + id + " is not a valid block for redstone construction; resetting to dirt");
			id = Block.dirt.blockID;
			if (helper != null && helper.getItem() instanceof ItemRedstoneHelper)
				ItemRedstoneHelper.setBaseBlockID(helper, id);
			else RedstoneHelper.setBaseBlock(id);
		}
		
		return id;
	}
	
	/**
	 * Returns base block metadata value from configuration or player's held item 
	 */
	public int getRealBlockMeta()
	{
		int meta = RedstoneHelper.getBaseBlockMeta();
		ItemStack helper = (player != null ? player.getHeldItem() : null);
		
		if (helper != null && helper.getItem() instanceof ItemRedstoneHelper)
			meta = ItemRedstoneHelper.getBaseBlockMeta(helper);
		
		return meta;
	}
	
	@Override
	public void onCustomBlockAdded(World world, int x, int y, int z, int fakeID, int customData1, int customData2)
	{
		//LogHelper.log(Level.INFO, "Custom block was added.");
		ItemStack helper = (player != null ? player.getHeldItem() : null);
		
		if (helper != null && helper.getItem() instanceof ItemRedstoneHelper)
		{
			if (ItemRedstoneHelper.getBaseBlockMeta(helper) != 0 && world.getBlockId(x, y, z) == ItemRedstoneHelper.getBaseBlockID(helper)) {
				world.setBlockMetadataWithNotify(x, y, z, ItemRedstoneHelper.getBaseBlockMeta(helper), 3);
			}
		}
	}
	
	/**
	 * Returns true if player has all items required to generate structure and consumes those items
	 */
	public final boolean checkInventory(EntityPlayer player, Map<List<Integer>, Integer> countMap)
	{
		if (countMap == null || countMap.isEmpty()) {
			LogHelper.log(Level.WARNING, "Map of required items was null or empty for currently generating structure.");
			return true;
		}
		
		boolean check = true;
		InventoryPlayer temp = new InventoryPlayer(FakePlayerFactory.getMinecraft(player.worldObj)); 
		temp.copyInventory(player.inventory);
		
		for (List<Integer> keyArray : countMap.keySet())
		{
			int stacksize = countMap.get(keyArray);
			int id = keyArray.get(0), meta = keyArray.get(1);
			
			if (CircuitBase.configurableBlocks.contains(id)) {
				if (id == CircuitBase.BASE || !highlightIO(player.getHeldItem()))
					meta = getRealBlockMeta();
				id = getRealBlockID(id, 0);
			}
			else if (id == Block.redstoneWire.blockID)
				id = Item.redstone.itemID;
			else if (id == Block.redstoneRepeaterIdle.blockID)
				id = Item.redstoneRepeater.itemID;
			else if (id == Block.redstoneComparatorIdle.blockID)
				id = Item.comparator.itemID;
			
			while (stacksize > 0 && check)
			{
				ItemStack stack = new ItemStack(id, (stacksize > 63 ? 64 : stacksize % 64), meta);
				//LogHelper.log(Level.INFO, "Stack to consume: " + stack.toString());
				check = consumeInventoryItemStack(temp, stack);
				if (!check) player.addChatMessage("Not enough " + stack.getDisplayName() + "; " + stack.stackSize + " required.");
				else stacksize -= 64;
			}
			
			if (!check) break;
		}
		
		if (check) player.inventory.copyInventory(temp);
		
		return check;
	}
	
	/**
	 * Returns a list of all items necessary to build a structure
	 */
	protected static final Map<List<Integer>, Integer> getRequiredItems(Structure structure)
	{
		HashMap<List<Integer>, Integer> countMap = new HashMap<List<Integer>, Integer>();
		
		for (int[][][][] blockArray : structure.blockArrayList()) {
			for (int y = 0; y < blockArray.length; ++y) {
				for (int x = 0; x < blockArray[y].length; ++x) {
					for (int z = 0; z < blockArray[y][x].length; ++z)
					{
						if (blockArray[y][x][z].length > 0 && blockArray[y][x][z][0] > 0)
						{
							int id = blockArray[y][x][z][0];
							int meta = (blockArray[y][x][z].length > 1 ? blockArray[y][x][z][1] : 0);
							
							if (countMap.containsKey(Arrays.asList(id, meta))) countMap.put(Arrays.asList(id, meta), countMap.get(Arrays.asList(id, meta)) + 1);
							else countMap.put(Arrays.asList(id, meta), 1);
						}
					}
				}
			}
		}
		
		return countMap;
	}
	
	/**
	 * Returns whether or not to highlight input / output signal locations.
	 * If 'helper' is null, default value is used instead
	 */
	private final boolean highlightIO(ItemStack helper) {
		return (helper != null && helper.getItem() instanceof ItemRedstoneHelper ? ItemRedstoneHelper.highlightIO(helper) : RedstoneHelper.highlightIO);
	}
	
	/**
	 * Consumes one item from player inventory matching itemstack's id and meta or
	 * returns false if no matching ItemStack found; only takes metadata into account
	 * for items with subtypes, not for items with durability
	 */
	private final boolean consumeInventoryItemStack(InventoryPlayer inventory, ItemStack itemstack)
	{
		if (itemstack != null)
		{
			while (itemstack.stackSize > 0)
			{
				for (ItemStack invstack : inventory.mainInventory)
				{
					if (invstack != null && invstack.itemID == itemstack.itemID && (!itemstack.getHasSubtypes() || invstack.getItemDamage() == itemstack.getItemDamage()))
					{
						if (invstack.stackSize <= itemstack.stackSize) {
							itemstack.stackSize -= invstack.stackSize;
							invstack.stackSize = 0;
						} else {
							invstack.stackSize -= itemstack.stackSize;
							return true;
						}
					}
				}
				
				// Gone through entire inventory but stack not empty
				return false;
			}
		}
		
		return false;
	}
}
