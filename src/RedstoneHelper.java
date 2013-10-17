package coolalias.redstonehelper;

import java.io.File;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import coolalias.redstonehelper.handlers.RHPacketHandler;
import coolalias.redstonehelper.lib.LogHelper;
import coolalias.redstonehelper.lib.ModInfo;
import coolalias.redstonehelper.lib.RHKeyBindings;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = ModInfo.MOD_ID, name = ModInfo.MOD_NAME, version = ModInfo.VERSION)//, dependencies = "required-after:coolAliasStructureGenMod")
@NetworkMod(clientSideRequired=true, serverSideRequired=false,
channels = {ModInfo.CHANNEL}, packetHandler = RHPacketHandler.class)

public class RedstoneHelper
{
	@Instance(ModInfo.MOD_ID)
	public static RedstoneHelper instance = new RedstoneHelper();
	
	//@SidedProxy(clientSide = ModInfo.CLIENT_PROXY, serverSide = ModInfo.COMMON_PROXY)
	//public static CommonProxy proxy;
	
	public static final int MOD_ITEM_INDEX_DEFAULT = 8889;
	
	private int modItemIndex;
	
	public static Item logicHelper;
	
	public static Item baseBlock;// = new Item(8890 - 256).setUnlocalizedName("baseBlockItem");
	
	private static int baseBlockID;
	
	public static boolean requiresMaterials;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		LogHelper.init();
		
		Configuration config = new Configuration(new File(event.getModConfigurationDirectory().getAbsolutePath() + "/RedstoneHelper.cfg"));
        config.load();
        
        modItemIndex = config.getItem("modItemIndex", MOD_ITEM_INDEX_DEFAULT).getInt() - 256;
        
        baseBlockID = config.get(Configuration.CATEGORY_BLOCK, "baseBlockID", Block.dirt.blockID).getInt();
        
        Property property = config.get(Configuration.CATEGORY_GENERAL, "Materials Required", "nothing");
        property.comment = "If true, consumes all blocks/items used in the circuit generated";
        requiresMaterials = property.getBoolean(true);
        
        if (FMLCommonHandler.instance().getSide().isClient())// && FMLCommonHandler.instance().findContainerFor(StructureGenMain.instance).)
        	RHKeyBindings.init(config);
        
        config.save();
	}
	
	@EventHandler
	public void load(FMLInitializationEvent event)
	{
		logicHelper = new ItemRedstoneHelper(modItemIndex++).setUnlocalizedName("logicHelper");
		GameRegistry.addShapelessRecipe(new ItemStack(logicHelper), Item.stick, Block.dirt);
		LanguageRegistry.addName(logicHelper, "Logic Helper");
		
		baseBlock = new Item(modItemIndex++).setUnlocalizedName("baseBlockItem");
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
	}
	
	/**
	 * Returns id of generic filler block used to build circuits/gates
	 */
	public static final int getBaseBlockID() {
		return baseBlockID;
	}
	
	/**
	 * Sets id of generic filler block used to build circuits/gates
	 */
	public static final void setBaseBlockID(int id) {
		baseBlockID = id;
	}
}
