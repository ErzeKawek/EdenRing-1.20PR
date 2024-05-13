package paulevs.edenring.mixin.common;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Vex;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import paulevs.edenring.EdenRing;

@Mixin(Vex.class)
public abstract class VexMixin extends MobMixin{

    @Accessor
    abstract boolean getHasLimitedLife();

    @Accessor
    abstract int getLimitedLifeTicks();

    @Accessor("limitedLifeTicks")
    abstract void setLimitedLifeTicks(int limitedLifeTicks);

    @Inject(method="tick()V", at=@At("HEAD"), cancellable = true)
    public void considerConvertingToAllay(CallbackInfo ci) {
        if (this.level().dimension() == EdenRing.EDEN_RING_KEY) {

            // TODO: consider reverting this, as the limited life thing is only for
            //       evoker-summon vexes
            if ((this.getHasLimitedLife()) && (this.getLimitedLifeTicks() < 20)) {
                this.setLimitedLifeTicks(20);
            }
            float timeOfDay = this.level().getTimeOfDay(1.0F);
            if ((timeOfDay < 0.25) || (timeOfDay > 0.75)){
                if (!this.level().isClientSide()) {
                    this.convertTo(EntityType.ALLAY, true).tick();
                    ci.cancel();
                }

            }

        }
    }

}
