package ma.dev7hd.facebooksearchapp.web;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.net.URI;

@Component
public class BrowserLauncher{
    @EventListener(ApplicationReadyEvent.class)

    public void launchBrowser(){
        System.setProperty("java.awt.headless", "false");
        Desktop desktop = Desktop.getDesktop();
        try{
            desktop.browse(new URI("http://localhost:4022"));
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}