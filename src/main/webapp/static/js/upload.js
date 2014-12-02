String.prototype.endsWith = function(suffix) {
    return this.indexOf(suffix, this.length - suffix.length) !== -1;
};

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

function viewFile(file, img, bgContainer) {
    // 通过file.size可以取得图片大小
    var reader = new FileReader();
    reader.onload = function(evt) {
        $(img).attr("src", evt.target.result);
        // bgContainer.css("background-image","url("+evt.target.result+")");
    }
    reader.readAsDataURL(file);
}
// Prevent the default action when a file is dropped on the window
$(document).on('drop dragover', function(e) {
    e.preventDefault();
});

function showLinks() {
    var length = $(".img-selected.uploaded").size();

    if (length > 0) {
        $(".count-result").text(length);
        $(".uploader-links-container").removeClass("hide");

        $(".uploader").removeClass("uploader-to-footer");
        $(".uploader").addClass("uploader-to-links");

        $(".origin-btn").click();
    } else {

        $(".uploader").removeClass("uploader-to-links");
        $(".uploader").addClass("uploader-to-footer");
        $(".uploader-links-container").addClass("hide");
    }
}

$(document)
    .ready(
        function() {

            // $("body").on("copy", "#copy_all_btn", function() {
            // });

            $(".uploader-browse-button").click(function() {
                $(".uploader-form-file").click();
            });

            $(".select-all-btn").click(function() {
                $(".uploaded").each(function() {
                    if ($(this).attr("class").indexOf("img-selected") < 0) {
                        $(this).click();
                    }
                });
            });

            $(".unselect-all-btn").click(function() {
                $(".uploaded").each(function() {
                    if ($(this).attr("class").indexOf("img-selected") >= 0) {
                        $(this).click();
                    }
                });
            });

            $(".origin-btn").click(function() {
                $(this).parent().find("a").removeClass("active");
                $(this).addClass("active");
                var text = "";
                $(".img-selected.uploaded").each(function() {
                    text += $(this).attr("link");
                    text += "\n";
                });

                $("#links-result").text(text);

            });

            $(".html-btn").click(function() {
                $(this).parent().find("a").removeClass("active");
                $(this).addClass("active");
                var text = "";
                $(".img-selected.uploaded").each(function() {
                    text += "<img src='" + $(this).attr("link") + "'>";
                    text += "\n";
                });
                $("#links-result").text(text);

            });

            $(".bb-btn").click(function() {
                $(this).parent().find("a").removeClass("active");
                $(this).addClass("active");
                var text = "";
                $(".img-selected.uploaded").each(function() {
                    text += "[img]" + $(this).attr("link") + "[/img]";
                    text += "\n";
                });
                $("#links-result").text(text);

            });
            
            
            $("#copy_all_btn").click(function(){
                $("#links-result").focus();
                $("#links-result").select();
            });

            $('.uploader-form')
                .fileupload(
                    {
                        // This element will accept file drag/drop uploading
                        dropZone : $('body'),
                        pasteZone : $('body'),
                        singleFileUploads : true,
                        limitMultiFileUploads : 1,

                        // This function is called when a file is added to the
                        // queue;
                        // either via the browse button, or via drag/drop:
                        add : function(e, data) {
                            if (data.files[0].type.indexOf("image") < 0) {
                                alert("请选择图片文件！");
                                return;
                            }

                            var tpl = $('<div class="img-float thumbnail"><img class="thumb" alt=""><div class="name-div"></div><div class="progress-div"></div></div></div>');

                            // Append the file name and file size
                            if (!data.files[0].name) {
                                data.files[0].name = '剪贴板.png';
                                data.files[0].type = 'image/png';
                            }
                            tpl.find('.name-div').text(data.files[0].name);
                            var img = tpl.find("img").eq(0);
                            // viewFile(data.files[0], img, tpl);

                            // 通过file.size可以取得图片大小
                            var reader = new FileReader();
                            reader.onload = function(evt) {
                                var base64 = evt.target.result;
                                $(img).attr("src", base64);

                                // Add the HTML to the UL element
                                data.context = tpl.appendTo($("#fileList"));
                                tpl.find('.name-div').css("width", $(img).width());

                                // Automatically upload the file once it is
                                // added to
                                // the queue

                                var jqXHR = data.submit();
                                // bgContainer.css("background-image","url("+evt.target.result+")");
                                tpl.addClass("uploading");
                                $(".uploader-div").addClass("uploader-div-top");
                            }
                            reader.readAsDataURL(data.files[0]);
                        },
                        progress : function(e, data) {
                            // Calculate the completion percentage of the upload
                            var progress = parseInt(data.loaded / data.total * 100, 10);

                            data.context.find("progress-div").css("width", (100 - progress) + "%");
                            if (progress == 100) {
                                data.context.find('label').eq(0).html("上传成功, 正在生成链接...");
                            }
                        },

                        fail : function(e, data) {
                            // Something has gone wrong!
                            data.context.addClass('error');
                            alert("上传失败:" + data.context.find(".name-div").text());
                        },
                        done : function(e, data) {
                            if (data.result.status != "success") {
                                alert("上传失败:" + data.context.find(".name-div").text() + "," + data.result.message);
                                return;
                            }
                            var filename = data.result.message;
                            var prefix = "";
                            filename = prefix + filename;

                            data.context.find(".progress-div").css("width", (100 - 100) + "%");
                            data.context.removeClass("uploading");
                            data.context.addClass("uploaded");

                            var link = window.location.protocol + "//" + window.location.host + "/" + filename;

                            data.context.find('img').attr("src", link);

                            data.context.attr("link", link);
                            $(".thumbnail").unbind("click");
                            $(".thumbnail").click(function() {
                                if ($(this).attr("class").indexOf("img-selected") < 0) {
                                    $(this).addClass("img-selected");
                                } else {
                                    $(this).removeClass("img-selected");
                                }
                                showLinks();
                            });
                        }

                    });

        });