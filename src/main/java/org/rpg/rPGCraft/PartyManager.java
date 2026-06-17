package org.rpg.rPGCraft;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.*;
import org.bukkit.persistence.PersistentDataType;
import org.rpg.rPGCraft.Definitions.MyNamespaces;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PartyManager
{
    File partyFile;
    File partyRulesFile;

    YamlConfiguration modifyPartyFile;
    YamlConfiguration modifyPartyRulesFile;
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

        // party rules
        partyRulesFile = new File(main.getDataFolder(), "partyRules.yml");
        if (!partyRulesFile.exists())
        {
            main.saveResource("partyRules.yml", false);
        }

        modifyPartyRulesFile = YamlConfiguration.loadConfiguration(partyRulesFile);
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
             else if (entity instanceof Tameable tameable)
             {
                 if (!tameable.isTamed())
                 {
                     return false;
                 }

                 if (tameable.getOwner() instanceof Entity owner && modifyPartyFile.get(partyName + "." + owner.getUniqueId()) != null)
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

    public boolean ShouldHitBeStoppedByParty(Entity entity1, Entity entity2)
    {
        if (main.partyManager.IsInTheSameParty(entity1, entity2))
        {
            for (String party : main.partyManager.SharedParties(entity1, entity2))
            {
                if (!main.partyManager.DoesPartyHaveFriendlyFire(party))
                {
                    return true;
                }
            }
        }

        return false;
    }

    public List<String> SharedParties(Entity entity1, Entity entity2)
    {
        List<String> parties = new ArrayList<>();

        for (String party : GetPartiesWithPlayer(entity1))
        {
            if (IsInParty(entity2, party))
            {
                parties.add(party);
            }
        }

        return parties;
    }

    public boolean PartyExists(String partyName)
    {
        return modifyPartyFile.get(partyName) != null;
    }

    public void CreateParty(Player owner, String partyName)
    {
        // if the party doesn't exist, make the party
        if (modifyPartyFile.get(partyName) == null)
        {
            modifyPartyFile.set(partyName + "." + owner.getUniqueId().toString(), 2);
            try {
                modifyPartyFile.save(partyFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // if the party doesn't have a rule set, make one
        if (modifyPartyRulesFile.get(partyName) == null)
        {
            SetDefaultPartyRules(partyName);
        }
    }

    private void SetDefaultPartyRules(String partyName)
    {
        modifyPartyRulesFile.set(partyName + ".should_share_class_xp", false);
        modifyPartyRulesFile.set(partyName + ".friendly_fire", false);

        try {
            modifyPartyRulesFile.save(partyRulesFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean DoesPartyHaveFriendlyFire(String partyName)
    {
        return (boolean) modifyPartyFile.get(partyName + ".friendly_fire");
    }

    public void UpdateFriendlyFireRule(boolean friendlyFire, String partyName)
    {
        modifyPartyRulesFile.set(partyName + ".friendly_fire", friendlyFire);

        try {
            modifyPartyRulesFile.save(partyRulesFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void UpdateShareClassXPRule(boolean shouldShare, String partyName)
    {
        modifyPartyRulesFile.set(partyName + ".should_share_class_xp", shouldShare);

        try {
            modifyPartyRulesFile.save(partyRulesFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean DoesPartyShareClassXP(String partyName)
    {
        return (boolean) modifyPartyFile.get(partyName + ".should_share_class_xp");
    }

    public void DisbandParty(String partyName)
    {
        if (modifyPartyFile.get(partyName) != null)
        {
            modifyPartyFile.set(partyName, null);
        }

        if (modifyPartyRulesFile.get(partyName) != null)
        {
            modifyPartyRulesFile.set(partyName, null);
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
        entity.getPersistentDataContainer().set(MyNamespaces.LAST_PARTY_INVITE.GetNamespacedKey(), PersistentDataType.STRING, partyName);

        // send a message
        TextComponent clickJoinMessage = new TextComponent("§a§l[Click Here]");
        clickJoinMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/party accept"));

        BaseComponent[] baseComponents = new BaseComponent[3];
        baseComponents[0] = new TextComponent("You have been invited to " + partyName + " ");
        baseComponents[1] = clickJoinMessage;
        baseComponents[2] = new TextComponent(" to join or do /party accept");


        entity.spigot().sendMessage(baseComponents);
    }

    public List<Player> GetPlayersToShareClassXpWith(Player player)
    {
        List<Player> players = new ArrayList<>();

        for (String party : GetPartiesWithPlayer(player))
        {
            if (!DoesPartyShareClassXP(party))
            {
                continue;
            }

            for (Player partyMembers : GetPlayersInParty(party))
            {
                if (!players.contains(partyMembers))
                {
                    players.add(partyMembers);
                }
            }
        }

        return players;
    }
}
