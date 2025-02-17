package org.ayple.hcfcore.commands;

import org.ayple.hcfcore.commands.faction.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandFaction implements CommandExecutor {
    private ArrayList<SubCommand> subcommands = new ArrayList<SubCommand>();

    public CommandFaction() {
        subcommands.add(new CommandFactionBal());
        subcommands.add(new CommandFactionClaim());
        subcommands.add(new CommandFactionCreate());
        subcommands.add(new CommandFactionDeposit());
        subcommands.add(new CommandFactionDisband());
        subcommands.add(new CommandFactionFocus());
        subcommands.add(new CommandFactionHome());
        subcommands.add(new CommandFactionInvite());
        subcommands.add(new CommandFactionJoin());
        subcommands.add(new CommandFactionKick());
        subcommands.add(new CommandFactionLeader());
        subcommands.add(new CommandFactionLeave());
        subcommands.add(new CommandFactionMap());
        subcommands.add(new CommandFactionPromote());
        subcommands.add(new CommandFactionRename());
        subcommands.add(new CommandFactionSetHome());
        subcommands.add(new CommandFactionWho());
        subcommands.add(new CommandFactionShow());
        subcommands.add(new CommandFactionWithdraw());

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;

        System.out.println(Arrays.toString(args));
        if (args.length > 0) {
            for (SubCommand subcommand : subcommands) {
                if (args[0].equalsIgnoreCase(subcommand.getName())) {
                    subcommand.runCommand(player, args);
                    return true;
                }
            }
        }

        for (SubCommand subcommand : subcommands) {
            player.sendMessage(subcommand.getSyntax());
        }

        return true;
    }
}
