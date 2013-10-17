package coolalias.redstonehelper;

import net.minecraft.block.Block;

public class LogicGates
{
	// Generic filler block on which to place redstone wire / torches / etc.
	public static final int BASE = 4096;
	
	private static final int
		// Color for wool blocks denoting input and output locations of circuit
		INPUT = 3, OUTPUT = 14, INOUT = Block.cloth.blockID,
		
		// Generic filler block on which to place redstone wire / torches / etc.
		// ERROR: causes exception error during static initialization, need to change to static 'load' method called from main mod
		//BASE = RedstoneHelper.getBaseBlockID(),
		//BASE = Block.dirt.blockID,
		
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
		{{{INOUT,INPUT},{INOUT,OUTPUT},{INOUT,INPUT}}},
	    {{{WIRE},{WIRE},{WIRE}}}
	};
	
	public static final int[][][][] orGateTall =
	{
	    {{{},{INOUT,OUTPUT},{INOUT,INPUT}}},
	    {{{BASE},{WIRE},{WIRE}}},
	    {{{REPEATER,2},{INOUT,INPUT}}}
	};
	
	public static final int[][][][] norGateFlat =
	{
		{
			{{INOUT,INPUT},{BASE},{INOUT,INPUT}},
			{{},{TORCH,1}},
			{{},{WIRE}}
		},
	    {{{WIRE},{WIRE},{WIRE}}}
	};
	
	public static final int[][][][] norGateTall =
	{
	    {{{},{},{INOUT,OUTPUT}}},
	    {{{INOUT,INPUT},{TORCH,3},{WIRE}}},
	    {{{WIRE}}},
	    {{{INOUT,INPUT}}}
	};
}
