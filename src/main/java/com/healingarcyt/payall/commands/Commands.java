package com.healingarcyt.payall.commands;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.Player;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;

import me.onebone.economyapi.EconomyAPI;

public class Commands extends PluginBase {
  private EconomyAPI economyapi;

  @Override
  public void onEnable() {
  this.economyapi = EconomyAPI.getInstance();

  /*try {
      this.economyapi = EconomyAPI.getInstance();
    }
    finally {
      this.getServer().getLogger().info(TextFormat.RED + "EconomyAPI konnte nicht verwendet werden");
      this.getServer().getLogger().info("Deaktiviert");
      this.getServer().getPluginManager().disablePlugin(this);
    }*/
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (command.getName().toLowerCase() == "payall") {
      if (sender instanceof Player) {
        Integer amount = Integer.valueOf(args[0]);
        if (this.getServer().getOnlinePlayers().size()*amount <= this.economyapi.myMoney(sender.getName())) {
          this.economyapi.reduceMoney(sender.getName(), this.getServer().getOnlinePlayers().size()*amount);
          for (Player player : this.getServer().getOnlinePlayers().values()) {
            this.economyapi.addMoney(player.getName(), amount);
          }
        }
        else {
          sender.sendMessage("Du hast nicht genug Geld dafür");
        }
        return true;
      }

      else {
        sender.sendMessage("Nur als Spieler ausführbar");
        return true;
      }
    }

    if (command.getName().toLowerCase() == "consolepayall") {
      Integer amount = Integer.valueOf(args[0]);
      for (Player player : this.getServer().getOnlinePlayers().values()) {
        this.economyapi.addMoney(player.getName(), amount);
      }
      return true;
    }
    return true;
  }
}
