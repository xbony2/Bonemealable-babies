package xbony2.bonemealablebabies;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.init.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteractSpecific;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = BonemealableBabies.MODID, version = BonemealableBabies.VERSION, modLanguageAdapter = "xbony2.forgegroove.GroovyAdapter")
class BonemealableBabies {
	public static final String MODID = "bonemealable_babies"
	public static final String VERSION = "1.3.0a"

	@EventHandler
	void init(FMLPreInitializationEvent event){
		MinecraftForge.EVENT_BUS.register new BoneMealHandler()
	}
	
	static class BoneMealHandler {
		@SubscribeEvent
		void boneMealAnimals(EntityInteractSpecific event){
			if(!event.getWorld().isRemote && event.getItemStack()?.getItem() == Items.DYE && event.getItemStack().getItemDamage() == 15 && event?.getTarget() instanceof EntityAgeable && ((EntityAgeable)event.getTarget()).getGrowingAge() < 0){
				((EntityAgeable)event.getTarget()).addGrowth 8000 / 20 as int //Starts at -24000, so takes 3 bone meal at most (also parameter is de-applified, read doc)
				if(!event.getEntityPlayer().capabilities.isCreativeMode)
					event.getItemStack().shrink 1
				
				event.getWorld().playEvent 2005, event.getPos(), 0 //ask minecraft, not me
			}
		}
	}
}
