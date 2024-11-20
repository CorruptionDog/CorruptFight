package net.corruptdog.cdm.skill;

import yesman.epicfight.skill.SkillCategory;

public enum ExSkill implements SkillCategory {
    EX_STEP(false,false,false),
    EX_GUARD(false,false,false);

    boolean shouldSaved;
    boolean shouldSyncronized;
    boolean modifiable;
    int id;

    ExSkill(boolean shouldSave, boolean shouldSyncronized, boolean modifiable) {
        this.shouldSaved = shouldSave;
        this.shouldSyncronized = shouldSyncronized;
        this.modifiable = modifiable;
        this.id = SkillCategory.ENUM_MANAGER.assign(this);
    }
    @Override
    public boolean shouldSave() {
        return false;
    }

    @Override
    public boolean shouldSynchronize() {
        return false;
    }

    @Override
    public boolean learnable() {
        return this.modifiable;
    }

    @Override
    public int universalOrdinal() {
        return this.id;
    }
}
