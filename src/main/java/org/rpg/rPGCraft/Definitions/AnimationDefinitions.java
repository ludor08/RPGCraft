package org.rpg.rPGCraft.Definitions;

import org.rpg.rPGCraft.Animation.Animation;
import org.rpg.rPGCraft.Animation.Animations.*;
import org.rpg.rPGCraft.Main;

import java.util.HashMap;

public class AnimationDefinitions
{
    private static HashMap<String, Animation> animationMap = new HashMap<String, Animation>();

    private static void AddAnimationToMap(Animation animation)
    {
        animationMap.put(animation.GetNameID(), animation);
    }

    public static void Initialize()
    {
        AddAnimationToMap(new TestBlackSquare());
        AddAnimationToMap(new UnTestBlackSquare());
        AddAnimationToMap(new VoidBombExplode());
        AddAnimationToMap(new TestBlackSquareTwo());
        AddAnimationToMap(new ZombieKingIdle());
    }

    public static Animation GetAnimationByID(String name_id)
    {
        if (animationMap.containsKey(name_id))
        {
            return animationMap.get(name_id);
        }
        else
        {
            Main.GetInstance().getLogger().warning("Animation \"" + name_id + "\" is not contained in animationMap.");
            return null;
        }
    }

    public static boolean HasDefinitionWithID(String name_id)
    {
        return animationMap.containsKey(name_id);
    }

    public static HashMap<String, Animation> GetAnimationIdMap()
    {
        return animationMap;
    }
}
