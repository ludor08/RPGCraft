package org.rpg.rPGCraft.Animation.Animations;

import org.bukkit.Material;
import org.joml.Vector3f;
import org.rpg.rPGCraft.Animation.Animation;
import org.rpg.rPGCraft.Animation.AnimationFrame;

import java.util.List;

public class VoidBombExplode extends Animation
{
    public VoidBombExplode()
    {
        super(List.of(
                new AnimationFrame(22, Material.REDSTONE, new Vector3f(1,1,1), new Vector3f(0,2,0)),
                new AnimationFrame(22, Material.REDSTONE, new Vector3f(1.2f,1,1.2f), new Vector3f(0,2,0)),
                new AnimationFrame(22, Material.REDSTONE, new Vector3f(1.4f,1,1.4f), new Vector3f(0,2,0)),
                new AnimationFrame(22, Material.REDSTONE, new Vector3f(1.6f,1,1.6f), new Vector3f(0,2,0)),
                new AnimationFrame(22, Material.REDSTONE, new Vector3f(1.8f,1,1.8f), new Vector3f(0,2,0)),
                new AnimationFrame(22, Material.REDSTONE, new Vector3f(2f,1,2f), new Vector3f(0,2,0)),
                new AnimationFrame(22, Material.REDSTONE, new Vector3f(2.2f,1,2.2f), new Vector3f(0,2,0)),
                new AnimationFrame(22, Material.REDSTONE, new Vector3f(2.4f,1,2.4f), new Vector3f(0,2,0)),
                new AnimationFrame(22, Material.REDSTONE, new Vector3f(2.6f,1,2.6f), new Vector3f(0,2,0)),
                new AnimationFrame(22, Material.REDSTONE, new Vector3f(2.8f,1,2.8f), new Vector3f(0,2,0)),
                new AnimationFrame(22, Material.REDSTONE, new Vector3f(3f,1,3f), new Vector3f(0,2,0)),
                new AnimationFrame(22, Material.REDSTONE, new Vector3f(3.2f,1,3.2f), new Vector3f(0,2,0)),
                new AnimationFrame(22, Material.REDSTONE, new Vector3f(3.4f,1,3.4f), new Vector3f(0,2,0)),
                new AnimationFrame(22, Material.REDSTONE, new Vector3f(3.6f,1,3.6f), new Vector3f(0,2,0)),
                new AnimationFrame(22, Material.REDSTONE, new Vector3f(3.8f,1,3.8f), new Vector3f(0,2,0)),
                new AnimationFrame(22, Material.REDSTONE, new Vector3f(4f,1,4f), new Vector3f(0,2,0)),
                new AnimationFrame(22, Material.REDSTONE, new Vector3f(4.2f,1,4.2f), new Vector3f(0,2,0)),
                new AnimationFrame(22, Material.REDSTONE, new Vector3f(4.4f,1,4.4f), new Vector3f(0,2,0)),
                new AnimationFrame(22, Material.REDSTONE, new Vector3f(4.6f,1,4.6f), new Vector3f(0,2,0)),
                new AnimationFrame(22, Material.REDSTONE, new Vector3f(4.8f,1,4.8f), new Vector3f(0,2,0))),

                "void_bomb_explode",

                false);
    }
}
