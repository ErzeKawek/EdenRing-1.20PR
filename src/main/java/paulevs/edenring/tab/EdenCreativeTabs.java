package paulevs.edenring.tab;

import paulevs.edenring.EdenRing;
import paulevs.edenring.registries.EdenBlocks;

public class EdenCreativeTabs {

    public static void register() {
        org.betterx.wover.tabs.api.CreativeTabs
                .start(EdenRing.C)
                .createTab("edenring")
                .setIcon(EdenBlocks.EDEN_MOSS)
                .buildAndAdd()
                .processRegistries()
                .registerAllTabs();
    }
}
