package vong.piler.her.generator;

import vong.piler.her.enums.OperationEnum;

public class ValueModel {
	
	private Double zal;
	
	private Boolean isso;
	
	private String var;
	
	private boolean isVar;

	public ValueModel(Double zal) {
		this.zal = zal;
		this.isso = null;
		this.var = null;
		this.isVar = false;
	}

	public ValueModel(Boolean isso) {
		this.isso = isso;
		this.zal = null;
		this.var = null;
		this.isVar = false;
	}

	public ValueModel(String word, boolean isVar) {
		this.var = word;
		this.zal = null;
		this.isso = null;
		this.isVar = isVar;
	}

	public Double getZal() {
		return zal;
	}

	public void setZal(Double zal) {
		this.zal = zal;
		this.isso = null;
		this.var = null;
		this.isVar = false;
	}

	public Boolean getIsso() {
		return isso;
	}

	public void setIsso(Boolean isso) {
		this.isso = isso;
		this.zal = null;
		this.var = null;
		this.isVar = false;
	}

	public String getVarName() {
		return (isVar)?var:null;
	}

	public void setVarName(String varName) {
		this.var = varName;
		this.zal = null;
		this.isso = null;
		this.isVar = true;
	}

	public String getWord() {
		return (isVar)?null:var;
	}

	public void setWord(String word) {
		this.var = word;
		this.zal = null;
		this.isso = null;
		this.isVar = false;
	}
	
	public OperationEnum getOperation() {
		if (zal != null)
			return OperationEnum.PSZ;
		if (isso != null)
			return OperationEnum.PSI;
		if (var != null && !isVar)
			return OperationEnum.PSW;
		if (var != null && isVar) {
			return OperationEnum.PSA;
		}
		return null;
	}
	
	public String getValue() {
		if (zal != null)
			return zal.toString();
		if (isso != null)
			return (isso)?"1":"0";
		if (var != null) {
			return var;
		}
		return null;
	}

}
