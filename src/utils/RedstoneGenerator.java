package coolalias.redstonehelper.utils;

import java.util.HashMap;
import java.util.LinkedList;
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
import coolalias.redstonehelper.LogicGates;
import coolalias.redstonehelper.lib.LogHelper;
import coolalias.structuregen.StructureGeneratorBase;
import coolalias.structuregen.util.Structure;

public class RedstoneGenerator extends StructureGeneratorBase
{
	/** List storing all structures currently available */
	public static final List<Structure> structures = new LinkedList();
	
	/** List of all items required to build a structure, retrieved by structure's name */
	public static final Map<String, List<ItemStack>> itemsRequired = new HashMap();
	
	public RedstoneGenerator() {}

	public RedstoneGenerator(Entity entity, int[][][][] blocks) {
		super(entity, blocks);
	}

	public RedstoneGenerator(Entity entity, int[][][][] blocks, int structureFacing) {
		super(entity, blocks, structureFacing);
	}

	public RedstoneGenerator(Entity entity, int[][][][] blocks, int structureFacing, int offX, int offY, int offZ) {
		super(entity, blocks, structureFacing, offX, offY, offZ);
	}

	@Override
	public int getRealBlockID(int fakeID, int customData1) {
		return Math.abs(fakeID);
	}

	@Override
	public void onCustomBlockAdded(World world, int x, int y, int z, int fakeID, int customData1, int customData2) {}
	
	/**
	 * Returns true if player has all items required to generate structure and consumes those items
	 */
	public static final boolean checkInventory(EntityPlayer player, List<ItemStack> list)
	{
		if (list == null || list.isEmpty()) {
			LogHelper.log(Level.WARNING, "List of required items was null or empty for currently generating structure.");
			return true;
		}
		
		boolean check = true;
		InventoryPlayer temp = new InventoryPlayer(FakePlayerFactory.getMinecraft(player.worldObj)); 
		temp.copyInventory(player.inventory);
		
		for (ItemStack stack : list)
		{
			int itemID = stack.itemID;
			if (stack.itemID == Block.redstoneWire.blockID)
				itemID = Item.redstone.itemID;
			else if (stack.itemID == Block.redstoneRepeaterIdle.blockID)
				itemID = Item.redstoneRepeater.itemID;
			else if (stack.itemID == Block.redstoneComparatorIdle.blockID)
				itemID = Item.comparator.itemID;
			
			for (int i = 0; i < stack.stackSize; ++i) {
				check = temp.consumeInventoryItem(itemID);
			}
			if (!check) {
				player.addChatMessage("Not enough " + stack.getDisplayName() + "; " + stack.stackSize + " required.");
				break;
			}
		}
		
		if (check) player.inventory.copyInventory(temp);
		
		return check;
	}
	
	/**
	 * Returns a list of all items necessary to build a structure
	 */
	private static final List<ItemStack> getRequiredItems(Structure structure)
	{
		List<ItemStack> list = new LinkedList<ItemStack>();
		HashMap<Integer, Integer> countMap = new HashMap<Integer, Integer>();
		
		for (int[][][][] blockArray : structure.blockArrayList()) {
			for (int y = 0; y < blockArray.length; ++y) {
				for (int x = 0; x < blockArray[y].length; ++x) {
					for (int z = 0; z < blockArray[y][x].length; ++z)
					{
						if (blockArray[y][x][z].length > 0)
						{
							int id = blockArray[y][x][z][0];
							
							if (countMap.containsKey(id))
								countMap.put(id, countMap.get(id) + 1);
							else
								countMap.put(id, 1);
						}
					}
				}
			}
		}
		
		for (int key : countMap.keySet())
			list.add(new ItemStack(Block.blocksList[key], countMap.get(key)));
		
		return list;
	}
	
	/** Add structures to list */
	static
	{
		Structure structure = new Structure("orGateFlat");
		structure.addBlockArray(LogicGates.orGateFlat);
		structures.add(structure);
		
		structure = new Structure("orGateTall");
		structure.addBlockArray(LogicGates.orGateTall);
		structures.add(structure);
		
		structure = new Structure("norGateFlat");
		structure.addBlockArray(LogicGates.norGateFlat);
		structures.add(structure);
		
		structure = new Structure("norGateTall");
		structure.addBlockArray(LogicGates.norGateTall);
		structures.add(structure);
		
		for (Structure s : structures)
			itemsRequired.put(s.name, getRequiredItems(s));
	}
}
