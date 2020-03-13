package com.leonzhangxf.sms.enumeration;

/**
 * 删除状态枚举
 *
 * @author leonzhangxf 20180905
 */
public enum DeleteFlag {

    /**
     * 被删除的
     */
    DELETED(1),

    /**
     * 未被删除的
     */
    NO_DELETED(0);

    private int deleteFlag;

    DeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public int getDeleteFlag() {
        return deleteFlag;
    }
}
