package com.cherish.health.constant;

/**
 * @author Cherish
 * @version 1.8.0_121
 * @date 2020/11/21
 * <p>
 * 消息信息常量类
 * <p/>
 */
public interface MessageConstant {
    static final String DELETE_CHECKITEM_FAIL = "【有关联的检查组！！！】删除检查项失败";
    static final String DELETE_CHECKITEM_SUCCESS = "删除检查项成功";
    static final String ADD_CHECKITEM_SUCCESS = "新增检查项成功";
    static final String ADD_CHECKITEM_FAIL = "新增检查项失败";
    static final String EDIT_CHECKITEM_FAIL = "编辑检查项失败";
    static final String EDIT_CHECKITEM_SUCCESS = "编辑检查项成功";
    static final String QUERY_CHECKITEM_SUCCESS = "查询检查项成功";
    static final String QUERY_CHECKITEM_FAIL = "查询检查项失败";
    static final String UPLOAD_SUCCESS = "上传成功";
    static final String ADD_CHECKGROUP_FAIL = "新增检查组失败";
    static final String ADD_CHECKGROUP_SUCCESS = "新增检查组成功";
    static final String DELETE_CHECKGROUP_FAIL = "【有关联的套餐！！！】删除失败！";
    static final String DELETE_CHECKGROUP_SUCCESS = "删除检查组成功";
    static final String QUERY_CHECKGROUP_SUCCESS = "查询检查组成功";
    static final String QUERY_CHECKGROUP_FAIL = "查询检查组失败";
    static final String EDIT_CHECKGROUP_FAIL = "编辑检查组失败";
    static final String EDIT_CHECKGROUP_SUCCESS = "编辑检查组成功";
    static final String PIC_UPLOAD_SUCCESS = "图片上传成功";
    static final String PIC_UPLOAD_FAIL = "图片上传失败";
    static final String ADD_SETMEAL_FAIL = "新增套餐失败";
    static final String ADD_SETMEAL_SUCCESS = "新增套餐成功";
    static final String EDIT_SETMEAL_SUCCESS = "编辑套餐成功";
    static final String EDIT_SETMEAL_FAIL = "编辑套餐失败";
    static final String IMPORT_ORDERSETTING_FAIL = "批量导入预约设置数据失败";
    static final String IMPORT_ORDERSETTING_SUCCESS = "批量导入预约设置数据成功";
    static final String GET_ORDERSETTING_SUCCESS = "获取预约设置数据成功";
    static final String GET_ORDERSETTING_FAIL = "获取预约设置数据失败";
    static final String ORDERSETTING_SUCCESS = "预约设置成功";
    static final String ORDERSETTING_FAIL = "预约设置失败";
    static final String ADD_MEMBER_FAIL = "新增会员失败";
    static final String ADD_MEMBER_SUCCESS = "新增会员成功";
    static final String DELETE_MEMBER_FAIL = "删除会员失败";
    static final String DELETE_MEMBER_SUCCESS = "删除会员成功";
    static final String EDIT_MEMBER_FAIL = "编辑会员失败";
    static final String EDIT_MEMBER_SUCCESS = "编辑会员成功";
    static final String TELEPHONE_VALIDATECODE_NOTNULL = "手机号和验证码都不能为空";
    static final String LOGIN_SUCCESS = "登录成功";
    static final String VALIDATECODE_ERROR = "验证码输入错误";
    static final String QUERY_ORDER_SUCCESS = "查询预约信息成功";
    static final String QUERY_ORDER_FAIL = "查询预约信息失败";
    static final String QUERY_SETMEALLIST_SUCCESS = "查询套餐列表数据成功";
    static final String QUERY_SETMEALLIST_FAIL = "查询套餐列表数据失败";
    static final String QUERY_SETMEAL_SUCCESS = "查询套餐数据成功";
    static final String DELETE_SETMEAL_SUCCESS = "删除套餐数据成功";
    static final String DELETE_SETMEAL_FAIL = "【套餐已有订单】套餐删除失败";
    static final String QUERY_SETMEAL_FAIL = "查询套餐数据失败";
    static final String SEND_VALIDATECODE_FAIL = "验证码发送失败";
    static final String SEND_VALIDATECODE_SUCCESS = "验证码发送成功";
    static final String SELECTED_DATE_CANNOT_ORDER = "所选日期不能进行体检预约";
    static final String ORDER_FULL = "预约已满";
    static final String HAS_ORDERED = "已经完成预约，不能重复预约";
    static final String ORDER_SUCCESS = "预约成功";
    static final String ORDER_FAIL = "预约失败";
    static final String GET_USERNAME_SUCCESS = "获取当前登录用户名称成功";
    static final String GET_USERNAME_FAIL = "获取当前登录用户名称失败";
    static final String GET_MENU_SUCCESS = "获取当前登录用户菜单成功";
    static final String GET_MENU_FAIL = "获取当前登录用户菜单失败";
    static final String GET_MEMBER_NUMBER_REPORT_SUCCESS = "获取会员统计数据成功";
    static final String GET_MEMBER_NUMBER_REPORT_FAIL = "获取会员统计数据失败";
    static final String GET_SETMEAL_COUNT_REPORT_SUCCESS = "获取套餐统计数据成功";
    static final String GET_SETMEAL_COUNT_REPORT_FAIL = "获取套餐统计数据失败";
    static final String GET_BUSINESS_REPORT_SUCCESS = "获取运营统计数据成功";
    static final String GET_BUSINESS_REPORT_FAIL = "获取运营统计数据失败";
    static final String GET_SETMEAL_LIST_SUCCESS = "查询套餐列表数据成功";
    static final String GET_SETMEAL_LIST_FAIL = "查询套餐列表数据失败";
    String SENT_VALIDATECODE = "验证码已经发送了，请注意查收";
}
