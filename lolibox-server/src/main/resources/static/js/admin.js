String.prototype.endsWith = function (suffix) {
    return this.indexOf(suffix, this.length - suffix.length) !== -1;
};

// 扩展Date的format方法
Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "H+": this.getHours(),
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

// Helper function that formats the file sizes
function formatFileSize(bytes) {
    if (typeof bytes !== 'number') {
        return '';
    }

    if (bytes >= 1000000000) {
        return (bytes / 1000000000).toFixed(2) + ' GB';
    }

    if (bytes >= 1000000) {
        return (bytes / 1000000).toFixed(2) + ' MB';
    }

    return (bytes / 1000).toFixed(2) + ' KB';
}

var year = null;
var month = null;
var day = null;

$(document).ready(function () {
    var App = {
        Models: {},
        Routers: {},
        Collections: {},
        Views: {}
    };

    App.Routers.Main = Backbone.Router.extend({

        // Hash maps for routes
        routes: {
            "": "index",
            "images/": "index",
            "images/:year": "getImages",
            "images/:year/:month": "getImages",
            "images/:year/:month/:day": "getImages"
        },

        index: function () {
            getImagesByDate();
        },

        getImages: function (year, month, day) {
            getImagesByDate(year, month, day);
        }

    });
    var router = new App.Routers.Main();
    Backbone.history.start();

});

function fileSizeFormat(size) {
    var kb = 1024;
    var mb = 1024 * 1024;
    if (size < 1024) {
        return size.toFixed(2) + "B";
    }
    if (size < mb) {
        return (size / kb).toFixed(2) + "KB";
    }
    if (size > mb) {
        return (size / mb).toFixed(2) + "MB";
    }
    return "";

}

function fileDateFormat(date) {
    return new Date(date).format("yyyy-MM-dd HH:mm:ss");
}

function getFileLink(name) {
    if (!name) {
        if (year) {
            if (month) {
                if (day) {
                    return year + "/" + month;
                }
                return year;
            }
            return "";
        }
        return "";
    }
    if (!year) {
        return name;
    } else if (!month) {
        return year + "/" + name;
    } else if (!day) {
        return year + "/" + month + "/" + name;
    }
}

function processResult(result) {
    var size = result.length;
    var html = "";
    html += "<tr>";
    html += "<td class='name-td'>" + "<a href='#/images/" + getFileLink()
        + "'>" + ".." + "</a>" + "</td><td></td><td></td><td></td>";
    html += "</tr>";
    for (var i = 0; i < result.length; i++) {
        html += "<tr class='file-tr'>";
        if (!result[i].file) {
            // if is folder
            html += "<td class='name-td folder-td'>" + "<a href='#/images/"
                + getFileLink(result[i].name) + "'>" + result[i].name
                + "</a>" + "</td>";
        } else {
            // if is file
            html += "<td class='name-td file-td'>"
                + "<a class='imgLink' url='" + result[i].url + "' href='javascript:void(0)' onclick='showImage(this)'>"
                + result[i].name + "</a>" + "</td>";
        }
        html += "<td>"
            + (result[i].size == 0 ? "" : fileSizeFormat(result[i].size))
            + "</td>";
        html += "<td>" + fileDateFormat(result[i].lastModified) + "</td>";
        html += "<td></td>";
        html += "</tr>";
    }
    $("#file-list").html(html);
}

var currentShow;

function showImage(obj) {
    currentShow = obj;
    $('#preview-modal').modal();
    var path = "/images/" + $(obj).attr("url");
    $("#preview-body").html("<img class='preview-img' src='" + path + "'>");
}

function nextImage() {
    if ($(currentShow).parent().parent().next().find(".imgLink").length==0) {
        $.notify("已经是最后一张图片了 !", "info");
        return;
    }
    showImage($(currentShow).parent().parent().next().find(".imgLink"));
}

function lastImage() {
    if ($(currentShow).parent().parent().prev().find(".imgLink").length==0) {
        $.notify("已经是第一张图片了 !", "info");
        return;
    }
    showImage($(currentShow).parent().parent().prev().find(".imgLink"));
}

function deleteImage() {

    var imgObj = $(currentShow);
    var image = imgObj.attr("url");
    $.ajax({
        url: '/admin/delete',
        type: 'POST',
        data: {
            year: year,
            month: month,
            day: day,
            name: image
        },
        contentType: "application/x-www-form-urlencoded",
        async: true,
        success: function (response, textStatus) {
            if (response.status == "success") {
                $.notify("删除成功", "success");
                getImagesByDate(year, month, day);
                nextImage();
            } else {
                $.notify(response.message, "error");
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
}

function getImagesByDate(myear, mmonth, mday) {
    if (mday) {
        if (myear) {
            year = myear;
        } else {
            year = null;
            month = null;
            day = null;
        }
        if (mmonth) {
            month = mmonth;
        } else {
            month = null;
            day = null;
        }
        if (mday) {
            day = mday;
        } else {
            day = null;
        }

    } else {
        year = myear;
        month = mmonth;
        day = null;
    }

    list(myear, mmonth, mday);
}

function list(year, month, day, func) {
    if (!year && !month && !day) {
        $("#current-path").text("当前位置: /");
        $.get("/admin/list", {}, function (result) {
            processResult(result);
            if (func)
                func();
        });
    } else if (year && !month && !day) {
        $("#current-path").text("当前位置: /" + year);
        $.get("/admin/list", {
            year: year
        }, function (result) {
            processResult(result);
            if (func)
                func();
        });
    } else if (year && month && !day) {
        $("#current-path").text("当前位置: /" + year + "/" + month);
        $.get("/admin/list", {
            year: year,
            month: month
        }, function (result) {
            processResult(result);
            if (func)
                func();
        });
    } else if (year && month && day) {
        $("#current-path").text("当前位置: /" + year + "/" + month + "/" + day);
        $.get("/admin/list", {
            year: year,
            month: month,
            day: day
        }, function (result) {
            processResult(result);
            if (func)
                func();
        });
    }

}