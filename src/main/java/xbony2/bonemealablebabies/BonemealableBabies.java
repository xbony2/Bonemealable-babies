package xbony2.bonemealablebabies;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.init.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteractSpecific;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = BonemealableBabies.MODID, version = BonemealableBabies.VERSION)
public class BonemealableBabies {
	public static final String MODID = "bonemealable_babies";
	public static final String VERSION = "1.1.0a";

	@EventHandler
	public void init(FMLInitializationEvent event){
		MinecraftForge.EVENT_BUS.register(new BoneMealHanlder());
	}
	
	private static class BoneMealHanlder {
		@SubscribeEvent
		public void boneMealAnimals(EntityInteractSpecific event){
			if(!event.getWorld().isRemote && event.getItemStack() != null && event.getItemStack().getItem() == Items.DYE && event.getItemStack().getItemDamage() == 15 && event.getTarget() != null && event.getTarget() instanceof EntityAgeable && ((EntityAgeable)event.getTarget()).getGrowingAge() < 0){
				((EntityAgeable)event.getTarget()).addGrowth(8000 / 20); //Starts at -24000, so takes 3 bone meal at most (also parameter is de-applified, read doc)
				if(!event.getEntityPlayer().capabilities.isCreativeMode)
					event.getItemStack().func_190918_g(1); //func_190918_g(int p_190918_1_) ~= decreaseStackSize(int amount)
				
				event.getWorld().playEvent(2005, event.getPos(), 0); //ask minecraft, not me
			}
		}
	}
}
