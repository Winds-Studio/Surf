package org.surf.command;

import java.util.List;

public abstract class BaseTabCommand extends BaseCommand {
    private List<String> tabCompletions;

    public BaseTabCommand(String name, String usage, String permission) {
        super(name, usage, permission);
    }

    public BaseTabCommand(String name, String usage, String permission, String description) {
        super(name, usage, permission, description);
    }
    public BaseTabCommand(String name, String usage, String permission, String description, String[] subCommands) {
        super(name, usage, permission, description, subCommands);
    }

    public abstract List<String> onTab(String[] args);

    public List<String> getTabCompletions() {
        return tabCompletions;
    }

    public void setTabCompletions(List<String> tabCompletions) {
        this.tabCompletions = tabCompletions;
    }
}