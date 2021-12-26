package xbony2.bonemealablebabies;

import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteractSpecific;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@Mod(BonemealableBabies.MODID)
public class BonemealableBabies {
	public static final String MODID = "bonemealable_babies";

	public BonemealableBabies(){
		MinecraftForge.EVENT_BUS.register(new BoneMealHandler());
	}

	private static class BoneMealHandler {
		@SubscribeEvent
		public void boneMealAnimals(EntityInteractSpecific event){
			if(event != null
					&& !event.getWorld().isClientSide
					&& event.getItemStack().getItem() == Items.BONE_MEAL
					&& event.getTarget() != null
					&& event.getTarget() instanceof AgeableMob ageableMob
					&& ageableMob.getAge() < 0){

				// Starts at -24000, so takes 3 bone meal at most
				// The parameter is de-amplified, according to the javadoc that is now lost
				ageableMob.ageUp(8000 / 20);

				if(!event.getPlayer().getAbilities().instabuild)
					event.getItemStack().shrink(1);

				// This makes the particles appear
				// below(1) is needed bc the addGrowthParticles requires a solid block, which it puts the particles atop
				event.getWorld().levelEvent(1505, event.getPos().below(1), 1);
			}
		}
	}
}
