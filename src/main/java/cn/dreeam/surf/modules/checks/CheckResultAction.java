package cn.dreeam.surf.modules.checks;

import cn.dreeam.surf.Surf;

public enum CheckResultAction {

    // Remove the entire item
    REMOVE,
    // Remove the illegal component data
    SANITIZE,
    // Do nothing (No idea why it exists)
    NONE;

    public static CheckResultAction fromString(String string) {
        for (CheckResultAction format : values()) {
            if (format.toString().equalsIgnoreCase(string)) {
                return format;
            }
        }

        Surf.LOGGER.warn("Invalid check result action mode: {}, fallback to 'none'", string);

        return NONE;
    }
}
