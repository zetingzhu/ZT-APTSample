package com.jjshome.mobile.autotrackings.entiy;

import java.util.List;
import java.util.Objects;

/**
 * @author: zeting
 * @date: 2023/11/13
 * 分析信息参数
 */
public class AnalyticsMethodObj {
    /**
     * 方法名字
     */
    private String name;
    /**
     * 方法参数和返回值描述
     */
    private String desc;

    /**
     * 方法所在的接口或类
     */
    private String parent;

    /**
     * 调用采集数据方法
     */
    private String agentName;

    /**
     * 采集数据的方法描述
     */
    private String agentDesc;

    /**
     * 采集数据的方法参数起始索引（ 0：this，1+：普通参数 ）
     */
    private Integer paramsStart;

    /**
     * 采集数据的方法参数个数
     */
    private Integer paramsCount = 0;

    /**
     * 参数类型对应的ASM指令，加载不同类型的参数需要不同的指令
     */
    private List<Integer> opcodes;

    public AnalyticsMethodObj(String name, String desc, String parent, String agentName, String agentDesc, Integer paramsStart, Integer paramsCount, List<Integer> opcodes) {
        this.name = name;
        this.desc = desc;
        this.agentName = agentName;
        this.parent = parent;
        this.agentDesc = agentDesc;
        this.paramsStart = paramsStart;
        this.paramsCount = paramsCount;
        this.opcodes = opcodes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getAgentDesc() {
        return agentDesc;
    }

    public void setAgentDesc(String agentDesc) {
        this.agentDesc = agentDesc;
    }

    public Integer getParamsStart() {
        return paramsStart;
    }

    public void setParamsStart(Integer paramsStart) {
        this.paramsStart = paramsStart;
    }

    public Integer getParamsCount() {
        return paramsCount;
    }

    public void setParamsCount(Integer paramsCount) {
        this.paramsCount = paramsCount;
    }

    public List<Integer> getOpcodes() {
        return opcodes;
    }

    public void setOpcodes(List<Integer> opcodes) {
        this.opcodes = opcodes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AnalyticsMethodObj)) return false;
        AnalyticsMethodObj that = (AnalyticsMethodObj) o;
        return Objects.equals(getName(), that.getName()) && Objects.equals(getDesc(), that.getDesc()) && Objects.equals(getAgentName(), that.getAgentName()) && Objects.equals(getParent(), that.getParent()) && Objects.equals(getAgentDesc(), that.getAgentDesc()) && Objects.equals(getParamsStart(), that.getParamsStart()) && Objects.equals(getParamsCount(), that.getParamsCount()) && Objects.equals(getOpcodes(), that.getOpcodes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDesc(), getAgentName(), getParent(), getAgentDesc(), getParamsStart(), getParamsCount(), getOpcodes());
    }

    @Override
    public String toString() {
        return "SensorsAnalyticsMethodCell{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", agentName='" + agentName + '\'' +
                ", parent='" + parent + '\'' +
                ", agentDesc='" + agentDesc + '\'' +
                ", paramsStart=" + paramsStart +
                ", paramsCount=" + paramsCount +
                ", opcodes=" + opcodes +
                '}';
    }
}
