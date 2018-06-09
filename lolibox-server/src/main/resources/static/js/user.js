/**
 * 删除图片的方法
 *
 * 需要获取页面隐藏域的csrf值传入后台
 * @param shortName
 */
function deleteImage(shortName) {
    var csrf_token = $("#csrf_token").val();
    var csrf_param = $("#csrf_token").attr("name");
    var params = {};
    params[csrf_param] = csrf_token;
    params['name'] = encodeURI(shortName);
    $.ajax({
        url: '/image/delete',
        type: 'POST',
        data: params,
        dataType: 'json',
        async: true,
        success: function (response, textStatus) {
            if (response.status == "success") {
                // $.notify("删除成功", "success");
                window.location.reload();
            } else {
                $.notify(response.message, "error");
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
}