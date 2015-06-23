package drools.cookbook.chapter07.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Lucas Amador
 * 
 */
public class Server {

    private String name;
    private int processors;
    private int memory;
    private int diskSpace;
    private List<Virtualization> virtualizations;
    private int cpuUsage;
    private boolean online;

    public Server(String name, int processors, int memory, int diskSpace, int cpuUsage) {
        this(name, processors, memory, diskSpace, new ArrayList<Virtualization>(), cpuUsage);
    }

    public Server(String name, int processors, int memory, int diskSpace, List<Virtualization> virtualizations,
            int cpuUsage) {
        this.name = name;
        this.processors = processors;
        this.memory = memory;
        this.diskSpace = diskSpace;
        this.virtualizations = virtualizations;
        this.cpuUsage = cpuUsage;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setProcessors(int processors) {
        this.processors = processors;
    }

    public int getProcessors() {
        return processors;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public int getMemory() {
        return memory;
    }

    public void setDiskSpace(int diskSpace) {
        this.diskSpace = diskSpace;
    }

    public int getDiskSpace() {
        return diskSpace;
    }

    public void addVirtualization(Virtualization virtualization) {
        if (virtualizations == null) {
            virtualizations = new ArrayList<Virtualization>();
        }
        virtualizations.add(virtualization);
    }

    public void setVirtualizations(List<Virtualization> virtualizations) {
        this.virtualizations = virtualizations;
    }

    public List<Virtualization> getVirtualizations() {
        return virtualizations;
    }

    public void setCpuUsage(int cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    public int getCpuUsage() {
        return cpuUsage;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public boolean isOnline() {
        return online;
    }

}
