package org.rpg.rPGCraft.Animation;

import java.util.List;

public class Animation
{
    private final List<AnimationFrame> frames;
    private final String nameID;
    private final boolean looping;

    public Animation(List<AnimationFrame> frames, String nameID, boolean looping)
    {
        this.frames = frames;
        this.nameID = nameID;
        this.looping = looping;
    }

    public int GetNumberOfFrames()
    {
        return frames.size();
    }

    public String GetNameID()
    {
        return nameID;
    }

    public boolean IsLooping()
    {
        return looping;
    }

    public AnimationFrame GetFrame(int frameNumber)
    {
        return frames.get(frameNumber-1);
    }
}
