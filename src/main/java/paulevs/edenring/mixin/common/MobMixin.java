package paulevs.edenring.mixin.common;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Mob.class)
public abstract class MobMixin extends EntityMixin{

    @Shadow
    public abstract <T extends Mob> T convertTo(EntityType entityType, boolean keepInventory);

}
