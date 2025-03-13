package net.liven.coreapi.gui.custom.action;

import org.bukkit.entity.Player;

public class ServerSwitchAction implements ExecutableAction {


    @Override
    public ExecuteStatus execute(Player player, String... args) {
        if(args.length == 0) {
            return ExecuteStatus.FAILED;
        }

        String serverName = args[0];
        //TODO SWITCH SERVER WITH PACKETS
        return ExecuteStatus.SUCCESS;
    }
}
