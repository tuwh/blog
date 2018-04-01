pageInit();
var jsonData = {
    articleId: 0
};
var xh = null;

function pageInit() {

    /*xh = $('#editor').xheditor({
        plugins: getCodePlugin(),
        layerShadow: 0,
        tools: "Bold,Italic,Underline,Strikethrough,Fontface,Blocktag,FontSize,FontColor,BackColor,|,Align,List,Outdent,Indent,|,Img,Table,Emot,Link,Unlink,Code,|,CsdnFullScreen",
        upImgUrl: "/UploadImage",
        width: 971,
        height: 560,
        upImgExt: 'jpg,jpeg,gif,png,bmp',
        skin: "fashion",
        loadCSS: xhCssUrl
    });*/

    //tags_init();


    $("#selType").change(function() {
        //if (this.value == "2") {
        //  $("#chkHomeDiv").hide();
        //} else {
        //  $("#chkHomeDiv").show();
        //}

        if (this.value == "1") {
            $("#panleCopyright").show();
        } else {
            $("#panleCopyright").hide();
        }
    });
    window.onbeforeunload = function() {
        if (showConfirm) {
            if (csdn.val2("editor").replace(/<.+?>/g, "").length > 0) {
                try {
                    return "您的文章尚未保存！";
                } catch (err) {}
            }
        }
    };

    $("#imagecode,#seeagain").click(function() {
        checkcodeRefesh();
    });

    var articlepanleid = 0;

}


function checkcodeRefesh() {
    setTimeout(function() {
        var imagecode = $("#imagecode");
        if (imagecode.length > 0) {
            $("#imagecode").attr("src", "/image/index?r=" + Math.random());
        }
    }, 500);
}

function topNote(text) {
    var div = document.createElement("div");
    div.id = "top_note";
    div.className = "radius";
    div.innerHTML = text;
    document.body.appendChild(div);
    $("#txtTitle").blur();
    $(".btn_area_1 input").attr("disabled", true);
}
//插入代码
function getCodePlugin() {
    var codeArr1 = ["html", "objc", "javascript", "css", "php", "csharp", "cpp", "java", "python", "ruby", "vb", "delphi", "sql", "plain"];
    var codeArr2 = ["HTML/XML", "Objective-C", "JavaScript", "CSS", "PHP", "C#", "C++", "Java", "Python", "Ruby", "Visual Basic", "Delphi", "SQL", "其它"];
    var opts = '';
    for (var i = 0; i < codeArr1.length; i++) {
        // opts += $.format('<option value="{0}">{1}</option>', codeArr1[i], codeArr2[i]);
    }
    var htmlCode = '<div>编程语言: <select id="xheCodeType">' + opts + '</select></div>' +
        '<div><textarea id="xheCodeCon" rows=6 cols=40></textarea></div>' +
        '<div style="text-align:right;"><input type="button" id="xheSave" value="确定" /></div>';

    var codePlugin = {
        Code: {
            c: 'Code',
            t: '插入代码',
            h: 1,
            e: function() {
                var _this = this;
                var jTest = $(htmlCode);
                var jSave = $('#xheSave', jTest);
                jSave.click(function() {
                    var sel_code = $("#xheCodeType").val();
                    //var str = csdn.format('<pre name="code" class="{0}">{1}</pre><br />', sel_code, _this.domEncode($("#xheCodeCon").val()));
                    var xheCodeConContent = $("#xheCodeCon").val() + "";
                    xheCodeConContent = xheCodeConContent.replace(/&reg/g, "&amp;reg").replace(/&copy/g, "&amp;copy");
                    var pNode = _this.getParent('p');
                    var str = '<pre name="code" class="' + sel_code + '">' + _this.domEncode(xheCodeConContent) + '</pre><br />';
                    _this.loadBookmark();
                    if (pNode.length > 0) {
                        pNode.after(str);
                        if (!pNode.text().length > 0) {
                            pNode.remove();
                        }
                    } else {
                        _this.pasteHTML(str);
                    }
                    _this.hidePanel();
                    document.cookie = "postedit_code=" + sel_code + "; expires=" + function() {
                        var d = new Date();
                        d.setFullYear(d.getFullYear() + 1);
                        return d.toGMTString();
                    }();
                    return false;
                });
                _this.saveBookmark();
                _this.showDialog(jTest);
                var _his_code = document.cookie.match(new RegExp("(^|\s)postedit_code=([^;]*)(;|$)"));
                if (_his_code) $('#xheCodeType').val(_his_code);
                else $("#xheCodeType option")[0].selected = true;
            }
        },
        /*ToMarkdown: {
            c: 'ToMarkdown',
            t: '切换Markdown编辑器',
            i: function() {

            },
            e: function() {
                var _this = $(this);
                openSwitchMdMode();
            }
        },*/
       /* CsdnFullScreen: {
            c: 'CsdnFullScreen',
            t: '切换全屏',
            e: function() {
                var _thisBtn = $("#xhe0_Tool").find("a[cmd='CsdnFullScreen']");
                var contentBody = $("#content-body");
                if (!_thisBtn.hasClass("xheActive")) {
                    _thisBtn.addClass("xheActive");
                    $("body").addClass("ed_fullscreen");
                    $("div.suspension-edit-box").removeClass("d-none");
                } else {
                    _thisBtn.removeClass("xheActive");
                    $("body").removeClass("ed_fullscreen");
                    $("div.suspension-edit-box").addClass("d-none");
                }
            }
        }*/
    };
    //codePlugin.SCode.showPop();
    return codePlugin;
}










