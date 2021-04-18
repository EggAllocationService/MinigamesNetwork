package io.egg.minigames.entities;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.minestom.server.attribute.Attribute;
import net.minestom.server.entity.EntityCreature;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.metadata.other.BoatMeta;
import net.minestom.server.event.entity.EntityDamageEvent;

public class MonsterBoatCreature extends EntityCreature {
    public MonsterBoatCreature() {
        super(EntityType.BOAT);
        /*addAIGroup(new EntityAIGroupBuilder()
                .addGoalSelector(new FollowTargetGoal(this, new UpdateOption(1, TimeUnit.SECOND)))
                .addGoalSelector(new RandomStrollGoal(this, 5))
                .addTargetSelector(new ClosestEntityTarget(this, 20, Player.class))
                .build()
        );*/
        this.setNoGravity(true);

        BoatMeta meta = (BoatMeta) getEntityMeta();

        meta.setCustomName(Component.text("OOOOO IM A GHOST BOAT", TextColor.color(0xff0000)));
        meta.setCustomNameVisible(true);
        getAttribute(Attribute.MAX_HEALTH).setBaseValue(20f);
        setHealth(20);

        addEventCallback(EntityDamageEvent.class, event -> {
           setVelocity(getVelocity().add(0, 1,0));
            if (getHealth() < 0) {
                remove();
            }
        });



    }
}
