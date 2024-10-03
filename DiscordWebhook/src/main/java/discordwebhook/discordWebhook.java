package discordwebhook;

import java.io.IOException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.Listener;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class discordWebhook extends JavaPlugin implements Listener{
	
    	private sender webhookSender;
    	private String webhookUrl;
    	private int players = 0;
    
	    @Override
	    public void onEnable() {
	    	getServer().getPluginManager().registerEvents(this, this);
	    	FileConfiguration config = getConfig();
	        webhookUrl = config.getString("webhook-url");
	        
	        getCommand("setwebhook").setExecutor((sender, command, label, args) -> {
	            if (args.length != 1) {
	                sender.sendMessage("Usage: /setwebhook <url>");
	                return false;
	            }
	            webhookUrl = args[0];
	            config.set("webhook-url", webhookUrl);
	            saveConfig();
	            webhookSender.setWebhookUrl(webhookUrl);
	            sender.sendMessage("Webhook URL updated.");
	            return true;
	        });
	        webhookSender = new sender(webhookUrl);
	    }

	    @Override
	    public void onDisable() {

	    }
	    
	    @EventHandler
	    public void onPlayerJoin(PlayerJoinEvent event) {

	        players++;
	        sendWebhookMessage(event.getPlayer().getName() + " is online! (" + players + " are online)");
	        System.out.println(players);

	    }
	    
	    @EventHandler
	    public void onPlayerQuit(PlayerQuitEvent event) {

	        players--;
	        sendWebhookMessage(event.getPlayer().getName() + " left (" + players + " are online)");
	        System.out.println(players);

	    }
	    
	    public void sendWebhookMessage(String message) {
	        try {
	            webhookSender.sendWebhook(message);
	        } catch (IOException e) {
	            getLogger().warning("Failed to send webhook: " + e.getMessage());
	        }
	    }
}
