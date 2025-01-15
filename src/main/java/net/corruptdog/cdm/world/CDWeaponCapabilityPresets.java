package net.corruptdog.cdm.world;

import java.util.function.Function;

import net.corruptdog.cdm.gameasset.CDSkills;
import net.corruptdog.cdm.gameasset.CorruptAnimations;
import net.corruptdog.cdm.gameasset.CorruptCollider;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.Tiers;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.gameasset.*;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.skill.SkillDataKeys;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.CapabilityItem.Styles;
import yesman.epicfight.world.capabilities.item.CapabilityItem.WeaponCategories;
import yesman.epicfight.world.capabilities.item.WeaponCapability;

public class CDWeaponCapabilityPresets {

    public static final Function<Item, CapabilityItem.Builder> KATANA = (item) ->
            WeaponCapability.builder()
                    .category(WeaponCategories.UCHIGATANA)
                    .styleProvider((entitypatch) -> {
                        if (entitypatch instanceof PlayerPatch<?> playerpatch && (playerpatch.getSkill(SkillSlots.WEAPON_PASSIVE).getDataManager().hasData(SkillDataKeys.SHEATH.get()) &&
                                playerpatch.getSkill(SkillSlots.WEAPON_PASSIVE).getDataManager().getDataValue(SkillDataKeys.SHEATH.get()))) {
                            return Styles.SHEATH;
                        }
                        return Styles.TWO_HAND;
                    })
                    .passiveSkill(EpicFightSkills.BATTOJUTSU_PASSIVE)
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .collider(ColliderPreset.UCHIGATANA)
                    .canBePlacedOffhand(true)
                    .newStyleCombo(Styles.SHEATH, CorruptAnimations.KATANA_SHEATHING_AUTO, CorruptAnimations.KATANA_SHEATHING_DASH, CorruptAnimations.KATANA_SHEATH_AIR_SLASH)
                    .newStyleCombo(Styles.TWO_HAND, CorruptAnimations.KATANA_AUTO1, CorruptAnimations.KATANA_AUTO2, CorruptAnimations.KATANA_AUTO3,  CorruptAnimations.YAMATO_DASH, CorruptAnimations.KATANA_AIR_SLASH)
                    .newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
                    .innateSkill(Styles.SHEATH, (itemstack) -> CDSkills.KATANASKILL)
                    .innateSkill(Styles.TWO_HAND, (itemstack) -> CDSkills.KATANASKILL)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, CorruptAnimations.BIPED_HOLD_KATANA)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, CorruptAnimations.BIPED_HOLD_KATANA)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, CorruptAnimations.WALK_KATANA)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, CorruptAnimations.WALK_KATANA)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.JUMP, Animations.BIPED_JUMP)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, CorruptAnimations.RUN_KATANA)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, CorruptAnimations.BIPED_HOLD_KATANA)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, CorruptAnimations.BIPED_HOLD_KATANA)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.FALL, CorruptAnimations.BIPED_HOLD_KATANA)
                    .livingMotionModifier(Styles.SHEATH, LivingMotions.IDLE, CorruptAnimations.BIPED_HOLD_KATANA_SHEATHING)
                    .livingMotionModifier(Styles.SHEATH, LivingMotions.KNEEL, CorruptAnimations.BIPED_HOLD_KATANA_SHEATHING)
                    .livingMotionModifier(Styles.SHEATH, LivingMotions.WALK, CorruptAnimations.BIPED_HOLD_KATANA_SHEATHING)
                    .livingMotionModifier(Styles.SHEATH, LivingMotions.CHASE, CorruptAnimations.BIPED_HOLD_KATANA_SHEATHING)
                    .livingMotionModifier(Styles.SHEATH, LivingMotions.RUN, CorruptAnimations.BIPED_HOLD_KATANA_SHEATHING)
                    .livingMotionModifier(Styles.SHEATH, LivingMotions.SNEAK, CorruptAnimations.BIPED_HOLD_KATANA_SHEATHING)
                    .livingMotionModifier(Styles.SHEATH, LivingMotions.SWIM, CorruptAnimations.BIPED_HOLD_KATANA_SHEATHING)
                    .livingMotionModifier(Styles.SHEATH, LivingMotions.FALL, CorruptAnimations.BIPED_HOLD_KATANA_SHEATHING)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.LANDING_RECOVERY, Animations.BIPED_LANDING)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.UCHIGATANA_GUARD);


    public static final Function<Item, CapabilityItem.Builder> S_GREATSWORD = (item) ->
            WeaponCapability.builder()
                    .category(CorruptWeaponCategories.S_GREATSWORD)
                    .styleProvider((playerpatch) -> playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CorruptWeaponCategories.S_GREATSWORD ? Styles.TWO_HAND : Styles.ONE_HAND)
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .swingSound(EpicFightSounds.WHOOSH_BIG.get())
                    .canBePlacedOffhand(true)
                    .collider(ColliderPreset.GREATSWORD)
                    .weaponCombinationPredicator((entitypatch) -> EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == CorruptWeaponCategories.S_GREATSWORD)
                    .newStyleCombo(Styles.ONE_HAND, CorruptAnimations.GREATSWORD_OLD_AUTO1, CorruptAnimations.GREATSWORD_OLD_AUTO2, CorruptAnimations.GREATSWORD_OLD_AUTO3, CorruptAnimations.GREATSWORD_OLD_DASH, CorruptAnimations.GREATSWORD_OLD_AIRSLASH)
                    .newStyleCombo(Styles.TWO_HAND, CorruptAnimations.DUAL_GREATSWORD_AUTO1, CorruptAnimations.DUAL_GREATSWORD_AUTO2, CorruptAnimations.DUAL_GREATSWORD_AUTO3, CorruptAnimations.DUAL_GREATSWORD_AUTO4, CorruptAnimations.DUAL_GREATSWORD_DASH, CorruptAnimations.DUAL_GREATSWORD_AIRSLASH)
                    .innateSkill(Styles.ONE_HAND, (itemstack) -> CDSkills.WIND_SLASH)
                    .innateSkill(Styles.TWO_HAND, (itemstack) -> CDSkills.DUAL_GREATSWORD_SKILL)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, CorruptAnimations.GREATSWORD_OLD_IDLE)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.JUMP, Animations.BIPED_JUMP)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, CorruptAnimations.GREATSWORD_OLD_WALK)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, CorruptAnimations.GREATSWORD_OLD_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, CorruptAnimations.DUAL_GREATSWORD_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, CorruptAnimations.DUAL_GREATSWORD_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, CorruptAnimations.DUAL_GREATSWORD_WALK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, CorruptAnimations.DUAL_GREATSWORD_WALK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, CorruptAnimations.DUAL_GREATSWORD_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, Animations.BIPED_HOLD_DUAL_WEAPON)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_DUAL_WEAPON)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.FLOAT, Animations.BIPED_HOLD_DUAL_WEAPON)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.FALL, Animations.BIPED_HOLD_DUAL_WEAPON)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.GREATSWORD_GUARD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
                    .weaponCombinationPredicator((entitypatch) -> EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == CorruptWeaponCategories.S_GREATSWORD);


    public static final Function<Item, CapabilityItem.Builder> S_TACHI = (item) ->
        WeaponCapability.builder()
                .category(CorruptWeaponCategories.S_TACHI)
                .styleProvider((playerpatch) -> playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CorruptWeaponCategories.S_TACHI ? Styles.TWO_HAND : Styles.ONE_HAND)
                .collider(ColliderPreset.TACHI)
                .hitSound(EpicFightSounds.BLADE_HIT.get())
                .canBePlacedOffhand(true)
                .weaponCombinationPredicator((entitypatch) -> EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == CorruptWeaponCategories.S_TACHI)
                .newStyleCombo(Styles.ONE_HAND, CorruptAnimations.LONGSWORD_OLD_AUTO1, CorruptAnimations.LONGSWORD_OLD_AUTO2, CorruptAnimations.LONGSWORD_OLD_AUTO3, CorruptAnimations.LONGSWORD_OLD_AUTO4, CorruptAnimations.LONGSWORD_OLD_DASH,CorruptAnimations.LONGSWORD_OLD_AIRSLASH)
                .newStyleCombo(Styles.TWO_HAND, CorruptAnimations.DUAL_TACHI_AUTO1, CorruptAnimations.DUAL_TACHI_AUTO2, CorruptAnimations.DUAL_TACHI_AUTO3, CorruptAnimations.DUAL_TACHI_AUTO4, Animations.DAGGER_DUAL_DASH, Animations.SWORD_DUAL_AIR_SLASH)
                .newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
                .innateSkill(Styles.ONE_HAND, (itemstack) -> CDSkills.LETHAL_SLICING)
                .innateSkill(Styles.TWO_HAND, (itemstack) -> CDSkills.KATANASPSKILL)
                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_TACHI)
                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.KNEEL, Animations.BIPED_HOLD_TACHI)
                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_TACHI)
                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.CHASE, Animations.BIPED_HOLD_TACHI)
                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, Animations.BIPED_HOLD_TACHI)
                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.SNEAK, Animations.BIPED_HOLD_TACHI)
                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_TACHI)
                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.FLOAT, Animations.BIPED_HOLD_TACHI)
                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.FALL, Animations.BIPED_HOLD_TACHI)
                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, CorruptAnimations.TACHI_GUARD)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, CorruptAnimations.DUAL_GREATSWORD_IDLE)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, Animations.BIPED_HOLD_DUAL_WEAPON)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, CorruptAnimations.WALK_KATANA)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, CorruptAnimations.WALK_KATANA)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_DUAL)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.LANDING_RECOVERY, Animations.BIPED_LANDING)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.FALL, Animations.BIPED_HOLD_DUAL_WEAPON)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD);


    public static final Function<Item, CapabilityItem.Builder> DUAL_TACHI = (item) ->
            WeaponCapability.builder()
                    .category(CorruptWeaponCategories.DUAL_TACHI)
                    .styleProvider((playerpatch) -> playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CorruptWeaponCategories.DUAL_TACHI ? Styles.TWO_HAND : Styles.ONE_HAND)
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .swingSound(EpicFightSounds.WHOOSH.get())
                    .canBePlacedOffhand(true)
                    .collider(ColliderPreset.TACHI)
                    .weaponCombinationPredicator((entitypatch) -> EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == CorruptWeaponCategories.S_TACHI)
                    .newStyleCombo(Styles.ONE_HAND, CorruptAnimations.UCHIGATANA_AUTO1, CorruptAnimations.UCHIGATANA_AUTO2, CorruptAnimations.UCHIGATANA_AUTO3,CorruptAnimations.UCHIGATANA_AUTO4,CorruptAnimations.UCHIGATANA_AUTO5, CorruptAnimations.UCHIGATANA_DASH, CorruptAnimations.LONGSWORD_OLD_AIRSLASH)
                    .newStyleCombo(Styles.TWO_HAND, CorruptAnimations.DUAL_TACHI_AUTO1, CorruptAnimations.DUAL_TACHI_AUTO2, CorruptAnimations.DUAL_TACHI_AUTO3, CorruptAnimations.DUAL_TACHI_AUTO4, Animations.DAGGER_DUAL_DASH, Animations.SWORD_DUAL_AIR_SLASH)
                    .innateSkill(Styles.ONE_HAND, (itemstack) -> CDSkills.DUAL_TACHISKILL)
                    .innateSkill(Styles.TWO_HAND, (itemstack) -> CDSkills.KATANASKILL)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_LONGSWORD)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, Animations.BIPED_WALK_LONGSWORD)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, CorruptAnimations.RUN_KATANA)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, CorruptAnimations.DUAL_GREATSWORD_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, Animations.BIPED_HOLD_DUAL_WEAPON)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, CorruptAnimations.WALK_KATANA)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, CorruptAnimations.WALK_KATANA)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_DUAL)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, Animations.BIPED_HOLD_DUAL_WEAPON)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_DUAL_WEAPON)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.FLOAT, Animations.BIPED_HOLD_DUAL_WEAPON)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.FALL, Animations.BIPED_HOLD_DUAL_WEAPON)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, CorruptAnimations.TACHI_GUARD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
                    .weaponCombinationPredicator((entitypatch) -> EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == CorruptWeaponCategories.DUAL_TACHI);


    public static final Function<Item, CapabilityItem.Builder> S_DAGGER = (item) ->
            WeaponCapability.builder()
                    .category(CorruptWeaponCategories.S_DAGGER)
                    .styleProvider((playerpatch) -> playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CorruptWeaponCategories.S_DAGGER ? Styles.TWO_HAND : Styles.ONE_HAND)
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .swingSound(EpicFightSounds.WHOOSH_SMALL.get())
                    .collider(ColliderPreset.DAGGER)
                    .weaponCombinationPredicator((entitypatch) -> EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == CorruptWeaponCategories.S_DAGGER)
                    .newStyleCombo(Styles.ONE_HAND, Animations.DAGGER_AUTO1, Animations.DAGGER_AUTO2, Animations.DAGGER_AUTO3, Animations.DAGGER_DASH, Animations.DAGGER_AIR_SLASH)
                    .newStyleCombo(Styles.TWO_HAND, Animations.DAGGER_DUAL_AUTO1, Animations.DAGGER_DUAL_AUTO2, Animations.DAGGER_DUAL_AUTO3, Animations.BIPED_MOB_DAGGER_TWOHAND1, Animations.DAGGER_DUAL_DASH, Animations.DAGGER_DUAL_AIR_SLASH)
                    .newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
                    .innateSkill(Styles.ONE_HAND, (itemstack) -> EpicFightSkills.EVISCERATE)
                    .innateSkill(Styles.TWO_HAND, (itemstack) -> CDSkills.BLADE_RUSH)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_DUAL_WEAPON)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, Animations.BIPED_HOLD_DUAL_WEAPON)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_DUAL_WEAPON)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_HOLD_DUAL_WEAPON)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_DUAL)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, Animations.BIPED_HOLD_DUAL_WEAPON)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_DUAL_WEAPON)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.FLOAT, Animations.BIPED_HOLD_DUAL_WEAPON)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.FALL, Animations.BIPED_HOLD_DUAL_WEAPON);


    public static final Function<Item, CapabilityItem.Builder> S_LONGSWORD = (item) ->
            WeaponCapability.builder()
                    .category(CorruptWeaponCategories.S_LONGSWORD)
                    .styleProvider((playerpatch) -> {
                        if (playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SHIELD) {
                            return Styles.ONE_HAND;
                        } else if (playerpatch instanceof PlayerPatch<?> tplayerpatch) {
                            return tplayerpatch.getSkill(SkillSlots.WEAPON_INNATE).isActivated() ? Styles.OCHS : Styles.TWO_HAND;
                        }

                        return Styles.TWO_HAND;
                    })
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .collider(ColliderPreset.LONGSWORD)
                    .canBePlacedOffhand(false)
                    .newStyleCombo(Styles.ONE_HAND, CorruptAnimations.SWORD_ONEHAND_AUTO1, CorruptAnimations.SWORD_ONEHAND_AUTO2, CorruptAnimations.SWORD_ONEHAND_AUTO3, CorruptAnimations.SWORD_ONEHAND_AUTO4, CorruptAnimations.SWORD_ONEHAND_DASH, Animations.SWORD_AIR_SLASH)
                    .newStyleCombo(Styles.TWO_HAND, CorruptAnimations.TACHI_TWOHAND_AUTO_1, CorruptAnimations.TACHI_TWOHAND_AUTO_2, CorruptAnimations.TACHI_TWOHAND_AUTO_3, CorruptAnimations.TACHI_TWOHAND_AUTO_4, CorruptAnimations.LONGSWORD_OLD_DASH,Animations.LONGSWORD_AIR_SLASH)
                    .newStyleCombo(Styles.OCHS, Animations.LONGSWORD_LIECHTENAUER_AUTO1, Animations.LONGSWORD_LIECHTENAUER_AUTO2, Animations.LONGSWORD_LIECHTENAUER_AUTO3, Animations.LONGSWORD_DASH, Animations.LONGSWORD_AIR_SLASH)
                    .innateSkill(Styles.ONE_HAND, (itemstack) -> CDSkills.SHILEDSLASH)
                    .innateSkill(Styles.TWO_HAND, (itemstack) -> CDSkills.GUARDPARRY)
                    .innateSkill(Styles.OCHS, (itemstack) -> CDSkills.GUARDPARRY)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.IDLE, Animations.BIPED_HOLD_LONGSWORD)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.WALK, Animations.BIPED_WALK_LONGSWORD)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.CHASE, Animations.BIPED_WALK_LONGSWORD)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.RUN, Animations.BIPED_RUN_LONGSWORD)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.SNEAK, Animations.BIPED_HOLD_LONGSWORD)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.KNEEL, Animations.BIPED_HOLD_LONGSWORD)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.JUMP, Animations.BIPED_HOLD_LONGSWORD)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.SWIM, Animations.BIPED_HOLD_LONGSWORD)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.IDLE, Animations.BIPED_HOLD_LIECHTENAUER)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.WALK, Animations.BIPED_WALK_LIECHTENAUER)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.CHASE, Animations.BIPED_WALK_LIECHTENAUER)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.RUN, Animations.BIPED_HOLD_LIECHTENAUER)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.SNEAK, Animations.BIPED_HOLD_LIECHTENAUER)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.KNEEL, Animations.BIPED_HOLD_LIECHTENAUER)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.JUMP, Animations.BIPED_HOLD_LIECHTENAUER)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.SWIM, Animations.BIPED_HOLD_LIECHTENAUER)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD)
                    .livingMotionModifier(Styles.OCHS, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD);



    public static final Function<Item, CapabilityItem.Builder> S_SPEAR = (item) ->
            WeaponCapability.builder()
                    .category(CorruptWeaponCategories.S_SPEAR)
                    .styleProvider((playerpatch) -> (playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategories.SHIELD) ?
                            Styles.ONE_HAND : Styles.TWO_HAND)
                    .collider(ColliderPreset.SPEAR)
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .canBePlacedOffhand(false)
                    .newStyleCombo(Styles.ONE_HAND, CorruptAnimations.SSPEAR_ONEHAND_AUTO, CorruptAnimations.SSPEAR_DASH, Animations.SPEAR_ONEHAND_AIR_SLASH)
                    .newStyleCombo(Styles.TWO_HAND, CorruptAnimations.SSPEAR_TWOHAND_AUTO1, CorruptAnimations.SSPEAR_TWOHAND_AUTO2, CorruptAnimations.SSPEAR_DASH, Animations.SPEAR_TWOHAND_AIR_SLASH)
                    .newStyleCombo(Styles.MOUNT, Animations.SPEAR_MOUNT_ATTACK)
                    .innateSkill(Styles.ONE_HAND, (itemstack) -> EpicFightSkills.HEARTPIERCER)
                    .innateSkill(Styles.TWO_HAND, (itemstack) -> CDSkills.SPEAR_SLASH)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_SPEAR)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK_SPEAR)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_WALK_SPEAR)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_SPEAR)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SPEAR_GUARD);


    public static final Function<Item, CapabilityItem.Builder> YAMATO = item ->
            WeaponCapability.builder()
                    .category(CorruptWeaponCategories.YAMATO)
                    .canBePlacedOffhand(false)
                    .styleProvider((playerpatch) -> Styles.TWO_HAND)
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .collider(CorruptCollider.YAMATO)
                    .innateSkill(Styles.TWO_HAND, (itemstack) -> CDSkills.YAMATOSKILL)
                    .newStyleCombo(Styles.TWO_HAND, CorruptAnimations.YAMATO_AUTO1, CorruptAnimations.YAMATO_AUTO2,CorruptAnimations.YAMATO_AUTO3,CorruptAnimations.YAMATO_AUTO4, CorruptAnimations.YAMATO_STRIKE1, CorruptAnimations.YAMATO_POWER3_REPEAT, CorruptAnimations.YAMATO_TWIN_SLASH,CorruptAnimations.YAMATO_TURN_SLASH)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, CorruptAnimations.YAMATO_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, CorruptAnimations.YAMATO_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, CorruptAnimations.YAMATO_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, CorruptAnimations.YAMATO_WALK)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, CorruptAnimations.YAMATO_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, CorruptAnimations.YAMATO_RUN)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.FALL, Animations.BIPED_FALL)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, CorruptAnimations.YAMATO_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, CorruptAnimations.YAMATO_GUARD)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.LANDING_RECOVERY, Animations.BIPED_LANDING);


    public static final Function<Item, CapabilityItem.Builder> S_SWORD = (item) -> {
        WeaponCapability.Builder builder = WeaponCapability.builder()
                .category(CorruptWeaponCategories.S_SWORD)
                .styleProvider((playerpatch) -> playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CorruptWeaponCategories.S_SWORD? Styles.TWO_HAND : Styles.ONE_HAND)
                .collider(ColliderPreset.SWORD)
                .newStyleCombo(Styles.ONE_HAND, CorruptAnimations.SWORD_ONEHAND_AUTO1, CorruptAnimations.SWORD_ONEHAND_AUTO2, CorruptAnimations.SWORD_ONEHAND_AUTO3, CorruptAnimations.SWORD_ONEHAND_AUTO4, CorruptAnimations.SWORD_ONEHAND_DASH, Animations.SWORD_AIR_SLASH)
                .newStyleCombo(Styles.TWO_HAND, Animations.SWORD_DUAL_AUTO1, Animations.SWORD_DUAL_AUTO2,Animations.DAGGER_DUAL_AUTO3, Animations.SWORD_DUAL_AUTO3,Animations.DAGGER_DUAL_AUTO4,Animations.SWORD_DUAL_DASH, Animations.SWORD_DUAL_AIR_SLASH)
                .newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
                .innateSkill(Styles.ONE_HAND, (itemstack) -> CDSkills.SWORD_SLASH)
                .innateSkill(Styles.TWO_HAND, (itemstack) -> CDSkills.DUAL_SLASH)
                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_LONGSWORD)
                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, Animations.BIPED_WALK_LONGSWORD)
                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.CHASE, Animations.BIPED_WALK_LONGSWORD)
                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, Animations.BIPED_RUN_LONGSWORD)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_DUAL_WEAPON)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, Animations.BIPED_HOLD_DUAL_WEAPON)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_DUAL_WEAPON)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_HOLD_DUAL_WEAPON)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_DUAL)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, Animations.BIPED_HOLD_DUAL_WEAPON)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_DUAL_WEAPON)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.FLOAT, Animations.BIPED_HOLD_DUAL_WEAPON)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.FALL, Animations.BIPED_HOLD_DUAL_WEAPON)
                .weaponCombinationPredicator((entitypatch) -> EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == CorruptWeaponCategories.S_SWORD);

        if (item instanceof TieredItem tieredItem) {
            builder.hitSound(tieredItem.getTier() == Tiers.WOOD ? EpicFightSounds.BLUNT_HIT.get() : EpicFightSounds.BLADE_HIT.get());
            builder.hitParticle(tieredItem.getTier() == Tiers.WOOD ? EpicFightParticles.HIT_BLUNT.get() : EpicFightParticles.HIT_BLADE.get());
        }




        return builder;
    };


//    public static final Function<Item, CapabilityItem.Builder> SANJI = (item) -> {
//        CapabilityItem.Builder builder = WeaponCapability.builder()
//                .category(WeaponCategories.FIST)
//                .styleProvider((playerpatch) -> playerpatch instanceof PlayerPatch && ((PlayerPatch)playerpatch)
//                        .getSkill(SkillSlots.WEAPON_INNATE).getRemainDuration() > 0 ? Styles.OCHS : Styles.ONE_HAND)
//                .canBePlacedOffhand(false)
//                .collider(CorruptCollider.KICK)
//                .swingSound(EpicFightSounds.WHOOSH.get())
//                .hitSound(EpicFightSounds.BLUNT_HIT.get())
//                .newStyleCombo(Styles.ONE_HAND, CorruptAnimations.SANJI_AUTO_1, CorruptAnimations.SANJI_AUTO_2, CorruptAnimations.SANJI_AUTO_3, CorruptAnimations.SANJI_AUTO_4, CorruptAnimations.SANJI_ANTIMANER, CorruptAnimations.SANJI_CONCASSER)
//                .innateSkill(Styles.ONE_HAND, (itemstack) -> OPXEFSkills.DIABLE_JAMBU)
//                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, CorruptAnimations.SANJI_IDLE)
//                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, CorruptAnimations.SANJI_WALK)
//                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.CHASE, CorruptAnimations.SANJI_RUN)
//                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, CorruptAnimations.SANJI_RUN)
//                .newStyleCombo(Styles.OCHS,CorruptAnimations.SANJI_DIABLE_AUTO_1, CorruptAnimations.SANJI_DIABLE_AUTO_2, CorruptAnimations.SANJI_DIABLE_AUTO_3, CorruptAnimations.SANJI_DIABLE_AUTO_4, CorruptAnimations.SANJI_DIABLE_ANTIMANER, CorruptAnimations.SANJI_CONCASSER)
//                .innateSkill(Styles.OCHS, (itemstack) -> OPXEFSkills.DIABLE_JAMBU)
//                .livingMotionModifier(Styles.OCHS, LivingMotions.IDLE, CorruptAnimations.SANJI_DIABLE_IDLE)
//                .livingMotionModifier(Styles.OCHS, LivingMotions.WALK, CorruptAnimations.SANJI_DIABLE_WALK)
//                .livingMotionModifier(Styles.OCHS, LivingMotions.CHASE, CorruptAnimations.SANJI_RUN)
//                .livingMotionModifier(Styles.OCHS, LivingMotions.RUN, CorruptAnimations.SANJI_RUN);
//        return builder;
//    };






}

