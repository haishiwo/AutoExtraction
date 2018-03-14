package bean;

import java.util.List;

public class Rule {
	private String name;// 规则名
	private String time;// 规则创建时间
	private String label;// 抽取信息的通称
	private String length;// 抽取信息名称的长度
	private String regex;// 开始行规则
	private List<String> endRegexList;// 结束行规则
	private List<String> modelsList;// 模板
	private String regexTFS;// 开始行规则TFS
	private String endRegexTFS;// 结束行规则TFS

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public List<String> getEndRegexList() {
		return endRegexList;
	}

	public void setEndRegexList(List<String> endRegexList) {
		this.endRegexList = endRegexList;
	}

	public List<String> getModelsList() {
		return modelsList;
	}

	public void setModelsList(List<String> modelsList) {
		this.modelsList = modelsList;
	}

	public String getRegexTFS() {
		return regexTFS;
	}

	public void setRegexTFS(String regexTFS) {
		this.regexTFS = regexTFS;
	}

	public String getEndRegexTFS() {
		return endRegexTFS;
	}

	public void setEndRegexTFS(String endRegexTFS) {
		this.endRegexTFS = endRegexTFS;
	}

	// 通过提供内部九个变量的值来新建一个规则
	public Rule(String name, String time, String label, String length, String regex, List<String> endRegexList,
			List<String> modelsList, String regexTFS, String endRegexTFS) {
		super();
		this.name = name;
		this.time = time;
		this.label = label;
		this.length = length;
		this.regex = regex;
		this.endRegexList = endRegexList;
		this.modelsList = modelsList;
		this.regexTFS = regexTFS;
		this.endRegexTFS = endRegexTFS;
	}

	public Rule() {
	}

}
