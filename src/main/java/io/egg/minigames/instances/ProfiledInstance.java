package io.egg.minigames.instances;

import io.egg.minigames.profiles.DefaultProfileDelegate;
import io.egg.minigames.profiles.ProfileData;
import net.minestom.server.instance.Instance;




public class ProfiledInstance {
    private Instance i;
    private ProfileData pd;
    private DefaultProfileDelegate p;
    public ProfiledInstance(Instance ii, DefaultProfileDelegate a, ProfileData ppd) {
        i = ii;
        pd = ppd;
        p = a;
    }
}
