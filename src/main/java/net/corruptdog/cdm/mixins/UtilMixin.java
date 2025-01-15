package net.corruptdog.cdm.mixins;

import net.corruptdog.cdm.main.CDmoveset;
import net.minecraft.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = Util.class, priority = Integer.MAX_VALUE)
public abstract class UtilMixin {

    /**
     * @author mega
     * @reason change the add speed
     */
    @Overwrite
    public static long getMillis() {
        return CDmoveset.millis;
    }
}
