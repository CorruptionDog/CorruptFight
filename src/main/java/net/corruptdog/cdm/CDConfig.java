package net.corruptdog.cdm;

import net.corruptdog.cdm.main.CDmoveset;
import net.minecraftforge.common.ForgeConfigSpec;

public class CDConfig {

    public static final ForgeConfigSpec.BooleanValue ENABLE_DODGE_SOUND;
    public static final ForgeConfigSpec.BooleanValue ENABLE_DODGESUCCESS_SOUND;
    public static final ForgeConfigSpec.BooleanValue SLOW_TIME;
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec CLIENT_SPEC;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.push("CorruptFight config");
        ENABLE_DODGE_SOUND = createBool(builder, "enable_dodge_sound", false, "enable dodge sound");
        ENABLE_DODGESUCCESS_SOUND = createBool(builder, "enable_dodgesuccess_sound", true, "enable dodgesuccess sound");
        SLOW_TIME = createBool(builder, "slow_time", false, "enable slow_time");
        builder.pop();
        SPEC = builder.build();

        ForgeConfigSpec.Builder clientBuilder = new ForgeConfigSpec.Builder();
        CLIENT_SPEC = clientBuilder.build();
    }


    private static ForgeConfigSpec.BooleanValue createBool(ForgeConfigSpec.Builder builder, String key, boolean defaultValue, String... comment) {
        return builder
                .translation("config." + "Resurrection" + ".common." + key)
                .comment(comment)
                .define(key, defaultValue);
    }
}
