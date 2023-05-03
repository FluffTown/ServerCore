package com.zip.servercore;

import com.zip.servercore.utility.DBControl;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedMetaData;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Random;

public final class Main extends JavaPlugin implements Listener {
    //test
    DBControl control;
    @Override
    public void onEnable() {
        //load Survival world
        getServer().createWorld(new WorldCreator("Survival"));
        //register events
        getServer().getPluginManager().registerEvents(this, this);
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        control = new DBControl();
    }
    public void reconnectDB() {
        try {
            if (control.connection.isClosed()) {
                control.connection = DriverManager.getConnection("jdbc:mariadb://127.0.0.1/Server","root","password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

    }
    public String color(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        //prevent kick
        String[] blocked_players = new String[] {"_7zip","Hero_2006"};
        if (command.getName().equalsIgnoreCase("kick")) {
            if (args.length == 0) {
                return false;
            }
            Player target = Bukkit.getPlayer(args[0]);
            for (String blockedPlayer : blocked_players) {
                if (Objects.equals(args[0], blockedPlayer)) {
                    return true;
                }
            }
            if (target != null) {
                target.kickPlayer("kicked");
            }
            return true;
        }

        //gmc command
        if (command.getName().equalsIgnoreCase("gmc")) {
            if (args.length == 0) {
                //no args
                player.setGameMode(GameMode.CREATIVE);
            } else {
                //player argument
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    target.setGameMode(GameMode.CREATIVE);
                } else {
                    player.sendMessage(color("&8[&c!&8]&7 Player not found"));
                }
            }
            return true;
        }

        //gms command
        if (command.getName().equalsIgnoreCase("gms")) {
            if (args.length == 0) {
                //no args
                player.setGameMode(GameMode.SURVIVAL);
            } else {
                //player argument
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    target.setGameMode(GameMode.SURVIVAL);
                } else {
                    player.sendMessage(color("&8[&c!&8]&7 Player not found"));
                }
            }
            return true;
        }

        //gmsp command
        if (command.getName().equalsIgnoreCase("gmsp")) {
            if (args.length == 0) {
                //no args
                player.setGameMode(GameMode.SPECTATOR);
            } else {
                //player argument
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    target.setGameMode(GameMode.SPECTATOR);
                } else {
                    player.sendMessage(color("&8[&c!&8]&7 Player not found"));
                }
            }
            return true;
        }

        //heal command
        if (command.getName().equalsIgnoreCase("heal")) {
            if (args.length == 0) {
                //no args
                player.setHealth(20);
                player.setFoodLevel(20);
            } else {
                //player argument
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    target.setHealth(20);
                    target.setFoodLevel(20);
                } else {
                    player.sendMessage(color("&8[&c!&8]&7 Player not found"));
                }
            }
            return true;
        }

        //feed command
        if (command.getName().equalsIgnoreCase("feed")) {
            if (args.length == 0) {
                //no args
                player.setFoodLevel(20);
            } else {
                //player argument
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    target.setFoodLevel(20);
                } else {
                    player.sendMessage(color("&8[&c!&8]&7 Player not found"));
                }
            }
            return true;
        }

        //fly command
        if (command.getName().equalsIgnoreCase("fly")) {
            if (args.length == 0) {
                //no args
                player.setAllowFlight(!player.getAllowFlight());
                player.setFlying(player.getAllowFlight());
            } else {
                //player argument
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    target.setAllowFlight(!target.getAllowFlight());
                    target.setFlying(target.getAllowFlight());
                } else {
                    player.sendMessage(color("&8[&c!&8]&7 Player not found"));
                }
            }
            return true;
        }

        //openinv command
        if (command.getName().equalsIgnoreCase("openinv")) {
            if (args.length == 0) {
                //no args
                player.openInventory(player.getInventory());
            } else {
                //player argument
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    player.openInventory(target.getInventory());
                } else {
                    player.sendMessage(color("&8[&c!&8]&7 Player not found"));
                }
            }
            return true;
        }

        //openender command
        if (command.getName().equalsIgnoreCase("openender")) {
            if (args.length == 0) {
                //no args
                player.openInventory(player.getEnderChest());
            } else {
                //player argument
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    player.openInventory(target.getEnderChest());
                } else {
                    player.sendMessage(color("&8[&c!&8]&7 Player not found"));
                }
            }
            return true;
        }

        if (command.getName().equalsIgnoreCase("msg")) {
            if (args.length == 0) {
                player.sendMessage(color("&8[&c!&8]&7 Please specify a player"));
            } else {
                //player argument
                Player target = Bukkit.getPlayer(args[0]);
                StringBuffer sb = new StringBuffer();
                for(int i = 1; i < args.length; i++) {
                    sb.append(args[i] + " ");
                }
                String message = sb.toString();
                if (target != null) {
                    //&8[&2M&8]&7 &7[&rPlayer &7-> &rTarget&7] &7»&r Hello World
                    player.sendMessage(color("&8[&2M&8]&7 &7[&rYou &7→&7 &r"+target.getName()+"&7] &7»&r "+message));
                    target.sendMessage(color("&8[&2M&8]&7 &7[&r"+player.getName()+" &7→ &rYou&7] &7»&r "+message));
                } else {
                    player.sendMessage(color("&8[&c!&8]&7 Player not found"));
                }
            }
            return true;
        }

        if (command.getName().equalsIgnoreCase("sudo")) {
            if (args.length == 0) {
                player.sendMessage(color("&8[&c!&8]&7 Please specify a player"));
            } else {
                //player argument
                Player target = Bukkit.getPlayer(args[0]);
                StringBuffer sb = new StringBuffer();
                for(int i = 1; i < args.length; i++) {
                    sb.append(args[i] + " ");
                }
                String message = sb.toString();
                if (target != null) {
                    //&8[&2M&8]&7 &7[&rPlayer &7-> &rTarget&7] &7»&r Hello World
                    target.performCommand(message);
                } else {
                    player.sendMessage(color("&8[&c!&8]&7 Player not found"));
                }
            }
            return true;
        }

        //rtp command
        if (command.getName().equalsIgnoreCase("rtp")) {
            Random random = new Random();
            boolean safe = false;
            int count = 0;
            while (!safe) {
                count++;
                if (count > 100) {
                    player.sendMessage(color("&4[Error]&f Unable to find safe location"));
                    break;
                }
                int x = random.nextInt(-1000,1000);
                int y = random.nextInt(-1000,1000);
                try {
                    Block block = Bukkit.getWorld("Survival").getHighestBlockAt(x, y);
                    if (block.getType() != Material.WATER) {
                        safe = true;
                        player.teleport(block.getLocation());
                    }
                } catch (Exception e) {
                    player.sendMessage(color("&4[Error]&f World Does Not Exist"));
                    return true;
                }
            }
            return true;
        }
        //spawn command
        if (command.getName().equalsIgnoreCase("spawn")) {
            player.teleport(new Location(Bukkit.getWorld("world"), 0.5,1,0.5));
            return true;
        }

        if (command.getName().equalsIgnoreCase("settag")) {
            reconnectDB();
            control.delete("Tags","uuid","\""+ player.getUniqueId() +"\"");
            if (args.length > 0) {
                control.insert("Tags", new String[]{player.getUniqueId().toString(), args[0]});
                player.sendMessage(color("[i] tag set to: " + args[0]));
            } else {
                player.sendMessage("[i] tag removed");
            }
            return true;
        }
        if (command.getName().equalsIgnoreCase("deltag")) {
            reconnectDB();
            control.delete("Tags","uuid","\""+ player.getUniqueId() +"\"");
            player.sendMessage("[i] tag removed");
            return true;
        }

        if (command.getName().equalsIgnoreCase("repair")) {
            if (args.length == 0) {
                Inventory inv = player.getInventory();
                for (int i = 0 ; i < inv.getContents().length; i++) {
                    ItemStack item = inv.getContents()[i];
                    if (item != null) {
                        org.bukkit.inventory.meta.Damageable meta = (org.bukkit.inventory.meta.Damageable) item.getItemMeta();
                        meta.setDamage(0);
                        item.setItemMeta(meta);
                    }
                }
            } else {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    Inventory inv = target.getInventory();
                    for (int i = 0 ; i < inv.getContents().length; i++) {
                        ItemStack item = inv.getContents()[i];
                        if (item != null) {
                            org.bukkit.inventory.meta.Damageable meta = (org.bukkit.inventory.meta.Damageable) item.getItemMeta();
                            meta.setDamage(0);
                            item.setItemMeta(meta);
                        }
                    }
                } else {
                    player.sendMessage("[i] player not found");
                }
            }
            return true;
        }
        //valid command was not run
        return false;
    }

    @EventHandler
    public void onPlayerPortal(PlayerPortalEvent e) {
        World fromWorld = e.getFrom().getWorld();
        if (e.getCause() == PlayerTeleportEvent.TeleportCause.NETHER_PORTAL) {
            if (fromWorld.getEnvironment() == World.Environment.NETHER) {
                Location to = e.getTo();
                to.setWorld(Bukkit.getWorld("Survival"));
                e.setTo(to);
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        //new players are teleported to spawn
        if (!player.hasPlayedBefore()) {
            player.teleport(new Location(Bukkit.getWorld("world"), 0.5,1,0.5));
        }
        CachedMetaData metaData = LuckPermsProvider.get().getPlayerAdapter(Player.class).getMetaData(event.getPlayer());
        event.getPlayer().setPlayerListName(ChatColor.translateAlternateColorCodes('&', "&7[&f" + metaData.getPrefix() + "&f&7] &f" + event.getPlayer().getDisplayName()));
        event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', "&8[&a+&8] &a" + event.getPlayer().getName()));
    }
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        event.setQuitMessage(ChatColor.translateAlternateColorCodes('&', "&8[&c-&8] &c" + event.getPlayer().getName()));
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        LuckPerms api = LuckPermsProvider.get();
        CachedMetaData metaData = api.getPlayerAdapter(Player.class).getMetaData(event.getPlayer());
        String prefix = metaData.getPrefix();
        reconnectDB();
        ResultSet rs = control.selectRaw("Tags", "uuid", "\""+event.getPlayer().getUniqueId().toString()+"\"");
        if (prefix != null) {
            prefix = prefix.substring(0,2) + ChatColor.stripColor(prefix).substring(2,3);
        }
        String message = (prefix == null ? "&fU" : prefix) + "&f " + event.getPlayer().getDisplayName() + "&f&7 » &f" + event.getMessage();
        try {
            if (rs.next()) {
                message = prefix + " &7[&f"+rs.getString("tag") +"&f&7] &f" + event.getPlayer().getDisplayName() + "&f&7 » &f" + event.getMessage();
            }
        } catch (Exception e) {}
        event.setFormat(ChatColor.translateAlternateColorCodes('&',message));
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.isGliding()) {
            if (player.getWorld().getName().equals("world")) {
                player.setGliding(false);
                event.setCancelled(true);
            }
        }
    }
}