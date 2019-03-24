/**
 * 验证手机号码
 */
function verifyPhone(phone) {
    var reg = /^1[3|4|5|7|8]\d{9}$/;
    return reg.test(phone);
}

/**
 * 验证金额
 */
function verifyMoney(money) {
    var reg = /^\d{1,8}(\.\d{1,2})?$/;
    return reg.test(money);
}

/**
 * 验证数量
 */
function verifyNumber(number) {
    var reg = /^[1-9]\d*$/;
    return reg.test(number);
}

/**
 * 验证中文
 */
function verifyChinese(input) {
    var reg = /^[\u4e00-\u9fa5]{1,25}$/;
    return reg.test(input);
}

/**
 * 验证邮箱
 */
function verifyMail(email) {
    var reg = /^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,})$/;
    return reg.test(email);
}

/**
 * 验证申请日期
 */
function verityApplyTime(value){
    var reg = new RegExp("-","g");
    var applyDate = value.replace(reg,"");
    var today = (((new Date()).toISOString()).substr(0,10)).replace(reg,"");
    if(applyDate > today){
        return true;
    }else {
        return false;
    }
}


/**
 * 验证申请日期
 */