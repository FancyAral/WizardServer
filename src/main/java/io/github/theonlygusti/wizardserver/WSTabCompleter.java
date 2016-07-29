package io.github.theonlygusti.wizardserver;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.TabCompleter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class WSTabCompleter implements TabCompleter {
  private final WizardServer plugin;

  public WSTabCompleter(WizardServer plugin) {
    this.plugin = plugin;
  }

  public final List<String> getCompletions(String toComplete, List<String> possibilities) {
    if (toComplete.length() > 0) {
      return possibilities.stream().filter(possibility -> Pattern.matches("^" + toComplete + ".*", possibility)).collect(Collectors.toList());
    } else {
      return possibilities;
    }
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
    List<String> possibilities = new ArrayList<String>();
    if (command.getName().equals("wizardserver")) {
      if (args.length == 1) {
        possibilities.addAll(getCompletions(args[0], Arrays.asList("inventory", "player", "database", "gui", "tutorial")));
      } else if (args[0].equals("inventory")) {
        possibilities.addAll(getCompletions(args[1], Arrays.asList("spells")));
      } else if (args[0].equals("database")) {
        possibilities.addAll(getCompletions(args[1], Arrays.asList("show", "query", "execute")));
      } else if (args[0].equals("player")) {
        possibilities.addAll(getCompletions(args[1], Arrays.asList("setrank", "getadminlevel", "setadminlevel")));
      } else {
        return null;
      }
    } else {
      return null;
    }
    return possibilities;
  }
}
