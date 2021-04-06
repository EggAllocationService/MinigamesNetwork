package io.egg.minigames.instances;

import io.egg.minigames.profiles.DefaultProfileDelegate;
import io.egg.minigames.profiles.ProfileData;
import net.minestom.server.instance.Instance;




public class ProfiledInstance {
    private String name;
    private Instance i;
    private ProfileData pd;
    private DefaultProfileDelegate p;
    public ProfiledInstance(Instance ii, DefaultProfileDelegate a, ProfileData ppd, String n) {
        i = ii;
        pd = ppd;
        p = a;
        name = n;
    }

    public ProfileData getProfileData() {
        return pd;
    }

    public DefaultProfileDelegate getDelegate() {
        return p;
    }

    public String getName() {
        return name;
    }
}
