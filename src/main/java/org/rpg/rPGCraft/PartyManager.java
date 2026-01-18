package org.rpg.rPGCraft;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Wolf;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PartyManager
{
    File partyFile;

    YamlConfiguration modifyPartyFile;
    Main main;

    public PartyManager()
    {
        this.main = Main.GetInstance();

        File pluginFolder = main.getDataFolder();
        if (!pluginFolder.exists())
        {
            pluginFolder.mkdirs();
        }

        // parties
        partyFile = new File(main.getDataFolder(), "parties.yml");
        if (!partyFile.exists())
        {
            main.saveResource("parties.yml", false);
        }

        modifyPartyFile = YamlConfiguration.loadConfiguration(partyFile);
    }

    public List<String> GetParties()
    {
        List<String> parties = new ArrayList<>();

        parties.addAll(modifyPartyFile.getKeys(false));

        return parties;
    }

    public List<String> GetPartiesWithPlayer(Entity entity)
    {
        List<String> occupiedParties = new ArrayList<>();

        for (String party : GetParties())
        {
            if (IsInParty(entity, party))
            {
                occupiedParties.add(party);
            }
        }

        return occupiedParties;
    }

    public List<String> GetPartiesWithoutPlayer(Entity entity)
    {
        List<String> nonOccupiedParties = new ArrayList<>();

        for (String party : GetParties())
        {
            if (!IsInParty(entity, party))
            {
                nonOccupiedParties.add(party);
            }
        }

        return nonOccupiedParties;
    }

    public boolean IsInParty(Entity entity, String partyName)
    {
         if (modifyPartyFile.get(partyName) != null)
         {
             if (modifyPartyFile.get(partyName + "." + entity.getUniqueId()) != null)
             {
                 return true;
             }
             else if (entity instanceof Wolf wolf)
             {
                 if (!wolf.isTamed())
                 {
                     return false;
                 }

                 if (wolf.getOwner() instanceof Entity owner && modifyPartyFile.get(partyName + "." + owner.getUniqueId()) != null)
                 {
                     return true;
                 }
             }
             else if (entity instanceof Projectile projectile)
             {
                 if (projectile.getShooter() != null && modifyPartyFile.get(partyName + "." + projectile.getOwnerUniqueId()) != null)
                 {
                     return true;
                 }
             }
         }

         return false;
    }

    public List<Player> GetPlayersInParty(String partyName)
    {
        List<Player> players = new ArrayList<>();

        for (String key : modifyPartyFile.getKeys(true))
        {
            if (Objects.equals(key.split("\\.")[0], partyName))
            {
                if (Objects.equals(key, partyName))
                {
                    continue;
                }

                players.add(Bukkit.getPlayer(key.split("\\.")[1]));
            }
        }

        return players;
    }

    public boolean IsInTheSameParty(Entity entity1, Entity entity2)
    {
        for (String party : GetPartiesWithPlayer(entity1))
        {
            if (IsInParty(entity2, party))
            {
                return true;
            }
        }

        return false;
    }

    public boolean PartyExists(String partyName)
    {
        return modifyPartyFile.get(partyName) != null;
    }

    public void CreateParty(Player owner, String partyName)
    {
        if (modifyPartyFile.get(partyName) == null)
        {
            modifyPartyFile.set(partyName + "." + owner.getUniqueId().toString(), 2);
            try {
                modifyPartyFile.save(partyFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void DisbandParty(String partyName)
    {
        if (modifyPartyFile.get(partyName) != null)
        {
            System.out.println("i did a thing");
            modifyPartyFile.set(partyName, null);
        }
    }

    public void AddToParty(Entity entity, String partyName, int permissions)
    {
        if (modifyPartyFile.get(partyName) != null)
        {
            modifyPartyFile.set(partyName + "." + entity.getUniqueId().toString(), permissions);
            try
            {
                modifyPartyFile.save(partyFile);
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    public void RemoveFromParty(Entity entity, String partyName)
    {
        if (modifyPartyFile.get(partyName) != null)
        {
            if (IsInParty(entity, partyName))
            {
                modifyPartyFile.set(partyName + "." + entity.getUniqueId().toString(), null);
                try
                {
                    modifyPartyFile.save(partyFile);
                }
                catch (IOException e)
                {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public int GetPermissionsForParty(Entity entity, String partyName)
    {
        if (IsInParty(entity, partyName))
        {
            return modifyPartyFile.getInt(partyName + "." + entity.getUniqueId());
        }

        return -1;
    }

    public void SetPermissionsForParty(Entity entity, String partyName, int permissionLevel)
    {
        if (IsInParty(entity, partyName))
        {
            modifyPartyFile.set(partyName + "." + entity.getUniqueId(), permissionLevel);
        }
    }

    public void InvitePlayerToParty(Entity entity, String partyName)
    {
        entity.getPersistentDataContainer().set(main.GetLastPartyInviteKey(), PersistentDataType.STRING, partyName);

        // send a message
        TextComponent clickJoinMessage = new TextComponent("§a§l[Click Here]");
        clickJoinMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/party accept"));

        BaseComponent[] baseComponents = new BaseComponent[3];
        baseComponents[0] = new TextComponent("You have been invited to " + partyName + " ");
        baseComponents[1] = clickJoinMessage;
        baseComponents[2] = new TextComponent(" to join or do /party accept");


        entity.spigot().sendMessage(baseComponents);

    }
}
