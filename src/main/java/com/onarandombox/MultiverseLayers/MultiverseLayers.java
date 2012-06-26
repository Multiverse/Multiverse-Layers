package com.onarandombox.MultiverseLayers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import me.main__.util.SerializationConfig.SerializationConfig;

import org.bukkit.Location;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import com.onarandombox.MultiverseCore.api.MultiversePlugin;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import com.pneumaticraft.commandhandler.CommandHandler;

public class MultiverseLayers extends MultiversePlugin {
    private List<Sandvich> lunch;
    private Map<String, Sandvich> quickLookup;

    @Override
    public void onLoad() {
        SerializationConfig.registerAll(Sandvich.class);
    }

    @Override
    public int getProtocolVersion() {
        return 15;
    }

    @Override
    protected void onPluginEnable() {
        this.lunch = new ArrayList<Sandvich>();
        if (this.getConfig().isList("lunch")) {
            List<?> stuff = this.getConfig().getList("lunch");
            for (Object o : stuff) {
                if (o instanceof Sandvich)
                    // yummy!
                    lunch.add((Sandvich) o);
                else // RAGE!
                    this.log(Level.WARNING, "That's not a Sandvich: " + o);
            }

        }

        if (lunch.isEmpty()) {
            System.out.println("defaults");
            // add some defaults
            Sandvich s = new Sandvich();
            s.getFillings().add(this.getCore().getMVWorldManager().getFirstSpawnWorld().getName());
            this.lunch.add(s);
            System.out.println("lunch is now " + lunch);
        }

        this.quickLookup = new HashMap<String, Sandvich>();
        for (Sandvich s : lunch) {
            for (String worldName : s.getFillings()) {
                this.quickLookup.put(worldName, s);
            }
        }

        // register
        this.getServer().getPluginManager().registerEvents(new ListenerBridge(this), this);
    }

    @Override
    public void onDisable() {
        this.getConfig().set("lunch", lunch);
        this.saveConfig();
    }

    @Override
    protected void registerCommands(CommandHandler handler) {
        // no commands (yet?)
    }

    public void onPlayerMove(PlayerMoveEvent e) {
        int y = e.getTo().getBlockY();
        if ((y <= 0) || (y >= e.getTo().getWorld().getMaxHeight())) {
            Vector v = e.getPlayer().getVelocity();
            String worldName = e.getTo().getWorld().getName();
            Sandvich s = this.quickLookup.get(worldName);
            int index = s.getFillings().indexOf(worldName);
            if (y <= 0) {
                y = e.getTo().getWorld().getMaxHeight();
                index--; // fall down
            } else {
                y = 0;
                index++; // go up
            }
            if ((index < 0) || (index >= s.getFillings().size()))
                return;

            MultiverseWorld world = this.getCore().getMVWorldManager().getMVWorld(s.getFillings().get(index));
            e.getPlayer().teleport(new Location(world.getCBWorld(), e.getTo().getX(), y, e.getTo().getZ()));
            e.getPlayer().setVelocity(v);
        }
    }
}
