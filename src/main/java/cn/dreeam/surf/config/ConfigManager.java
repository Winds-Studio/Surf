package cn.dreeam.surf.config;

import cn.dreeam.surf.Surf;
import io.github.thatsmusic99.configurationmaster.api.ConfigFile;
import io.github.thatsmusic99.configurationmaster.api.ConfigSection;

import java.io.File;
import java.util.List;
import java.util.Map;

/*
 *  Yoinked from: https://github.com/xGinko/AnarchyExploitFixes/
 *  @author: xGinko
 */
public class ConfigManager {

    private static ConfigFile config;

    public ConfigManager() throws Exception {
        // Load config.yml with ConfigMaster
        config = ConfigFile.loadConfig(new File(Surf.getInstance().getDataFolder(), "config.yml"));
        config.set("config-version", 1.0);
        config.addComments("config-version", """
                Surf 5.0.0
                Contact me on QQ:2682173972 or Discord: dreeam___
                For help with this plugin""");

        // Pre-structure to force order
        //structureConfig();
    }

    public void saveConfig() throws Exception {
        Config.initConfig();
        config.save();
    }

    private void structureConfig() {
        createTitledSection("Language", "language");
        createTitledSection("General", "general");
        createTitledSection("Miscellaneous", "misc");
    }

    public void createTitledSection(String title, String path) {
        config.addSection(title);
        config.addDefault(path, null);
    }

    public boolean getBoolean(String path, boolean def, String comment) {
        config.addDefault(path, def, comment);
        return config.getBoolean(path, def);
    }

    public boolean getBoolean(String path, boolean def) {
        config.addDefault(path, def);
        return config.getBoolean(path, def);
    }

    public String getString(String path, String def, String comment) {
        config.addDefault(path, def, comment);
        return config.getString(path, def);
    }

    public String getString(String path, String def) {
        config.addDefault(path, def);
        return config.getString(path, def);
    }

    public double getDouble(String path, double def, String comment) {
        config.addDefault(path, def, comment);
        return config.getDouble(path, def);
    }

    public double getDouble(String path, double def) {
        config.addDefault(path, def);
        return config.getDouble(path, def);
    }

    public int getInt(String path, int def, String comment) {
        config.addDefault(path, def, comment);
        return config.getInteger(path, def);
    }

    public int getInt(String path, int def) {
        config.addDefault(path, def);
        return config.getInteger(path, def);
    }

    public List<String> getList(String path, List<String> def, String comment) {
        config.addDefault(path, def, comment);
        return config.getStringList(path);
    }

    public List<String> getList(String path, List<String> def) {
        config.addDefault(path, def);
        return config.getStringList(path);
    }

    public ConfigSection getConfigSection(String path, Map<String, Object> defaultKeyValue) {
        config.addDefault(path, null);
        config.makeSectionLenient(path);
        defaultKeyValue.forEach((string, object) -> config.addExample(path + "." + string, object));
        return config.getConfigSection(path);
    }

    public ConfigSection getConfigSection(String path, Map<String, Object> defaultKeyValue, String comment) {
        config.addDefault(path, null, comment);
        config.makeSectionLenient(path);
        defaultKeyValue.forEach((string, object) -> config.addExample(path + "." + string, object));
        return config.getConfigSection(path);
    }

    public void addComment(String path, String comment) {
        config.addComment(path, comment);
    }
}
