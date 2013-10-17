package coolalias.redstonehelper.lib;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.Configuration;

import org.lwjgl.input.Keyboard;

import coolalias.redstonehelper.handlers.RHKeyHandler;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RHKeyBindings
{
	/** Key index for easy handling */
	public static final byte PLUS_X = 0, MINUS_X = 1, PLUS_Z = 2, MINUS_Z = 3, OFFSET_Y = 4,
			INVERT_Y = 5, RESET_OFFSET = 6, ROTATE = 7, NEXT_STRUCT = 8, PREV_STRUCT = 9,
			TOGGLE_REMOVE = 10;
	
	/** Key descriptions */
	private static final String[] desc = {"PlusX", "MinusX", "PlusZ", "MinusZ", "OffsetY","InvertY",
		"Reset", "Rotate", "NextStruct", "PrevStruct", "ToggleRemove"
	};
	
	/** Default key values */
	private static final int[] keyValues = {Keyboard.KEY_UP, Keyboard.KEY_DOWN, Keyboard.KEY_RIGHT,
		Keyboard.KEY_LEFT, Keyboard.KEY_Y, Keyboard.KEY_I, Keyboard.KEY_U, Keyboard.KEY_O,
		Keyboard.KEY_RBRACKET, Keyboard.KEY_LBRACKET, Keyboard.KEY_V
	};
	
	/** Maps Keyboard values to RedstoneHelper KeyBinding values */
	public static final Map<Integer, Byte> RHKeyMap = new HashMap<Integer, Byte>();
	
	public static void init(Configuration config)
	{
		KeyBinding[] key = new KeyBinding[desc.length];
		boolean[] repeat = new boolean[desc.length];
		
		for (int i = 0; i < desc.length; ++i)
		{
			key[i] = new KeyBinding(desc[i], config.get(RHKeyHandler.label, desc[i], keyValues[i]).getInt());
			repeat[i] = false;
			RHKeyMap.put(key[i].keyCode, (byte) i);
		}
		
        KeyBindingRegistry.registerKeyBinding(new RHKeyHandler(key, repeat));
	}
}