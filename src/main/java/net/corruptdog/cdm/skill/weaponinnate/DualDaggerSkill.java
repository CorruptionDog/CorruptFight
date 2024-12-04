//package net.corruptdog.cdm.skill.weaponinnate;
//
//
//import java.util.UUID;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;
//
//import net.corruptdog.cdm.gameasset.CorruptAnimations;
//import net.minecraft.network.FriendlyByteBuf;
//import yesman.epicfight.skill.*;
//import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
//import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
//import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;
//
//import static net.corruptdog.cdm.skill.CDSkillDataKeys.*;
//import static yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType.*;
//
//public class DualDaggerSkill extends WeaponInnateSkill {
//    private static final UUID EVENT_UUID = UUID.fromString("f082557a-b2f9-11eb-8529-0242ac130003");
//    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
//
//    public DualDaggerSkill(Builder<? extends Skill> builder) {
//        super(builder);
//    }
//
//    public void onInitiate(SkillContainer container) {
//        super.onInitiate(container);
//        PlayerEventListener listener = container.getExecuter().getEventListener();
//        listener.addEventListener(PlayerEventListener.EventType.ATTACK_ANIMATION_END_EVENT, EVENT_UUID, (event) -> {
//            int id = event.getAnimation().getId();
//            if (id == CorruptAnimations.BLADE_RUSH4.getId()) {
//                container.getDataManager().setData(RUSH.get(), 0);
//            }
//    });
//        listener.addEventListener(DEALT_DAMAGE_EVENT_DAMAGE, EVENT_UUID, (event) -> {
//            Integer r = container.getDataManager().getDataValue(RUSH.get());
//            int id = event.getDamageSource().getAnimation().getId();
//            if (id == CorruptAnimations.BLADE_RUSH1.getId() || id == CorruptAnimations.BLADE_RUSH2.getId() || id == CorruptAnimations.BLADE_RUSH3.getId() ) {
//                container.getDataManager().setData(RUSH.get(), r + 1);
//            }
//            scheduler.schedule(() -> {
//                if (r != 0) {
//                    container.getDataManager().setData(RUSH.get(), 0);
//                }
//            }, 5, TimeUnit.SECONDS);
//        });
//    }
//
//    public void onRemoved(SkillContainer container) {
//        container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.DEALT_DAMAGE_EVENT_DAMAGE, EVENT_UUID);
//        container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.ATTACK_ANIMATION_END_EVENT, EVENT_UUID);
//        scheduler.shutdown();
//    }
//
//
//    @Override
//    public void executeOnServer(ServerPlayerPatch execute, FriendlyByteBuf args) {
//        SkillContainer skillContainer = execute.getSkill(SkillSlots.WEAPON_INNATE);
//        int rush = skillContainer.getDataManager().getDataValue(RUSH.get());
//        if (rush == 0) {
//            execute.playAnimationSynchronized(CorruptAnimations.BLADE_RUSH1, 0);
//        }
//        if (rush == 1) {
//            execute.playAnimationSynchronized(CorruptAnimations.BLADE_RUSH2, 0);
//        }
//        if (rush == 2) {
//            execute.playAnimationSynchronized(CorruptAnimations.BLADE_RUSH3, 0);
//        }
//        if (rush == 3) {
//            execute.playAnimationSynchronized(CorruptAnimations.BLADE_RUSH4, 0);
//        }
//    }
//}
