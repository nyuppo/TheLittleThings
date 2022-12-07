package com.nyuppo.world;

import com.nyuppo.TheLittleThings;
import com.nyuppo.world.region.ModRegion;
import com.nyuppo.world.surfacerule.ModSurfaceRuleData;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.TerraBlenderApi;

public class TheLittleThingsWorld implements TerraBlenderApi {
    @Override
    public void onTerraBlenderInitialized() {
        Regions.register(new ModRegion(TheLittleThings.ID("thelittlethings"), 2));

        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, TheLittleThings.MOD_ID, ModSurfaceRuleData.makeRules());
    }
}
