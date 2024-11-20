package net.corruptdog.cdm.skill.guard;

import net.corruptdog.cdm.gameasset.CorruptAnimations;
import net.corruptdog.cdm.world.CorruptWeaponCategories;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.guard.GuardSkill;
import yesman.epicfight.skill.guard.ParryingSkill;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

import static yesman.epicfight.skill.SkillDataKeys.PENALTY;

public class CDParrySkill extends ParryingSkill {
    public static Builder createBuilder(ResourceLocation resourceLocation) {
        return GuardSkill.createGuardBuilder()
                .addAdvancedGuardMotion(CorruptWeaponCategories.YAMATO, (itemCap, playerpatch) ->
                        new StaticAnimation[]{CorruptAnimations.YAMATO_ACTIVE_GUARD_HIT, CorruptAnimations.YAMATO_ACTIVE_GUARD_HIT2})
                .addGuardMotion(CorruptWeaponCategories.YAMATO, (item, player) -> CorruptAnimations.YAMATO_GUARD_HIT)
                .addGuardBreakMotion(CorruptWeaponCategories.YAMATO, (item, player) -> Animations.BIPED_COMMON_NEUTRALIZED)
                .addAdvancedGuardMotion(CorruptWeaponCategories.S_SPEAR, (itemCap, playerpatch) ->
                        new StaticAnimation[]{Animations.LONGSWORD_GUARD_ACTIVE_HIT1, Animations.LONGSWORD_GUARD_ACTIVE_HIT2})
                .addGuardMotion(CorruptWeaponCategories.S_SPEAR, (item, player) -> Animations.SPEAR_GUARD_HIT)
                .addGuardBreakMotion(CorruptWeaponCategories.S_SPEAR, (item, player) -> Animations.GREATSWORD_GUARD_BREAK)
                .addAdvancedGuardMotion(CorruptWeaponCategories.S_GREATSWORD, (itemCap, playerpatch) -> itemCap.getStyle(playerpatch) == CapabilityItem.Styles.ONE_HAND ?
                        new StaticAnimation[] { Animations.LONGSWORD_GUARD_ACTIVE_HIT1, Animations.LONGSWORD_GUARD_ACTIVE_HIT2 } :
                        new StaticAnimation[] { Animations.SWORD_GUARD_ACTIVE_HIT2, Animations.SWORD_GUARD_ACTIVE_HIT3 })
                .addGuardMotion(CorruptWeaponCategories.S_GREATSWORD, (item, player) -> item.getStyle(player) == CapabilityItem.Styles.ONE_HAND ? Animations.GREATSWORD_GUARD_HIT : Animations.SWORD_DUAL_GUARD_HIT)
                .addGuardBreakMotion(CorruptWeaponCategories.S_GREATSWORD, (item, player) -> item.getStyle(player) == CapabilityItem.Styles.ONE_HAND ? Animations.GREATSWORD_GUARD_BREAK : Animations.BIPED_COMMON_NEUTRALIZED)
                .addAdvancedGuardMotion(CorruptWeaponCategories.S_SWORD, (itemCap, playerpatch) ->
                        new StaticAnimation[]{Animations.SWORD_GUARD_ACTIVE_HIT1, Animations.SWORD_GUARD_ACTIVE_HIT2})
                .addGuardMotion(CorruptWeaponCategories.S_SWORD, (item, player) -> Animations.SWORD_GUARD_HIT)
                .addGuardBreakMotion(CorruptWeaponCategories.S_SWORD, (item, player) -> Animations.BIPED_COMMON_NEUTRALIZED)
                .addAdvancedGuardMotion(CapabilityItem.WeaponCategories.SWORD, (itemCap, playerpatch) -> itemCap.getStyle(playerpatch) == CapabilityItem.Styles.ONE_HAND ?
                        new StaticAnimation[] { Animations.SWORD_GUARD_ACTIVE_HIT1, Animations.SWORD_GUARD_ACTIVE_HIT2 } :
                        new StaticAnimation[] { Animations.SWORD_GUARD_ACTIVE_HIT2, Animations.SWORD_GUARD_ACTIVE_HIT3 })
                .addAdvancedGuardMotion(CapabilityItem.WeaponCategories.LONGSWORD, (itemCap, playerpatch) ->
                        new StaticAnimation[] { Animations.LONGSWORD_GUARD_ACTIVE_HIT1, Animations.LONGSWORD_GUARD_ACTIVE_HIT2 })
                .addAdvancedGuardMotion(CapabilityItem.WeaponCategories.UCHIGATANA, (itemCap, playerpatch) ->
                        new StaticAnimation[] { Animations.SWORD_GUARD_ACTIVE_HIT1, Animations.SWORD_GUARD_ACTIVE_HIT2 })
                .addAdvancedGuardMotion(CapabilityItem.WeaponCategories.TACHI, (itemCap, playerpatch) ->
                        new StaticAnimation[] { Animations.LONGSWORD_GUARD_ACTIVE_HIT1, Animations.LONGSWORD_GUARD_ACTIVE_HIT2 })
                .addGuardMotion(CorruptWeaponCategories.S_TACHI, (item, player) -> Animations.LONGSWORD_GUARD_HIT)
                .addGuardBreakMotion(CorruptWeaponCategories.S_TACHI, (item, player) -> Animations.BIPED_COMMON_NEUTRALIZED)
                .addAdvancedGuardMotion(CorruptWeaponCategories.S_TACHI, (itemCap, playerpatch) ->
                        new StaticAnimation[] { Animations.LONGSWORD_GUARD_ACTIVE_HIT1, Animations.LONGSWORD_GUARD_ACTIVE_HIT2 });
    }

    public CDParrySkill(Builder builder) {
        super(builder);
    }
    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
    }
    @OnlyIn(Dist.CLIENT) @Override
    public boolean shouldDraw(SkillContainer container) {
        return container.getExecuter().getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == CorruptWeaponCategories.YAMATO && container.getDataManager().getDataValue(PENALTY.get()) > 0.0F;
    }

    @Override
    public void onRemoved(SkillContainer container) {
        super.onRemoved(container);
    }
    @Override
    public Skill getPriorSkill() {
        return EpicFightSkills.GUARD;
    }

    protected boolean isAdvancedGuard() {
        return true;
    }
}
