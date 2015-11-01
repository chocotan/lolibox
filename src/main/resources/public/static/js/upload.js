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

function getPrefix(){
    if($("#simpleUrlMode").get(0).checked){
        return window.location.protocol + "//" + window.location.host;    
    }
    
    if($("#cdnHost").val()&&$("#cdnHost").val()!="@cdnHost@"){
        return $("#cdnHost").val();
    }
    return window.location.protocol + "//" + window.location.host;
}

var count=0;
$(document)
    .ready(
        function() {
            if((!$("#cdnHost").val())||$("#cdnHost").val()=="@cdnHost@"){
                $("#urlModeGroup").hide();
            }else{
                $(".urlMode").change(function(){
                    if($(".origin-btn").hasClass("active")){
                        $(".origin-btn").click();
                    }
                    if($(".html-btn").hasClass("active")){
                        $(".html-btn").click();
                    }
                    if($(".bb-btn").hasClass("active")){
                        $(".bb-btn").click();
                    }
                });
            }
            
            
            $("#copy_all_btn").click(function(){
                $("#links-result").focus();
                $("#links-result").select();
            });
            
            var client = new ZeroClipboard($("#copy_all_btn"));
            client.on("copy", function(event) {
                var clipboard = event.clipboardData;
                clipboard.setData("text/plain", $("#links-result").text());
            });
            client.on("aftercopy", function(event) {
                $("#links-result").focus();
                $("#links-result").select();
            });
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
                var prefix = getPrefix();
                var text = "";
                $(".img-selected.uploaded").each(function() {
                    text += prefix+"/"+$(this).attr("filename");
                    text += "\n";
                });

                $("#links-result").text(text);

            });

            $(".html-btn").click(function() {
                $(this).parent().find("a").removeClass("active");
                $(this).addClass("active");
                var prefix = getPrefix();
                var text = "";
                $(".img-selected.uploaded").each(function() {
                    text += "<img src='" + prefix+"/"+ $(this).attr("filename") + "'>";
                    text += "\n";
                });
                $("#links-result").text(text);

            });

            $(".bb-btn").click(function() {
                $(this).parent().find("a").removeClass("active");
                $(this).addClass("active");
                var text = "";
                var prefix = getPrefix();
                $(".img-selected.uploaded").each(function() {
                    text += "[img]"+ prefix+"/" + $(this).attr("filename") + "[/img]";
                    text += "\n";
                });
                $("#links-result").text(text);

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
                            data.context = tpl.appendTo($("#fileList"));

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
                                tpl.find('.name-div').hide();
                                tpl.find('.name-div').css("width", $(img).width());
                                tpl.find('.name-div').show();
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
                            data.context.find(".progress-div").css("width", (100 - progress) + "%");
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

                            var link = getPrefix()  + "/" + filename;
                            var oriLink = window.location.protocol + "//" + window.location.host + "/" + filename;
                            data.context.find('img').attr("src", oriLink);

                            data.context.attr("link", link);
                            data.context.attr("filename", filename);
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