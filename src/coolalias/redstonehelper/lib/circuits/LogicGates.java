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

package coolalias.redstonehelper.lib.circuits;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.block.Block;

public class LogicGates extends CircuitBase
{
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
		{{{INOUT,OUTPUT}},{{INOUT,INPUT}},{}},
	    {{{WIRE}},{{WIRE}},{{BASE}}},
	    {{},{{INOUT,INPUT}},{{REPEATER,3}}}
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
	
	public static final int[][][][] andGateTall =
	{
		{{{WIRE}},{{TORCH,2}},{{BASE}},{{INOUT,INPUT}},{}},
		{{},{},{{WIRE}},{{TORCH,5}},{{BASE}}},
		{{},{},{{TORCH,2}},{{INOUT,INPUT}},{{REPEATER,3}}}
	};
	
	public static final int[][][][] andGateTallPiston =
	{
		{{{INOUT,OUTPUT}},{{BASE}},{{BASE}},{{INOUT,INPUT}}},
		{{{WIRE}},{{0}},{{REPEATER,3}},{{WIRE}}},
		{{},{{BASE}},{{INOUT,INPUT}},{{0}}},
		{{},{{STICKY,0}},{{WIRE}}}
	};
	
	public static final int[][][][] nandGateFlat =
	{
		{{{INOUT,INPUT},{INOUT,OUTPUT},{INOUT,INPUT}}},
		{{{TORCH,5},{WIRE},{TORCH,5}}}
	};
	
	public static final int[][][][] nandGateTall =
	{
		{{{INOUT,OUTPUT}},{{INOUT,INPUT}},{}},
		{{{WIRE}},{{TORCH,5}},{{BASE}}},
		{{{TORCH,2}},{{INOUT,INPUT}},{{REPEATER,3}}}
	};
}
