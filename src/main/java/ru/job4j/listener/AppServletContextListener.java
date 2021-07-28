package ru.job4j.listener;

import ru.job4j.PropertySource;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AppServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent contextEvent) {
        PropertySource.load("app.properties");
    }
}
