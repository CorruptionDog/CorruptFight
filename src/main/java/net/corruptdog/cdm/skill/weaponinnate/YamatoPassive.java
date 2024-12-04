//package net.corruptdog.cdm.skill.weaponinnate;
//
//import net.corruptdog.cdm.gameasset.CDSkills;
//import net.corruptdog.cdm.skill.ExSkill;
//import net.minecraft.world.item.ItemStack;
//import yesman.epicfight.api.animation.types.AttackAnimation;
//import yesman.epicfight.gameasset.EpicFightSkills;
//import yesman.epicfight.skill.Skill;
//import yesman.epicfight.skill.SkillCategories;
//import yesman.epicfight.skill.SkillContainer;
//import yesman.epicfight.skill.SkillDataManager;
//import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
//import yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;
//
//import java.util.UUID;
//
//public class YamatoPassive extends Skill {
//    private static final UUID EVENT_UUID = UUID.fromString("a446c93a-42cb-11eb-b378-0242ac130002");
//
//    public YamatoPassive(Builder<? extends Skill> builder) {
//        super(builder);
//    }
//
//    @Override
//    public void onInitiate(SkillContainer container) {
//        PlayerPatch<?> executer = container.getExecuter();
//
//        SkillContainer step = executer.getSkillCapability().skillContainers[SkillCategories.DODGE.universalOrdinal()];
//
//        if(!step.isEmpty() && step.hasSkill(EpicFightSkills.STEP)){
//            step.setSkill(CDSkills.YAMATO_STEP);
//        }
//
//        executer.getSkillCapability().skillContainers[SkillCategories.BASIC_ATTACK.universalOrdinal()].setSkill(CDSkills.YAMATO_ATTACK);
//
//        executer.getEventListener().addEventListener(EventType.ACTION_EVENT_SERVER, EVENT_UUID, (event) -> {
//            if (!(event.getAnimation() instanceof AttackAnimation)){
//                this.onReset(container);
//            }
//        });
//    }
//
//    @Override
//    public void onRemoved(SkillContainer container) {
//        PlayerPatch executer = container.getExecuter();
//        SkillContainer exdodgeskill = executer.getSkillCapability().skillContainers[SkillCategories.DODGE.universalOrdinal()];
//
//        executer.getEventListener().removeListener(EventType.ACTION_EVENT_SERVER, EVENT_UUID);
//        executer.getSkillCapability().skillContainers[SkillCategories.BASIC_ATTACK.universalOrdinal()].setSkill(EpicFightSkills.BASIC_ATTACK);
//
//
//        if(exdodgeskill.hasSkill(CDSkills.YAMATO_STEP)){
//            exdodgeskill.setSkill(EpicFightSkills.STEP);
//        }
//    }
//}