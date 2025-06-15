package org.rpg.rPGCraft.Races;

import org.bukkit.entity.Player;
import org.rpg.rPGCraft.Main;
import org.rpg.rPGCraft.StatSheet;

import java.util.ArrayList;
import java.util.List;

public class StatSheetManager
{
    Main main;


    // stat sheets
    private List<StatSheet> statSheets = new ArrayList<>();

    // Getter
    public List<StatSheet> GetStatSheets()
    {
        return statSheets;
    }

    public StatSheet FindStatSheetByPlayer(Player player)
    {
        for (StatSheet statSheet : statSheets)
        {
            if (statSheet.GetPlayer().equals(player))
            {
                return statSheet;
            }
        }

        return null;
    }

    // Adder
    public void AddStatSheet(StatSheet statSheet)
    {
        this.statSheets.add(statSheet);
    }

    public StatSheetManager(Main main)
    {
        this.main = main;
    }

}
