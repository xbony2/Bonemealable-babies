package xbony2.bonemealablebabies;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.init.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteractSpecific;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = BonemealableBabies.MODID, version = BonemealableBabies.VERSION)
public class BonemealableBabies {
	public static final String MODID = "bonemealablebabies";
	public static final String VERSION = "1.0.0a";

	@EventHandler
	public void init(FMLInitializationEvent event){
		MinecraftForge.EVENT_BUS.register(new BoneMealHanlder());
	}
	
	private static class BoneMealHanlder {
		@SubscribeEvent
		public void boneMealAnimals(EntityInteractSpecific event){
			if(event.getItemStack() != null && event.getItemStack().getItem() == Items.DYE && event.getItemStack().getItemDamage() == 15 && event.getTarget() != null && event.getTarget() instanceof EntityAgeable && ((EntityAgeable)event.getTarget()).getGrowingAge() < 0){
				((EntityAgeable)event.getTarget()).addGrowth(24000); //This should work
				if(!event.getEntityPlayer().capabilities.isCreativeMode)
					event.getItemStack().stackSize--;
			}
		}
	}
}
