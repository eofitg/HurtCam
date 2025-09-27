package net.eofitg.hurtcam.command;

import net.eofitg.hurtcam.HurtCam;
import net.eofitg.hurtcam.util.PlayerUtil;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;

import java.util.*;

public class HurtCamCommand extends CommandBase {

    private static final List<String> SUBCOMMANDS = Arrays.asList("toggle", "set");

    @Override
    public String getCommandName() {
        return "hurtcam";
    }

    @Override
    public List<String> getCommandAliases() {
        return Collections.singletonList("hc");
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/hurtcam toggle|set <value>";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (!(sender instanceof EntityPlayer)) return;
        if (args.length == 0) {
            PlayerUtil.addMessage(EnumChatFormatting.YELLOW + "Usage: " + getCommandUsage(sender));
            return;
        }

        String sub = args[0].toLowerCase();

        switch (sub) {
            case "toggle": {
                HurtCam.config.setEnabled(!HurtCam.config.isEnabled());
                boolean isEnabled = HurtCam.config.isEnabled();
                String status = isEnabled ? EnumChatFormatting.GREEN + "enabled" : EnumChatFormatting.RED + "disabled";
                PlayerUtil.addMessage(EnumChatFormatting.GOLD + "Mod " + status + EnumChatFormatting.GOLD + ".");
                HurtCam.saveConfig();
                break;
            }
            case "set": {
                if (args.length >= 2) {
                    try {
                        float n = Float.parseFloat(args[1]);
                        HurtCam.config.setMultiplier(n);
                        PlayerUtil.addMessage(EnumChatFormatting.GOLD + "Hurtcam multiplier set to: " + EnumChatFormatting.BOLD + EnumChatFormatting.WHITE + HurtCam.config.getMultiplier());
                        HurtCam.saveConfig();
                    } catch (NumberFormatException e) {
                        PlayerUtil.addMessage(EnumChatFormatting.RED + "Invalid number.");
                    }
                } else {
                    PlayerUtil.addMessage(EnumChatFormatting.GOLD + "Current hurtcam multiplier: " + EnumChatFormatting.BOLD + EnumChatFormatting.WHITE + HurtCam.config.getMultiplier());
                }
                break;
            }
            default: {
                PlayerUtil.addMessage(EnumChatFormatting.RED + "Unknown argument: " + sub);
                PlayerUtil.addMessage(EnumChatFormatting.YELLOW + "Usage: " + getCommandUsage(sender));
            }
        }
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            String prefix = args[0].toLowerCase();
            for (String cmd : SUBCOMMANDS) {
                if (cmd.startsWith(prefix)) {
                    completions.add(cmd);
                }
            }
        } else if (args.length == 2) {
            String sub = args[0].toLowerCase();
            if (sub.equals("set")) {
                completions.add(14 + "");
            }
        }

        return completions.isEmpty() ? null : completions;
    }

}
