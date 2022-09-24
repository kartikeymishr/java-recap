package com.kartikey.oops.composition;

// Has - A relationship
// The PC has Case, Monitor and Motherboard
public class PC {
    private Case _case;
    private Monitor monitor;
    private Motherboard motherboard;

    public PC(Case _case, Monitor monitor, Motherboard motherboard) {
        this._case = _case;
        this.monitor = monitor;
        this.motherboard = motherboard;
    }

    public void powerUp() {
        getCase().pressPowerButton();
        drawLogo();
    }

    private void drawLogo() {
        // Fancy Motherboard graphics
        monitor.drawPixelAt(1200, 150, "red");
    }

    private Case getCase() {
        return _case;
    }

    public void setCase(Case _case) {
        this._case = _case;
    }

    private Monitor getMonitor() {
        return monitor;
    }

    public void setMonitor(Monitor monitor) {
        this.monitor = monitor;
    }

    private Motherboard getMotherboard() {
        return motherboard;
    }

    public void setMotherboard(Motherboard motherboard) {
        this.motherboard = motherboard;
    }
}
