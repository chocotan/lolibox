/**
 * 删除图片的方法
 *
 * 需要获取页面隐藏域的csrf值传入后台
 * @param shortName
 */
function deleteImage(shortName) {
    layer.confirm('确认要删除该图片吗？', {
        btn: ['是','否'] //按钮
    }, function(){
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
                    window.location.reload();
                } else {
                    layer.msg('删除失败！');
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                layer.error("出错啦，请稍后再试！")
            }
        });
    });
}