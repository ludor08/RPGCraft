package org.rpg.rPGCraft.Animation.Animations;

import org.bukkit.Material;
import org.joml.Vector2d;
import org.joml.Vector3f;
import org.rpg.rPGCraft.Animation.Animation;
import org.rpg.rPGCraft.Animation.AnimationFrame;

import java.util.List;

public class TestBlackSquare extends Animation
{
    public TestBlackSquare()
    {
        super(List.of(
                new AnimationFrame(1, Material.REDSTONE, new Vector3f(1,1,1), new Vector3f()),
                new AnimationFrame(2, Material.REDSTONE, new Vector3f(1,1,1), new Vector3f()),
                new AnimationFrame(3, Material.REDSTONE, new Vector3f(1,1,1), new Vector3f()),
                new AnimationFrame(4, Material.REDSTONE, new Vector3f(1,1,1), new Vector3f()),
                new AnimationFrame(5, Material.REDSTONE, new Vector3f(1,1,1), new Vector3f()),
                new AnimationFrame(6, Material.REDSTONE, new Vector3f(1,1,1), new Vector3f()),
                new AnimationFrame(7, Material.REDSTONE, new Vector3f(1,1,1), new Vector3f()),
                new AnimationFrame(8, Material.REDSTONE, new Vector3f(1,1,1), new Vector3f()),
                new AnimationFrame(9, Material.REDSTONE, new Vector3f(1,1,1), new Vector3f()),
                new AnimationFrame(10, Material.REDSTONE, new Vector3f(1,1,1), new Vector3f()),
                new AnimationFrame(11, Material.REDSTONE, new Vector3f(1,1,1), new Vector3f()),
                new AnimationFrame(12, Material.REDSTONE, new Vector3f(1,1,1), new Vector3f()),
                new AnimationFrame(13, Material.REDSTONE, new Vector3f(1,1,1), new Vector3f()),
                new AnimationFrame(14, Material.REDSTONE, new Vector3f(1,1,1), new Vector3f()),
                new AnimationFrame(15, Material.REDSTONE, new Vector3f(1,1,1), new Vector3f()),
                new AnimationFrame(16, Material.REDSTONE, new Vector3f(1,1,1), new Vector3f()),
                new AnimationFrame(17, Material.REDSTONE, new Vector3f(1,1,1), new Vector3f()),
                new AnimationFrame(18, Material.REDSTONE, new Vector3f(1,1,1), new Vector3f()),
                new AnimationFrame(19, Material.REDSTONE, new Vector3f(1,1,1), new Vector3f()),
                new AnimationFrame(20, Material.REDSTONE, new Vector3f(1,1,1), new Vector3f())),

                "test_black_square",

                false
        );
    }
}
