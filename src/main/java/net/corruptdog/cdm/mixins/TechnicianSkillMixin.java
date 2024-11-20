package net.corruptdog.cdm.mixins;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import net.corruptdog.cdm.network.server.NetworkManager;
import net.corruptdog.cdm.network.server.SPAfterImagine;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.skill.passive.PassiveSkill;
import yesman.epicfight.skill.passive.TechnicianSkill;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;

@Mixin(
        value = {TechnicianSkill.class},
        remap = false
)
public class TechnicianSkillMixin extends PassiveSkill {
    @Shadow
    private static final UUID EVENT_UUID = UUID.fromString("99e5c782-fdaf-11eb-9a03-0242ac130003");


    public TechnicianSkillMixin(Skill.Builder<? extends Skill> builder) {
        super(builder);
    }

    /**
     * @author
     * @reason
     */
    @Overwrite
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getExecuter().getEventListener().addEventListener(EventType.DODGE_SUCCESS_EVENT, EVENT_UUID, (event) -> {
            float consumption = container.getExecuter().getModifiedStaminaConsume(container.getExecuter().getSkill(SkillSlots.DODGE).getSkill().getConsumption());
            container.getExecuter().setStamina(container.getExecuter().getStamina() + consumption);
            ServerPlayer serverPlayer = (ServerPlayer)container.getExecuter().getOriginal();
            SPAfterImagine msg = new SPAfterImagine(serverPlayer.position(), serverPlayer.getId());
            NetworkManager.sendToAllPlayerTrackingThisEntityWithSelf(msg, serverPlayer);
        });
    }
}
