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

package coolalias.redstonehelper;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.block.Block;

public class LogicGates
{
	/** All configurable blocks stored here for ease of reference in calling getRealBlockID */
	public static final List<Integer> configurableBlocks = new LinkedList();
	
	// Generic filler block on which to place redstone wire / torches / etc.
	public static final int BASE = 4096, INOUT = 4097;
	
	private static final int
		// Color for wool blocks denoting input and output locations of circuit
		INPUT = 3, OUTPUT = 14,
		PISTON = Block.pistonBase.blockID,
		STICKY = Block.pistonStickyBase.blockID,
		TORCH = Block.torchRedstoneActive.blockID,
		REPEATER = Block.redstoneRepeaterIdle.blockID,
		WIRE = Block.redstoneWire.blockID,
		LEVER = Block.lever.blockID;
	
	static {
		configurableBlocks.add(BASE);
		configurableBlocks.add(INOUT);
	}
	
	/**	EAST Front: max x, Back: x = 0, Left (south): max z, Right (north): z = 0 */
	
	public static final int[][][][] notGateFlat =
	{
	    {{{WIRE},{BASE},{TORCH,3},{WIRE}}}
	};
	
	public static final int[][][][] orGateFlat =
	{
		{{{INOUT,INPUT},{INOUT,OUTPUT},{INOUT,INPUT}}},
	    {{{WIRE},{WIRE},{WIRE}}}
	};
	
	public static final int[][][][] orGateTall =
	{
	    {{{}},{{INOUT,OUTPUT}},{{INOUT,INPUT}}},
	    {{{BASE}},{{WIRE}},{{WIRE}}},
	    {{{REPEATER,1}},{{INOUT,INPUT}}}
	};
	
	public static final int[][][][] norGateFlat =
	{
		{{{},{WIRE},{}},{{},{TORCH,2}},{{INOUT,INPUT},{BASE},{INOUT,INPUT}}},
	    {{},{},{{WIRE},{WIRE},{WIRE}}}
	};
	
	public static final int[][][][] norGateTall =
	{
		{{{INOUT,OUTPUT}},{},{}},
	    {{{WIRE}},{{TORCH,2}},{{INOUT,INPUT}}},
	    {{{}},{{}},{{WIRE}}},
	    {{{}},{{}},{{INOUT,INPUT}}}
	};
	
	public static final int[][][][] andGateFlat =
	{
		{{{},{WIRE},{}},{{},{TORCH,2}},{{INOUT,INPUT},{BASE},{INOUT,INPUT}}},
	    {{},{},{{TORCH,5},{WIRE},{TORCH,5}}}
	};
	
	public static final int[][][][] andGateFlatPiston =
	{
		{{{INOUT,OUTPUT},{},{}},{{WIRE}},{{INOUT,INPUT}},{{INOUT,INPUT}}},
	    {{{WIRE}},{{BASE},{STICKY,2},{INOUT,INPUT}},{{WIRE},{},{LEVER,1}},{{LEVER,6},{},{}}}
	};
}
