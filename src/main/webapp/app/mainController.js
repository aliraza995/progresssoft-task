angular.module('ps.services',[]);
angular.module('ps.controllers',[]);
angular.module('ps.controllers').directive("required", function() {
    return {
        restrict: 'A', //only want it triggered for attributes
        compile: function(element) {
            //could add a check to make sure it's an input element if need be
            element.after("<span class='required'>*</span>");
        }
    }
});
angular.module('ps.controllers').directive("requiredField", function() {
    return {
        restrict: 'A', //only want it triggered for attributes
        compile: function(element) {
            //could add a check to make sure it's an input element if need be
            element.after("<span class='required'>*</span>");
        }
    }
});
angular.module('ps.controllers').directive("requiredMsg", function($translate) {
    return {
        restrict: 'A', //only want it triggered for attributes
        compile: function(element) {
            //could add a check to make sure it's an input element if need be
            $translate('Required_Field_Msg').then(function (reqFieldMsg) {
                element.after("<div style='font-size:11px; margin-left:30px; margin-top:10px; float:left; color: #c9302c;'>" + reqFieldMsg + "</div>");
            });

        }
    }
});
angular.module('ps.controllers').directive("requiredScript", function() {
    return {
        restrict: 'A', //only want it triggered for attributes
        compile: function(element) {
            //could add a check to make sure it's an input element if need be
            element.after("<span class='required-script'>*</span>");
        }
    }
});
angular.module('ps.controllers').directive("removeActiveClass", function() {
    var linkFunction = function (scope, element, attributes) {
        $(element).on("click", function () {
            $("#errors .modal-body").children().removeClass("activeError");
        });
    };
    return {
        restrict: "A",
        link: linkFunction
    };
});

angular.module('ps.controllers').directive("addActiveClass", function() {
    var linkFunction = function (scope, element, attributes) {
        $(element).on("click", function () {
            var el = $(element).closest('div[class^="modal-body"]');
            el.children().addClass("activeError");
            el.removeClass("activeError");
            var pos = el.position().top + el.parent().scrollTop();
            $('#errors').animate({scrollTop: pos}, 800);
        });
    };
    return {
        restrict: "A",
        link: linkFunction
    };
});

angular.module('ps.controllers').directive("scrollToError", function() {
    var linkFunction = function (scope, element, attributes) {
        $(element).on("click", function () {
            var el = $("#errors .ERROR");
            if (typeof el != 'undefined' && typeof el.position() != 'undefined') {
                var pos = el.offset().top + $("#error-list").scrollTop();
                $('#errors .modal-body').animate({scrollTop: pos - 100}, 800);
            }
        });
    };
    return {
        restrict: "A",
        link: linkFunction
    };
});

angular.module('ps.controllers').directive("scrollToWarning", function() {
    var linkFunction = function (scope, element, attributes) {
        $(element).on("click", function () {
            var el = $("#errors .WARNING");
            if (typeof el != 'undefined' && typeof el.position() != 'undefined') {
                var pos = el.offset().top  + $("#error-list").scrollTop();
                $('#errors .modal-body').animate({scrollTop: pos - 100}, 800);
            }
        });
    };
    return {
        restrict: "A",
        link: linkFunction
    };
});

angular.module('ps.controllers').directive("scrollToInfo", function() {
    var linkFunction = function (scope, element, attributes) {
        $(element).on("click", function () {
            var el = $("#errors .INFO");
            if (typeof el != 'undefined' && typeof el.position() != 'undefined') {
                var pos = el.offset().top + $("#error-list").scrollTop();
                $('#errors .modal-body').animate({scrollTop: pos - 100}, 800);
            }
        });
    };
    return {
        restrict: "A",
        link: linkFunction
    };
});

angular.module('ps.controllers').directive("scrollToCritical", function() {
    var linkFunction = function (scope, element, attributes) {
        $(element).on("click", function () {
            var el = $("#errors .CRITICAL");
            if (typeof el != 'undefined' && typeof el.position() != 'undefined') {
                var pos = el.offset().top + $("#error-list").scrollTop();
                $('#errors .modal-body').animate({scrollTop: pos - 100}, 800);
            }
        });
    };
    return {
        restrict: "A",
        link: linkFunction
    };
});

angular.module('ps.controllers').directive('spinnerLoading', ['$http','$timeout',function ($http,$timeout) {
        return {
            restrict: 'A',
            link: function (scope, element, attrs) {
                scope.isLoading = function () {
                    return $http.pendingRequests.length > 1;
                };
                scope.$watch(scope.isLoading, function (value) {
                    $timeout(function() {
                        var element =$('#waitingDialog');
                        if (value) {
                            element.modal('show');
                        } else {
                            element.modal('hide');
                        }
                    });
                });
            }
        };
}]);

angular.module('ps.controllers').directive('ace', ['$timeout', function ($timeout) {

    var resizeEditor = function (editor, elem) {
        var lineHeight = editor.renderer.lineHeight;
        var rows = editor.getSession().getLength();

        $(elem).height(rows * lineHeight);
        editor.resize();
    };

    return {
        restrict: 'A',
        require: '?ngModel',
        scope: true,
        link: function (scope, elem, attrs, ngModel) {
            var node = elem[0];

            var editor = ace.edit(node);

            editor.setTheme('ace/theme/xcode');
            editor.getSession().setMode('ace/mode/xml');

            // set editor options
            editor.setShowPrintMargin(false);
 
            // data binding to ngModel
            ngModel.$render = function () {
                editor.setValue(ngModel.$viewValue);
                resizeEditor(editor, elem);
            };

            editor.on('change', function () {
                $timeout(function () {
                    scope.$apply(function () {
                        var value = editor.getValue();
                        ngModel.$setViewValue(value);
                    });
                });

                resizeEditor(editor, elem);
            });
        }
    };
}]);
angular.module('ps.services').service('UtilService',function($rootScope){
    return {
        updateEndDate : function() {
            console.log("update");
            $rootScope.$broadcast('valid-end-date-changed');
        },
        updateStartDate : function() {
            $rootScope.$broadcast('valid-start-date-changed');
        },
        beforeRenderStartDate : function($view, $dates, endDate) {
            if (endDate) {
                var activeDate = moment(endDate);
                for (var i = 0; i < $dates.length; i++) {
                    if ($dates[i].localDateValue() >= activeDate.valueOf()) $dates[i].selectable = false;
                }
            }
        },
        beforeRenderEndDate : function($view, $dates, startDate) {
            if (startDate) {
                var activeDate = moment(startDate).subtract(1, $view).add(1, 'minute');
                for (var i = 0; i < $dates.length; i++) {
                    if ($dates[i].localDateValue() <= activeDate.valueOf()) {
                        $dates[i].selectable = false;
                    }
                }
            }
        }
    };
});
angular.module('ps.services').filter('stringFormat', function () {

    // function _toFormattedString is based on String.js from http://ajaxcontroltoolkit.codeplex.com/SourceControl/latest#Client/MicrosoftAjax/Extensions/String.js
    // as seen in http://stackoverflow.com/questions/2534803/string-format-in-javascript
    function toFormattedString(useLocale, format, values) {
        var result = '';

        for (var i = 0; ; ) {
            // Find the next opening or closing brace
            var open = format.indexOf('{', i);
            var close = format.indexOf('}', i);
            if ((open < 0) && (close < 0)) {
                // Not found: copy the end of the string and break
                result += format.slice(i);
                break;
            }
            if ((close > 0) && ((close < open) || (open < 0))) {

                if (format.charAt(close + 1) !== '}') {
                    throw new Error('format stringFormatBraceMismatch');
                }

                result += format.slice(i, close + 1);
                i = close + 2;
                continue;
            }

            // Copy the string before the brace
            result += format.slice(i, open);
            i = open + 1;

            // Check for double braces (which display as one and are not arguments)
            if (format.charAt(i) === '{') {
                result += '{';
                i++;
                continue;
            }

            if (close < 0) throw new Error('format stringFormatBraceMismatch');
            
            // Find the closing brace

            // Get the string between the braces, and split it around the ':' (if any)
            var brace = format.substring(i, close);
            var colonIndex = brace.indexOf(':');
            var argNumber = parseInt((colonIndex < 0) ? brace : brace.substring(0, colonIndex), 10);

            if (isNaN(argNumber)) throw new Error('format stringFormatInvalid');

            var argFormat = (colonIndex < 0) ? '' : brace.substring(colonIndex + 1);

            var arg = values[argNumber];
            if (typeof (arg) === "undefined" || arg === null) {
                arg = '';
            }

            // If it has a toFormattedString method, call it.  Otherwise, call toString()
            if (arg.toFormattedString) {
                result += arg.toFormattedString(argFormat);
            } else if (useLocale && arg.localeFormat) {
                result += arg.localeFormat(argFormat);
            } else if (arg.format) {
                result += arg.format(argFormat);
            } else
                result += arg.toString();

            i = close + 1;
        }

        return result;
    };

    return function (/*string*/template, /*array*/values) {
        if (!values || !values.length || !template) {
            return template;
        }
        return toFormattedString(false, template, values);
    };
});


