package org.rpg.rPGCraft.Animation.Animations;

import org.bukkit.Material;
import org.joml.Vector2d;
import org.joml.Vector3f;
import org.rpg.rPGCraft.Animation.Animation;
import org.rpg.rPGCraft.Animation.AnimationFrame;

import java.util.List;

public class TestBlackSquareTwo extends Animation
{
    public TestBlackSquareTwo()
    {
        super(List.of(
                new AnimationFrame(1, Material.REDSTONE, new Vector3f(1,1, 1), new Vector3f())),

                "test_black_square_two",

                true
        );
    }
}
