package org.ayple.hcfcore.commands.faction;

import org.ayple.hcfcore.Hcfcore;
import org.ayple.hcfcore.commands.SubCommand;
import org.ayple.hcfcore.core.BalanceHandler;
import org.ayple.hcfcore.core.faction.Faction;
import org.ayple.hcfcore.core.faction.NewFactionManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class CommandFactionDeposit extends SubCommand {
    @Override
    public String getName() {
        return "deposit";
    }

    @Override
    public String getDescription() {
        return "deposit money into the faction balance";
    }

    @Override
    public String getSyntax() {
        return "/f deposit [amount]";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (args.length >= 2) {
            double amount;

            if (args[1].equalsIgnoreCase("all")) {
                amount = BalanceHandler.getPlayerBalance(player);
            } else {
                amount = Integer.parseInt(args[1]);
            }

            if (amount == 0) {
                return;
            }


            Faction player_faction = NewFactionManager.getFactionFromPlayerID(player.getUniqueId());
            if (player_faction == null) {
                player.sendMessage("You are not in a faction!");
                return;
            }

            if (BalanceHandler.getPlayerBalance(player) >= amount) {
                BalanceHandler.takeMoneyFromPlayer(player, amount);
                BalanceHandler.giveMoneyToFaction(player_faction.getFactionID(), amount);
                player.sendMessage(ChatColor.GREEN + "Deposited " + Double.toString(amount) + " into the faction balance!");
                return;
            }

            player.sendMessage("Insufficient funds");
            return;
        }
    }
}
