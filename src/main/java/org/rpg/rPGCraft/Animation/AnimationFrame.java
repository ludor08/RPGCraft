package org.rpg.rPGCraft.Animation;

import org.bukkit.Material;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3d;
import org.joml.Vector3f;

public class AnimationFrame
{
    ItemStack frameStack;
    Transformation transformation;

    public AnimationFrame(int customModelData, Material itemType, Vector3f scale, Vector3f translation)
    {
        // make the item stack
        frameStack = new ItemStack(itemType);
        ItemMeta frameMeta = frameStack.getItemMeta();

        frameMeta.setCustomModelData(customModelData);

        frameStack.setItemMeta(frameMeta);

        // make the transformation
        transformation = new Transformation(
                translation,
                new AxisAngle4f(),
                scale,
                new AxisAngle4f()
        );
    }

    public ItemStack GetItemStackForFrame()
    {
        return frameStack;
    }

    public void SetTransformationForDisplay(ItemDisplay itemDisplay)
    {
        itemDisplay.setTransformation(transformation);
    }

    public Transformation GetTransformation()
    {
        return transformation;
    }
}
