package xbony2.bonemealablebabies;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.init.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.EntityInteractEvent;

@Mod(modid = BonemealableBabies.MODID, version = BonemealableBabies.VERSION)
public class BonemealableBabies {
	public static final String MODID = "bonemealablebabies";
	public static final String VERSION = "1.0.0a";

	@EventHandler
	public void init(FMLInitializationEvent event){
		MinecraftForge.EVENT_BUS.register(new BoneMealHanlder());
	}
	
	public static class BoneMealHanlder {
		@SubscribeEvent
		public void boneMealAnimals(EntityInteractEvent event){
			if(!event.entityPlayer.worldObj.isRemote && event.entityPlayer.inventory.mainInventory[event.entityPlayer.inventory.currentItem] != null && event.entityPlayer.inventory.mainInventory[event.entityPlayer.inventory.currentItem].getItem() == Items.dye && event.entityPlayer.inventory.mainInventory[event.entityPlayer.inventory.currentItem].getItemDamage() == 15 && event.target != null && event.target instanceof EntityAgeable && ((EntityAgeable)event.target).getGrowingAge() < 0){
				((EntityAgeable)event.target).addGrowth(8000 / 20); //Starts at -24000, so takes 3 bone meal at most (also parameter is de-applified, read doc)
				if(!event.entityPlayer.capabilities.isCreativeMode)
					event.entityPlayer.inventory.mainInventory[event.entityPlayer.inventory.currentItem].stackSize--;
				
				event.entityPlayer.worldObj.playAuxSFX(2005, (int)event.target.posX, (int)event.target.posY, (int)event.target.posZ, 0); //ask minecraft, not me
			}
		}
	}
}
