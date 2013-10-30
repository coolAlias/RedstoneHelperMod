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

package coolalias.redstonehelper.handlers;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.settings.KeyBinding;
import coolalias.redstonehelper.ItemRedstoneHelper;
import coolalias.redstonehelper.RedstoneHelper;
import coolalias.redstonehelper.lib.ModInfo;
import coolalias.redstonehelper.lib.RHKeyBindings;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RHKeyHandler extends KeyHandler
{
	public static final String label = ModInfo.MOD_NAME + " Key";
	
	private EnumSet tickTypes = EnumSet.of(TickType.PLAYER);
			
	public RHKeyHandler(KeyBinding[] keyBindings, boolean[] repeatings) {
		super(keyBindings, repeatings);
	}

	public RHKeyHandler(KeyBinding[] keyBindings) {
		super(keyBindings);
	}

	@Override
	public String getLabel() {
		return this.label;
	}

	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd, boolean isRepeat)
	{
		if (tickEnd && RHKeyBindings.RHKeyMap.containsKey(kb.keyCode) && FMLClientHandler.instance().getClient().inGameHasFocus)
		{
			EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
			
			if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemRedstoneHelper)
			{
				if (RHKeyBindings.RHKeyMap.get(kb.keyCode) == RHKeyBindings.GUI_CONFIG)
					player.openGui(RedstoneHelper.instance, RedstoneHelper.guiBaseSelectorID, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
				else
					RHPacketHandler.sendPacketKeyPress(RHKeyBindings.RHKeyMap.get(kb.keyCode));
			}
		}
	}

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) {}

	@Override
	public EnumSet<TickType> ticks() {
		return tickTypes;
	}
}
