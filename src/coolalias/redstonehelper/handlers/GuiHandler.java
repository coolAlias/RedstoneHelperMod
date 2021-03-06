package coolalias.redstonehelper.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import coolalias.redstonehelper.RedstoneHelper;
import coolalias.redstonehelper.client.ContainerBaseSelector;
import coolalias.redstonehelper.client.GuiBaseSelector;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{
	public GuiHandler() {}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if (ID == RedstoneHelper.guiBaseSelectorID)
			return new GuiBaseSelector(player);
		
		return null;
	}
}
