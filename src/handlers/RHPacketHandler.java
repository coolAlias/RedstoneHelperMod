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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import coolalias.redstonehelper.ItemRedstoneHelper;
import coolalias.redstonehelper.lib.LogHelper;
import coolalias.redstonehelper.lib.ModInfo;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class RHPacketHandler implements IPacketHandler
{
	/** Packet IDs */
	private static final byte PACKET_KEY_PRESS = 1, PACKET_SET_BASE_BLOCK = 2;

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
	{
		try {
			DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
			byte packetType;

			try {
				packetType = inputStream.readByte();

				switch (packetType) {
				case PACKET_KEY_PRESS: handlePacketKeyPress(packet, (EntityPlayer) player, inputStream); break;
				case PACKET_SET_BASE_BLOCK: handlePacketSetBaseBlock(packet, (EntityPlayer) player, inputStream); break;
				default: LogHelper.log(Level.SEVERE, "Unhandled packet exception for packet id " + packetType);
				}
			} finally {
				inputStream.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static final void sendPacketKeyPress(byte key)
	{
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			DataOutputStream outputStream = new DataOutputStream(bos);

			try {
				outputStream.writeByte(RHPacketHandler.PACKET_KEY_PRESS);
				outputStream.writeByte(key);
			} finally {
				outputStream.close();
			}

			PacketDispatcher.sendPacketToServer(PacketDispatcher.getPacket(ModInfo.CHANNEL, bos.toByteArray()));

		} catch (Exception ex) {
			LogHelper.log(Level.SEVERE, "Failed to send key press packet.");
			ex.printStackTrace();
		}
	}
	
	public static final void sendPacketSetBaseBlock(int id, int meta)
	{
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			DataOutputStream outputStream = new DataOutputStream(bos);

			try {
				outputStream.writeByte(RHPacketHandler.PACKET_SET_BASE_BLOCK);
				outputStream.writeInt(id);
				outputStream.writeInt(meta);
			} finally {
				outputStream.close();
			}

			PacketDispatcher.sendPacketToServer(PacketDispatcher.getPacket(ModInfo.CHANNEL, bos.toByteArray()));

		} catch (Exception ex) {
			LogHelper.log(Level.SEVERE, "Failed to send set base block packet.");
			ex.printStackTrace();
		}
	}

	private void handlePacketKeyPress(Packet250CustomPayload packet, EntityPlayer player, DataInputStream inputStream)
	{
		byte key;

		try {
			key = inputStream.readByte();

			if (player.getHeldItem() == null || !(player.getHeldItem().getItem() instanceof ItemRedstoneHelper))
				LogHelper.log(Level.SEVERE, "Held item is not an instance of ItemRedstoneHelper - unable to process key press packet");
			else
				ItemRedstoneHelper.handleKeyPressPacket(key, player.getHeldItem(), player);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void handlePacketSetBaseBlock(Packet250CustomPayload packet, EntityPlayer player, DataInputStream inputStream)
	{
		int id, meta;

		try {
			id = inputStream.readInt();
			meta = inputStream.readInt();

			if (player.getHeldItem() == null || !(player.getHeldItem().getItem() instanceof ItemRedstoneHelper))
				LogHelper.log(Level.SEVERE, "Held item is not an instance of ItemRedstoneHelper - unable to process set base block packet");
			else
				ItemRedstoneHelper.setBaseBlock(player.getHeldItem(), id, meta);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
