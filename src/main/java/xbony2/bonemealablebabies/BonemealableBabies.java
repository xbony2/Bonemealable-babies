package xbony2.bonemealablebabies;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.init.Items;
import net.minecraft.item.ItemDye;
import net.minecraft.util.EnumActionResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteractSpecific;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
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
				System.out.println(":/");
				System.out.println(((EntityAgeable)event.getTarget()).getGrowingAge());
				((EntityAgeable)event.getTarget()).addGrowth(8000); //Starts at -24000, so takes 3 bone meal at most
				if(!event.getEntityPlayer().capabilities.isCreativeMode)
					event.getItemStack().stackSize--;
				ItemDye.spawnBonemealParticles(event.getWorld(), event.getPos(), 15);
			}
		}
	}
}
