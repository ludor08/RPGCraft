package org.rpg.rPGCraft.Races;

import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.StatSheet;

import java.util.ArrayList;
import java.util.List;

public class StatSheetManager
{

    // stat sheets
    private List<StatSheet> statSheets = new ArrayList<>();

    // Getter
    public List<StatSheet> GetStatSheets()
    {
        return statSheets;
    }

    // Adder
    public void AddStatSheet(StatSheet statSheet)
    {
        this.statSheets.add(statSheet);
    }

    public StatSheetManager(Main main)
    {

    }

}
