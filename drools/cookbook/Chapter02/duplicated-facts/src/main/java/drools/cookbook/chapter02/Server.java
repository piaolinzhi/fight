package drools.cookbook.chapter02;

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

    public Server(String name, int processors, int memory, int diskSpace, List<Virtualization> virtualizations, int cpuUsage) {
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

    @Override
    public String toString() {
        return "name=" + name + " processors=" + processors + " memory=" + memory + " diskSpace=" + diskSpace + " virtualizations="
                + virtualizations.size();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Server)) {
            return false;
        }
        Server server = (Server) obj;
        return server.name.equals(name) && server.processors == processors && server.memory == memory && server.diskSpace == diskSpace
                && server.virtualizations.size() == virtualizations.size();
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + name.hashCode();
        result = 31 * result + processors;
        result = 31 * result + memory;
        result = 31 * result + diskSpace;
        return result;
    }

}
