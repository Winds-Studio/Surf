package cn.dreeam.surf.modules.checks;

// TODO: Needs to use better naming, CheckAction, or...
public enum CheckResult {

    // Remove the entire item
    REMOVE,
    // Remove the illegal component data
    SANITIZE,
    // Only log (TODO: Or make the logging just be an option in the config?)
    LOG_ONLY,
    NOTHING;
}
