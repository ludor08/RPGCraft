package org.rpg.rPGCraft.Animation.Animations;

import org.bukkit.Material;
import org.joml.Vector2d;
import org.joml.Vector3d;
import org.joml.Vector3f;
import org.rpg.rPGCraft.Animation.Animation;
import org.rpg.rPGCraft.Animation.AnimationFrame;

import java.util.List;

public class ZombieKingIdle extends Animation
{
    public ZombieKingIdle()
    {
        super(List.of(new AnimationFrame(21, Material.REDSTONE, new Vector3f(1.8f,1.8f, 1.8f), new Vector3f(0,1.55f,0))), "zombie_king_idle", true);
    }
}
