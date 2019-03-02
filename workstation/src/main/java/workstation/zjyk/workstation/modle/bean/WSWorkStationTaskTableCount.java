package workstation.zjyk.workstation.modle.bean;
/**
 * 实体类 - 工位任务列表数量
 */
public class WSWorkStationTaskTableCount {

	/**
	 * 正常任务数量
	 */
	private String common;

	/**
	 * 返工任务数量
	 */
	private String rework;

	/**
	 * 维修任务数量
	 */
	private String repair;
    /**
     * 历史任务数量
     */
    private String history;

	public String getCommon() {
		return common;
	}

	public void setCommon(String common) {
		this.common = common;
	}

	public String getRework() {
		return rework;
	}

	public void setRework(String rework) {
		this.rework = rework;
	}

	public String getRepair() {
		return repair;
	}

	public void setRepair(String repair) {
		this.repair = repair;
	}

    public String getHistory() {
        return history == null ? "" : history;
    }

    public void setHistory(String history) {
        this.history = history;
    }
}
