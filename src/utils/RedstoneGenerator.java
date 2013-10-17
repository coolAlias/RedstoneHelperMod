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
import coolalias.redstonehelper.RedstoneHelper;
import coolalias.redstonehelper.lib.LogHelper;
import coolalias.structuregen.StructureGeneratorBase;
import coolalias.structuregen.util.Structure;

public class RedstoneGenerator extends StructureGeneratorBase
{
	/** List storing all structures currently available */
	public static final List<Structure> structures = new LinkedList();
	
	/** List of all items required to build a structure, retrieved by structure's name */
	public static final Map<String, List<ItemStack>> itemsRequired = new HashMap();
	
	public static final Map<String, List<String>> descriptions = new HashMap();
	
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
	public int getRealBlockID(int fakeID, int customData1)
	{
		if (fakeID == LogicGates.BASE)
		{
			int id = RedstoneHelper.getBaseBlockID();
			
			if (Block.blocksList[id] == null || !Block.blocksList[id].isOpaqueCube()) {
				LogHelper.log(Level.WARNING, "Block id " + id + " is not a valid block for redstone construction; resetting to dirt");
				RedstoneHelper.setBaseBlockID(Block.dirt.blockID);
			}
			
			return RedstoneHelper.getBaseBlockID();
		}
		
		return 0;
	}

	@Override
	public void onCustomBlockAdded(World world, int x, int y, int z, int fakeID, int customData1, int customData2) {}
	
	/**
	 * Returns true if player has all items required to generate structure and consumes those items
	 */
	public final boolean checkInventory(EntityPlayer player, List<ItemStack> list)
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
			if (stack.itemID == RedstoneHelper.baseBlock.itemID)
				itemID = getRealBlockID(stack.itemID, 0);
			else if (stack.itemID == Block.redstoneWire.blockID)
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
							// allows configurable block id by using item id as placeholder
							int id = (blockArray[y][x][z][0] == LogicGates.BASE ? RedstoneHelper.baseBlock.itemID : blockArray[y][x][z][0]);
							
							if (countMap.containsKey(id)) countMap.put(id, countMap.get(id) + 1);
							else countMap.put(id, 1);
						}
					}
				}
			}
		}
		
		for (int key : countMap.keySet())
		{
			if (key == RedstoneHelper.baseBlock.itemID)
				list.add(new ItemStack(RedstoneHelper.baseBlock));
			else
				list.add(new ItemStack(Block.blocksList[key], countMap.get(key)));
		}
		
		return list;
	}
	
	/** Add structures to list */
	static
	{
		Map<String, String> desc2 = new HashMap();
		Structure structure = new Structure("orGateFlat");
		desc2.put(structure.name, "On if at least one input on");
		structure.addBlockArray(LogicGates.orGateFlat);
		structures.add(structure);
		
		structure = new Structure("orGateTall");
		desc2.put(structure.name, "On if at least one input on");
		structure.addBlockArray(LogicGates.orGateTall);
		structures.add(structure);
		
		structure = new Structure("norGateFlat");
		desc2.put(structure.name, "Off if at least one input on");
		structure.addBlockArray(LogicGates.norGateFlat);
		structures.add(structure);
		
		structure = new Structure("norGateTall");
		desc2.put(structure.name, "Off if at least one input on");
		structure.addBlockArray(LogicGates.norGateTall);
		structures.add(structure);
		
		for (Structure s : structures) {
			itemsRequired.put(s.name, getRequiredItems(s));
			List<String> desc = new LinkedList();
			desc.add("Current: " + s.name);
			desc.add(desc2.get(s.name));
			descriptions.put(s.name, desc);
		}
	}
}
