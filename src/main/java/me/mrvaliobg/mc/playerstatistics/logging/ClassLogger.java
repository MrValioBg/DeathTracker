package me.mrvaliobg.mc.playerstatistics.logging;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class ClassLogger extends Logger {

    private final String pluginName;

    public ClassLogger(Class<?> aClass) {
        super(aClass.getCanonicalName(), null);
        final JavaPlugin providingPlugin = JavaPlugin.getProvidingPlugin(aClass);
        String prefix = providingPlugin.getName();
        prefix += " - " + aClass.getSimpleName();
        pluginName = "[" + prefix + "] ";
        setParent(providingPlugin.getServer().getLogger());
        setLevel(Level.ALL);
    }

    @Override
    public void log(LogRecord logRecord) {
        logRecord.setMessage(pluginName + logRecord.getMessage());
        super.log(logRecord);
    }

}
