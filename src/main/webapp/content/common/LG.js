/*!
* @copyright: 版权所有 All Rights Reserved
* @author: Irving
* @version: 2013年12月19日 17:10:55
* @email: zhouyongtao@outlook.com
* @blog: http://cnblogs.com/irving
* @description: LG Plugin
* @require: jQuery v1.3.2 or later
* @jsmin: http://javascript.crockford.com/jsmin.html
*/
; (function ($) {
    $("#pwd").keyup(function (e) {
        if (e.keyCode === 13) {
            $("#btn_part_login").trigger("click");
        }
    });

    window['LG'] = window.LG || {};
    LG.log = function (data) {
        if (window.console && console.log) {
            var time = "NowTime: " + new Date().format("yyyy-MM-dd hh:mm:ss");
            if (typeof (data) === "object") {
                console.log("[ " + time + " ] " + JSON.stringify(data))
            } else {
                console.log("[ " + time + " ] " + data)
            }
        }
    };
    //会员登陆
    LG.login = function () {
        var accountNo = $("#accountNo").val();
        var pwd = $("#pwd").val();
        if ($("#head_check_input").hasClass("tc_fogetlabel")) {
            $("#autologin").val("yes");
        } else {
            $("#autologin").val("no");
        }
        LG.log(accountNo);
        if (accountNo === "") {
            $("#errorMsg").html("账号不能为空");
            $("#accountNo").focus();
            return;
        }
        if (pwd === "") {
            $("#errorMsg").html("密码不能为空");
            $("#pwd").focus();
            return;
        }
        if (!$(".head_myrj_box").is(":animated")) {
            $(".head_myrj_tc").animate({ width: '0px' }, "slow", function () {
                $(".head_myrj_box").animate({ height: '41px' }, "slow", function () {
                    $(".head_myrj_tc_login").hide("2000");
                });
            });
            $(".head_myrj_add").removeClass("head_myrj_top_iconon");
        }
        $("#loginForm").submit();
        return false;
    };

    /*
    LG.getMemberDetail = function () {
    $.getJSON("/Ajax/GetMemberDetail/" + Math.random(), null, function (result) {
    if (result.status == "true") {
    LG.log(result);   
    });
    }
    })
    };
    */

    //获取URL中的参数键值对数据(参数:location.href)
    LG.getParams = function (href) {
        var params = [];
        var param = {};
        var args = unescape(href).split("?")[1].split("&");
        for (var i = 0; i < args.length; i++) {
            var arg = args[i].split("=");
            param.Value = arg[1];
            param.Name = arg[0];
            params.push(param);
        }
        return params;
    };

    LG.NewGUID = function () {
        var guid = "";
        for (var i = 1; i <= 32; i++) {
            var n = Math.floor(Math.random() * 16.0).toString(16);
            guid += n;
            if ((i === 8) || (i === 12) || (i === 16) || (i === 20))
                guid += "-";
        }
        return guid;
    }

    LG.Eval = function (jsonText) {
        //JSON2.parse(jsonText);
        return window.eval("(" + jsonText + ")");
    };

    LG.book = function (hotelCd, rmTpCd) {
        var myForm = $("<form></form>");
        if (activityCode == "point") {
            $(myForm).attr("action", "/Resv/CreatePoint.aspx");
        } else {
            $(myForm).attr("action", "/Resv/Create.aspx");
        }
        $(myForm).attr("method", "post");
        $(myForm).append("<input type='hidden' name='hotelid' value='" + hotelid + "' />");
        $(myForm).append("<input type='hidden' name='RoomType' value='" + roomType + "' />");
        $(myForm).append("<input type='hidden' name='CheckIn' value='" + startDate + "' />");
        $(myForm).append("<input type='hidden' name='CheckOut' value='" + endDate + "' />");
        $(myForm).append("<input type='hidden' name='ActivityCode' value='" + activityCode + "' />");
        $(myForm).append("<input type='hidden' name='r' value='" + Math.random() + "' />");
        $(myForm).append("<input type='hidden' name='cityID' value='" + cityID + "'");
        $(myForm).append("<input type='submit' id='btnSubmit' />");
        $("body").append(myForm);
        myForm.submit();
    };

    LG.cookie = (function () {
        var fn = function () { };
        fn.prototype.get = function (name) {
            var cookieValue = "";
            var search = name + "=";
            var offset = document.cookie.indexOf(search);
            var end = document.cookie.indexOf(";", offset);
            if (document.cookie.length > 0) {
                if (offset !== -1) {
                    offset += search.length;
                    if (end === -1) end = document.cookie.length;
                    cookieValue = decodeURIComponent(document.cookie.substring(offset, end))
                }
            }
            return cookieValue;
        };
        fn.prototype.set = function (cookieName, cookieValue, DayValue) {
            var expire = "";
            var day_value = 1;
            if (DayValue !== null) {
                day_value = DayValue;
            }
            expire = new Date((new Date()).getTime() + day_value * 86400000);
            expire = "; expires=" + expire.toGMTString();
            document.cookie = cookieName + "=" + encodeURIComponent(cookieValue) + ";path=/" + expire;
        }
        fn.prototype.remvoe = function (cookieName) {
            var expire = "";
            expire = new Date((new Date()).getTime() - 1);
            expire = "; expires=" + expire.toGMTString();
            document.cookie = cookieName + "=" + escape("") + ";path=/" + expire;
        };
        return new fn();
    })();

    //提交服务器请求
    //返回json格式
    //1,提交给类 options.type  方法 options.method 处理
    //2,并返回 AjaxResult(这也是一个类)类型的的序列化好的字符串
    LG.ajax = function (options) {
        var p = options || {};
        var ashxUrl = options.ashxUrl || "../handler/ajax.ashx?";
        var url = p.url || ashxUrl + $.param({ type: p.type, method: p.method });
        $.ajax({
            cache: false,
            async: true,
            url: url,
            data: p.data,
            dataType: 'json', type: 'post',
            beforeSend: function () {
                $("#loading").show();
            },
            complete: function () {
                $("#loading").hide();
            },
            success: function (result) {
                if (!result.IsError) {
                    if (p.success)
                        p.success(result.Data, result.Message);
                }
                else {
                    if (p.error)
                        p.error(result.Message);
                }
            },
            error: function (result) {
                alert("发现系统错误");
                // LG.tip('发现系统错误 <BR>错误码：' + result.status);
            }
        });
    };

})(jQuery);

//JSON Plug
var JSON;
if (!JSON) JSON = {}; (function () {
    "use strict";
    function f(a) {
        return a < 10 ? "0" + a : a
    }
    if (typeof Date.prototype.toJSON !== "function") {
        Date.prototype.toJSON = function () {
            return isFinite(this.valueOf()) ? this.getUTCFullYear() + "-" + f(this.getUTCMonth() + 1) + "-" + f(this.getUTCDate()) + "T" + f(this.getUTCHours()) + ":" + f(this.getUTCMinutes()) + ":" + f(this.getUTCSeconds()) + "Z" : null
        };
        String.prototype.toJSON = Number.prototype.toJSON = Boolean.prototype.toJSON = function () {
            return this.valueOf();
        };
    }
    var cx = /[\u0000\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,
    escapable = /[\\\"\x00-\x1f\x7f-\x9f\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,
    gap, indent, meta = {
        "\b": "\\b",
        "\t": "\\t",
        "\n": "\\n",
        "\f": "\\f",
        "\r": "\\r",
        '"': '\\"',
        "\\": "\\\\"
    },
    rep;
    function quote(a) {
        escapable.lastIndex = 0;
        return escapable.test(a) ? '"' + a.replace(escapable,
        function (a) {
            var b = meta[a];
            return typeof b === "string" ? b : "\\u" + ("0000" + a.charCodeAt(0).toString(16)).slice(-4)
        }) + '"' : '"' + a + '"'
    }
    function str(h, i) {
        var c, e, d, f, g = gap,
        b, a = i[h];
        if (a && typeof a === "object" && typeof a.toJSON === "function") a = a.toJSON(h);
        if (typeof rep === "function") a = rep.call(i, h, a);
        switch (typeof a) {
            case "string":
                return quote(a);
            case "number":
                return isFinite(a) ? String(a) : "null";
            case "boolean":
            case "null":
                return String(a);
            case "object":
                if (!a) return "null";
                gap += indent;
                b = [];
                if (Object.prototype.toString.apply(a) === "[object Array]") {
                    f = a.length;
                    for (c = 0; c < f; c += 1) b[c] = str(c, a) || "null";
                    d = b.length === 0 ? "[]" : gap ? "[\n" + gap + b.join(",\n" + gap) + "\n" + g + "]" : "[" + b.join(",") + "]";
                    gap = g;
                    return d
                }
                if (rep && typeof rep === "object") {
                    f = rep.length;
                    for (c = 0; c < f; c += 1) if (typeof rep[c] === "string") {
                        e = rep[c];
                        d = str(e, a);
                        d && b.push(quote(e) + (gap ? ": " : ":") + d)
                    }
                } else for (e in a) if (Object.prototype.hasOwnProperty.call(a, e)) {
                    d = str(e, a);
                    d && b.push(quote(e) + (gap ? ": " : ":") + d)
                }
                d = b.length === 0 ? "{}" : gap ? "{\n" + gap + b.join(",\n" + gap) + "\n" + g + "}" : "{" + b.join(",") + "}";
                gap = g;
                return d
        }
    }
    if (typeof JSON.stringify !== "function") JSON.stringify = function (d, a, b) {
        var c;
        gap = "";
        indent = "";
        if (typeof b === "number") for (c = 0; c < b; c += 1) indent += " ";
        else if (typeof b === "string") indent = b;
        rep = a;
        if (a && typeof a !== "function" && (typeof a !== "object" || typeof a.length !== "number")) throw new Error("JSON.stringify");
        return str("", {
            "": d
        })
    };
    if (typeof JSON.parse !== "function") JSON.parse = function (text, reviver) {
        var j;
        function walk(d, e) {
            var b, c, a = d[e];
            if (a && typeof a === "object") for (b in a) if (Object.prototype.hasOwnProperty.call(a, b)) {
                c = walk(a, b);
                if (c !== undefined) a[b] = c;
                else delete a[b]
            }
            return reviver.call(d, e, a)
        }
        text = String(text);
        cx.lastIndex = 0;
        if (cx.test(text)) text = text.replace(cx,
        function (a) {
            return "\\u" + ("0000" + a.charCodeAt(0).toString(16)).slice(-4)
        });
        if (/^[\],:{}\s]*$/.test(text.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g, "@").replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g, "]").replace(/(?:^|:|,)(?:\s*\[)+/g, ""))) {
            j = eval("(" + text + ")");
            return typeof reviver === "function" ? walk({
                "": j
            },
            "") : j
        }
        throw new SyntaxError("JSON.parse");
    };
    /*add by Irving 2012年9月14日11:43:42*/
    if (typeof JSON.formToJSON !== "function") JSON.formToJSON = function (a) {
        var data = $('#' + a).serializeArray();
        var result = {};
        var multiChooseArray = result[arrName]; for (var formValue, j = 0; j < data.length; j++) {
            formValue = data[j];
            var name = formValue.name;
            var value = formValue.value;
            if (name.indexOf('.') < 0) {
                result[name] = value;
                continue;
            } else {
                var simpleNames = name.split('.');
                // 构建命名空间
                var obj = result;
                for (var i = 0; i < simpleNames.length - 1; i++) {
                    var simpleName = simpleNames[i];
                    if (simpleName.indexOf('[') < 0) {
                        if (obj[simpleName] === null) {
                            obj[simpleName] = {};
                        }
                        obj = obj[simpleName];
                    } else { // 数组
                        // 分隔
                        var arrNames = simpleName.split('[');
                        var arrName = arrNames[0];
                        var arrIndex = parseInt(arrNames[1]);
                        if (obj[arrName] === null) {
                            obj[arrName] = []; // new Array();
                        }
                        obj = obj[arrName];
                        if (obj[arrIndex] === null) {
                            obj[arrIndex] = {}; // new Object();
                        }
                        obj = obj[arrIndex];
                    }
                }
                if (obj[simpleNames[simpleNames.length - 1]]) {
                    var temp = obj[simpleNames[simpleNames.length - 1]];
                    obj[simpleNames[simpleNames.length - 1]] = temp;
                } else {
                    obj[simpleNames[simpleNames.length - 1]] = value;
                }
            }
        }
        //console.log(result);
        return result;
    }
})();

Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S": this.getMilliseconds()
    }
    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "")
                    .substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
                        : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
}


//-------------官网跳WAP网站--------------
function getArgs(argsid) {
    var pars = location.search; //获取当前url  
    var pos = pars.indexOf('?'); //查找第一个?  
    if (pos == -1) {
        //alert('参数为空!');
        return;
    }
    else
        pars = pars.substring(pos + 1); //获取参数部分  
    var ps = pars.split("&");
    var args = new Object();
    var temp;
    var name, value, index;
    for (var i = 0; i < ps.length; i++) {
        temp = ps[i];
        index = temp.indexOf("=");
        if (index == -1) continue; //如果参数中未包含=则继续  
        name = temp.substring(0, index); //参数名称  
        value = temp.substring(index + 1); //参数的值  
        args[name] = value;
    }
    // return args;
    for (var j in args) {
        if (argsid == j) {
            return args[j];
        }
    }
    return;
}  
/*
var thisURL = document.URL;
if (thisURL.indexOf("wap") == -1) {
    if (/AppleWebKit.*Mobile/i.test(navigator.userAgent) || (/MIDP|SymbianOS|NOKIA|SAMSUNG|LG|NEC|TCL|Alcatel|BIRD|DBTEL|Dopod|PHILIPS|HAIER|LENOVO|MOT-|Nokia|SonyEricsson|SIE-|Amoi|ZTE/.test(navigator.userAgent))) {
        if (window.location.href.indexOf("?mobile") < 0) {
            try {
                if (/Android|webOS|iPhone|iPod|BlackBerry/i.test(navigator.userAgent)) {
                    window.location.href = "http://m.homeinns.com";
                } else if (/iPad/i.test(navigator.userAgent)) {
                    window.location.href = "http://m.homeinns.com";
                }
            } catch (e) { }
        }
    }
    //平台、设备和操作系统
    var system = {
        win: false,
        mac: false,
        xll: false
    };
    //检测平台
    var p = navigator.platform;
    system.win = p.indexOf("Win") == 0;
    system.mac = p.indexOf("Mac") == 0;
    system.x11 = (p == "X11") || (p.indexOf("Linux") == 0);
    //跳转语句
    if (system.win || system.mac || system.xll) {//转向后台登陆页面
    } else {
        window.location.href = "http://m.homeinns.com";
    }
}
*/
//-------------------------------------------