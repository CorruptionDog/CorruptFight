package net.corruptdog.cdm;

import net.corruptdog.cdm.main.CDmoveset;
import net.minecraftforge.common.ForgeConfigSpec;

public class CDConfig {

    public static final ForgeConfigSpec.BooleanValue ENABLE_DODGE_SOUND;
    public static final ForgeConfigSpec.BooleanValue ENABLE_DODGESUCCESS_SOUND;
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec CLIENT_SPEC;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.push("闪避配置");
        ENABLE_DODGE_SOUND = createBool(builder, "enable_dodge_sound", false, "是否启用玩家闪避音效");
        ENABLE_DODGESUCCESS_SOUND = createBool(builder, "enable_dodgesuccess_sound", true, "是否启用玩家闪避成功音效");
        builder.pop();
        SPEC = builder.build();

        ForgeConfigSpec.Builder clientBuilder = new ForgeConfigSpec.Builder();
        CLIENT_SPEC = clientBuilder.build();
    }


    private static ForgeConfigSpec.BooleanValue createBool(ForgeConfigSpec.Builder builder, String key, boolean defaultValue, String... comment) {
        return builder
                .translation("config." + CDmoveset.MOD_ID + ".common." + key)
                .comment(comment)
                .define(key, defaultValue);
    }
}
