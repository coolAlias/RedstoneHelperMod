package coolalias.redstonehelper;

import net.minecraft.block.Block;

public class LogicGates
{
	private static final int
		BASE = Block.dirt.blockID,
		PISTON = Block.pistonBase.blockID,
		STICKY = Block.pistonStickyBase.blockID,
		TORCH = Block.torchRedstoneActive.blockID,
		REPEATER = Block.redstoneRepeaterIdle.blockID,
		WIRE = Block.redstoneWire.blockID,
		LEVER = Block.lever.blockID;
	
	public static final int[][][][] blockArrayTemplate =
	{
		{ // y = 1
	    	{ // x = 1
	        	{}
	        }
	    }
	};
	
	public static final int[][][][] notGateFlat =
	{
	    {{{WIRE},{BASE},{TORCH,3},{WIRE}}}
	};
	
	public static final int[][][][] orGateFlat =
	{
		{{{BASE},{BASE},{BASE}}},
	    {{{WIRE},{WIRE},{WIRE}}}
	};
	
	public static final int[][][][] orGateTall =
	{
	    {{{},{BASE},{BASE}}},
	    {{{BASE},{WIRE},{WIRE}}},
	    {{{REPEATER,2},{BASE}}}
	};
	
	public static final int[][][][] norGateFlat =
	{
		{
			{{BASE},{BASE},{BASE}},
			{{},{TORCH,2}},
			{{},{WIRE}}
		},
	    {{{WIRE},{WIRE},{WIRE}}}
	};
	
	public static final int[][][][] norGateTall =
	{
	    {{{},{},{BASE}}},
	    {{{BASE},{TORCH,3},{WIRE}}},
	    {{{WIRE}}},
	    {{{BASE}}}
	};
}
