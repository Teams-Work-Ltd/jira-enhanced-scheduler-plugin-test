package com.teamswork.scheduler.test.component;

import com.atlassian.event.api.EventListener;
import com.atlassian.event.api.EventPublisher;
import com.atlassian.plugin.event.events.PluginDisablingEvent;
import com.atlassian.plugin.event.events.PluginEnabledEvent;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import static com.teamswork.scheduler.test.component.Plugin.PLUGIN_KEY;

/**
 * Provides a start-up hook for other classes to latch onto
 * to do things like schedule jobs or one time tasks that should happen at startup.
 */
public abstract class PluginStateListener implements InitializingBean, DisposableBean {
    private final EventPublisher eventPublisher;

    public PluginStateListener(final EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    public void pluginStartup(final PluginEnabledEvent event) {
        if (PLUGIN_KEY.equals(event.getPlugin().getKey())) {
            onAppStart();
        }
    }

    @EventListener
    public void pluginShutdown(final PluginDisablingEvent event) {
        if (PLUGIN_KEY.equals(event.getPlugin().getKey())) {
            onAppShutdown();
        }
    }

    /**
     * Called when the plugin has been enabled.
     */
    @Override
    public void afterPropertiesSet() {
        eventPublisher.register(this);
    }

    /**
     * Called when the plugin is being disabled or removed.
     */
    @Override
    public void destroy() {
        eventPublisher.unregister(this);
    }

    protected abstract void onAppStart();

    protected abstract void onAppShutdown();
}
