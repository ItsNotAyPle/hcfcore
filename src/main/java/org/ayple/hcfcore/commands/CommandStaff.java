package org.ayple.hcfcore.commands;

import org.ayple.hcfcore.commands.staff.CommandSetDTR;
import org.ayple.hcfcore.commands.staff.CommandStartRegen;
import org.ayple.hcfcore.core.StaffHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CommandStaff implements CommandExecutor {
    private ArrayList<SubCommand> subcommands = new ArrayList<SubCommand>();


    public CommandStaff() {
        subcommands.add(new CommandSetDTR());
        subcommands.add(new CommandStartRegen());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        if (!player.hasPermission("hcf.core.command.staff")) {
            player.sendMessage(ChatColor.RED + "No permission!");
            return true;
        }

        if (args.length == 0) {
            if (!StaffHandler.playerInStaffMode(player)) {
                StaffHandler.onEnterStaffMode(player);
            } else {
                StaffHandler.onExitStaffMode(player);
            }

            return true;
        }

        for (SubCommand subcommand : subcommands) {
            if (args[0].equalsIgnoreCase(subcommand.getName())) {
                subcommand.runCommand(player, args);
                return true;
            }
        }

        return true;
    }
}