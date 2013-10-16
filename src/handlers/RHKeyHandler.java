package coolalias.redstonehelper.handlers;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.settings.KeyBinding;
import coolalias.redstonehelper.ItemRedstoneHelper;
import coolalias.redstonehelper.lib.ModInfo;
import coolalias.redstonehelper.lib.RHKeyBindings;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.FMLCommonHandler;
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
			
			if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemRedstoneHelper) {
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
