package dqu.additionaladditions.mixin;

import com.google.gson.JsonElement;
import dqu.additionaladditions.AdditionalAdditions;
import dqu.additionaladditions.config.Config;
import dqu.additionaladditions.config.value.ConfigValues;
import net.minecraft.resource.ResourceManager;
import net.minecraft.server.ServerAdvancementLoader;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

@Mixin(ServerAdvancementLoader.class)
public class ServerAdvancementLoaderMixin {
    @Inject(method = "apply", at = @At("HEAD"))
    private void removeAdvancements(Map<Identifier, JsonElement> map, ResourceManager resourceManager, Profiler profiler, CallbackInfo ci) {
        HashSet<Identifier> toRemove = new HashSet<>();
        Iterator<Map.Entry<Identifier, JsonElement>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Identifier identifier = iterator.next().getKey();
            if (identifier.getNamespace().equals(AdditionalAdditions.namespace)) {
                switch (identifier.getPath()) {
                    case "obtain_adoghr_disc" -> { if (!Config.getBool(ConfigValues.MUSIC_DISCS)) toRemove.add(identifier); }
                    case "obtain_chicken_nugget" -> { if (!Config.getBool(ConfigValues.CHICKEN_NUGGET)) toRemove.add(identifier); }
                    case "obtain_glow_stick" -> { if (!Config.getBool(ConfigValues.GLOW_STICK)) toRemove.add(identifier); }
                    case "obtain_mysterious_bundle" -> { if (!Config.getBool(ConfigValues.MYSTERIOUS_BUNDLE)) toRemove.add(identifier); }
                    case "obtain_pocket_jukebox" -> { if (!Config.getBool(ConfigValues.POCKET_JUKEBOX)) toRemove.add(identifier); }
                    case "obtain_rose_gold" -> { if (!Config.getBool(ConfigValues.ROSE_GOLD)) toRemove.add(identifier); }
                    case "obtain_scoped_crossbow", "shot_self_scoped_crossbow" -> { if (!Config.getBool(ConfigValues.CROSSBOWS)) toRemove.add(identifier); }
                    case "use_watering_can" -> { if (!Config.getBool(ConfigValues.WATERING_CAN)) toRemove.add(identifier); }
                }
            }
        }

        for (Identifier identifier : toRemove) {
            map.remove(identifier);
        }
    }
}
