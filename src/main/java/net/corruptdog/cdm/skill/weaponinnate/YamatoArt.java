//package net.corruptdog.cdm.skill.weaponinnate;
//
//import net.corruptdog.cdm.gameasset.CDSkills;
//import net.corruptdog.cdm.gameasset.CorruptAnimations;
//import net.corruptdog.cdm.network.server.NetworkManager;
//import net.corruptdog.cdm.network.server.SPAfterImagine;
//import net.minecraft.network.FriendlyByteBuf;
//import net.minecraft.server.level.ServerPlayer;
//import net.minecraft.tags.DamageTypeTags;
//import net.minecraft.world.damagesource.DamageSource;
//import net.minecraft.world.damagesource.DamageTypes;
//import net.minecraft.world.phys.Vec3;
//import yesman.epicfight.api.animation.AnimationPlayer;
//import yesman.epicfight.api.animation.types.StaticAnimation;
//import yesman.epicfight.gameasset.EpicFightSkills;
//import yesman.epicfight.skill.*;
//import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
//import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
//import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
//import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;
//
//import java.util.UUID;
//
//import static net.corruptdog.cdm.skill.CDSkillDataKeys.*;
//import static yesman.epicfight.skill.SkillCategories.WEAPON_PASSIVE;
//import static yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;
//import static yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType.HURT_EVENT_PRE;
//
//
//public class YamatoArt extends WeaponInnateSkill {
//    private static final UUID EVENT_UUID = UUID.fromString("99e5c655-fdaf-11eb-9a03-0242ac130003");
//
//    public YamatoArt(Skill.Builder<? extends Skill> builder) {
//        super(builder);
//    }
//
//    @Override
//    public void onInitiate(SkillContainer container) {
//
//        super.onInitiate(container);
//        PlayerEventListener listener = container.getExecuter().getEventListener();
//
//        listener.addEventListener(EventType.ATTACK_ANIMATION_END_EVENT, EVENT_UUID, (event) -> {
//            int id = event.getAnimation().getId();
//            if (id == CorruptAnimations.YAMATO_POWER3.getId() || id == CorruptAnimations.YAMATO_POWER3_REPEAT.getId()){
//                event.getPlayerPatch().reserveAnimation(CorruptAnimations.YAMATO_POWER3_FINISH);
//            } else if (id == CorruptAnimations.YAMATO_COUNTER1.getId()){
//                event.getPlayerPatch().reserveAnimation(CorruptAnimations.YAMATO_COUNTER2);
//            } else if (id == CorruptAnimations.YAMATO_POWER3_FINISH.getId() || id == CorruptAnimations.YAMATO_POWER_DASH.getId()){
//                container.getDataManager().setData(SLASH_COUNTER.get(), 1F);
//            }
//        });
//
//        listener.addEventListener(HURT_EVENT_PRE, EVENT_UUID, (event) -> {
//            ServerPlayerPatch executer = event.getPlayerPatch();
//            AnimationPlayer animationPlayer = executer.getAnimator().getPlayerFor(null);
//            float elapsedTime = animationPlayer.getElapsedTime();
//            if (elapsedTime <= 0.35F) {
//                int animationId = executer.getAnimator().getPlayerFor(null).getAnimation().getId();
//                if (animationId == CorruptAnimations.YAMATO_POWER0_1.getId()) {
//                    DamageSource damagesource = event.getDamageSource();
//                    Vec3 sourceLocation = damagesource.getSourcePosition();
//                    if (sourceLocation != null) {
//                        Vec3 viewVector = event.getPlayerPatch().getOriginal().getViewVector(1.0F);
//                        Vec3 toSourceLocation = sourceLocation.subtract(event.getPlayerPatch().getOriginal().position()).normalize();
//                        if (toSourceLocation.dot(viewVector) > 0.0D) {
//                            if (!damagesource.is(DamageTypeTags.IS_EXPLOSION)
//                                    && !damagesource.is(DamageTypes.MAGIC)
//                                    && !damagesource.is(DamageTypeTags.BYPASSES_ARMOR)
//                                    && !damagesource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
//                                Skill skill = container.getExecuter().getSkill(SkillSlots.WEAPON_INNATE).getSkill();
//                                ServerPlayer serverPlayer = (ServerPlayer) container.getExecuter().getOriginal();
//                                SPAfterImagine msg = new SPAfterImagine(serverPlayer.position(), serverPlayer.getId());
//                                NetworkManager.sendToAllPlayerTrackingThisEntityWithSelf(msg, serverPlayer);
//                                event.getPlayerPatch().playAnimationSynchronized(CorruptAnimations.YAMATO_POWER0_2,0F);
//                                if (skill != null) {
//                                    SkillContainer weaponInnateContainer = event.getPlayerPatch().getSkill(SkillSlots.WEAPON_INNATE);
//                                    weaponInnateContainer.getSkill().setConsumptionSynchronize(event.getPlayerPatch(), weaponInnateContainer.getResource() + 45F);
//                                } else {
//                                    event.getPlayerPatch().playAnimationSynchronized(CorruptAnimations.YAMATO_POWER0_2, 0.15F);
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        });
//        listener.addEventListener(EventType.ACTION_EVENT_SERVER, EVENT_UUID, (event) -> {
//            StaticAnimation animation = event.getAnimation();
//            if (!(animation == CorruptAnimations.YAMATO_POWER3 || animation == CorruptAnimations.YAMATO_POWER3_REPEAT || animation == CorruptAnimations.YAMATO_POWER3_FINISH || animation == CorruptAnimations.YAMATO_POWER_DASH)) {
//                if (container.getDataManager().getDataValue(SLASH_COUNTER.get())> 1) {
//                    container.getDataManager().setData(SLASH_COUNTER.get(), 1F);
//                }
//            }
//            if(animation != CorruptAnimations.YAMATO_COUNTER1 && container.getDataManager().getDataValue(COUNTER_SUCCESSED.get())){
//                container.getDataManager().setData(COUNTER_SUCCESSED.get(),false);
//            }
//        });
//
//        listener.addEventListener(EventType.DEALT_DAMAGE_EVENT_ATTACK, EVENT_UUID, (event) -> {
//            ServerPlayerPatch executer = event.getPlayerPatch();
//            int id = event.getDamageSource().getAnimation().getId();
//            float stamina =executer.getStamina();
//            float maxstamina = executer.getMaxStamina();
//            if (id == CorruptAnimations.YAMATO_POWER0_1.getId()) {
//                float k = 0.3F;
//                if (stamina < maxstamina) {
//                    executer.setStamina(stamina + k * maxstamina);
//                }
//            } else if(id == CorruptAnimations.YAMATO_COUNTER2.getId()) {
//                float k = 0.2F;
//                if (stamina < maxstamina) {
//                    executer.setStamina(stamina + k * maxstamina);
//                }
//            } else if (id == CorruptAnimations.YAMATO_POWER3.getId() || id == CorruptAnimations.YAMATO_POWER3_REPEAT.getId()){
//                container.getDataManager().setData(SLASH_COUNTER.get(), container.getDataManager().getDataValue(SLASH_COUNTER.get()) + 1F);
//            } else if (id == CorruptAnimations.YAMATO_COUNTER1.getId()) {
//                container.getDataManager().setData(COUNTER_SUCCESSED.get(),true);
//            }
//        });
//
//        listener.addEventListener(EventType.DEALT_DAMAGE_EVENT_DAMAGE, EVENT_UUID, (event) -> {
//            if (event.getDamageSource() != null) {
//                float attackDamage = event.getAttackDamage();
//                int id = event.getDamageSource().getAnimation().getId();
//                float k = container.getDataManager().getDataValue(SLASH_COUNTER.get());
//                float max = 15F;
//                float bonus = 0.33F;
//                if (id == CorruptAnimations.YAMATO_POWER3_FINISH.getId() || id == CorruptAnimations.YAMATO_POWER_DASH.getId()) {
//                    k = Math.min(k,max);
//                    event.setAttackDamage(attackDamage * (1F + bonus * k));
//                }
//            }
//        });
//    }
//
//    @Override
//    public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
//        int default_cost = 1;
//        int counter_cost = 2;
//        int strike2_cost = 2;
//        float maxstamina = executer.getMaxStamina();
//        float stamina = executer.getStamina();
//        int combo = executer.getSkill(SkillCategories.BASIC_ATTACK.universalOrdinal()).getDataManager().getDataValue(SkillDataKeys.COMBO_COUNTER.get());
//        if (executer.getSkill(SkillSlots.GUARD).hasSkill(EpicFightSkills.PARRYING) && executer.getSkill(SkillSlots.WEAPON_INNATE).getStack() >= counter_cost) {
//            executer.playAnimationSynchronized(CorruptAnimations.YAMATO_COUNTER1, 0);
//            this.stackCost(executer,counter_cost);
//        } else if (combo == 4 && executer.getSkill(SkillSlots.WEAPON_INNATE).getStack() >= strike2_cost) {
//            executer.playAnimationSynchronized(CorruptAnimations.YAMATO_STRIKE2,0);
//            this.stackCost(executer,strike2_cost);
//        } else if (combo == 5 || combo == 6){
//            executer.playAnimationSynchronized(CorruptAnimations.YAMATO_POWER_DASH, -0.3F);
//        } else if (artExecutable(executer)) {
//            if (executer.getOriginal().isSprinting()) {
//                float p = 0.25F;
//                if(stamina >= p * maxstamina) {
//                    executer.playAnimationSynchronized(CorruptAnimations.YAMATO_POWER_DASH, 0);
//                    executer.setStamina(stamina - p * maxstamina);
//                    this.stackCost(executer,default_cost);
//                }
//            }  else {
//                switch (combo){
//                    case 0:
//                        executer.playAnimationSynchronized(CorruptAnimations.YAMATO_POWER0_1, 0);
//                        this.stackCost(executer,default_cost);
//                        break;
//                    case 1:
//                        executer.playAnimationSynchronized(CorruptAnimations.YAMATO_POWER1,0);
//                        this.stackCost(executer,default_cost);
//                        break;
//                    case 2:
//                        executer.playAnimationSynchronized(CorruptAnimations.YAMATO_POWER2,0);
//                        this.stackCost(executer,default_cost);
//                        break;
//                    case 3:
//                        executer.playAnimationSynchronized(CorruptAnimations.YAMATO_POWER3,0);
//                        this.stackCost(executer,default_cost);
//                        break;
//                }
//            }
//        }
//    }
//    private boolean artExecutable(ServerPlayerPatch executer){
//        return executer.getSkill(CDSkills.YAMATOSKILL).getStack() >= 1 || executer.getOriginal().isCreative();
//    }
//    @Override
//    public void onRemoved(SkillContainer container) {
//        container.getExecuter().getEventListener().removeListener(EventType.ATTACK_ANIMATION_END_EVENT, EVENT_UUID);
//        container.getExecuter().getEventListener().removeListener(EventType.ACTION_EVENT_SERVER, EVENT_UUID);
//        container.getExecuter().getEventListener().removeListener(EventType.HURT_EVENT_PRE, EVENT_UUID);
//    }
//
//    @Override
//    public WeaponInnateSkill registerPropertiesToAnimation() {
//        return this;
//    }
//
//    public boolean resourcePredicate(PlayerPatch<?> playerpatch) {
//        return true;
//    }
//
//    public boolean isExecutableState(PlayerPatch<?> executer) {
//        return executer.getEntityState().canBasicAttack();
//    }
//
//    private void stackCost(ServerPlayerPatch player,int cost){
//        this.setStackSynchronize(player, player.getSkill(CDSkills.YAMATOSKILL).getStack() - cost);
//    }
//}