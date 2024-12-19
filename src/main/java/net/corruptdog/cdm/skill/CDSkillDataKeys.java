package net.corruptdog.cdm.skill;

import net.corruptdog.cdm.main.CDmoveset;
import net.corruptdog.cdm.skill.Dodge.SStep;
import net.corruptdog.cdm.skill.Passive.BloodWolf;
import net.corruptdog.cdm.skill.weaponinnate.DualDaggerSkill;
import net.corruptdog.cdm.skill.weaponinnate.YamatoAttack;
import net.corruptdog.cdm.skill.weaponinnate.YamatoSkill;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.main.EpicFightMod;
import yesman.epicfight.skill.SkillDataKey;

public class CDSkillDataKeys {
    public static final DeferredRegister<SkillDataKey<?>> DATA_KEYS = DeferredRegister.create(new ResourceLocation(EpicFightMod.MODID, "skill_data_keys"), CDmoveset.MOD_ID);
    public static final RegistryObject<SkillDataKey<Integer>> COUNT = DATA_KEYS.register("count", () ->
            SkillDataKey.createIntKey(0, false, SStep.class));
    public static final RegistryObject<SkillDataKey<Integer>> DIRECTION = DATA_KEYS.register("direction", () ->
            SkillDataKey.createIntKey(0, false, SStep.class));
    public static final RegistryObject<SkillDataKey<Integer>> RESET_TIMER = DATA_KEYS.register("reset_timer", () ->
            SkillDataKey.createIntKey(0, false, SStep.class));
    public static final RegistryObject<SkillDataKey<Boolean>> DODGE_PLAYED = DATA_KEYS.register("dodge_played", () ->
            SkillDataKey.createBooleanKey(false, false, SStep.class));

    public static final RegistryObject<SkillDataKey<Integer>> WOLFPASSIVE = DATA_KEYS.register("wolfpassive", () ->
            SkillDataKey.createIntKey(0, false, BloodWolf.class));
    public static final RegistryObject<SkillDataKey<Integer>> TIMER = DATA_KEYS.register("timer", () ->
            SkillDataKey.createIntKey(0, false, BloodWolf.class));
    public static final RegistryObject<SkillDataKey<Integer>> DAMAGE = DATA_KEYS.register("damage", () ->
            SkillDataKey.createIntKey(0, false, BloodWolf.class));


    public static final RegistryObject<SkillDataKey<Integer>> DAMAGES = DATA_KEYS.register("damages", () ->
            SkillDataKey.createIntKey(0, false, YamatoSkill.class));
    public static final RegistryObject<SkillDataKey<Boolean>> COUNTER_SUCCESS = DATA_KEYS.register("counter_success", () ->
            SkillDataKey.createBooleanKey(false, false, YamatoSkill.class));
    public static final RegistryObject<SkillDataKey<Boolean>> COUNTER = DATA_KEYS.register("counter", () ->
            SkillDataKey.createBooleanKey(false, false, YamatoSkill.class));
    public static final RegistryObject<SkillDataKey<Integer>> COMBO_COUNTER = DATA_KEYS.register("combo_counter", () ->
            SkillDataKey.createIntKey(0, false, YamatoAttack.class));
    public static final RegistryObject<SkillDataKey<Boolean>> POWER3 = DATA_KEYS.register("power3", () ->
            SkillDataKey.createBooleanKey(false, false, YamatoSkill.class));

//    public static final RegistryObject<SkillDataKey<Boolean>> FATAL = DATA_KEYS.register("fatal", () ->
//            SkillDataKey.createBooleanKey(false, false, YamatoSkill.class));

    public static final RegistryObject<SkillDataKey<Integer>> RUSH_COMBO = DATA_KEYS.register("rush_combo", () ->
            SkillDataKey.createIntKey(0, false, DualDaggerSkill.class));

    public static final RegistryObject<SkillDataKey<Boolean>> RUSH_STAR = DATA_KEYS.register("rush_end", () ->
            SkillDataKey.createBooleanKey(false, false, DualDaggerSkill.class));
//
//
//    public static final RegistryObject<SkillDataKey<Float>> SLASH_COUNTER = DATA_KEYS.register("slash_counter", () ->
//            SkillDataKey.createFloatKey(0F, false, YamatoArt.class));
//    public static final RegistryObject<SkillDataKey<Boolean>> COUNTER_SUCCESSED = DATA_KEYS.register("counter_successed", () ->
//            SkillDataKey.createBooleanKey(false, false, YamatoArt.class));
//    public static final RegistryObject<SkillDataKey<Boolean>> COUNTERS = DATA_KEYS.register("counter", () ->
//            SkillDataKey.createBooleanKey(false, false, YamatoArt.class));



}
