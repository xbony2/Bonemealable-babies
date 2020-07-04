package xbony2.bonemealablebabies;

import net.minecraft.entity.AgeableEntity;
import net.minecraft.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteractSpecific;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@Mod(BonemealableBabies.MODID)
public class BonemealableBabies {
	public static final String MODID = "bonemealable_babies";

	public BonemealableBabies(){
		MinecraftForge.EVENT_BUS.register(new BoneMealHanlder());
	}

	private static class BoneMealHanlder {
		@SubscribeEvent
		public void boneMealAnimals(EntityInteractSpecific event){
			if(event != null
					&& !event.getWorld().isRemote
					&& event.getItemStack() != null && event.getItemStack().getItem() == Items.BONE_MEAL
					&& event.getTarget() != null && event.getTarget() instanceof AgeableEntity
					&& ((AgeableEntity) event.getTarget()).getGrowingAge() < 0){
				
				// Starts at -24000, so takes 3 bone meal at most (also parameter is de-applified, read doc)
				((AgeableEntity) event.getTarget()).addGrowth(8000 / 20); 

				if(!event.getPlayer().abilities.isCreativeMode)
					event.getItemStack().shrink(1);
					
				event.getWorld().playEvent(2005, event.getPos(), 0); // This makes the particles appear
			}
		}
	}
}
