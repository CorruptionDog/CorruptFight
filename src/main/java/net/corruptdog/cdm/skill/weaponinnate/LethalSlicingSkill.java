package net.corruptdog.cdm.skill.weaponinnate;

import com.google.common.collect.Maps;
import net.corruptdog.cdm.gameasset.CorruptAnimations;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class LethalSlicingSkill extends WeaponInnateSkill {
    private  final UUID EVENT_UUID = UUID.fromString("f082557a-b2f9-11eb-8529-0242ac130003");


    public LethalSlicingSkill(Builder<? extends Skill> builder) {
        super(builder);
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getExecuter().getEventListener().addEventListener(EventType.ATTACK_ANIMATION_END_EVENT, EVENT_UUID, (event) -> {
            if (CorruptAnimations.LETHAL_SLICING_START.equals(event.getAnimation())) {
                event.getPlayerPatch().reserveAnimation(CorruptAnimations.LETHAL_SLICING_ONCE);
            } else if (CorruptAnimations.LETHAL_SLICING_ONCE.equals(event.getAnimation())) {
                List<LivingEntity> hurtEntities = event.getPlayerPatch().getCurrenltyHurtEntities();
                if (hurtEntities.size() >= 2) {
                    event.getPlayerPatch().reserveAnimation(CorruptAnimations.LETHAL_SLICING_TWICE);
                }
            }
        });
    }
    @Override
    public void onRemoved(SkillContainer container) {
        container.getExecuter().getEventListener().removeListener(
                EventType.ATTACK_ANIMATION_END_EVENT,
                EVENT_UUID
        );
        super.onRemoved(container);
    }

    @Override
    public List<Component> getTooltipOnItem(ItemStack itemStack, CapabilityItem cap, PlayerPatch<?> playerCap) {
        List<Component> list = super.getTooltipOnItem(itemStack, cap, playerCap);
        this.generateTooltipforPhase(list, itemStack, cap, playerCap, this.properties.get(0), "Start Strike:");
        this.generateTooltipforPhase(list, itemStack, cap, playerCap, this.properties.get(1), "Once Strike:");
        return list;
    }
    private void SLASH(ServerPlayerPatch executer) {
        executer.playAnimationSynchronized(CorruptAnimations.TACHI_SLASH, 0.0F);
    }

    @Override
    public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
        ResourceLocation rl = executer.getAnimator().getPlayerFor(null).getAnimation().getRegistryName();
        Map<ResourceLocation, Runnable> actionMap = Maps.newHashMap();
        actionMap.put(CorruptAnimations.LETHAL_SLICING_START.getRegistryName(), () -> SLASH(executer));
        if (actionMap.containsKey(rl)) {
            actionMap.get(rl).run();
        } else {
            executer.playAnimationSynchronized(CorruptAnimations.LETHAL_SLICING_START, 0);
            super.executeOnServer(executer, args);
        }
    }
}