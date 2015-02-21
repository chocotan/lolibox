String.prototype.endsWith = function(suffix) {
    return this.indexOf(suffix, this.length - suffix.length) !== -1;
};

// 扩展Date的format方法
Date.prototype.format = function(format) {
    var o = {
        "M+" : this.getMonth() + 1,
        "d+" : this.getDate(),
        "H+" : this.getHours(),
        "m+" : this.getMinutes(),
        "s+" : this.getSeconds(),
        "q+" : Math.floor((this.getMonth() + 3) / 3),
        "S" : this.getMilliseconds()
    }
    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for ( var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
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

$(document).ready(function() {
    processHash();
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
    html += "<td class='name-td'>" + "<a href='javascript:window.location.hash=\"#" + getFileLink()
        + "\";processHash()'>" + ".." + "</a>" + "</td><td></td><td></td><td></td>";
    html += "</tr>";
    for (var i = 0; i < result.length; i++) {
        html += "<tr>";
        if (!result[i].file) {
            // if is folder
            html += "<td class='name-td folder-td'>" + "<a href='javascript:window.location.hash=\"#"
                + getFileLink(result[i].name) + "\";processHash()'>" + result[i].name + "</a>" + "</td>";
        } else {
            // if is file
            html += "<td class='name-td file-td'>" + "<a href='javascript:showImage(" + i + ")'>" + result[i].name
                + "</a>" + "</td>";
        }
        html += "<td>" + (result[i].size == 0 ? "" : fileSizeFormat(result[i].size)) + "</td>";
        html += "<td>" + fileDateFormat(result[i].lastModified) + "</td>";
        html += "<td></td>";
        html += "</tr>";
    }
    $("#file-list").html(html);
}

var currentId;

function showImage(id) {
    var imgObj = $("#file-list").find("tr").eq(id + 1);
    var image = imgObj.find("td").eq(0).text();
    $('#preview-modal').modal();
    var path = "/images/" + year + "/" + month + "/" + day + "/" + image;
    $("#preview-body").html("<img class='preview-img' src='" + path + "'>");
    currentId = id;
}


function nextImage() {
    if (currentId + 1 > $("#file-list").find("tr").size()-2) {
        $.notify("已经是最后一张图片了 !", "info");
        return;
    }
    showImage(currentId + 1);
}

function lastImage() {
    if (currentId == 0) {
        $.notify("已经是第一张图片了 !", "info");
        return;
    }
    showImage(currentId - 1);
}

function deleteImage() {

    var imgObj = $("#file-list").find("tr").eq(currentId + 1);
    var image = imgObj.find("td").eq(0).text();

    $.ajax({
        url : '/admin/delete',
        type : 'POST',
        data : {
            year : year,
            month : month,
            day : day,
            name : image
        },
        contentType : "application/x-www-form-urlencoded",
        async : true,
        success : function(response, textStatus) {
            if (response.status == "success") {
                $.notify("删除成功", "success");
                processHash(function() {
                    currentId = currentId - 1;
                    nextImage();
                });

            } else {
                $.notify(response.message, "error");
            }
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
        }
    });
}

function processHash(func) {
    var hash = window.location.hash;
    if (hash) {
        hash = hash.substring(1);
        if (hash.indexOf("/") >= 0) {
            var strs = hash.split("/");
            if (strs[0]) {
                year = strs[0];
            } else {
                year = null;
                month = null;
                day = null;
            }
            if (strs[1]) {
                month = strs[1];
            } else {
                month = null;
                day = null;
            }
            if (strs[2]) {
                day = strs[2];
            } else {
                day = null;
            }

        } else {
            year = hash;
            month = null;
            day = null;
        }

    } else {
        year = null;
        month = null;
        day = null;
    }
    list(year, month, day, func);
}
function list(year, month, day, func) {
    if (!year && !month && !day) {
        $("#current-path").text("当前位置: /");
        $.get("/admin/list", {}, function(result) {
            processResult(result);
            if (func)
                func();
        });
    } else if (year && !month && !day) {
        $("#current-path").text("当前位置: /" + year);
        $.get("/admin/list", {
            year : year
        }, function(result) {
            processResult(result);
            if (func)
                func();
        });
    } else if (year && month && !day) {
        $("#current-path").text("当前位置: /" + year + "/" + month);
        $.get("/admin/list", {
            year : year,
            month : month
        }, function(result) {
            processResult(result);
            if (func)
                func();
        });
    } else if (year && month && day) {
        $("#current-path").text("当前位置: /" + year + "/" + month + "/" + day);
        $.get("/admin/list", {
            year : year,
            month : month,
            day : day
        }, function(result) {
            processResult(result);
            if (func)
                func();
        });
    }

}