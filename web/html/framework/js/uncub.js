$.extend({
    highlighterCode: function (orgCode, language) {
        if (!orgCode) return '';
        orgCode = orgCode.replace(/</g, '&lt;');
        return $.highlighter.highlighterCode(orgCode, language);
    },
    highlighterElem: function (orgCode, language) {
        if (!orgCode) return '';
        orgCode = orgCode.replace(/</g, '&lt;');
        return $.highlighter.highlighterElem(orgCode, language);
    },
    highlighter: SyntaxHighlighter,
    consolelog: function (msg) {
        if (console) {
            console.log(msg)
        }
    }
});

-function ($) {
    if (!$.fn.modal) {
        $.consolelog('本插件依赖bootstrap模块框组件，请导入')
    }
    var html = '<div id="[Id]" class="modal fade" role="dialog" aria-labelledby="modalLabel">' +
        '<div class="modal-dialog modal-sm">' +
        '<div class="modal-content">' +
        '<div class="modal-header ">' +//alert alert-danger
        '<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>' +
        '<h4 class="modal-title" id="modalLabel">[iconHtml] [Title]</h4>' +
        '</div>' +
        '<div class="modal-body">' +
        '<p>[Message]</p>' +
        '</div>' +
        '<div class="modal-footer">' +
        '<button type="button" class="btn btn-default cancel" data-dismiss="modal">[BtnCancel]</button>' +
        '<button type="button" class="btn btn-primary ok" data-dismiss="modal">[BtnOk]</button>' +
        '</div>' +
        '</div>' +
        '</div>' +
        '</div>';
    var dialogdHtml = '<div id="[Id]" class="modal fade" role="dialog" aria-labelledby="modalLabel">' +
        '<div class="modal-dialog">' +
        '<div class="modal-content">' +
        '<div class="modal-header">' +
        '<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>' +
        '<h4 class="modal-title" id="modalLabel">[iconHtml] [Title]</h4>' +
        '</div>' +
        '<div class="modal-body">' +
        '</div>' +
        '</div>' +
        '</div>' +
        '</div>';
    var reg = new RegExp("\\[([^\\[\\]]*?)\\]", 'igm');
    var generateId = function () {
        var date = new Date();
        return 'mdl' + date.valueOf();
    }
    var init = function (options) {
        options = $.extend({}, {
            title: "操作提示",
            message: "提示内容",
            btnok: "确定",
            btncl: "取消",
            width: 200,
            auto: false,
            css: 'margin-top:200px'
        }, options || {});
        var modalId = generateId();
        var content = html.replace(reg, function (node, key) {
            return {
                Id: modalId,
                Title: options.title,
                Message: options.message,
                BtnOk: options.btnok,
                BtnCancel: options.btncl,
                iconHtml: options.iconHtml || ''
            }[key];
        });
        $('body').append(content);
        $('#' + modalId).modal({
            width: options.width,
            backdrop: 'static'
        });
        $('#' + modalId).on('hide.bs.modal', function (e) {
            $('body').find('#' + modalId).remove();
        });
        $('#' + modalId).find('.modal-dialog').attr('style', options.css || '');
        return modalId;
    };

    var getIconHtml = function (level) {
        if (!level) return;
        if (level === 'success') {
            return '<span class="glyphicon glyphicon-ok-sign" style="color: #3c763d;"></span>';
        }
        if (level === 'info') {
            return '<span class="glyphicon glyphicon-info-sign" style="color: #31708f;"></span>';
        }
        if (level === 'error') {
            return '<span class="glyphicon glyphicon-remove-sign" style="color: #a94442;"></span>';
        }
        if (level === 'warn') {
            return '<span class="glyphicon glyphicon-question-sign" style="color: #8a6d3b;"></span>';
        }
    }

    var _alert = function (options) {
        if (typeof options == 'string') {
            options = {
                message: options
            };
        }
        var iconHtml = getIconHtml(options.level || '');
        options.iconHtml = iconHtml;
        var id = init(options);
        var modal = $('#' + id);
        modal.find('.ok').removeClass('btn-success').addClass('btn-primary');
        modal.find('.cancel').hide();

        return {
            id: id,
            on: function () {
                if (callback && callback instanceof Function) {
                    modal.find('.ok').click(function () {
                        callback(true);
                    });
                }
            },
            hide: function (callback) {
                if (callback && callback instanceof Function) {
                    modal.on('hide.bs.modal', function (e) {
                        callback(e);
                    });
                }
            }
        };
    };
    /**
     * 确认框
     * @param options
     * @returns {{id, on: on, hide: hide}}
     */
    $.fn.confirm = function (options) {
        var options = options || {};
        // options.level = options.level || 'info';
        options.iconHtml = getIconHtml(options.level);
        var id = init(options);
        var modal = $('#' + id);
        modal.find('.ok').removeClass('btn-primary').addClass('btn-success');
        modal.find('.cancel').show();
        return {
            id: id,
            on: function (callback) {
                if (callback && callback instanceof Function) {
                    modal.find('.ok').click(function () {
                        callback(true);
                    });
                    modal.find('.cancel').click(function () {
                        callback(false);
                    });
                }
            },
            hide: function (callback) {
                if (callback && callback instanceof Function) {
                    modal.on('hide.bs.modal', function (e) {
                        callback(e);
                    });
                }
            }
        };
    };
    /**
     * 普通弹出框
     * @param options
     * @returns {{id, on, hide}}
     */
    $.fn.alert = function (options) {
        if (typeof options == 'string') {
            options = {
                message: options
            };
        }
        options.level = "info";
        return _alert(options);
    };
    /**
     * 普通弹出框
     * @param options
     * @returns {{id, on, hide}}
     */
    $.fn.warn = function (options) {
        if (typeof options == 'string') {
            options = {
                message: options
            };
        }
        options.level = "info";
        return _alert(options);
    };
    /**
     * 成功弹出框
     * @param options
     * @returns {{id, on, hide}}
     */
    $.fn.success = function (options) {
        if (typeof options == 'string') {
            options = {
                message: options
            };
        }
        options.level = "sucess";
        return _alert(options);
    };
    /**
     * 危险弹出框
     * @param options
     * @returns {{id, on, hide}}
     */
    $.fn.error = function (options) {
        if (typeof options == 'string') {
            options = {
                message: options
            };
        }
        options.level = "error";
        // options.level
        return _alert(options);
    };
    $.fn.dialog = function (options) {
        options = $.extend({}, {
            title: 'title',
            url: '',
            width: 800,
            height: 550,
            onReady: function () {
            },
            onShown: function (e) {
            }
        }, options || {});
        var modalId = generateId();

        var content = dialogdHtml.replace(reg, function (node, key) {
            return {
                Id: modalId,
                Title: options.title
            }[key];
        });
        $('body').append(content);
        var target = $('#' + modalId);
        target.find('.modal-body').load(options.url);
        if (options.onReady())
            options.onReady.call(target);
        target.modal();
        target.on('shown.bs.modal', function (e) {
            if (options.onReady(e))
                options.onReady.call(target, e);
        });
        target.on('hide.bs.modal', function (e) {
            $('body').find(target).remove();
        });
    }


}(jQuery);


