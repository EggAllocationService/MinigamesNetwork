package io.egg.minigames.entities;

import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.minestom.server.attribute.Attribute;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.ai.EntityAIGroup;
import net.minestom.server.entity.ai.EntityAIGroupBuilder;
import net.minestom.server.entity.ai.TargetSelector;
import net.minestom.server.entity.ai.goal.FollowTargetGoal;
import net.minestom.server.entity.ai.goal.MeleeAttackGoal;
import net.minestom.server.entity.ai.goal.RandomStrollGoal;
import net.minestom.server.entity.ai.target.ClosestEntityTarget;
import net.minestom.server.entity.metadata.other.BoatMeta;
import net.minestom.server.entity.metadata.other.FallingBlockMeta;
import net.minestom.server.event.entity.EntityDamageEvent;
import net.minestom.server.instance.block.Block;
import net.minestom.server.utils.time.TimeUnit;
import net.minestom.server.utils.time.UpdateOption;
import org.jetbrains.annotations.NotNull;

public class MonsterBoatCreature extends EntityCreature {
    public MonsterBoatCreature() {
        super(EntityType.BOAT);
        addAIGroup(new EntityAIGroupBuilder()
                .addGoalSelector(new MeleeAttackGoal(this, 1, 4, TimeUnit.SECOND))
                .addGoalSelector(new FollowTargetGoal(this, new UpdateOption(1, TimeUnit.SECOND)))
                .addGoalSelector(new RandomStrollGoal(this, 5))
                .addTargetSelector(new ClosestEntityTarget(this, 40, Player.class))
                .build()
        );

        BoatMeta meta = (BoatMeta) getEntityMeta();

        meta.setCustomName(Component.text("YOU SHOULD BE SCARED", TextColor.color(0xff0000)));
        meta.setCustomNameVisible(true);
        getAttribute(Attribute.MAX_HEALTH).setBaseValue(20f);
        setHealth(20);

        addEventCallback(EntityDamageEvent.class, event -> {
           setVelocity(getVelocity().add(0, 1,0));
            if (getHealth() > 0) {
                remove();
            }
        });



    }
}
