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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import coolalias.redstonehelper.lib.circuits.LogicGates;
import coolalias.structuregen.api.util.Structure;

public class LogicGateGenerator extends CircuitGeneratorBase
{
	/** List storing all structures currently available */
	public static final List<Structure> structures = new LinkedList();
	
	/** List of all items required to build a structure, retrieved by structure's name */
	public static final Map<String, Map<List<Integer>, Integer>> itemsRequired = new HashMap();
	
	public static final Map<String, List<String>> descriptions = new HashMap();
	
	public LogicGateGenerator() {}
	
	public LogicGateGenerator(EntityPlayer player) {
		super(player);
	}

	public LogicGateGenerator(Entity entity, int[][][][] blocks) {
		super(entity, blocks);
	}

	public LogicGateGenerator(Entity entity, int[][][][] blocks, int structureFacing) {
		super(entity, blocks, structureFacing);
	}

	public LogicGateGenerator(Entity entity, int[][][][] blocks, int structureFacing, int offX, int offY, int offZ) {
		super(entity, blocks, structureFacing, offX, offY, offZ);
	}
	
	/** Add structures to list */
	static
	{
		Map<String, String> desc2 = new HashMap();
		Structure structure;
		
		structure = new Structure("orGateFlat");
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
		
		structure = new Structure("andGateFlat");
		desc2.put(structure.name, "On if both inputs are on");
		structure.addBlockArray(LogicGates.andGateFlat);
		structures.add(structure);
		
		structure = new Structure("andGateFlatPiston");
		desc2.put(structure.name, "On if both inputs are on; torchless");
		structure.addBlockArray(LogicGates.andGateFlatPiston);
		structures.add(structure);
		
		structure = new Structure("andGateTall");
		desc2.put(structure.name, "On if both inputs are on");
		structure.addBlockArray(LogicGates.andGateTall);
		structures.add(structure);
		
		structure = new Structure("andGateTallPiston");
		desc2.put(structure.name, "On if both inputs are on; torchless");
		structure.addBlockArray(LogicGates.andGateTallPiston);
		structures.add(structure);
		
		structure = new Structure("nandGateFlat");
		desc2.put(structure.name, "On if both inputs are off");
		structure.addBlockArray(LogicGates.nandGateFlat);
		structures.add(structure);
		
		structure = new Structure("nandGateTall");
		desc2.put(structure.name, "On if both inputs are off");
		structure.addBlockArray(LogicGates.nandGateTall);
		structures.add(structure);
		
		for (Structure s : structures) {
			itemsRequired.put(s.name, getRequiredItems(s));
			List<String> desc = new LinkedList();
			desc.add(EnumChatFormatting.BOLD + "Circuit: " + EnumChatFormatting.RESET + EnumChatFormatting.ITALIC + s.name);
			desc.add(EnumChatFormatting.ITALIC + desc2.get(s.name));
			descriptions.put(s.name, desc);
		}
	}
}
